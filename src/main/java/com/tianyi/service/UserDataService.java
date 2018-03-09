package com.tianyi.service;

import com.tianyi.bo.UserData;
import com.tianyi.dao.UserDataDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by 雪峰 on 2018/1/3.
 */
@Service("userDataService")
public class UserDataService {
    @Autowired
    UserDataDao userDataDao;


    public UserData getUserDataByUserId(long userId){
        UserData userData = userDataDao.getUserDataByUserId(userId);
        if(userData == null){
            userData = new UserData();
            userData.setUserId(userId);
            userDataDao.add(userData);
        }

        return  userDataDao.getUserDataByUserId(userId);
    }


    public void updateUserData(UserData userData){
        userDataDao.update(userData);
    }

}
