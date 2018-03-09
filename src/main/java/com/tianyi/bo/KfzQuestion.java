package com.tianyi.bo;

import com.tianyi.bo.base.BaseBo;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import java.io.Serializable;

/**
 * Created by anhui on 2018/3/2.
 */
@Entity
@DynamicUpdate
@DynamicInsert
public class KfzQuestion extends BaseBo implements Serializable {


    public String kfzQid;
    public String kfzQuestionBody;
    public String kfzAppid;
    public long	userId;
    public String kfzAppName;

    public String getKfzQid() {
        return kfzQid;
    }
    public void setKfzQid(String kfzQid) {
        this.kfzQid = kfzQid;
    }

    public String getKfzQuestionBody() {
        return kfzQuestionBody;
    }
    public void setKfzQuestionBody(String kfzQuestionBody) {
        this.kfzQuestionBody = kfzQuestionBody;
    }

    public String getKfzAppid() {
        return kfzAppid;
    }
    public void setKfzAppid(String kfzAppid) {
        this.kfzAppid = kfzAppid;
    }

    public long getUserId() {
        return userId;
    }
    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getKfzAppName() {
        return kfzAppName;
    }
    public void setKfzAppName(String kfzAppName) {
        this.kfzAppName = kfzAppName;
    }

}
