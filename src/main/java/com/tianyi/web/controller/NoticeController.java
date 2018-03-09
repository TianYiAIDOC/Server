package com.tianyi.web.controller;

import com.tianyi.bo.Notice;
import com.tianyi.bo.User;
import com.tianyi.bo.enums.NoticeTypeEnum;
import com.tianyi.service.NoticeService;
import com.tianyi.service.UserService;
import com.tianyi.web.AuthRequired;
import com.tianyi.web.model.ActionResult;
import com.tianyi.web.model.PagedListModel;
import com.tianyi.web.util.DateUtil;
import com.tianyi.web.util.JsonPathArg;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 雪峰 on 2018/1/1.
 */
@RestController
public class NoticeController {
    final private int count = 20;

    @Resource
    private NoticeService noticeService;
    @Resource
    private UserService userService;
    @AuthRequired
    @RequestMapping(value = "/admin/system/messages",method = RequestMethod.GET)
    public PagedListModel<List<Map<String,Object>>> getUserNotices(@RequestParam("p") Integer page, @RequestParam("p_size") Integer pageSize,
                                                                   @Value("#{request.getAttribute('currentUser')}") User currentUser) {
        page = page == null ? 0 : page;
        pageSize = pageSize == null ? count : pageSize;
        List<Notice> notices = noticeService.getNotices(0, page, pageSize,null, NoticeTypeEnum.OS);
        int total = noticeService.getTotalNum(0, null, NoticeTypeEnum.OS);
       List<Map<String,Object>> result = new ArrayList<>();
        for(Notice notice:notices){
            result.add(getMapFromNotice(notice));
        }
        return new PagedListModel(result,total,page,pageSize);
    }
    @AuthRequired
    @RequestMapping(value = "/admin/system/messages",method= RequestMethod.POST)
    public ActionResult sendNotice(@JsonPathArg("title") String title,
                                   @JsonPathArg("content") String content,
                                   @Value("#{request.getAttribute('currentUser')}") User currentUser) {


        if(StringUtils.isBlank(title)||StringUtils.isBlank(content)){
            throw new RuntimeException("标题或内容不能为空！！！！！！");
        }

        noticeService.saveOsNotice(title,content,currentUser.getId());

        return new ActionResult();
    }

    @AuthRequired
    @RequestMapping(value = "/admin/system/messages/{id}",method= RequestMethod.PUT)
    public ActionResult editNotice(
                                    @PathVariable("id") long noticId,
                                    @JsonPathArg("title") String title,
                                   @JsonPathArg("content") String content,
                                   @Value("#{request.getAttribute('currentUser')}") User currentUser) {

        noticeService.editOsNotice(noticId,title,content);

        return new ActionResult();
    }

    @AuthRequired
    @RequestMapping(value = "/admin/system/messages/{id}", method = RequestMethod.DELETE)
    public ActionResult deleteNoticeById(@PathVariable("id") long noticId) throws Exception {
        noticeService.deleteNotice(noticId);
        return new ActionResult();
    }


    @AuthRequired
    @RequestMapping(value = "/admin/system/messages/{id}", method = RequestMethod.GET)
    public Map<String,Object> getNoticeById(@PathVariable("id") long noticId) throws Exception {
        Notice notice =noticeService.getNoticeById( noticId);
        if(notice ==null){
            throw new RuntimeException("通知不存在！！！！");
        }
        return getMapFromNotice(notice);
    }


    @AuthRequired
    @RequestMapping(value = "/user/messages/{message_id}", method = RequestMethod.GET)
    public Map<String,Object> getUserNoticeById(@PathVariable("message_id") long noticId) throws Exception {
        Notice notice =noticeService.getNoticeById( noticId);
        if(notice ==null){
            throw new RuntimeException("通知不存在！！！！");
        }
        return getMapFromNotice(notice);
    }






