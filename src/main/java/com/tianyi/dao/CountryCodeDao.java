package com.tianyi.dao;

import com.tianyi.bo.CountryCode;
import com.tianyi.dao.base.BaseDao;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 雪峰 on 2018/2/7.
 */
@Repository
public class CountryCodeDao extends BaseDao<CountryCode> {


    /**
     * 国家简码列表
     */
    public List<CountryCode> getCountryCodes( final int page, final int pageSize) {
        StringBuffer strb = new StringBuffer("SELECT * FROM country_code WHERE 1=1 ");
        strb.append(" ORDER BY id desc ");

        List<CountryCode> results = execute(session -> session
                .createNativeQuery(strb.toString(), CountryCode.class)
                .setFirstResult((page - 1) * pageSize)
                .setMaxResults(pageSize)
                .getResultList());
        return results != null ? results : new ArrayList<CountryCode>();
    }
}
