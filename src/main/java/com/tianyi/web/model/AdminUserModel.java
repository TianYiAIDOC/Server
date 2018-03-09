package com.tianyi.web.model;

/**
 * Created by 雪峰 on 2016/9/13.
 */
public class AdminUserModel {
    private long id;//: ID
    private String username;//: 名称
    private String phone_number;//: 手机号
    private long login_count;//: 登录次数
    private String last_login_at;//: 最后登录时间
    private String last_login_ip;//: 最后登录IP
    private boolean enabled;//: 停用或开通 false/true
    private String  created;//: 创建时间
    private String real_name;

    private int sex;
    private String nick_name;
    private String birth;
    private int age;
    private int country_code;




    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public long getLogin_count() {
        return login_count;
    }

    public void setLogin_count(long login_count) {
        this.login_count = login_count;
    }

    public String getLast_login_at() {
        return last_login_at;
    }

    public void setLast_login_at(String last_login_at) {
        this.last_login_at = last_login_at;
    }

    public String getLast_login_ip() {
        return last_login_ip;
    }

    public void setLast_login_ip(String last_login_ip) {
        this.last_login_ip = last_login_ip;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getReal_name() {
        return real_name;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }


    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public int getCountry_code() {
        return country_code;
    }

    public void setCountry_code(int country_code) {
        this.country_code = country_code;
    }
}
