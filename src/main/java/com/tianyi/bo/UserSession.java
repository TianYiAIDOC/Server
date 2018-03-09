package com.tianyi.bo;

import com.tianyi.bo.base.BaseBo;
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
public class UserSession extends BaseBo implements Serializable {
    private static final long serialVersionUID = 6237499961429798537L;

    private String appName;
    private long userId;
    private String token;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }
}
