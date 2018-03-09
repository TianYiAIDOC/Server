package com.tianyi.bo;

import java.util.List;

/**
 * Created by anhui on 2018/3/8.
 */
public class AttachInfo {
    public String name;
    public List<String> urls;


    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
