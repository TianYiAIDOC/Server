package com.tianyi.web.controller;

import com.tianyi.bo.DataRegion;
import com.tianyi.bo.enums.LanguageEnum;
import com.tianyi.service.AreaService;
import com.tianyi.service.CountryService;
import com.tianyi.service.I18nService;
import com.tianyi.web.model.PagedListModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 雪峰 on 2018/1/1.
 */
@RestController
public class AreaController {
    @Resource
    private AreaService areaService;
    @Resource
    private I18nService i18nService;
    @Resource
    private CountryService countryService;


//    @AuthRequired
//    @RequestMapping(value = "/admin/products/cities/opened", method = RequestMethod.PUT)
//    public ActionResult openCity(@JsonPathArg("areaIds") Integer[] areaIds) {
//        areaService.openArea(areaIds);
//        return new ActionResult();
//    }
//
//
//    @AuthRequired
//    @RequestMapping(value = "/admin/products/cities/opened", method = RequestMethod.GET)
//    public List<AreaModel> getOpenCitys() {
//
//        List<AreaModel> modelList = new ArrayList<AreaModel>();
//        List<Area> areas = areaService.getAreasAll(true);
//        Map<Integer, AreaModel> shengMap = new TreeMap<Integer, AreaModel>();
//        Map<Integer, List<AreaModel>> shiMap = new TreeMap<Integer, List<AreaModel>>();
//        Map<Integer, List<AreaModel>> xianMap = new TreeMap<Integer, List<AreaModel>>();
//
//        for (Area area : areas) {
//            AreaModel aeraModel = new AreaModel();
//            aeraModel.setName(area.getArea());
//            aeraModel.setId(area.getAreaId());
//
//            if (area.getLevel() == 1) {
//                shengMap.put(area.getAreaId(), aeraModel);
//            } else if (area.getLevel() == 2) {
//                List<AreaModel> shiArea = shiMap.get(area.getParentId());
//                if (shiArea == null) {
//                    shiArea = new ArrayList<AreaModel>();
//                }
//                shiArea.add(aeraModel);
//                shiMap.put(area.getParentId(), shiArea);
//            } else if (area.getLevel() == 3) {
//                List<AreaModel> xianArea = xianMap.get(area.getParentId());
//                if (xianArea == null) {
//                    xianArea = new ArrayList<AreaModel>();
//                }
//                xianArea.add(aeraModel);
//                xianMap.put(area.getParentId(), xianArea);
//            }
//        }
//        for (Map.Entry<Integer, AreaModel> entry : shengMap.entrySet()) {
//            Integer key = (Integer) entry.getKey();
//            AreaModel areaModel = (AreaModel) entry.getValue();
//            List<AreaModel> shi_values = shiMap.get(key);
//            List<AreaModel> shi_value2 = new ArrayList<>();
//            for (AreaModel shi_areaModel : shi_values) {
//                shi_areaModel.setChildren(xianMap.get(shi_areaModel.getId()));
//                shi_value2.add(shi_areaModel);
//            }
//            areaModel.setChildren(shi_value2);
//            modelList.add(areaModel);
//        }
//        return modelList;
//    }
//
////    @AuthRequired
//    @RequestMapping("/lib/cities")
//    public List<AreaModel> getAreasAll() {
//        List<AreaModel> modelList = new ArrayList<AreaModel>();
//
//        List<Area> areas = areaService.getAreasAll();
//        Map<Integer, AreaModel> shengMap = new TreeMap<Integer, AreaModel>();
//        Map<Integer, List<AreaModel>> shiMap = new TreeMap<Integer, List<AreaModel>>();
//        Map<Integer, List<AreaModel>> xianMap = new TreeMap<Integer, List<AreaModel>>();
//
//        for (Area area : areas) {
//            AreaModel aeraModel = new AreaModel();
//            aeraModel.setName(area.getArea());
//            aeraModel.setId(area.getAreaId());
//
//            if (area.getLevel() == 1) {
//                shengMap.put(area.getAreaId(), aeraModel);
//            } else if (area.getLevel() == 2) {
//                List<AreaModel> shiArea = shiMap.get(area.getParentId());
//                if (shiArea == null) {
//                    shiArea = new ArrayList<AreaModel>();
//                }
//                shiArea.add(aeraModel);
//                shiMap.put(area.getParentId(), shiArea);
//            } else if (area.getLevel() == 3) {
//                List<AreaModel> xianArea = xianMap.get(area.getParentId());
//                if (xianArea == null) {
//                    xianArea = new ArrayList<AreaModel>();
//                }
//                xianArea.add(aeraModel);
//                xianMap.put(area.getParentId(), xianArea);
//            }
//        }
//        for (Map.Entry<Integer, AreaModel> entry : shengMap.entrySet()) {
//            Integer key = (Integer) entry.getKey();
//            AreaModel areaModel = (AreaModel) entry.getValue();
//            List<AreaModel> shi_values = shiMap.get(key);
//            List<AreaModel> shi_value2 = new ArrayList<>();
//            for (AreaModel shi_areaModel : shi_values) {
//                shi_areaModel.setChildren(xianMap.get(shi_areaModel.getId()));
//                shi_value2.add(shi_areaModel);
//            }
//            areaModel.setChildren(shi_value2);
//            modelList.add(areaModel);
//        }
//        return modelList;
//    }

    @RequestMapping("/lib/area")
    public PagedListModel<List<Map<String, Object>>> getACountryByPid(@RequestParam(value = "p", required = false) Integer page,
                                                                      @RequestParam(value = "p_size", required = false) Integer pageSize,
                                                                      @RequestParam(value = "pid") Integer pid,
                                                                      @Value("#{request.getAttribute('lang')}") String lang) {
        List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
        List<DataRegion> dataRegions =null;
        page = page == null ? 0 : page;
        pageSize = pageSize == null ? Integer.MAX_VALUE : pageSize;
        int total = 0;
        if(pid ==null||pid == 0){
            dataRegions = countryService.getCountrys(page,pageSize);
            total = countryService.getCountryTotal();
        }else{
            dataRegions = countryService.getDataRegions(pid,page,pageSize);
            total = countryService.getDataRegionsTotal(pid);
        }


        for(DataRegion dataRegion:dataRegions){
            Map<String,Object> map = new HashMap<>();
            map.put("id",dataRegion.getId());
            if(LanguageEnum.en.name().equals(lang)){
                map.put("name",dataRegion.getNameEn());
            }else {
                map.put("name",dataRegion.getName());
            }
            result.add(map);
        }
        return new PagedListModel(result, total, page, pageSize);
    }

}