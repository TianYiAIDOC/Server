package com.tianyi.bo;

import com.tianyi.bo.base.BaseBo;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by 雪峰 on 2018/1/16.
 */
@Entity
@DynamicUpdate
@DynamicInsert
public class AidocSet extends BaseBo implements Serializable {

    /**
     * 发放金额
     */
    private int aidocTotal;
    /**
     * 生效时间
     */
    private Date effectiveDate;
    /**
     * 失效时间
     */
    private Date invalidDate;
    /**
     * 类别
     * 0：默认发放金额
     * 1：设置金额
     */
    private int setType;
    /**
     * 类别
     * 0：无效
     * 1：有效
     */
    private int status;

    /**
     *
     */
    private long  operationUserId;



    public int getAidocTotal() {
        return aidocTotal;
    }

    public void setAidocTotal(int aidocTotal) {
        this.aidocTotal = aidocTotal;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public Date getInvalidDate() {
        return invalidDate;
    }

    public void setInvalidDate(Date invalidDate) {
        this.invalidDate = invalidDate;
    }


    public int getSetType() {
        return setType;
    }

    public void setSetType(int setType) {
        this.setType = setType;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getOperationUserId() {
        return operationUserId;
    }

    public void setOperationUserId(long operationUserId) {
        this.operationUserId = operationUserId;
    }
}
