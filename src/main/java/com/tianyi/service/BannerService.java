package com.tianyi.service;

import com.tianyi.bo.Banner;
import com.tianyi.bo.enums.BannerTypeEnum;
import com.tianyi.dao.BannerDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by 雪峰 on 2015/12/29.
 */
@Service("bannerService")
public class BannerService {
    @Resource
    private BannerDao bannerDao;
    
    public List<Banner> getBanners(int type,int page, int pageSize) {
        return bannerDao.getBanners(type, page,  pageSize);
    }

    
    public void delBanner(long id) {
        Banner banner  = getBannerById(id);
        if(banner == null){
            return;
        }
        bannerDao.delete(banner);
    }

    
    public Banner getBannerById(long id) {
        return bannerDao.getById(id);
    }

    
    public Banner addBanner(String name, String fileKeyUrl, String goUrl, int sortOrder, BannerTypeEnum bannerType) {
        Banner banner = new Banner();
        banner.setBannerType(bannerType);
        banner.setFileKey(fileKeyUrl);
        banner.setGoUrl(goUrl);
        banner.setName(name);
        banner.setSortOrder(sortOrder);
        bannerDao.add(banner);
        return getBannerById(banner.getId());
    }

    
    public void editBanner(Banner banner) {
        bannerDao.update(banner);
    }

    
    public List<Banner> getBanners( int page, int pageSize) {
        return bannerDao.getBanners(page,pageSize);
    }

    
    public long getTotalNum() {
        return bannerDao.getTotlaNumber().longValue();
    }
}
