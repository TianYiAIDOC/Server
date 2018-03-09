package com.tianyi.web.controller;


import com.tianyi.bo.Banner;
import com.tianyi.bo.enums.BannerTypeEnum;
import com.tianyi.service.BannerService;
import com.tianyi.web.model.ActionResult;
import com.tianyi.web.model.BannerModel;
import com.tianyi.web.model.PagedListModel;
import com.tianyi.web.util.DateUtil;
import com.tianyi.web.util.JsonPathArg;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 雪峰 on 2018/1/1.
 */
@RestController
public class BannerController {

    @Resource
    private BannerService bannerService;

    @RequestMapping(value = "/banners", method = RequestMethod.GET)
    public List<BannerModel> getBanners(@RequestParam(value = "group", required = false) Integer group) {
        group = group ==null?0:group;

        List<BannerModel> modelList = new ArrayList<BannerModel>();

        List<Banner> banners = bannerService.getBanners(group,1, 10);
        for(Banner banner:banners){
            BannerModel bannerModel = new BannerModel();
            bannerModel.setImage(banner.getFileKey());
            bannerModel.setUrl(banner.getGoUrl());
            bannerModel.setType(banner.getBannerType().ordinal());
            bannerModel.setId(banner.getId());
            modelList.add(bannerModel);
        }


        return modelList;
    }

    @RequestMapping(value = "/admin/banners",method= RequestMethod.GET)
    public PagedListModel< Map<String,Object>> getBanners(@RequestParam("p") Integer page, @RequestParam("p_size") Integer pageSize) {
        page = page == null ? 0 : page;
        pageSize = pageSize == null ? 20 : pageSize;

        List<Map<String,Object>> modelList = new ArrayList< Map<String,Object>>();

        List<Banner> banners = bannerService.getBanners(page,pageSize);
        long totalNum = bannerService.getTotalNum();
        for(Banner banner:banners){
            modelList.add(getMapFromBanner(banner));
        }
        return new PagedListModel(modelList,totalNum,page,pageSize);
    }

    @RequestMapping(value = "/admin/banners",method= RequestMethod.POST)
    public Map<String,Object> addBanners(@JsonPathArg("title") String file_name,
                                         @JsonPathArg("image") String file,
                                         @JsonPathArg("url") String gourl) {
        Banner banner = bannerService.addBanner(file_name, file, gourl,1, BannerTypeEnum.LIVE);
        return  getMapFromBanner(banner);
    }

    @RequestMapping(value = "/admin/banners/{id}",method= RequestMethod.PUT)
    public ActionResult editBanners(@PathVariable("id") long bannerId, @JsonPathArg("title") String file_name,
                                    @JsonPathArg("image") String file,
//                                    @JsonPathArg("order") int order,
                                    @JsonPathArg("url") String gourl) {
        Banner banner =bannerService.getBannerById(bannerId) ;
        if(banner == null){
            throw new RuntimeException("banner不存在");
        }
        banner.setName(file_name);
//        banner.setSortOrder(order);
        banner.setGoUrl(gourl);
        banner.setFileKey(file);
        bannerService.editBanner(banner);


        return  new ActionResult();
    }

    @RequestMapping(value = "/admin/banners/{id}",method= RequestMethod.DELETE)
    public ActionResult delBanners(@PathVariable("id") long bannerId) {
       bannerService.delBanner(bannerId);
        return  new ActionResult();
    }

    private Map<String,Object> getMapFromBanner(Banner banner){
        Map<String,Object> map = new HashMap<>();
        map.put("id",banner.getId());
        map.put("title",banner.getName());
        map.put("image",banner.getFileKey());
        map.put("created_at", DateUtil.format(banner.getCreatedOn()));
//        map.put("order",banner.getSortOrder());
        map.put("url",banner.getGoUrl());
        return map;
    }
}
