package crwaler.bean;

import java.util.Set;

/**
 * 每一个回答对应的实体对象
 * 只选取作者 回答ID 评论数 赞同数 以及图片地址
 * 回答ID暂时并未用
 */
public class AnswerContent {
    private String author;
    private String id;
    private String commentCount;//评论数
    private String voteupCount;//赞同数
    private Set<String> pictures;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Set<String> getPictures() {
        return pictures;
    }

    public void setPictures(Set<String> pictures) {
        this.pictures = pictures;
    }

    public String getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(String commentCount) {
        this.commentCount = commentCount;
    }

    public String getVoteupCount() {
        return voteupCount;
    }

    public void setVoteupCount(String voteupCount) {
        this.voteupCount = voteupCount;
    }

    @Override
    public String toString() {
        return "AnswerContent{" +
                "author='" + author + '\'' +
                ", id='" + id + '\'' +
                ", 评论数=" + commentCount +
                ", 赞同数=" + voteupCount +
                ", pictures=" + pictures +
                '}';
    }
}
