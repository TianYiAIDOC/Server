package com.tianyi.bo;

import com.tianyi.bo.base.BaseBo;

import javax.persistence.Entity;
import java.io.Serializable;
import java.math.BigInteger;

/**
 * Created by anhui on 2018/3/5.
 */

public class UserDataResult{
    public BigInteger userId;
    public int dataNumber;

    public  String mobile;
    public String nickname;
    public  String signature;
    public  String avatar;

    public void setUserId(BigInteger userId) {
        this.userId = userId;
    }

    public BigInteger getUserId() {
        return userId;
    }

    public int getDataNumber() {
        return dataNumber;
    }

    public String getMobile() {
        return mobile;
    }

    public String getNickname() {
        return nickname;
    }

    public String getSignature() {
        return signature;
    }

    public void setDataNumber(int dataNumber) {
        this.dataNumber = dataNumber;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
