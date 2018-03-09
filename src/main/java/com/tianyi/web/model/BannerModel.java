package com.tianyi.web.model;

/**
 * Created by 雪峰 on 2015/12/29.
 */
public class BannerModel {
    private String image;
    private String url;
    private int type;
    private long id;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
