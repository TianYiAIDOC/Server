package com.tianyi.dao;

import com.tianyi.bo.Account;
import com.tianyi.dao.base.BaseDao;
import org.springframework.stereotype.Repository;

/**
 * Created by 雪峰 on 2018/1/16.
 */
@Repository
public class AccountDao extends BaseDao<Account> {

    public Account getAccountByUserId(final long userId) {
        return execute(session -> session
                .createNativeQuery("SELECT * FROM account WHERE user_id=:userId ", Account.class)
                .setParameter("userId", userId)
                .uniqueResult());
    }
}
