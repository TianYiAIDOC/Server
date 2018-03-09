package com.tianyi.web.util;



import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import org.springframework.stereotype.Component;

/**
 * Created by on 2016/1/9.
 */
@Component
public class SendSMS {


    //产品名称:云通信短信API产品,开发者无需替换
    private static final String product = "Dysmsapi";
    //产品域名,开发者无需替换
    private static final String domain = "dysmsapi.aliyuncs.com";

    private static final String accessKeyId = "LTAIdHEXYDE6UM0W";
    private static final String accessKeySecret = "mzLC7wZPLKlhjX45IB2vDldjZbZb6O";



//    public SendSmsResponse register(String phone,String code){
//        String smsTemplateCode="SMS_121165543";
//        String smsParamString="{code:'"+code+"'}";
//        return sendSms(  smsTemplateCode, smsParamString, phone);
//    }
//    public SendSmsResponse chengePassword(String phone,String code){
//        String smsTemplateCode="SMS_121145586";
//        String smsParamString="{code:'"+code+"'}";
//        return sendSms(  smsTemplateCode, smsParamString, phone);
//    }
//    public SendSmsResponse chengeAccount(String phone,String code){
//        String smsTemplateCode="SMS_121160563";
//        String smsParamString="{code:'"+code+"'}";
//        return sendSms(  smsTemplateCode, smsParamString, phone);
//    }

    private static SendSmsResponse sendSms(String smsTemplateCode,String smsParamString,String phone){

        try {
            //可自助调整超时时间
            System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
            System.setProperty("sun.net.client.defaultReadTimeout", "10000");

            //初始化acsClient,暂不支持region化
            IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
            IAcsClient acsClient = new DefaultAcsClient(profile);

            //组装请求对象-具体描述见控制台-文档部分内容
            SendSmsRequest request = new SendSmsRequest();
            //必填:待发送手机号
            request.setPhoneNumbers(phone);
            //必填:短信签名-可在短信控制台中找到
            request.setSignName("天医AIDOC");
            //必填:短信模板-可在短信控制台中找到
            request.setTemplateCode(smsTemplateCode);
            //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
            request.setTemplateParam(smsParamString);

            //选填-上行短信扩展码(无特殊需求用户请忽略此字段)
            //request.setSmsUpExtendCode("90997");

            //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
            request.setOutId("yourOutId");

            //hint 此处可能会抛出异常，注意catch
            SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);

            return sendSmsResponse;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
