package com.tianyi.dao;

import com.tianyi.bo.AccountDetail;
import com.tianyi.dao.base.BaseDao;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 雪峰 on 2018/1/16.
 */
@Repository
public class AccountDetailDao extends BaseDao<AccountDetail> {

    public List<AccountDetail> getAccountDetails(final long accountId ,final int page, final int pageSize) {

        List<AccountDetail> results = execute(session -> (List<AccountDetail>) session
                .createNativeQuery("SELECT * FROM account_detail WHERE account_id=:accountId order by id desc", AccountDetail.class)
                .setParameter("accountId", accountId)
                .setFirstResult((page - 1) * pageSize)
                .setMaxResults(pageSize)
                .getResultList());


        return results != null ? results : new ArrayList<AccountDetail>();
    }


    public long getDayMoney(long userId,String day) {

        BigDecimal num = execute(session -> (BigDecimal) session
                .createNativeQuery("select sum(ad.reward_amount)  FROM account_detail ad ,account a where ad.account_id = a.id and a.user_id =:userId and DATE_FORMAT(ad.created_on,'%Y-%m-%d') =:day")
                .setParameter("day", day)
                .setParameter("userId",userId)
                .getSingleResult());

        return num == null ? 0 : num.longValue();
    }
}
