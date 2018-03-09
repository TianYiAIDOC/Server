package com.tianyi.dao;

import com.tianyi.bo.SMSCode;
import com.tianyi.dao.base.BaseDao;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 雪峰 on 2018/1/1.
 */
@Repository
public class SMSCodeDao extends BaseDao<SMSCode> {

    /**
     * 验证code是否有效
     */
    public SMSCode getSMSCodeByCode(final String code) {
        return execute(session -> session
                .createNativeQuery("SELECT * FROM smscode WHERE code=:code ", SMSCode.class)
                .setParameter("code", code)
                .uniqueResult());

    }

    public List<SMSCode> getSMSCodes() {
        return execute(session -> {
            Query<SMSCode> query = session.createNativeQuery("SELECT * FROM smscode ", SMSCode.class);
            return query.getResultList();
        });
    }


}
