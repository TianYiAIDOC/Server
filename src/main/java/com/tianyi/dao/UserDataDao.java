package com.tianyi.dao;

import com.tianyi.bo.UserData;
import com.tianyi.dao.base.BaseDao;
import org.springframework.stereotype.Repository;

/**
 * Created by 雪峰 on 2018/1/1.
 */
@Repository
public class UserDataDao extends BaseDao<UserData> {

    public UserData getUserDataByUserId(final long userId) {
        return execute(session -> session
                .createNativeQuery("SELECT * FROM user_data WHERE user_id=:userId ", UserData.class)
                .setParameter("userId", userId)
                .uniqueResult());
    }
}
