package com.tianyi.dao;

import com.tianyi.bo.User;
import com.tianyi.bo.UserDataResult;
import com.tianyi.bo.enums.SexEnum;
import com.tianyi.bo.enums.UserStatusEnum;
import com.tianyi.bo.enums.UserType;
import com.tianyi.dao.base.BaseDao;
import com.tianyi.web.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by 雪峰 on 2018/1/1.
 */
@Repository
public class UserDao extends BaseDao<User> {

    public User getUserByUsername(final String username) {
        return execute(session -> session
                .createNativeQuery("SELECT * FROM user WHERE username=:username", User.class)
                .setParameter("username", username)
                .uniqueResult());
    }

    public User getUserByUsername(final String username, final UserType userType) {
        return execute(session -> session
                .createNativeQuery("SELECT * FROM user WHERE username=:username and user_type=:user_type", User.class)
                .setParameter("username", username)
                .setParameter("user_type",  userType.ordinal())
                .uniqueResult());
    }

    public User getUserByMobile(final int countryCode ,final String mobile) {
        return execute(session -> session
                .createNativeQuery("SELECT * FROM user WHERE mobile=:mobile and country_code=:countryCode", User.class)
                .setParameter("mobile", mobile)
                .setParameter("countryCode", countryCode)
                .uniqueResult());
    }

    public User getUserByMobile(final int countryCode ,final String mobile, final UserType userType) {
        return execute(session -> session
                .createNativeQuery("SELECT * FROM user WHERE mobile=:mobile and user_type=:user_type", User.class)
                .setParameter("mobile", mobile)
                .setParameter("user_type",  userType.ordinal())
                //.setParameter("countryCode", countryCode)
                .uniqueResult());
    }

    public List<User> getUserList(final UserType userType, final int page, final int pageSize) {
        List<User> results = execute(session -> (List<User>) session
                .createNativeQuery("SELECT * FROM user where user_type=:user_type ORDER BY id DESC ", User.class)
                .setParameter("user_type", userType.ordinal())
                .setFirstResult((page - 1) * pageSize)
                .setMaxResults(pageSize)
                .getResultList());
        return results != null ? results : new ArrayList<User>();
    }

    public BigInteger getUserTotalNum(final UserType userType) {
        return execute(session -> (BigInteger) session
                .createNativeQuery("select count(*)  from user where user_type=:user_type")
                .setParameter("user_type",  userType.ordinal())
                .getSingleResult());
    }



    public List<User> getUserList(final UserType userType, final int page, final int pageSize, final int start_areaId, final int end_areaId, final String keyword, final UserStatusEnum userStatus,
                                  final SexEnum sex ) {


        StringBuffer strb = new StringBuffer("select * from user  where  1=1");

        if (start_areaId != 0 && end_areaId == 0) {
            strb.append(" and area_id = ").append(start_areaId);
        } else if (start_areaId != 0 && end_areaId != 0) {
            strb.append(" and area_id > ").append(start_areaId);
            strb.append(" and  area_id <").append(end_areaId);
        }
        if (!StringUtils.isEmpty(keyword)) {
            if(keyword.length() == 11&&keyword.matches("[0-9]+")){
                strb.append(" and mobile = '").append(keyword).append("'");
            }else{
                strb.append(" and real_name like '%").append(keyword).append("%'");
            }
        }
        if(userStatus !=null){
            strb.append(" and user_status = ").append(userStatus.ordinal());
        }
        if(sex !=null){
            strb.append(" and sex =").append(sex.ordinal());
        }

        strb.append(" and user_type =").append(userType.ordinal());

        strb.append(" order by id desc");


        List<User> results = execute(session -> session
                .createNativeQuery(strb.toString(), User.class)
                .setFirstResult((page - 1) * pageSize)
                .setMaxResults(pageSize)
                .getResultList());
        return results != null ? results : new ArrayList<User>();
    }


    public long getTotalUserNumber(final UserType userType, final int start_areaId, final int end_areaId, final String keyword, final UserStatusEnum userStatus,
                                         final SexEnum sex ) {
        StringBuffer strb = new StringBuffer("select count(*) from user  where 1=1");

        if (start_areaId != 0 && end_areaId == 0) {
            strb.append(" and area_id = ").append(start_areaId);
        } else if (start_areaId != 0 && end_areaId != 0) {
            strb.append(" and area_id > ").append(start_areaId);
            strb.append(" and  area_id <").append(end_areaId);
        }


        if (!StringUtils.isEmpty(keyword)) {
            if(keyword.length() == 11&&keyword.matches("[0-9]+")){
                strb.append(" and mobile = '").append(keyword).append("'");
            }else{
                strb.append(" and real_name like '%").append(keyword).append("%'");
            }
        }
        if(userStatus !=null){
            strb.append(" and user_status = ").append(userStatus.ordinal());
        }
        if(sex !=null){
            strb.append(" and sex =").append(sex.ordinal());
        }

        strb.append(" and user_type =").append(userType.ordinal());
        BigInteger num =  execute(session -> (BigInteger) session
                .createNativeQuery(strb.toString())
                .getSingleResult());

        return num ==null?0:num.longValue();
    }




    public int getTotalUserNumber(final UserType userType, final Date start_Date, final Date end_Date) {
        BigInteger num =   execute(session -> (BigInteger)session
                .createNativeQuery("select count(*) from user where user_type=:user_type and created_on>:start_date and created_on<:end_date")
                .setParameter("user_type",  userType.ordinal())
                .setParameter("start_date",  DateUtil.formatTime(start_Date))
                .setParameter("end_date",  DateUtil.formatTime(end_Date))
                .getSingleResult());
        return num ==null?0:num.intValue();
    }


    public List<UserDataResult> getUserRanking(){

        StringBuffer sql = new StringBuffer(
                "select " +
                        "u.mobile mobile, " +
                        "u.nickname nickname, " +
                        "ifnull(u.signature,'未设置') signature, " +
                        "d.user_id userId, " +
                        "d.data_number dataNumber, " +
                        "u.avatar avatar  "+
                        "from user u  " +
                        "inner join ( " +
                        "SELECT user_id,data_number FROM user_day_data " +
                        "where " +
                        "left(created_on,10)=left(now(),10) " +
                        "order by data_number desc " +
                        "limit 50 " +
                        ") d on d.user_id=u.id " +
                        " ");

        Object results = execute(session ->session
                .createNativeQuery(sql.toString()).getResultList()
        );
        return results != null ? (ArrayList<UserDataResult>)results : new ArrayList<UserDataResult>();
    }

    public List<UserDataResult> getUserTotalRanking(){

        StringBuffer sql = new StringBuffer(
                "select " +
                        "u.mobile, " +
                        "u.nickname, " +
                        "ifnull(u.signature,'未设置') signature, " +
                        "d.user_id userId, " +
                        "d.data_number  dataNumber, " +
                        "u.avatar "+
                        "from user u  " +
                        "inner join ( " +
                        "SELECT max(user_id) user_id,sum(data_number) data_number FROM user_day_data " +
                        "group by user_id "+
                        "order by sum(data_number) desc " +
                        "limit 50 " +
                        ") d on d.user_id=u.id " +
                        " ");





        Object results = execute(session ->session
                .createNativeQuery(sql.toString()).getResultList()
        );
        return results != null ? (ArrayList<UserDataResult>)results : new ArrayList<UserDataResult>();

    }


}
