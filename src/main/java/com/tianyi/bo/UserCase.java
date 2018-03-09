package com.tianyi.bo;

import com.tianyi.bo.base.BaseBo;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by 雪峰 on 2018/1/1.
 */
@Entity
@DynamicUpdate
@DynamicInsert
public class UserCase extends BaseBo implements Serializable {
    private long userId;
    //就诊时间
    private Date visitDay;
    //就诊类型0:门诊，1：急诊，2：住院，3取消
    private int visitType;
    //就诊医院
    private String visitHospial;
    //就诊科室
    private String visitDepartment;
    //医生名称
    private String doctorName;
    //就诊费用
    private float visitFee;
    //就诊原因
    private String visitReason;
    //就诊结果
    private String visitResult;
    //医生建议
    private String doctorAdvice;
    //用药与处方
    private String doctorInfo;
    //检查报告
    private String visitRepost;

    //病例原件
    private String casePhoto;

    //逻辑删除 1 可用 0 删除
    private int caseStatus;


    public int getCaseStatus() {
        return caseStatus;
    }

    public void setCaseStatus(int caseStatus) {
        this.caseStatus = caseStatus;
    }

    public List<String> getVisitReasonImg() {
        String key="visitReason";
        return getUrls(key);
    }
    public List<String> getVisitResultImg() {
        String key="visitResult";
        return getUrls(key);
    }
    public List<String> getDoctorAdviceImg() {
        String key="doctorAdvice";
        return getUrls(key);
    }
    public List<String> getDoctorInfoImg() {
        String key="doctorInfo";
        return getUrls(key);
    }
    public List<String> getVisitRepostImg() {
        String key="visitRepost";
        return getUrls(key);
    }
    public List<String> getCasePhotoImg() {
        String key="casePhoto";
        return getUrls(key);
    }



    private List<String> getUrls(String key) {
        try {

                List<AttachInfo> attchList = JSON.parseArray(attachFileKeyJson, AttachInfo.class);

            for (AttachInfo attch : attchList) {
                if (attch != null && attch.urls != null) {
                    if (key.toLowerCase().equals(attch.getName().toLowerCase()))
                        return attch.urls;
                }
            }
            return new ArrayList<String>();

        } catch (Exception ex) {
            return new ArrayList<String>();
        }
    }


    /**
     * 附件文件key,JSON数组格式
     */
    private String attachFileKeyJson;
    @Transient
    private List<String> attachFileKeys;


    public void setAttachFileKeyJson(String attachFileKeyJson) {
        this.attachFileKeyJson = attachFileKeyJson;
    }

    public List<String> getAttachFileKeys() {
        if (attachFileKeys != null && attachFileKeys.size() > 0) {
            return attachFileKeys;
        }
        attachFileKeys = new ArrayList<String>();

        if (StringUtils.isNotBlank(attachFileKeyJson)) {
            attachFileKeys.addAll(JSON.parseArray(attachFileKeyJson, String.class));
        }

        return attachFileKeys;
    }


    public void setCasePhoto(String casePhoto) {
        this.casePhoto = casePhoto;
    }

    public String getCasePhoto() {
        return casePhoto;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Date getVisitDay() {
        return visitDay;
    }

    public void setVisitDay(Date visitDay) {
        this.visitDay = visitDay;
    }

    public int getVisitType() {
        return visitType;
    }

    public void setVisitType(int visitType) {
        this.visitType = visitType;
    }

    public String getVisitHospial() {
        return visitHospial;
    }

    public void setVisitHospial(String visitHospial) {
        this.visitHospial = visitHospial;
    }

    public String getVisitDepartment() {
        return visitDepartment;
    }

    public void setVisitDepartment(String visitDepartment) {
        this.visitDepartment = visitDepartment;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public float getVisitFee() {
        return visitFee;
    }

    public void setVisitFee(float visitFee) {
        this.visitFee = visitFee;
    }

    public String getVisitReason() {
        return visitReason;
    }

    public void setVisitReason(String visitReason) {
        this.visitReason = visitReason;
    }

    public String getVisitResult() {
        return visitResult;
    }

    public void setVisitResult(String visitResult) {
        this.visitResult = visitResult;
    }

    public String getDoctorAdvice() {
        return doctorAdvice;
    }

    public void setDoctorAdvice(String doctorAdvice) {
        this.doctorAdvice = doctorAdvice;
    }

    public String getDoctorInfo() {
        return doctorInfo;
    }

    public void setDoctorInfo(String doctorInfo) {
        this.doctorInfo = doctorInfo;
    }

    public String getVisitRepost() {
        return visitRepost;
    }

    public void setVisitRepost(String visitRepost) {
        this.visitRepost = visitRepost;
    }


}