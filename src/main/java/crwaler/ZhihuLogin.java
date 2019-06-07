package crwaler;

import com.alibaba.fastjson.JSONObject;
import crwaler.bean.HeaderBean;
import crwaler.bean.LoginBean;
import crwaler.http.HttpService;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.Header;
import org.apache.log4j.Logger;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ZhihuLogin {
    static Logger log = Logger.getLogger(ZhihuLogin.class.getName());
    static String loginApi ="https://www.zhihu.com/api/v3/oauth/sign_in";
    static String checkApi ="https://www.zhihu.com/signup";
    static String selfApi = "https://www.zhihu.com/api/v4/me";
    static String captchaApi = "https://www.zhihu.com/api/v3/oauth/captcha?lang=en";
    static Pattern token = Pattern.compile("_xsrf=([\\w|-]+)");

    /**
     * 第三个参数代表是否加载cookie
     * @param username
     * @param password
     * @param load
     * @return
     */
    public static boolean login(String username, String password, Boolean load){
        if(load&&loadCookies()){
            if(checkLogin()){
                return true;
            }
        }
        HeaderBean.headers.put("x-xsrftoken",getXsrf());
        LoginBean.loginData.put("username",username);
        LoginBean.loginData.put("password",password);
        LoginBean.loginData.put("timestamp",String.valueOf(System.currentTimeMillis()));
        LoginBean.loginData.put("signature",getSignature(LoginBean.loginData));
        String captcha  = getCaptcha();
        if(captcha!=null){
            LoginBean.loginData.put("captcha",captcha);
        }
        boolean result = HttpService.httpPostLogin(loginApi,LoginBean.loginData,HeaderBean.headers);
        return result;
    }


    public static String getXsrf() {
        Header[] headers = HttpService.httpGetHeaders(checkApi,"Set-Cookie");
        if(headers!=null){
            for(Header header:headers){
                Matcher m = token.matcher(header.getValue());
                if(m.find()){
                    return m.group(1);
                }
            }

        }
        return null;
    }

    /**
     * 调用校验接口获取用户名证明已登陆
     * @return
     */
    private static boolean checkLogin() {
        JSONObject resp = HttpService.httpGetAfterLogin(selfApi);
        String name = resp.getString("name");
        if(name!=null){
            log.info("你登陆的账号名是："+name);
            return true;
        }
        return false;
    }

    private static boolean loadCookies() {
        return HttpService.loadCookies();
    }

    private static String getSignature(HashMap<String,String> loginData) {
        String data = loginData.get("grant_type")+loginData.get("client_id")+loginData.get("source")+loginData.get("timestamp");
        String signature ="";
        try {
            signature =  HMACSHA1.HmacSHA1Encrypt(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return signature;
    }

    /**
     * 获取验证码的base64
     * @param headers
     * @return
     */
    private static String getCaptchaBase64(HashMap<String,String> headers) {
        String base64 = "";
        JSONObject json = HttpService.httpGetCaptcha(captchaApi,headers);
        if(json!=null){
            base64 =  json.getString("img_base64");
        }
        return base64;
    }

    /**
     * 解析验证码bas64到图片
     * @return
     */
    private static String getCaptcha(){
        String result = "";
        String imgStr =getCaptchaBase64(HeaderBean.headers);
        //Base64解码
        if(imgStr==null||imgStr==""){
            log.info("验证码为空");
            return null;
        }
        byte[] a = Base64.decodeBase64(imgStr);
        //生成jpeg图片
        try {
            String imgFilePath = "./captcha.jpg";//新生成的图片
            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(a);
            out.flush();
            out.close();
            Desktop.getDesktop().open(new File(imgFilePath));
            Scanner sc = new Scanner(System.in);
            log.info("输入验证码字符：");
            result = sc.nextLine();
            log.info("验证码接收："+result);
            JSONObject json = HttpService.httpPostCaptcha(captchaApi,result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

}
