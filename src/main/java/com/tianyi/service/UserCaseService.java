package com.tianyi.service;

import com.tianyi.bo.UserCase;
import com.tianyi.dao.UserCaseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 雪峰 on 2018/1/1.
 */
@Service("userCaseService")
public class UserCaseService {

    @Autowired
    UserCaseDao userCaseDao;

    public void add(UserCase userCase) {
        userCaseDao.add(userCase);
    }

    public List<UserCase> getUserCaseByUserId(long userId){
        return  userCaseDao.getUserCaseListByUserId(userId);
    }

    public UserCase getById(long id){
        return userCaseDao.getById(id);
    }

    public void deleteById(long id){

        UserCase userCase = userCaseDao.getById(id);
        userCase.setCaseStatus(0);

        userCaseDao.update(userCase);
    };
}