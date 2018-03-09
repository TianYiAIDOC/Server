package com.tianyi.dao;

import com.tianyi.bo.UserSession;
import com.tianyi.dao.base.BaseDao;
import org.springframework.stereotype.Repository;

/**
 * Created by 雪峰 on 2018/1/1.
 */
@Repository
public class UserSessionDao extends BaseDao<UserSession> {
    public UserSession getUserSessionByUserId(final long userId) {
        return execute(session -> session
                .createNativeQuery("SELECT * FROM user_session WHERE  user_id=:user_id", UserSession.class)
                .setParameter("user_id", userId)
                .uniqueResult());
    }

    public UserSession getUserSessionByToken(final String token) {
        return execute(session -> session
                .createNativeQuery("SELECT * FROM user_session WHERE   token=:token", UserSession.class)
                .setParameter("token", token)
                .uniqueResult());
    }
}
