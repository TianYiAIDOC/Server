package com.tianyi.web.controller;

import com.tianyi.bo.News;
import com.tianyi.bo.User;
import com.tianyi.bo.enums.LanguageEnum;
import com.tianyi.bo.enums.NewsEnum;
import com.tianyi.bo.enums.UserDayEnum;
import com.tianyi.service.I18nService;
import com.tianyi.service.NewsService;
import com.tianyi.service.UserDayDataService;
import com.tianyi.web.AuthRequired;
import com.tianyi.web.model.ActionResult;
import com.tianyi.web.model.PagedListModel;
import com.tianyi.web.util.DateUtil;
import com.tianyi.web.util.JsonPathArg;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by 雪峰 on 2018/1/1.
 */
@RestController
public class NewController {
    @Resource
    private NewsService newsService;
    @Autowired
    UserDayDataService userDayDataService;
    @Autowired
    I18nService i18nService;


    //    @AuthRequired
    @RequestMapping(value = "/archives/categories/{category_id}", method = RequestMethod.GET)
    public PagedListModel<List<Map<String, Object>>> getNews(@PathVariable("category_id") int categoryId,
                                                             @RequestParam(value = "p", required = false) Integer page,
                                                             @Value("#{request.getAttribute('lang')}") String lang,
                                                             @RequestHeader("X-Client") String client,
                                                             @RequestParam(value = "p_size", required = false) Integer pageSize) {
        page = page == null ? 1 : page;
        pageSize = pageSize == null ? 20 : pageSize;
        LanguageEnum languageEnum = LanguageEnum.valueOf(lang);

        if(client !=null && StringUtils.isNotEmpty(client)&&"ADMIN".equals(client)){
            languageEnum = null;
        }


        List<News> newss = newsService.getNews(NewsEnum.values()[categoryId], page, pageSize, languageEnum);
        int total = newsService.getNewsTotleNum(NewsEnum.values()[categoryId],languageEnum);
        List<Map<String, Object>> result = new ArrayList<>();
        for (News news : newss) {
            news.setContent("");
            result.add(getMapFromNew(news));
        }
        return new PagedListModel(result, total, page, pageSize);
    }

    @RequestMapping(value = "/archives/{archive_id}", method = RequestMethod.GET)
    public Map<String, Object> getNewsById(@PathVariable("archive_id") long archiveId,@Value("#{request.getAttribute('lang')}") String lang
    ) {



        if(archiveId<10&&LanguageEnum.en.name().equals(lang)){
            archiveId = archiveId+10;
        }
        News news = newsService.getNewsById(archiveId);
        if (news == null) {
            throw new RuntimeException(i18nService.getMessage("" + 112,lang));
        }



        news.setWatchNum(news.getWatchNum()+(1+new Random().nextInt(5)));
        newsService.updateNews(news);

        return getMapFromNew(news);
    }



    @RequestMapping(value = "/archives/share/{archive_id}", method = RequestMethod.GET)
    public Map<String, Object> shareNewsById(@PathVariable("archive_id") long archiveId,@Value("#{request.getAttribute('currentUser')}") User currentUser,
                                             @Value("#{request.getAttribute('lang')}") String lang) {
        News news = newsService.getNewsById(archiveId);
        if (news == null) {
            throw new RuntimeException(i18nService.getMessage("" + 112,lang));
        }

        news.setForwardNum(news.getForwardNum()+(1+new Random().nextInt(3)));
        newsService.updateNews(news);


        long userId = 0;
        if(currentUser !=null){
            userId = currentUser.getId();

        }
        userDayDataService.saveEveryDayData(userId, UserDayEnum.SHARE,1,"");

        return getMapFromNew(news);
    }

    @AuthRequired
    @RequestMapping(value = "/archives/categories/{category_id}", method = RequestMethod.POST)
    public Map<String, Object> addNews(@PathVariable("category_id") int categoryId,
                                       @JsonPathArg("title") String title,
                                       @JsonPathArg("content") String content,
                                       @JsonPathArg("cover") String cover,
                                       @JsonPathArg("lang") int lang,
                                       @Value("#{request.getAttribute('currentUser')}") User currentUser) {
        News news = newsService.addNews(title, content, currentUser.getId(), NewsEnum.values()[categoryId],cover,LanguageEnum.values()[lang]);
        return getMapFromNew(news);
    }


    @AuthRequired
    @RequestMapping(value = "/archives/{archive_id}", method = RequestMethod.PUT)
    public ActionResult editNews(@PathVariable("archive_id") int newId,
                                 @JsonPathArg("title") String title,
                                 @JsonPathArg("content") String content,
                                 @JsonPathArg("cover") String cover,
                                 @JsonPathArg("lang") int lang,
                                 @Value("#{request.getAttribute('currentUser')}") User currentUser) {
        newsService.updateNews(newId, title, content,cover,LanguageEnum.values()[lang]);
        return new ActionResult();
    }

    @AuthRequired
    @RequestMapping(value = "/archives/{archive_id}", method = RequestMethod.DELETE)
    public ActionResult delNews(@PathVariable("archive_id") int newId,@Value("#{request.getAttribute('lang')}") String lang) {
        if(newId < 11){
            throw new RuntimeException(i18nService.getMessage("" + 114,lang));
        }

        newsService.deleteNews(newId);
        return new ActionResult();
    }


    private Map<String, Object> getMapFromNew(News news) {
        Map<String, Object> result = new HashedMap();
        result.put("id", news.getId());
        result.put("title", news.getTitle());
        result.put("content", news.getContent());
        result.put("created_at", DateUtil.formatTime(news.getCreatedOn()));
        result.put("cover",news.getCover());
        result.put("watch_num",news.getWatchNum());
        result.put("forward_num",news.getForwardNum());
        result.put("lang",news.getLanguage().ordinal());
        return result;
    }

}
