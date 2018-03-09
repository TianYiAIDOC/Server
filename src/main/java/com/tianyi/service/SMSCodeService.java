package com.tianyi.service;

import com.tianyi.bo.SMSCode;
import com.tianyi.bo.SMSCodeLog;
import com.tianyi.dao.SMSCodeDao;
import com.tianyi.dao.SMSCodeLogDao;
import com.tianyi.web.util.DateUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by 雪峰 on 2018/1/1.
 */
@Service("smsCodeService")
public class SMSCodeService   {
    @Resource
    private SMSCodeDao smsCodeDao;
    @Resource
    private SMSCodeLogDao smsCodeLogDao;

    public SMSCode getSMSCodeByCode(String code) {
        return smsCodeDao.getSMSCodeByCode(code);
    }

    public void cleanSMSCodes() {
        List<SMSCode>  smscodes = smsCodeDao.getSMSCodes();
        for(SMSCode smsCode:smscodes){
            if((smsCode.getCreatedOn().getTime() - DateUtil.getCurrentDate().getTime()) > 5*60*1000){
                smsCodeDao.delete(smsCode);
            }
        }
    }

    public void addSMSCode(String mobilePhoneNumber,String code) {
        SMSCode smsCode = new SMSCode();
        smsCode.setCode(code);
        smsCode.setMobile(mobilePhoneNumber);
        smsCode.setCreatedOn(DateUtil.getCurrentDate());
        smsCodeDao.add(smsCode);
    }

    public void delSMSCode(SMSCode smsCode) {
        smsCodeDao.delete(smsCode);
    }

    public void addSMSCodeLong(String phone, String code, String result) {
        SMSCodeLog smsCodeLog = new SMSCodeLog();
        smsCodeLog.setMobile(phone);
        smsCodeLog.setCode(code);
        smsCodeLog.setResult(result);

        smsCodeLogDao.add(smsCodeLog);
    }
}
