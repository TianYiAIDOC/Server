package com.tianyi.web.model.account;

/**
 * Created by 雪峰 on 2018/1/1.
 */
public class LoginResult {
    private long id;
    private String username;
    private String nickname;
    private String realname;
    private String token;
    private int type;
    private long msg_count;
    private String avatar;
    private String address;
    private String signature;
    private String phone_number;
    private int city_id;
    private String city_name;
    private int isSign;
    private double yesterdayAidoc;
    private double totalAidoc;
    private String birthday;
    private int sex;
    private int language;
    private int country_code;


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }



    public long getMsg_count() {
        return msg_count;
    }

    public void setMsg_count(long msg_count) {
        this.msg_count = msg_count;
    }




    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public int getCity_id() {
        return city_id;
    }

    public void setCity_id(int city_id) {
        this.city_id = city_id;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public int getIsSign() {
        return isSign;
    }

    public void setIsSign(int isSign) {
        this.isSign = isSign;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public double getYesterdayAidoc() {
        return yesterdayAidoc;
    }

    public void setYesterdayAidoc(double yesterdayAidoc) {
        this.yesterdayAidoc = yesterdayAidoc;
    }

    public double getTotalAidoc() {
        return totalAidoc;
    }

    public void setTotalAidoc(double totalAidoc) {
        this.totalAidoc = totalAidoc;
    }


    public int getLanguage() {
        return language;
    }

    public void setLanguage(int language) {
        this.language = language;
    }

    public int getCountry_code() {
        return country_code;
    }

    public void setCountry_code(int country_code) {
        this.country_code = country_code;
    }
}
