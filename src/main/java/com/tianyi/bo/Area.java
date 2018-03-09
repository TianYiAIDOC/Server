package com.tianyi.bo;

import com.tianyi.bo.base.BaseBo;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by 雪峰 on 2018/1/1.
 */
@Entity
@DynamicUpdate
@DynamicInsert
public class Area extends BaseBo implements Serializable {
    /**
     * 地区ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int areaId;
    /**
     * 地区名称
     */
    private String area;
    /**
     * 父类id
     */
    private  int parentId;
    /**
     * 是否启用
     */
    private boolean isUse;

    /**
     * 等级
     * 1省/直辖市,2地级市,3区县
     */
    private int level;



    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }



    public boolean isUse() {
        return isUse;
    }

    public void setUse(boolean use) {
        isUse = use;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }
}
