package crwaler.bean;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class HeaderBean {
    public static HashMap<String,String> headers;
    static{
        headers = new HashMap<>();
        headers.put("Connection","keep-alive");
        headers.put("Host","www.zhihu.com");
        headers.put("Referer","https://www.zhihu.com/");
        headers.put("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.139 Safari/537.36");
        headers.put("authorization","oauth c3cef7c66a1843f8b3a9e6a1e3160e20");
    }
}
