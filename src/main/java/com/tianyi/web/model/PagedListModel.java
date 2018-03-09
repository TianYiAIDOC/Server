package com.tianyi.web.model;

import java.util.List;
import java.util.Map;

/**
 * Created by lingqing.wan on 2016/1/9.
 */
public class PagedListModel<T> {
    private long page;
    private long page_size;
    private long count;
    private long pages;
    private List<T> list;
    private Map<String,Object> extend;

    public PagedListModel(List<T> list, long count, long pageIndex, long page_size) {
        this.list = list;
        this.count = count;
        this.page = pageIndex;
        this.page_size = page_size;

        pages = count / page_size;
        if (count % page_size > 0) {
            pages++;
        }
    }

    public long getPage() {
        return page;
    }

    public void setPage(long page) {
        this.page = page;
    }


    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public long getPages() {
        return pages;
    }

    public void setPages(long pages) {
        this.pages = pages;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public long getPage_size() {
        return page_size;
    }

    public void setPage_size(long page_size) {
        this.page_size = page_size;
    }

    public Map<String, Object> getExtend() {
        return extend;
    }

    public void setExtend(Map<String, Object> extend) {
        this.extend = extend;
    }
}
