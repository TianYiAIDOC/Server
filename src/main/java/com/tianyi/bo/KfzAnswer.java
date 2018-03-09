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
public class KfzAnswer  extends BaseBo implements Serializable {


    public String kfzAnswerId;
    public String kfzQid;
    public String kfzAnswerBody;
    public String kfzAppid;
    public String kfzAppName;
    public long	userId;


    public String getKfzQid() {
        return kfzQid;
    }
    public void setKfzQid(String kfzQid) {
        this.kfzQid = kfzQid;
    }
    public String getKfzAnswerId() {
        return kfzAnswerId;
    }
    public void setKfzAnswerId(String kfzAnswerId) {
        this.kfzAnswerId = kfzAnswerId;
    }
    public String getKfzAnswerBody() {
        return kfzAnswerBody;
    }
    public void setKfzAnswerBody(String kfzAnswerBody) {
        this.kfzAnswerBody = kfzAnswerBody;
    }
    public String getKfzAppid() {
        return kfzAppid;
    }
    public void setKfzAppid(String kfzAppid) {
        this.kfzAppid = kfzAppid;
    }
    public String getKfzAppName() {
        return kfzAppName;
    }
    public void setKfzAppName(String kfzAppName) {
        this.kfzAppName = kfzAppName;
    }
    public long getUserId() {
        return userId;
    }
    public void setUserId(long userId) {
        this.userId = userId;
    }
}
