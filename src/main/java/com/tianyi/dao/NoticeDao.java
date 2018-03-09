package com.tianyi.dao;

import com.tianyi.bo.Notice;
import com.tianyi.bo.enums.NoticeTypeEnum;
import com.tianyi.dao.base.BaseDao;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 雪峰 on 2018/1/1.
 */
@Repository
public class NoticeDao extends BaseDao<Notice> {
    /**
     * 查找通知
     */
    public List<Notice> getNotices(final long userId, final int page, final int pageSize, final Boolean is_read, final NoticeTypeEnum noticeType) {
        StringBuffer strb = new StringBuffer("SELECT * FROM notice WHERE 1=1 ");
        if(userId > -1){
            strb.append(" and user_id = ").append(userId);
        }
        if(is_read != null){
            strb.append(" and is_read = ").append(is_read);
        }
        if(noticeType != null){
            strb.append(" and notice_type = ").append(noticeType.ordinal());
        }

        strb.append(" order by id desc");


        List<Notice> results = execute(session -> session
                .createNativeQuery(strb.toString(), Notice.class)
                .setFirstResult((page - 1) * pageSize)
                .setMaxResults(pageSize)
                .getResultList());
        return results != null ? results : new ArrayList<Notice>();

    }
    public int getTotalNum(final long userId, final Boolean is_read, final NoticeTypeEnum noticeType) {

        StringBuffer strb = new StringBuffer("select count(*)  from notice where 1=1 ");
        if(userId > -1){
            strb.append(" and user_id = ").append(userId);
        }
        if(is_read != null){
            strb.append(" and is_read = ").append(is_read);
        }
        if(noticeType != null){
            strb.append(" and notice_type = ").append(noticeType.ordinal());
        }

        BigInteger num =  execute(session -> (BigInteger)session
                .createNativeQuery(strb.toString())
                .getSingleResult());

        return num ==null?0:num.intValue();
    }
}
