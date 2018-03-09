package com.tianyi.service;

import com.tianyi.dao.AreaDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by 雪峰 on 2018/1/1.
 */
@Service("areaService")
public class AreaService {
    @Resource
    private AreaDao areaDao;


//    public List<Area> getAreaOpensByParentId(int parentId) {
//        return areaDao.getAreas(parentId, true);
//    }
//
//
//    public List<Area> getAreaAllsByParentId(int parentId) {
//        return areaDao.getAreas(parentId);
//    }
//
//
//    public Area getAreaById(int areaId) {
//        return areaDao.getAreaByAreaId(areaId);
//    }
//
//
//    public void openArea(Integer[] areaIds) {
//        areaDao.updateAreaAllClose();
//        for (Integer aid : areaIds) {
//            Area area = areaDao.getAreaByAreaId(aid);
//            area.setUse(true);
//            areaDao.update(area);
//        }
//    }
//
//    public List<Area> getAreasAll() {
//        return areaDao.getAreaAll();
//    }
//
//    public List<Area> getAreasAll(boolean isOpen) {
//        return areaDao.getAreasOpenedAll(isOpen);
//    }
//
//    public int[] getSection(int areaId) {
//        Area area = getAreaById(areaId);
//        if (area == null) {
//            return null;
//        }
//        if (area.getLevel() == 3) {
//            return new int[]{(area.getAreaId()), 0};
//        } else if (area.getLevel() == 1) {
//            return new int[]{(area.getAreaId()), (area.getAreaId() + 10000)};
//        } else if (area.getLevel() == 2) {
//            return new int[]{(area.getAreaId()), (area.getAreaId() + 100)};
//        }
//        return null;
//    }
//
//
//    public Map<Integer,Area> getAreaMapFromAllArea(){
//        Map<Integer,Area> result = new HashedMap();
//        List<Area> areas =  getAreasAll();
//        for(Area area:areas){
//            result.put(area.getAreaId(),area);
//        }
//        return result;
//
//    }
//    public String getShengShiXianName(Map<Integer,Area> allAreaMap,int areaId){
//        Area area = allAreaMap.get(areaId);
//        if (area == null) {
//            return "";
//        }
//        if (area.getParentId() == 0) {
//            return area.getArea();
//        }
//        Area twoarea = allAreaMap.get(area.getParentId());
//        if (twoarea.getParentId() == 0) {
//            return twoarea.getArea() + "-" + area.getArea();
//        }
//        Area threearea = allAreaMap.get(twoarea.getParentId());
//        return threearea.getArea() + "-" + twoarea.getArea() + "-" + area.getArea();
//    }
//    public String getShengShiXianId(Map<Integer,Area> allAreaMap,int areaId){
//        Area area = allAreaMap.get(areaId);
//        if (area == null) {
//            return "";
//        }
//        if (area.getParentId() == 0) {
//            return String.valueOf(area.getAreaId());
//        }
//        Area twoarea = allAreaMap.get(area.getParentId());
//        if (twoarea.getParentId() == 0) {
//            return twoarea.getAreaId() + " " + area.getAreaId();
//        }
//        Area threearea = allAreaMap.get(twoarea.getParentId());
//        return threearea.getAreaId() + " " + twoarea.getAreaId() + " " + area.getAreaId();
//    }

}
