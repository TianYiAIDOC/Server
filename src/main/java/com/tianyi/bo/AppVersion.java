package com.tianyi.bo;

import com.tianyi.bo.base.BaseBo;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import java.io.Serializable;

/**
 * Created by 雪峰 on 2018/01/26.
 */



@Entity
@DynamicUpdate
@DynamicInsert
public class AppVersion extends BaseBo implements Serializable {
    private long srcId;
    private String mobileOs;
    private String version;
    private String downUrl;
    private String updateLog;
    private String targetSize;
    private int isMust;

    public long getSrcId() {
        return srcId;
    }

    public void setSrcId(long srcId) {
        this.srcId = srcId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDownUrl() {
        return downUrl;
    }

    public void setDownUrl(String downUrl) {
        this.downUrl = downUrl;
    }

    public String getUpdateLog() {
        return updateLog;
    }

    public void setUpdateLog(String updateLog) {
        this.updateLog = updateLog;
    }


    public int getIsMust() {
        return isMust;
    }

    public void setIsMust(int isMust) {
        this.isMust = isMust;
    }

    public String getMobileOs() {
        return mobileOs;
    }

    public void setMobileOs(String mobileOs) {
        this.mobileOs = mobileOs;
    }

    public String getTargetSize() {
        return targetSize;
    }

    public void setTargetSize(String targetSize) {
        this.targetSize = targetSize;
    }
}
