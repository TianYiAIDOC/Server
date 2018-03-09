package com.tianyi.service;

import com.tianyi.bo.UserDayData;
import com.tianyi.bo.enums.UserDayEnum;
import com.tianyi.dao.UserDayDataDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by 雪峰 on 2018/1/3.
 */
@Service("userDayDataService")
public class UserDayDataService {
    @Autowired
    UserDayDataDao userDayDataDao;
    @Autowired
    UserDayDataService userDayDataService;


    public void saveEveryDayData(long userId, UserDayEnum dayType, int dataNumber, String dataString) {

        UserDayData userDayData = new UserDayData();
        userDayData.setDataNumber(dataNumber);
        userDayData.setDataString(dataString);
        userDayData.setDayType(dayType);
        userDayData.setUserId(userId);
        userDayDataDao.add(userDayData);

    }


    public UserDayData getUserDayDataByDay(long userId, UserDayEnum dayType,String day) {
        return userDayDataDao.getDatDataByUserIdAndDay(userId, dayType,day);
    }

    public List<UserDayData> getUserDayDatasByUserId(long userId, UserDayEnum dayType, final int page, final int pageSize) {
        return userDayDataDao.getUserDayDatas(userId, dayType, page, pageSize);
    }

    public long getTotalNum(long userId, UserDayEnum dayType) {
        return userDayDataDao.getTotlaNumber(userId, dayType);
    }

    public void updateUserDayDat(UserDayData userDayData) {
        userDayDataDao.update(userDayData);
    }

    public List<UserDayData> getUserDayDatasByMotnth( long userId, UserDayEnum dayEnum, String month) {
        return  userDayDataDao.getUserDayDatasByMotnth(userId,dayEnum,month);
    }


    public long getTotlaNumberByDay(final String day, UserDayEnum dayEnum){
        return  userDayDataDao.getTotlaNumberByDay(day,dayEnum);
    }

    public List<Object[]> getUserDayDatasDay(Date date){
        return  userDayDataDao.getUserDayDatasDay(date);
    }
}
