package com.tianyi.dao;

import com.tianyi.bo.AidocSet;
import com.tianyi.dao.base.BaseDao;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 雪峰 on 2018/1/16.
 */
@Repository
public class AidocSetDao extends BaseDao<AidocSet> {

    public List<AidocSet> getAidocSets(final int type, final int page, final int pageSize,final int status) {

        List<AidocSet> results = execute(session -> (List<AidocSet>) session
                .createNativeQuery("SELECT * FROM aidoc_set WHERE set_type=:setType and status=:status order by id desc", AidocSet.class)
                .setParameter("setType", type)
                .setParameter("status",status)
                .setFirstResult((page - 1) * pageSize)
                .setMaxResults(pageSize)
                .getResultList());


        return results != null ? results : new ArrayList<AidocSet>();
    }



    public Integer getTotalNumber(final int type,final int status) {
        StringBuffer strb = new StringBuffer("SELECT count(*) FROM  aidoc_set WHERE set_type=:setType and status=:status order by id desc ");
        BigInteger num = execute(session -> (BigInteger) session
                .createNativeQuery(strb.toString())
                .setParameter("setType", type)
                .setParameter("status",status)
                .getSingleResult());
        return num == null ? 0 : num.intValue();
    }

    public List<AidocSet> getAidocSetsByDay(final int type, final String day,int status) {

        List<AidocSet> results = execute(session -> (List<AidocSet>) session
                .createNativeQuery("SELECT * FROM aidoc_set WHERE effective_date<:day and invalid_date >:day and set_type=:setType  and status=:status order by id desc", AidocSet.class)
                .setParameter("setType", type)
                .setParameter("day", day)
                .setParameter("status",status)
                .getResultList());


        return results != null ? results : new ArrayList<AidocSet>();
    }
}
