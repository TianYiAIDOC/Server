package com.tianyi.web.controller;

import com.tianyi.bo.AidocSet;
import com.tianyi.bo.User;
import com.tianyi.service.AidocSetService;
import com.tianyi.web.AuthRequired;
import com.tianyi.web.model.ActionResult;
import com.tianyi.web.model.PagedListModel;
import com.tianyi.web.util.JsonPathArg;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 雪峰 on 2018/1/18.
 */
@RestController
public class AidocSetController {

    @Resource
    AidocSetService aidocSetService;

    @AuthRequired
    @RequestMapping(value = "/manager/aidoc", method = RequestMethod.POST)
    public ActionResult addAidocSet(
                                        @JsonPathArg("type") int type,
                                       @JsonPathArg("startDay") String startDay,
                                       @JsonPathArg("endDay") String endDay,
                                       @JsonPathArg("amount") int amount,
                                       @Value("#{request.getAttribute('currentUser')}") User currentUser) {

        aidocSetService.createAidocSet(0,type,startDay,endDay,amount,currentUser.getId());


        return new ActionResult();
    }

    @AuthRequired
    @RequestMapping(value = "/manager/aidoc/{aidoc_id}", method = RequestMethod.PUT)
    public ActionResult editAidoc(@PathVariable("aidoc_id") int aidocId,
                                         @JsonPathArg("type") int type,
                                         @JsonPathArg("startDay") String startDay,
                                         @JsonPathArg("endDay") String endDay,
                                         @JsonPathArg("amount") int amount,
                                       @Value("#{request.getAttribute('currentUser')}") User currentUser) {
        aidocSetService.createAidocSet(aidocId,type,startDay,endDay,amount,currentUser.getId());
        return new ActionResult();
    }



    @AuthRequired
    @RequestMapping(value = "/manager/aidoc/{type}", method = RequestMethod.GET)
    public PagedListModel<List<Map<String, Object>>> getaIdocs(@PathVariable("type") int type,
                                                             @RequestParam(value = "p", required = false) Integer page,
                                                             @RequestParam(value = "p_size", required = false) Integer pageSize) {
        page = page == null ? 1 : page;
        pageSize = pageSize == null ? 20 : pageSize;
        List<Map<String, Object>> result = new ArrayList<>();

        List<AidocSet> aidocSets = aidocSetService.getAidocSets(type,page,pageSize);
        long total = aidocSetService.getTotalNumber(type,0);

        for(AidocSet as:aidocSets){
            Map<String,Object> map = new HashMap<>();
            map.put("id",as.getId());
            map.put("startDay",as.getEffectiveDate());
            map.put("endDay",as.getInvalidDate());
            map.put("amount",as.getAidocTotal());
            map.put("type",as.getSetType());
            result.add(map);
        }

        return new PagedListModel(result, total, page, pageSize);
    }

    @AuthRequired
    @RequestMapping(value = "/manager/aidoc/formula", method = RequestMethod.GET)
    public Map<String, Object> getFormula(@RequestParam(value = "type", required = false) Integer type) {
        type = type == null ? 0 : type;

            Map<String,Object> map = new HashMap<>();
            map.put("formula","weight*step*0.5*1.036");
        return map;
    }



}
