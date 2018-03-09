package com.tianyi.service;

import com.tianyi.bo.AppVersion;
import com.tianyi.dao.AppVersionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 雪峰 on 2017/10/17.
 */
@Service("appVersionService")
public class AppVersionService {


    @Autowired
    private AppVersionDao appVersionDao;

    public List<AppVersion> getAppVersionList(String version, String os) {

        return appVersionDao.getAppVersionList(version, os);
    }

    public List<AppVersion> getAppVersions( final int page, final int pageSize){
        return appVersionDao.getAppVersions(page,pageSize);
    }

    public Integer getTotalNumber(){
        return appVersionDao.getTotalNumber();
    }



    public AppVersion getAppVersionById(long id){
        return appVersionDao.getById(id);
    }

    public void addAppVersion(AppVersion appVersion ){
        appVersionDao.add(appVersion);
    }

    public void editAppVersion(AppVersion appVersion){
        appVersionDao.update(appVersion);
    }

    public void delAppVersion(AppVersion appVersion){
        appVersionDao.delete(appVersion);
    }
}
