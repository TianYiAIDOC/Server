package com.tianyi.bo;

import com.tianyi.bo.base.BaseBo;
import com.tianyi.bo.enums.UserStatusEnum;
import com.tianyi.bo.enums.UserType;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import java.io.Serializable;

/**
 * Created by 雪峰 on 2018/1/1.
 */
@Entity
@DynamicUpdate
@DynamicInsert
public class User extends BaseBo implements Serializable {
    private static final long serialVersionUID = 6237499961429798537L;
    /**
     * 账号
     */
    private String username;
    /**
     * 密码
     */
    private String passwordHash;
    /**
     * 手机号
     */
    private String mobile;
    //昵称
    private String nickname;
    /**
     * 用户类型
     */
    private UserType userType;
    /**
     * 用户状态
     */
    private UserStatusEnum userStatus;
    /**
     * avatar
     * 头像
     *
     * ***/
    private String avatar;


    /***
     * 住址
     * */
    private int areaId;
    /***
     * 详细住址
     * */
    private String address;
    /***
     * 签名
     * */
    private String signature;


    /**
     *真实姓名
     */
    private String realName;

    /**
     *d登录IP
     */
    private String longinIp;
    /**
     *d登录次数
     */
    private long longinNum;

    /**
     *用户语言
     */
    private int userLanguage;
    /**
     *国家码
     */

    private int  countryCode;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }


    public UserStatusEnum getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(UserStatusEnum userStatus) {
        this.userStatus = userStatus;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }


    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }


    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }



    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getLonginIp() {
        return longinIp;
    }

    public void setLonginIp(String longinIp) {
        this.longinIp = longinIp;
    }

    public long getLonginNum() {
        return longinNum;
    }

    public void setLonginNum(long longinNum) {
        this.longinNum = longinNum;
    }

    public int getUserLanguage() {
        return userLanguage;
    }

    public void setUserLanguage(int userLanguage) {
        this.userLanguage = userLanguage;
    }

    public int getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(int countryCode) {
        this.countryCode = countryCode;
    }
}
