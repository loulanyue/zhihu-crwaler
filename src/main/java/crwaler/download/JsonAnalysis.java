package crwaler.download;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import crwaler.MyCrawler;
import crwaler.bean.AnswerContent;
import crwaler.http.HttpService;
import org.apache.log4j.Logger;


import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *传递url并请求获取json分析成List存储，请求下载图片
 */
public class JsonAnalysis {
    static Integer threadNum = 10;//下载图片线程数
    static Pattern FilePattern = Pattern.compile("[\\\\/:*?\"<>|]");//windows创建目录非法字符排除
    private static ExecutorService pool = Executors.newFixedThreadPool(threadNum);
    static Logger log = Logger.getLogger(MyCrawler.class.getName());

    /**
     * 使用线程池下载List列表中每个回答，一个线程下载一个回答下的所有图片
     * @param answers
     */
    public static void downloadContent(List<AnswerContent> answers){
        for(final AnswerContent ans:answers){
            pool.submit(new Runnable(){
                @Override
                public void run() {
                    log.info(Thread.currentThread().getName()+"--下载--="+ans.getAuthor());
                    downloadPic(ans);
                    log.info(Thread.currentThread().getName()+"下载结束");
                }
            });
        }
    }
public static void closeTask(){
    pool.shutdown();
}
    /**
     * 为每一个回答创建目录，并请求图片地址下载。
     * @param content
     */
    public static void downloadPic(AnswerContent content){
        String flag = content.getVoteupCount()+"个点赞~用户-"+content.getAuthor()+"~评论数-"+content.getCommentCount();
        File path = new File(MyCrawler.base + "/"+MyCrawler.questionTitle+"/"+flag);
        if(!path.exists()){
            log.debug(Thread.currentThread().getName()+"创建目录"+path);
            path.mkdirs();
        }
        for(String pic:content.getPictures()){
            HttpService.downloadEachPic(flag,pic);
        }
    }

    /**
     * 请求url解析json为List
     * @param url
     * @return 解析后的List
     */
    public static List<AnswerContent> getContent(String url){
        Pattern contentPattern = Pattern.compile("data-original=\\\"(https://.*?.zhimg.com.*?_r.jpg)");//图片地址匹配
        JSONArray array = HttpService.httpGetAfterLogin(url).getJSONArray("data");
        List<AnswerContent> result = new ArrayList<>();
        for(int i=0;i<array.size();i++){
            JSONObject json = array.getJSONObject(i);
            Matcher content = contentPattern.matcher(json.getString("content"));
            AnswerContent answerContent = new AnswerContent();
            if(content.find()){
                content.reset();
                Set<String> pictures = new HashSet<>();
                while(content.find()){
                    pictures.add(content.group(1));
                }
                answerContent.setPictures(pictures);
            }else{
                continue;//如果没有图片，则跳过这个回答
            }
            answerContent.setAuthor(json.getJSONObject("author").getString("name"));
            answerContent.setId(json.getString("id"));
            answerContent.setVoteupCount(json.getString("voteup_count"));
            answerContent.setCommentCount(json.getString("comment_count"));
            result.add(answerContent);
        }
        return result;
    }


    /**
     * 获取该问题回答总数，以用于遍历所有回答
     * @param url
     * @return
     */
    public static Integer getCount(String url){
        Integer count=0;
        JSONObject json= HttpService.httpGetAfterLogin(url);
        if(json!=null){
            count = json.getJSONObject("paging").getInteger("totals");
        }
        return count;
    }

    /**
     * 获取问题标题，为爬取多个问题分别创建目录
     * @param url
     * @return
     */
    public  static String getTitle(String url){
        String title = "";
        JSONObject json= HttpService.httpGetAfterLogin(url);
        if(json!=null){
            title = json.getJSONArray("data").getJSONObject(0).getJSONObject("question").getString("title");
        }
        return title==null?null:FilePattern.matcher(title).replaceAll("");//非法字符排除
    }
}
