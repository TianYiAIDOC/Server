package com.tianyi.bo;

import com.tianyi.bo.base.BaseBo;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import java.io.Serializable;

/**
 * Created by 雪峰 on 2018/2/7.
 */
@Entity
@DynamicUpdate
@DynamicInsert
public class CountryCode extends BaseBo implements Serializable {

    private String countryEn;

    private String countryCn;

    private String countryLg;

    private String countryCode;

    private Integer status;

    public String getCountryEn() {
        return countryEn;
    }

    public void setCountryEn(String countryEn) {
        this.countryEn = countryEn;
    }

    public String getCountryCn() {
        return countryCn;
    }

    public void setCountryCn(String countryCn) {
        this.countryCn = countryCn;
    }

    public String getCountryLg() {
        return countryLg;
    }

    public void setCountryLg(String countryLg) {
        this.countryLg = countryLg;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
