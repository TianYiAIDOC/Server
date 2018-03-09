package com.tianyi.dao;

import com.tianyi.bo.UserDayData;
import com.tianyi.bo.enums.UserDayEnum;
import com.tianyi.dao.base.BaseDao;
import com.tianyi.web.util.DateUtil;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by 雪峰 on 2018/1/1.
 */
@Repository
public class UserDayDataDao extends BaseDao<UserDayData> {

    public UserDayData getDatDataByUserIdAndDay(final long userId, UserDayEnum dayEnum,String day) {
        return execute(session -> session
                .createNativeQuery("SELECT * FROM user_day_data WHERE user_id=:userId and day_type=:dayType and DATE_FORMAT(created_on,'%Y-%m-%d') =:day", UserDayData.class)
                .setParameter("userId", userId)
                .setParameter("dayType", dayEnum.ordinal())
                .setParameter("day",day)
                .uniqueResult());
    }


    public List<UserDayData> getUserDayDatas(final long userId, UserDayEnum dayEnum, final int page, final int pageSize) {

        List<UserDayData> results = execute(session -> (List<UserDayData>) session
                .createNativeQuery("SELECT * FROM user_day_data WHERE user_id=:userId and day_type=:dayType order by id desc", UserDayData.class)
                .setParameter("userId", userId)
                .setParameter("dayType", dayEnum.ordinal())
                .setFirstResult((page - 1) * pageSize)
                .setMaxResults(pageSize)
                .getResultList());


        return results != null ? results : new ArrayList<UserDayData>();
    }

    public long getTotlaNumber(final long userId, UserDayEnum dayEnum) {
        BigInteger num = execute(session -> (BigInteger) session
                .createNativeQuery("select count(*)  from user_day_data where user_id=:userId and day_type=:dayType")
                .setParameter("userId", userId)
                .setParameter("dayType", dayEnum.ordinal())
                .getSingleResult());

        return num == null ? 0 : num.longValue();
    }


    public List<UserDayData> getUserDayDatasByMotnth(final long userId, UserDayEnum dayEnum, String month) {

        List<UserDayData> results = execute(session -> (List<UserDayData>) session
                .createNativeQuery("SELECT * FROM user_day_data WHERE user_id=:userId and day_type=:dayType and DATE_FORMAT(created_on,'%Y-%m') =:month order by id desc", UserDayData.class)
                .setParameter("userId", userId)
                .setParameter("dayType", dayEnum.ordinal())
                .setParameter("month", month)
                .getResultList());


        return results != null ? results : new ArrayList<UserDayData>();
    }


    public long getTotlaNumberByDay(final String day, UserDayEnum dayEnum) {
        BigDecimal num = execute(session -> (BigDecimal) session
                .createNativeQuery("select sum(data_number)  FROM user_day_data WHERE day_type=:dayType and DATE_FORMAT(created_on,'%Y-%m-%d') =:day")
                .setParameter("dayType", dayEnum.ordinal())
                .setParameter("day", day)
                .getSingleResult());

        return num == null ? 0 : num.longValue();
    }

//
//    public List<YesterDayStepVo> getUserDayDatasYesterday() {
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.DAY_OF_MONTH, -1);
//
////        List<YesterDayStepVo> results = execute(session -> (List<YesterDayStepVo>) session.createQuery(
////                "SELECT new com.tianyi.dao.vo.YesterDayStepVo(user_id,sum(data_number)) FROM user_day_data WHERE day_type=1 and DATE_FORMAT(created_on,'%Y-%c-%d') ='"+DateUtil.format(calendar.getTime(), "yyyy-MM-dd")+"' order by id desc")
////                .getResultList());
////        return results;
//
//        String hql = "SELECT new com.tianyi.dao.vo.YesterDayStepVo(user_id,sum(data_number)) FROM UserDayData WHERE day_type=1 and DATE_FORMAT(created_on,'%Y-%c-%d') ='" + DateUtil.format(calendar.getTime(), "yyyy-MM-dd") + "' order by id desc";
//
//        Query query = execute(session -> session.createQuery(hql, UserDayData.class));
//
//
//        return query.getResultList();
//    }


    public List<Object[]> getUserDayDatasDay(Date date) {

        List<Object[]> results = execute(session -> (List<Object[]>) session.createNativeQuery(
                "SELECT user_id,sum(data_number) FROM user_day_data WHERE day_type=1 and DATE_FORMAT(created_on,'%Y-%m-%d') ='"+DateUtil.format(date, "yyyy-MM-dd")+"' group by user_id")
                .getResultList());

        return results == null?new ArrayList<>():results;
    }

}
