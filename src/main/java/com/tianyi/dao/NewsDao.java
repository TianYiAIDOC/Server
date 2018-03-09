package com.tianyi.dao;

import com.tianyi.bo.News;
import com.tianyi.bo.enums.LanguageEnum;
import com.tianyi.bo.enums.NewsEnum;
import com.tianyi.dao.base.BaseDao;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 雪峰 on 2018/1/1.
 */
@Repository
public class NewsDao extends BaseDao<News> {
    /**
     * 新闻列表
     */
    public List<News> getNews(final NewsEnum newsType, final LanguageEnum lang, final int page, final int pageSize) {
        StringBuffer strb = new StringBuffer("SELECT * FROM news WHERE 1=1 ");
        if (newsType == null) {
            strb.append(" and news_type >1 ");
        } else {
            strb.append(" and news_type = ").append(newsType.ordinal());
        }

        if(lang !=null){
            strb.append(" and language =").append(lang.ordinal());
        }

        strb.append(" ORDER BY updated_on desc ");

        List<News> results = execute(session -> session
                .createNativeQuery(strb.toString(), News.class)
                .setFirstResult((page - 1) * pageSize)
                .setMaxResults(pageSize)
                .getResultList());
        return results != null ? results : new ArrayList<News>();
    }

    public Integer getNoReadNumber(final NewsEnum newsType,final LanguageEnum lang) {
        StringBuffer strb = new StringBuffer("SELECT count(*)  FROM news WHERE 1=1 ");
        if (newsType == null) {
            strb.append(" and news_type >1 ");
        } else {
            strb.append(" and news_type = ").append(newsType.ordinal());
        }

        if(lang !=null){
            strb.append(" and language =").append(lang.ordinal());
        }
        BigInteger num = execute(session -> (BigInteger) session
                .createNativeQuery(strb.toString())
                .getSingleResult());
        return num == null ? 0 : num.intValue();
    }
}
