package com.tianyi.service;

import com.tianyi.bo.Notice;
import com.tianyi.bo.User;
import com.tianyi.bo.enums.NoticeTypeEnum;
import com.tianyi.bo.enums.UserType;
import com.tianyi.dao.NoticeDao;
import com.tianyi.web.util.SendSMS;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by 雪峰 on 2018/1/1.
 */
@Service("noticeService")
public class NoticeService  {
    @Resource
    private NoticeDao noticeDao;
    @Resource
    private UserService userService;
    @Resource
    private SendSMS sendSMS;


    public List<Notice> getNotices( long userId,  int page,  int pageSize,  Boolean is_read,  NoticeTypeEnum noticeType) {
        return noticeDao.getNotices(userId,page,pageSize,is_read,noticeType);
    }

    public void read(String[] noticeIds,long userId) {
        for(String id:noticeIds){
            if(StringUtils.isBlank(id)){
                continue;
            }
            Notice notice = noticeDao.getById(Long.parseLong(id));
            if(notice != null&&userId == notice.getUserId()){
                notice.setRead(true);
                noticeDao.update(notice);
            }
        }
    }
    public Integer getTotalNum(long userId,Boolean is_read,  NoticeTypeEnum noticeType) {
        return noticeDao.getTotalNum(userId,is_read,noticeType);
    }

    public void saveNotice(long userId, String title, String content,NoticeTypeEnum noticeTypeEnum) {

        Notice noticeUser = new Notice();
        noticeUser.setUserId(userId);
        noticeUser.setNoticeType(noticeTypeEnum);
        noticeUser.setNoticeId(0);
        noticeUser.setTitle(title);
        noticeUser.setContent(content);
        noticeDao.add(noticeUser);


    }
    public void saveOsNotice( String title, String content, long sendUserId) {
        Notice notice = new Notice();
        notice.setUserId(0);
        notice.setTitle(title);
        notice.setContent(content);
        notice.setSendUserId(sendUserId);
        notice.setNoticeType(NoticeTypeEnum.OS);
        noticeDao.add(notice);

        Notice noticeOS = noticeDao.getById(notice.getId());


        List<User> users = userService.getUserByUserType(UserType.USER,1,Integer.MAX_VALUE);
        for(User user:users){
            Notice noticeUser = new Notice();
            noticeUser.setUserId(user.getId());
            noticeUser.setNoticeType(NoticeTypeEnum.OS);
            noticeUser.setNoticeId(noticeOS.getId());
            noticeDao.add(noticeUser);
        }
    }


    public void editOsNotice(long noticeId, String title, String content) {
        Notice notice = getNoticeById(noticeId);
        if(notice == null){
            throw new RuntimeException("消息不存在");
        }
        notice.setTitle(title);
        notice.setContent(content);
        noticeDao.update(notice);
    }



    public Notice getNoticeById(long noticeId) {
        return noticeDao.getById(noticeId);
    }

    
    public void updateNotice(Notice notice) {
        noticeDao.update(notice);
    }

    
    public void deleteNotice(long noticeId) {
        Notice notice = getNoticeById( noticeId);
        if(notice != null){
            noticeDao.delete(notice);
        }
    }


    public void sendNotice(sendNoticeType sendType,long sendUserId,String param){



    }

    private void sendFollowInfo(long sendUserId,String info,String pushTitle){


    }





    public enum sendNoticeType{
        livePrediction,
        liveStart,
        orderPaySuccess,
        orderCheckReceive,

    }




}
