package com.tianyi.bo;

import com.tianyi.bo.base.BaseBo;
import com.tianyi.bo.enums.UserDayEnum;
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
public class UserDayData extends BaseBo implements Serializable {

    private long userId;

    private UserDayEnum dayType;

    private int dataNumber;

    private String dataString;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public UserDayEnum getDayType() {
        return dayType;
    }

    public void setDayType(UserDayEnum dayType) {
        this.dayType = dayType;
    }

    public int getDataNumber() {
        return dataNumber;
    }

    public void setDataNumber(int dataNumber) {
        this.dataNumber = dataNumber;
    }

    public String getDataString() {
        return dataString;
    }

    public void setDataString(String dataString) {
        this.dataString = dataString;
    }
}
