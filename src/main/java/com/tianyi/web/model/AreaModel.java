package com.tianyi.web.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 雪峰 on 2015/12/24.
 */
public class AreaModel {
    private int id;
    private String name;
    private List<AreaModel> children = new ArrayList<>();




    public List<AreaModel> getChildren() {
        return children;
    }

    public void setChildren(List<AreaModel> children) {
        this.children = children;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
