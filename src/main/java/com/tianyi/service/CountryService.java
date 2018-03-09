package com.tianyi.service;

import com.tianyi.bo.CountryCode;
import com.tianyi.bo.DataRegion;
import com.tianyi.bo.enums.LanguageEnum;
import com.tianyi.dao.CountryCodeDao;
import com.tianyi.dao.DataRegionDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 雪峰 on 2018/2/7.
 */
@Service("countryCodeService")
public class CountryService {
    @Resource
    CountryCodeDao countryCodeDao;
    @Resource
    DataRegionDao dataRegionDao;

    public List<CountryCode> getCountryCodes(  int page,  int pageSize){
        return countryCodeDao.getCountryCodes(page,pageSize);
    }

    public List<DataRegion> getDataRegions(int pid, int page,  int pageSize){
        return dataRegionDao.getDataRegions(pid,page,pageSize);
    }

    public List<DataRegion> getCountrys(final int page, final int pageSize){
        return  dataRegionDao.getCountrys(page,pageSize);
    }

    public Integer getDataRegionsTotal(final int pid ){
        return  dataRegionDao.getDataRegionsTotal(pid);
    }

    public Integer getCountryTotal(){
        return dataRegionDao.getCountryTotal();
    }

    public DataRegion getDataRegionById(long id){
        return  dataRegionDao.getById(id);
    }



    public String getAreaName(int id,LanguageEnum lang){
        List<String> strs = new ArrayList<>();
        if(id!=0){
            DataRegion dataRegion = getDataRegionById(id);
            if(dataRegion == null ){
                return "";
            }
            if(lang == LanguageEnum.en){
                strs.add(dataRegion.getNameEn());
            }else{
                strs.add(dataRegion.getName());
            }
            int pid = dataRegion.getPid();
            for(int i = dataRegion.getLevel();i>0;i--){
                dataRegion = getDataRegionById(pid);
                if(dataRegion == null){
                    break;
                }
                pid = dataRegion.getPid();

                if(dataRegion.getLevel() < 2){
                    break;
                }

                if(lang == LanguageEnum.en){
                    strs.add(dataRegion.getNameEn());
                }else{
                    strs.add(dataRegion.getName());
                }


            }
        }
        StringBuffer strb = new StringBuffer();
        for(int i = strs.size()-1;i>=0;i--){
            strb.append(strs.get(i)).append("-");
        }
        if(strb.indexOf("-")>0){
            return strb.substring(0,strb.length()-1);
        }
        return strb.toString();
    }
}
