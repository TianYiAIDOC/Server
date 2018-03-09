package com.tianyi.dao;

import com.tianyi.bo.UserCase;
import com.tianyi.dao.base.BaseDao;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 雪峰 on 2018/1/1.
 */
@Repository
public class UserCaseDao extends BaseDao<UserCase> {

    public List<UserCase> getUserCaseListByUserId(long userId){

        List<UserCase> results = execute(session -> (List<UserCase>) session
                .createNativeQuery("SELECT * FROM user_case where user_id=:user_id ORDER BY id DESC ", UserCase.class)
                .setParameter("user_id", userId)
                .getResultList());
        return results != null ? results : new ArrayList<UserCase>();
    }
}
