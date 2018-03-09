package com.tianyi.service;

import com.tianyi.bo.News;
import com.tianyi.bo.enums.LanguageEnum;
import com.tianyi.bo.enums.NewsEnum;
import com.tianyi.dao.NewsDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by 雪峰 on 2018/1/1.
 */
@Service("newsService")
public class NewsService {
    @Resource
    private NewsDao newsDao;
    public List<News> getNews(NewsEnum newsType, int page, int pageSize,LanguageEnum lang){
        return newsDao.getNews(newsType,lang,page,pageSize);
    }
    public int getNewsTotleNum(NewsEnum newsType,LanguageEnum lang){
        return newsDao.getNoReadNumber(newsType,lang);
    }


    public News getNewsById(long newsId){
        return newsDao.getById(newsId);
    }

    public News addNews(String title,String content,long sendUserId,NewsEnum newsType,String cover,LanguageEnum lang){
        News news = new News();
        news.setContent(content);
        news.setCover(cover);
        news.setNewsType(newsType);
        news.setSendUserId(sendUserId);
        news.setTitle(title);
        news.setLanguage(lang);
        newsDao.add(news);
        return newsDao.getById(news.getId());
    }
    public void updateNews(long newsId,String title,String content,String cover,LanguageEnum lang){
        News news = getNewsById(newsId);
        if(news ==null){
            throw new RuntimeException("新闻不存在！！！");
        }
        news.setTitle(title);
        news.setContent(content);
        news.setCover(cover);
        news.setLanguage(lang);
        newsDao.update(news);
    }

    public void updateNews(News news){
        newsDao.update(news);
    }

    public void deleteNews(long newsId){
        if(newsId<20){
            throw new RuntimeException("该内容不能删除！！！");
        }
        News news = getNewsById(newsId);
        if(news !=null){
            newsDao.delete(news);
        }

    }


}
