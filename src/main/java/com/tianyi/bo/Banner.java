package com.tianyi.bo;

import com.tianyi.bo.base.BaseBo;
import com.tianyi.bo.enums.BannerTypeEnum;
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
public class Banner extends BaseBo implements Serializable {

    /**
     *
     */
    private String name;
    /**
     * 预览图地址
     */
    private String fileKey;
    /**
     * 跳转地址
     */
    private String goUrl;
    /**
     * 类别
     */
    private BannerTypeEnum bannerType;

    /**
     * 排序
     */
    private int sortOrder;

    public String getFileKey() {
        return fileKey;
    }

    public void setFileKey(String fileKey) {
        this.fileKey = fileKey;
    }

    public String getGoUrl() {
        return goUrl;
    }

    public void setGoUrl(String goUrl) {
        this.goUrl = goUrl;
    }

    public BannerTypeEnum getBannerType() {
        return bannerType;
    }

    public void setBannerType(BannerTypeEnum bannerType) {
        this.bannerType = bannerType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }
}
