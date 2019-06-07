package crwaler.bean;

import java.util.HashMap;

public class LoginBean {
    public  static HashMap<String,String> loginData;
    static{
        loginData  = new HashMap<>();
        loginData.put("client_id","c3cef7c66a1843f8b3a9e6a1e3160e20");
        loginData.put("grant_type","password");
        loginData.put("timestamp","");
        loginData.put("source","com.zhihu.web");
        loginData.put("password","");
        loginData.put("username","");
        loginData.put("lang","en");
        loginData.put("ref_source","homepage");
        loginData.put("signature","");

    }
}
