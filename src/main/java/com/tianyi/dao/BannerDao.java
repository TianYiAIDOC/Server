package com.tianyi.dao;


import com.tianyi.bo.Banner;
import com.tianyi.dao.base.BaseDao;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 雪峰 on 2018/1/1.
 */
@Repository
public class BannerDao extends BaseDao<Banner> {

    public List<Banner> getBanners(final int type, final int page, final int pageSize) {

        List<Banner> results = execute(session -> (List<Banner>) session
                .createNativeQuery("SELECT * FROM banner where  banner_type=:banner_type", Banner.class)
                .setParameter("banner_type", type)
                .setFirstResult((page - 1) * pageSize)
                .setMaxResults(pageSize)
                .getResultList());


        return results != null ? results : new ArrayList<Banner>();
    }

    public BigInteger getTotlaNumber() {
        return execute(session -> (BigInteger) session
                .createNativeQuery("select count(*)  from banner")
                .getSingleResult());
    }


    public List<Banner> getBanners(final int page, final int pageSize) {
        List<Banner> results = execute(session -> (List<Banner>) session
                .createNativeQuery("SELECT * FROM banner ", Banner.class)
                .setFirstResult((page - 1) * pageSize)
                .setMaxResults(pageSize)
                .getResultList());
        return results != null ? results : new ArrayList<Banner>();

    }
}
