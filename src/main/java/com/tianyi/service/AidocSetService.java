package com.tianyi.service;

import com.alibaba.fastjson.JSON;
import com.tianyi.bo.AidocSet;
import com.tianyi.bo.enums.UserDayEnum;
import com.tianyi.dao.AidocSetDao;
import com.tianyi.web.util.DateUtil;
import com.tianyi.web.util.Tools;
import org.apache.commons.lang.StringUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by 雪峰 on 2018/1/16.
 */
@Service("aidocSetService")
public class AidocSetService {

    @Resource
    AidocSetDao aidocSetDao;
    @Resource
    UserDayDataService userDayDataService;
    @Resource
    AccountService accountService;


    public void createAidocSet(long aiDocId, int type, String startDay, String endDay, int amount, long userId) {
        if (StringUtils.isEmpty(startDay)) {
            startDay = DateUtil.format(DateUtil.getCurrentDate(), DateUtil.C_DATE_PATTON_DEFAULT);
            endDay = DateUtil.format(DateUtil.getCurrentDate(), DateUtil.C_DATE_PATTON_DEFAULT);
        }


        if (type == 0) {
            List<AidocSet> aidocSets = aidocSetDao.getAidocSets(0, 0, Integer.MAX_VALUE, 0);
            for (AidocSet as : aidocSets) {
                as.setStatus(0);
                aidocSetDao.update(as);
            }
            addAidocSet(0, startDay, endDay, amount, userId);
        } else if (type == 1) {

            List<AidocSet> aidocSets = aidocSetDao.getAidocSetsByDay(1, startDay, 0);
            if (aidocSets != null && !aidocSets.isEmpty()) {
                throw new RuntimeException("时间冲突！！！");
            }
            aidocSets = aidocSetDao.getAidocSetsByDay(1, endDay, 0);
            if (aidocSets != null && !aidocSets.isEmpty()) {
                throw new RuntimeException("时间冲突！！！");
            }
            if (aiDocId > 0) {
                List<AidocSet> aidocSetsOld = aidocSetDao.getAidocSets(1, 0, Integer.MAX_VALUE, 0);
                for (AidocSet as : aidocSetsOld) {
                    if (as.getInvalidDate() != null && as.getInvalidDate().getTime() < DateUtil.getCurrentDate().getTime()) {
                        as.setStatus(0);
                        aidocSetDao.update(as);
                    }
                }
            }
            AidocSet aidocSet = aidocSetDao.getById(aiDocId);
            if (aidocSet == null) {
                addAidocSet(1, startDay, endDay, amount, userId);
            } else {
                editAidocSet(aidocSet, 1, startDay, endDay, amount, userId);
            }
        }


    }


    public List<AidocSet> getAidocSets(int type, int page, int pageSize) {
        return aidocSetDao.getAidocSets(type, page, pageSize, 0);
    }

    public Integer getTotalNumber(int type, int status) {
        return aidocSetDao.getTotalNumber(type, status);
    }

        @Scheduled(cron = "0 45 23 ? * *")
       // @Scheduled(cron = "*/30 * * * * *")
    private void aidoc() {

        List<AidocSet> aidocSets = aidocSetDao.getAidocSetsByDay(1, DateUtil.format(DateUtil.getCurrentDate(), "yyyy-MM-dd"), 1);

        if (aidocSets == null || aidocSets.isEmpty()) {
            aidocSets = aidocSetDao.getAidocSets(0, 0, 10, 1);
        }

        int namu = 100;
        if (aidocSets != null && !aidocSets.isEmpty()) {
            namu = aidocSets.get(0).getAidocTotal();
        }


        Calendar calendar = Calendar.getInstance();
        calendar.setTime(DateUtil.getCurrentDate());
        calendar.add(Calendar.DATE, -1);

        long stepTotal = userDayDataService.getTotlaNumberByDay(DateUtil.format(calendar.getTime(), DateUtil.C_DATE_PATTON_DEFAULT), UserDayEnum.STEPSNUM);

        if (stepTotal == 0) {
            return;
        }


        //用户获取数=今日总AIDOC/今日所有用户步数之和*本用户今日步数

        List<Object[]> userDayDataList = userDayDataService.getUserDayDatasDay(new Date());
            System.out.println("userDayDataList========"+ JSON.toJSONString(userDayDataList));
        for (Object[] vo : userDayDataList) {

            if (vo[0] == null || vo[1] == null) {
                continue;
            }


            BigInteger userId = (BigInteger) vo[0];
            BigDecimal total = (BigDecimal) vo[1];

            double ddd = Tools.getDecimalFour(namu * total.doubleValue() / stepTotal);

            if(ddd > 5){
                ddd = 5;
            }

            //System.out.println(stepTotal+"=="+namu+"===="+userId+"======"+total+"====="+ddd);
            //long userId, Integer coinNum,String channel,double coins

             accountService.coin(userId.longValue(),"give",(long)(ddd*1000));
        }


    }


    private void addAidocSet(int type, String startDay, String endDay, int amount, long userId) {
        AidocSet aidocSet = new AidocSet();
        aidocSet.setAidocTotal(amount);
        aidocSet.setEffectiveDate(DateUtil.parseDate(DateUtil.C_DATE_PATTON_DEFAULT, startDay));
        aidocSet.setInvalidDate(DateUtil.parseDate(DateUtil.C_DATE_PATTON_DEFAULT, endDay));
        aidocSet.setSetType(type);
        aidocSet.setStatus(0);
        aidocSet.setOperationUserId(userId);
        aidocSetDao.add(aidocSet);
    }


    private void editAidocSet(AidocSet aidocSet, int type, String startDay, String endDay, int amount, long userId) {
        aidocSet.setAidocTotal(amount);
        aidocSet.setEffectiveDate(DateUtil.parseDate(DateUtil.C_DATE_PATTON_DEFAULT, startDay));
        aidocSet.setInvalidDate(DateUtil.parseDate(DateUtil.C_DATE_PATTON_DEFAULT, endDay));
        aidocSet.setSetType(type);
        aidocSet.setOperationUserId(userId);
        aidocSetDao.update(aidocSet);
    }


}
