package com.tianyi.dao;

import com.tianyi.bo.AppVersion;
import com.tianyi.dao.base.BaseDao;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 雪峰 on 2018/01/26.
 */
@Repository
public class AppVersionDao extends BaseDao<AppVersion> {

    public List<AppVersion> getAppVersionList(final String version,final String os) {


        List<AppVersion> results = execute(session -> (List<AppVersion>) session
                .createNativeQuery("select * from app_version  where  version >:version and mobile_os =:os order by id desc", AppVersion.class)
                .setParameter("version", version)
                .setParameter("os",os)
                .getResultList());


        return results != null ? results : new ArrayList<AppVersion>();
    }


    public List<AppVersion> getAppVersions( final int page, final int pageSize) {

        List<AppVersion> results = execute(session -> (List<AppVersion>) session
                .createNativeQuery("SELECT * FROM app_version order by id desc", AppVersion.class)
                .setFirstResult((page - 1) * pageSize)
                .setMaxResults(pageSize)
                .getResultList());


        return results != null ? results : new ArrayList<AppVersion>();
    }



    public Integer getTotalNumber() {
        StringBuffer strb = new StringBuffer("SELECT count(*) FROM  app_version  ");
        BigInteger num = execute(session -> (BigInteger) session
                .createNativeQuery(strb.toString())
                .getSingleResult());
        return num == null ? 0 : num.intValue();
    }



}
