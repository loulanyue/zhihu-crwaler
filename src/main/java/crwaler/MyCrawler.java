package crwaler;


import crwaler.bean.AnswerContent;
import crwaler.bean.ZhihuUrl;
import crwaler.download.JsonAnalysis;
import org.apache.log4j.Logger;

import java.util.List;

public class MyCrawler {
    public static String  base = "D:/zhihu";//保存图片地址
    public static String questionTitle;//问题标题
    static Integer totals = 50;
    static Logger log = Logger.getLogger(MyCrawler.class.getName());

    public static void main(String[] args) throws CloneNotSupportedException {
            ZhihuUrl url = new ZhihuUrl("294196112",20,0);
            startCrawler(url);

    }

    public static void startCrawler(ZhihuUrl url){
        totals = JsonAnalysis.getCount(url.getAddress());//获取任务总数，也可以使用默认50条回答
        log.debug("下载目标地址："+url.getAddress());
        questionTitle = JsonAnalysis.getTitle(url.getAddress());
        if(totals!=null&&questionTitle!=null){
            log.info("下载问题："+questionTitle+"---------回答总数："+totals);
            Integer limit = url.getLimit();
            for (int i= 0;i*limit<totals;i++){//遍历
                url.setOffset(i*limit);
                List<AnswerContent> jsonResult = JsonAnalysis.getContent(url.getAddress());
                JsonAnalysis.downloadContent(jsonResult);
            }
            JsonAnalysis.closeTask();
        }else {
            log.error("读取答案总数或者问题标题出错");
        }

    }



}
