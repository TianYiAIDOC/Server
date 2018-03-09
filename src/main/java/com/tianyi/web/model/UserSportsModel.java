package com.tianyi.web.model;

/**
 * Created by 雪峰 on 2018/1/3.
 */
public class UserSportsModel {

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
}