    @AuthRequired
    @RequestMapping(value = "/user/messages/read/all",method = RequestMethod.GET)
    public Map<String,Integer> readsAll(@Value("#{request.getAttribute('currentUser')}") User currentUser,
                                        @RequestParam("type") Integer type) {
        NoticeTypeEnum noticeTypeEnum = type ==null?NoticeTypeEnum.OS:NoticeTypeEnum.values()[type];
        List<Notice> notices = noticeService.getNotices(currentUser.getId(), 1, Integer.MAX_VALUE ,false,noticeTypeEnum);
        if(notices !=null&&notices.size() > 0) {
            String[] noticeIds = new String[notices.size()];
            int i = 0;
            for (Notice notice : notices) {
                noticeIds[i] = String.valueOf(notice.getId());
                i++;
            }
            noticeService.read(noticeIds, currentUser.getId());
        }

        Map<String,Integer> map = new HashMap();
        map.put("msg_count",0);
        return map;
    }


    @AuthRequired
    @RequestMapping(value = "/user/messages/type/{type}",method = RequestMethod.GET)
    public PagedListModel<List<Map<String,Object>>>  getUserMessages(@RequestParam("p") Integer page,
                                                                     @RequestParam("p_size") Integer pageSize,
                                                                     @Value("#{request.getAttribute('currentUser')}") User currentUser,
                                                                     @PathVariable("type") Integer type) {
        page = page == null ? 0 : page;
        pageSize = pageSize == null ? count : pageSize;
        NoticeTypeEnum noticeTypeEnum = type==null?NoticeTypeEnum.OS:NoticeTypeEnum.values()[type];


        List<Notice> notices = noticeService.getNotices(currentUser.getId(), page, pageSize,null, noticeTypeEnum);
        int total = noticeService.getTotalNum(currentUser.getId(), null, noticeTypeEnum);
        List<Map<String,Object>> result = new ArrayList<>();
        for(Notice notice:notices){
            Map<String,Object>  map = new HashedMap();
            map.put("id", String.valueOf(notice.getId()));
            map.put("created_at",DateUtil.formatTime(notice.getCreatedOn()));
            map.put("is_read",notice.isRead());
            if(NoticeTypeEnum.OS == notice.getNoticeType()){
                Notice notice1 = noticeService.getNoticeById(notice.getNoticeId());
                map.put("content",notice1 ==null?"信息已经被删除":notice1.getContent());
                map.put("title",notice1 ==null?"信息已经被删除":notice1.getTitle());
            }else{
                map.put("content",notice.getContent());
                map.put("title",notice.getTitle());
            }

            result.add(map);
        }
        return new PagedListModel(result,total,page,pageSize);
    }





    @AuthRequired
    @RequestMapping(value = "/user/messages/read",method = RequestMethod.POST)
    public ActionResult reads(@JsonPathArg("ids") String[] noticeIds,
                                      //@RequestParam("type") Integer type,
                                      @Value("#{request.getAttribute('currentUser')}") User currentUser) {
        noticeService.read(noticeIds,currentUser.getId());
        //NoticeTypeEnum noticeTypeEnum = type==null?NoticeTypeEnum.OS:NoticeTypeEnum.values()[type];

//        int noReadNum = noticeService.getTotalNum(currentUser.getId(),false, noticeTypeEnum);
//        Map<String,Integer> map = new HashMap();
//        map.put("msg_count",noReadNum);
        return new ActionResult();
    }
    private Map<String,Object> getMapFromNotice(Notice notice){
        Map<String,Object>  map = new HashedMap();
        map.put("id", String.valueOf(notice.getId()));
        map.put("created_at",DateUtil.formatTime(notice.getCreatedOn()));
        String content = notice.getContent();
        String title = notice.getTitle();

        if(notice.getNoticeId()!=0){
            Notice not = noticeService.getNoticeById(notice.getNoticeId());
            if(not!=null){
                content = not.getContent();
                title = not.getTitle();
            }
        }


        map.put("content",content);
        map.put("title",title);
        map.put("is_read",notice.isRead());
        return map;
    }

}
