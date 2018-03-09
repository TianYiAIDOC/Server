package com.tianyi.web.model;

/**
 * Created by 雪峰 on 2018/1/3.
 */
public class UserArchivesModel {
    private  long userId;

    private int sex;//: 性别
    /***
     * 生日
     * */
    private String birthday;
    //身高
    private int height;
    //体重
    private int weight;
    //婚姻状态
    private int maritalSttus;
    //生育状态
    private int birthStatus;
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

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
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

    public int getMaritalSttus() {
        return maritalSttus;
    }

    public void setMaritalSttus(int maritalSttus) {
        this.maritalSttus = maritalSttus;
    }

    public int getBirthStatus() {
        return birthStatus;
    }

    public void setBirthStatus(int birthStatus) {
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

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }
}
