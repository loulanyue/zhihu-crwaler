package crwaler.http;

import com.alibaba.fastjson.JSONObject;
import crwaler.MyCrawler;
import crwaler.bean.HeaderBean;
import org.apache.http.*;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 处理有关http请求的部分
 */
public class HttpService {
    static Logger log = Logger.getLogger(HttpService.class.getName());
    static CookieStore cookieStore = new BasicCookieStore();
    static CloseableHttpClient client = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
    static RequestConfig defaultConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build();


    /**
     * 请求url并返回解析成json对象
     *
     * @param url 请求url
     * @return
     */
/*    public static JSONObject httpGet(String url) {
        JSONObject jsonResult = null;
        try {
            HttpGet request = new HttpGet(url);
            CloseableHttpResponse response = client.execute(request);
            System.out.println(response.getStatusLine());
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String strResult = EntityUtils.toString(response.getEntity());
                System.out.println(strResult);
                jsonResult = JSONObject.parseObject(strResult);
                System.out.println(jsonResult);
            }
        } catch (IOException e) {
            log.error("请求地址失败："+url+" 错误信息："+e.getMessage());
        }
        return jsonResult;
    }*/
    public static JSONObject httpGetCaptcha(String url, HashMap<String, String> headers) {
        JSONObject putResult = null;
        HttpGet get = new HttpGet(url);
        HttpPut put = new HttpPut(url);
        get.setConfig(defaultConfig);
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            get.addHeader(entry.getKey(), entry.getValue());
        }
        try {
            HttpResponse getResp = client.execute(get);
            String getStr = EntityUtils.toString(getResp.getEntity());
            JSONObject getResult = JSONObject.parseObject(getStr);
            if (getResult != null && getResult.getString("show_captcha") == "true") {
                log.debug("准备获取验证码");
                for (Map.Entry<String, String> entry : HeaderBean.headers.entrySet()) {
                    put.addHeader(entry.getKey(), entry.getValue());
                }
                HttpResponse putResp = client.execute(put);
                String putStr = EntityUtils.toString(putResp.getEntity());
                putResult = JSONObject.parseObject(putStr);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return putResult;
    }

    /**
     * 请求图片地址并下载
     *
     * @param flag
     * @param url
     */
    public static void downloadEachPic(String flag, String url) {
        String path = MyCrawler.base + "/" + MyCrawler.questionTitle + "/" + flag + "/" + url.substring(23);//url中的图片名称命名
        try {
            HttpGet request = new HttpGet(url);
            HttpResponse response = client.execute(request);

            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = response.getEntity();
                byte[] data = EntityUtils.toByteArray(entity);
                FileOutputStream fos = new FileOutputStream(path);
                fos.write(data);
                fos.close();
                log.debug(Thread.currentThread().getName() + "下载文件:" + path + "成功");
            }
        } catch (IOException e) {
            log.error("下载图片失败：" + url + " 错误信息：" + e.getMessage());
        }
    }


    public static JSONObject httpGetAfterLogin(String url) {
        JSONObject jsonResult = null;
        HttpGet get = new HttpGet(url);
        get.setConfig(defaultConfig);
        HttpResponse resp = null;
        try {
            resp = client.execute(get);
            if (resp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                log.debug("已登陆，请求网页成功：" + url);
                String strResult = EntityUtils.toString(resp.getEntity());
                jsonResult = JSONObject.parseObject(strResult);
            }
        } catch (IOException e) {
            log.error("已登陆，请求网页失败：" + url);
        }
        return jsonResult;
    }

    /**
     * 需要三次http请求，一次get获取是否要验证码，一次put获得验证码，一次post传递验证码
     *
     * @param url
     * @param params
     * @param headers
     * @return
     */
    public static boolean httpPostLogin(String url, Map<String, String> params, Map<String, String> headers) {
        JSONObject jsonResult = null;
        try {
            HttpPost post = new HttpPost(url);
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                String name = entry.getKey();
                String value = entry.getValue();
                post.setHeader(name, value);
            }
            List<BasicNameValuePair> nvps = new ArrayList<>();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                String name = entry.getKey();
                String value = entry.getValue();
                BasicNameValuePair pair = new BasicNameValuePair(name, value);
                nvps.add(new BasicNameValuePair(name, value));
            }
            post.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
            post.setConfig(defaultConfig);
            HttpResponse response = client.execute(post);
            String strResult = EntityUtils.toString(response.getEntity());
            jsonResult = JSONObject.parseObject(strResult);
            if (jsonResult != null && jsonResult.getString("access_token") != null) {
                log.info("请求登陆成功，准备保存cookie");
                CookieUtil.save(cookieStore.getCookies());
                return true;
            }
        } catch (IOException e) {
            log.error("请求登陆失败");
        }
        return false;
    }


    public static JSONObject httpPostCaptcha(String captchaApi, String captcha) {

        JSONObject jsonResult = null;
        HttpPost post = new HttpPost(captchaApi);
        List<BasicNameValuePair> nvps = new ArrayList<>();
        nvps.add(new BasicNameValuePair("input_text", captcha));
        post.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
        for (Map.Entry<String, String> entry : HeaderBean.headers.entrySet()) {
            String name = entry.getKey();
            String value = entry.getValue();
            post.setHeader(name, value);
        }
        try {
            HttpResponse resp = client.execute(post);
            String strResult = EntityUtils.toString(resp.getEntity());
            log.debug("正在获取验证码base64信息");
            jsonResult = JSONObject.parseObject(strResult);
        } catch (IOException e) {
            log.error("获取验证码base64信息失败");
        }
        return jsonResult;
    }

    public static boolean loadCookies() {
        cookieStore.clear();
        List<Cookie> cookies = CookieUtil.load();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                cookieStore.addCookie(cookie);
            }
            log.info("加载cookie成功");
            return true;
        }
        log.info("加载cookie为空");
        return false;
    }


    public static Header[] httpGetHeaders(String checkApi, String headerName) {
        HttpGet get = new HttpGet(checkApi);
        get.setConfig(defaultConfig);
        try {
            HttpResponse resp = client.execute(get);
            Header[] headers = resp.getHeaders(headerName);
            log.debug("获取header" + headers);
            return headers;
        } catch (IOException e) {
            log.debug("获取header失败");
        }
        return null;
    }
}
