package crwaler.bean;

/**
 * 方便创建URL对象，默认limit是5条每页，偏移量是0
 */
public class ZhihuUrl {
    //url地址模板
    static String urlMode = "https://www.zhihu.com/api/v4/questions/questionId/answers?include=data%5B%2A%5D.is_normal%2Cadmin_closed_comment%2Creward_info%2Cis_collapsed%2Cannotation_action%2Cannotation_detail%2Ccollapse_reason%2Cis_sticky%2Ccollapsed_by%2Csuggest_edit%2Ccomment_count%2Ccan_comment%2Ccontent%2Ceditable_content%2Cvoteup_count%2Creshipment_settings%2Ccomment_permission%2Ccreated_time%2Cupdated_time%2Creview_info%2Crelevant_info%2Cquestion%2Cexcerpt%2Crelationship.is_authorized%2Cis_author%2Cvoting%2Cis_thanked%2Cis_nothelp%3Bdata%5B%2A%5D.mark_infos%5B%2A%5D.url%3Bdata%5B%2A%5D.author.follower_count%2Cbadge%5B%2A%5D.topics&limit=limitNum&offset=offsetNum&sort_by=default";
    private String address;//返回url地址
    private String questionId;//问题编号
    private Integer offset=0;//偏移量
    private Integer limit=5;//每次显示，测试知乎最大为20

    public  Integer getOffset() {
        return offset;
    }

    public  void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getLimit() {
        return limit;
    }

    public String getQuestionId() {
        return questionId;
    }

    public String getAddress() {
        this.address = urlMode.replace("questionId",questionId).replace("offsetNum",offset.toString()).replace("limitNum",limit.toString());
        return address;
    }

    public ZhihuUrl(String questionId,  Integer limit,Integer offset) {
        this.questionId = questionId;
        this.limit = limit;
        this.offset = offset;
    }
    public ZhihuUrl(String questionId) {
        this.questionId = questionId;//可以只传问题ID，使用默认其他变量
    }

    @Override
    public String toString() {
        return "ZhihuUrl{" +
                "address='" + address + '\'' +
                ", questionId='" + questionId + '\'' +
                ", offset=" + offset +
                ", limit=" + limit +
                '}';
    }

}
