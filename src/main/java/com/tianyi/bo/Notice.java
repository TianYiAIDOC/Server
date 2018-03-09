package com.tianyi.bo;

import com.tianyi.bo.base.BaseBo;
import com.tianyi.bo.enums.NoticeTypeEnum;
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
public class Notice extends BaseBo implements Serializable {

    /**
     * 通知内容
     */
    private String content;
    /**
     * 通知标题
     */
    private String title;
    /**
     * 用户id
     */
    private long userId;
    /**
     * 是否已读
     */
    private boolean isRead;
    /**
     * 发送人
     */
    private long sendUserId;


    private NoticeTypeEnum noticeType;
    /**
     * 消息id用于系统消息
     */
    private long noticeId;


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


    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getSendUserId() {
        return sendUserId;
    }

    public void setSendUserId(long sendUserId) {
        this.sendUserId = sendUserId;
    }

    public NoticeTypeEnum getNoticeType() {
        return noticeType;
    }

    public void setNoticeType(NoticeTypeEnum noticeType) {
        this.noticeType = noticeType;
    }

    public long getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(long noticeId) {
        this.noticeId = noticeId;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }
}
