package com.tianyi.bo;

import com.tianyi.bo.base.BaseBo;
import com.tianyi.bo.enums.LanguageEnum;
import com.tianyi.bo.enums.NewsEnum;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import java.io.Serializable;

/**
 * Created by 雪峰 on 2018/1/1.
 */
@Entity
@DynamicUpdate
@DynamicInsert
public class News extends BaseBo implements Serializable {

    /**
     * 内容
     */
    private String content;
    /**
     * 标题
     */
    private String title;
    /**
     * 显示图片
     */
    private String cover;

    /**
     * 新闻类型
     */
    private NewsEnum newsType;
    /**
     * 发布人
     */
    private long sendUserId;

    /**
     * 观看次数
     */
    private int watchNum;

    /**
     * 分享次数
     * */
    private int forwardNum;

    /**
     * 语言
     * */
    private LanguageEnum language;



    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public NewsEnum getNewsType() {
        return newsType;
    }

    public void setNewsType(NewsEnum newsType) {
        this.newsType = newsType;
    }

    public long getSendUserId() {
        return sendUserId;
    }

    public void setSendUserId(long sendUserId) {
        this.sendUserId = sendUserId;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public int getWatchNum() {
        return watchNum;
    }

    public void setWatchNum(int watchNum) {
        this.watchNum = watchNum;
    }

    public int getForwardNum() {
        return forwardNum;
    }

    public void setForwardNum(int forwardNum) {
        this.forwardNum = forwardNum;
    }


    public LanguageEnum getLanguage() {
        return language;
    }

    public void setLanguage(LanguageEnum language) {
        this.language = language;
    }
}
