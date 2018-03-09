package com.tianyi.web.util;



import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.tianyi.bo.enums.LanguageEnum;
import org.springframework.stereotype.Component;

/**
 * Created by on 2016/1/9.
 */
@Component
public class Send253SMS {


    //请求地址
    static String url="http://intapi.253.com/send/json";

    //API账号，50位以内。必填
    private  static String account="CI0102506";

    //API账号对应密钥，联系客服获取。必填
    private static String password="uv2jzyk8SQe37f";


    public SendSmsResponse register(int countryCode,String phone, String code, LanguageEnum lang){

        String msg="【AIDOC】欢迎您成为天医用户，您此次注册的验证码为"+code+"。工作人员不会向您索取，请勿透漏任何人，感谢您的支持！";

        if(lang !=null && lang == LanguageEnum.en){
            msg = "[AIDOC]You are welcome to become a AIDOC user. The verification code of your registration is "+code+". The staff will not ask for code from you. please do not disclose to others . Thank you for your support!";
        }

        return sendSms(  msg, countryCode+phone);
    }
    public SendSmsResponse chengePassword(int countryCode,String phone, String code, LanguageEnum lang){
        String msg="【AIDOC】您好，您正在进行找回密码操作，本次验证码为"+code+"。切勿将验证码泄露于他人，请妥善保管！";

        if(lang !=null && lang == LanguageEnum.en){
            msg = "[AIDOC]Hello, you are doing the password recovery operation, the verification code is "+code+". Do not disclose the verification code to others, please keep it properly! ";
        }

        return sendSms(  msg, countryCode+phone);
    }
    public SendSmsResponse chengeAccount(int countryCode,String phone, String code, LanguageEnum lang){
        String msg="【AIDOC】您好，您正在进行账户手机变更操作，本次验证码为"+code+"。切勿将验证码泄露于他人，请妥善保管";

        if(lang !=null && lang == LanguageEnum.en){
            msg = "[AIDOC]Hello, you are changing  the mobile phone of your account, this verification code is "+code+". Do not disclose the verification code to others, please keep it properly! ";
        }

        return sendSms(  msg, countryCode+phone);
    }

    private static SendSmsResponse sendSms(String msg,String mobile){
        SendSmsResponse sendSmsResponse= new SendSmsResponse();
        try {
            //短信内容。长度不能超过536个字符。必填
//            String msg="【253】您的验证码是：2530";

            //手机号码，格式(区号+手机号码)，例如：8615800000000，其中86为中国的区号，区号前不使用00开头,15800000000为接收短信的真实手机号码。5-20位。必填
//            String mobile="8618721605772";

            //组装请求参数
            JSONObject map=new JSONObject();
            map.put("account", account);
            map.put("password", password);
            map.put("msg", msg);
            map.put("mobile", mobile);

            String params=map.toString();

          //  logger.info("请求参数为:" + params);
            try {
                String result= HttpUtil.post(url, params);

            //    logger.info("返回参数为:" + result);

                JSONObject jsonObject =  JSON.parseObject(result);
                String code = jsonObject.get("code").toString();
                String msgid = jsonObject.get("msgid").toString();
                String error = jsonObject.get("error").toString();


                sendSmsResponse.setCode(code);
                sendSmsResponse.setMessage(error);
                sendSmsResponse.setRequestId(msgid);

           //     logger.info("状态码:" + code + ",状态码说明:" + error + ",消息id:" + msgid);
            } catch (Exception e) {
                // TODO: handle exception
           //     logger.error("请求异常：" + e);
            }
            return sendSmsResponse;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
