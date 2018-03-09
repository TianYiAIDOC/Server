package com.tianyi.bo;

import com.tianyi.bo.base.BaseBo;
import com.tianyi.bo.enums.BirthStatusEnum;
import com.tianyi.bo.enums.MaritalStatusEunm;
import com.tianyi.bo.enums.SexEnum;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by 雪峰 on 2018/1/1.
 */

@Entity
@DynamicUpdate
@DynamicInsert
public class UserData extends BaseBo implements Serializable {

    private  long userId;
    /***
     * 性别
     * */
    private SexEnum sex;

    //目标
    private int target;
    /***
     * 生日
     * */
    private Date birthday;
    //身高
    private int height;
    //体重
    private int weight;
    //婚姻状态
    private MaritalStatusEunm maritalStatus;
    //生育状态
    private BirthStatusEnum birthStatus;
    //手术外伤
    private String surgicalTrauma;
    //家族病史
    private String medicalHistory;
    //药物过敏
    private String drugAllergy;
    //其他过敏
    private String otherAllergy;
    //个人习惯
    private  String personalHabits;
    //其他习惯
    private String otherHabits;
    //运动状态，0未添加，1：零基础，2：有经验，3：经验丰富
    private int sportsState;
    //运动目的 0，未填写：1:健康，2：康复，3：完美
    private  int sportsGoal;
    //喜欢的运动
    private String sportsLiked;
    //运动场地 json串
    private String sportsFieild;
    //运动天数 0:未填写，1：不动，2：1-2，3：3-4，3：5-7
    private int sportDay;
    //运动小时 0:未填写，1：30分钟，2：30-60，3：60-90，4：90以上
    private int sportsHour;
    //体重信息0:未填写 ，1：长胖，2：保持，3：不会胖
    private int weightInfo;
    //体型状态 0：未填写，1：圆润，2：标准，3：苗条
    private int shape;
    //手腕长度0:未填写 1：有间隙，2：，3：
    private int wristLength;


    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getTarget() {
        return target;
    }

    public void setTarget(int target) {
        this.target = target;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public BirthStatusEnum getBirthStatus() {
        return birthStatus;
    }

    public void setBirthStatus(BirthStatusEnum birthStatus) {
        this.birthStatus = birthStatus;
    }

    public String getSurgicalTrauma() {
        return surgicalTrauma;
    }

    public void setSurgicalTrauma(String surgicalTrauma) {
        this.surgicalTrauma = surgicalTrauma;
    }

    public String getMedicalHistory() {
        return medicalHistory;
    }

    public void setMedicalHistory(String medicalHistory) {
        this.medicalHistory = medicalHistory;
    }

    public String getDrugAllergy() {
        return drugAllergy;
    }

    public void setDrugAllergy(String drugAllergy) {
        this.drugAllergy = drugAllergy;
    }

    public String getOtherAllergy() {
        return otherAllergy;
    }

    public void setOtherAllergy(String otherAllergy) {
        this.otherAllergy = otherAllergy;
    }

    public String getPersonalHabits() {
        return personalHabits;
    }

    public void setPersonalHabits(String personalHabits) {
        this.personalHabits = personalHabits;
    }

    public String getOtherHabits() {
        return otherHabits;
    }

    public void setOtherHabits(String otherHabits) {
        this.otherHabits = otherHabits;
    }

    public int getSportsState() {
        return sportsState;
    }

    public void setSportsState(int sportsState) {
        this.sportsState = sportsState;
    }

    public int getSportsGoal() {
        return sportsGoal;
    }

    public void setSportsGoal(int sportsGoal) {
        this.sportsGoal = sportsGoal;
    }

    public String getSportsLiked() {
        return sportsLiked;
    }

    public void setSportsLiked(String sportsLiked) {
        this.sportsLiked = sportsLiked;
    }

    public String getSportsFieild() {
        return sportsFieild;
    }

    public void setSportsFieild(String sportsFieild) {
        this.sportsFieild = sportsFieild;
    }

    public int getSportDay() {
        return sportDay;
    }

    public void setSportDay(int sportDay) {
        this.sportDay = sportDay;
    }

    public int getSportsHour() {
        return sportsHour;
    }

    public void setSportsHour(int sportsHour) {
        this.sportsHour = sportsHour;
    }

    public int getWeightInfo() {
        return weightInfo;
    }

    public void setWeightInfo(int weightInfo) {
        this.weightInfo = weightInfo;
    }

    public int getShape() {
        return shape;
    }

    public void setShape(int shape) {
        this.shape = shape;
    }

    public int getWristLength() {
        return wristLength;
    }

    public void setWristLength(int wristLength) {
        this.wristLength = wristLength;
    }

    public SexEnum getSex() {
        return sex;
    }

    public void setSex(SexEnum sex) {
        this.sex = sex;
    }

    public MaritalStatusEunm getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(MaritalStatusEunm maritalStatus) {
        this.maritalStatus = maritalStatus;
    }
}
