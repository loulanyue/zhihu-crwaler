package crwaler.http;

import org.apache.http.cookie.Cookie;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.List;

public class CookieUtil {
    static Logger log = Logger.getLogger(CookieUtil.class.getName());
    static final String path = "./data.dat";

    public static List<Cookie> load() {
        List<Cookie> cookie = null;
        try {
            File file = new File(path);
            if (file.exists()) {
                InputStream is = new FileInputStream(file);
                ObjectInputStream objin = new ObjectInputStream(is);
                cookie = (List<Cookie>) objin.readObject();
                log.info("读取cookie成功");
            } else {
                log.info("没有检查到cookie文件，请重新登陆");
            }
        } catch (FileNotFoundException e) {
            log.error(path + " 文件找不到");
        } catch (IOException e) {
            log.error(path + " 文件读取错误");
        } catch (ClassNotFoundException e) {
            log.error(path + " 类型转换错误");
        }
        return cookie;
    }

    public static void save(List<Cookie> cookies) {
        try {
            OutputStream out = new FileOutputStream(new File(path));
            ObjectOutputStream objout = new ObjectOutputStream(out);
            objout.writeObject(cookies);
            objout.flush();
            objout.close();
            log.info("存储cookie成功");
        } catch (IOException e) {
            log.error(" 存储cookie失败");
        }
    }
}
