package com.tianyi.web.model;

/**
 * Created by 雪峰
 */
public class AuthorModel extends  UserBasicModel{
    private String title;//公司名称
    private int level;
    private boolean followed;//是否已经关注
    private String product_custom;//作者定制文本信息供前台查询
    private long product_id;//作者定制文本对应的直播信息
    private String description;
    private String tags;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }


    public boolean isFollowed() {
        return followed;
    }

    public void setFollowed(boolean followed) {
        this.followed = followed;
    }

    public String getProduct_custom() {
        return product_custom;
    }

    public void setProduct_custom(String product_custom) {
        this.product_custom = product_custom;
    }

    public long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(long product_id) {
        this.product_id = product_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
}
