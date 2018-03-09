package com.tianyi.service;

import com.google.gson.Gson;
import com.tianyi.bo.KfzQuestion;
import com.tianyi.bo.KfzResult;
import com.tianyi.bo.User;
import com.tianyi.bo.UserData;
import com.tianyi.bo.kangfuzi.UserEnc;
import com.tianyi.dao.CountryCodeDao;
import com.tianyi.dao.KfzAnswerDao;
import com.tianyi.dao.KfzQuestionDao;
import com.tianyi.dao.KfzResultDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.annotation.Resource;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.util.*;


/**
 * Created by anhui on 2018/2/28.
 */
@Service("kfzService")
public class KfzService {

    @Resource
    KfzAnswerDao kfzAnswerDao;
    @Resource
    KfzQuestionDao kfzQuestionDao;
    @Resource
    KfzResultDao kfzResultDao;


    @Autowired
    UserService userService;
    @Autowired
    UserDataService userDataService;

    @Value("${kangfuzi.url}")
    private String url;

    @Value("${kangfuzi.tyzndz.appId}")
    private String tyzndzAppId;
    @Value("${kangfuzi.tyzndz.appSecret}")
    private String tyzndzSecret;

    @Value("${kangfuzi.tyywz.appId}")
    private String tyywzAppId;
    @Value("${kangfuzi.tyywz.appSecret}")
    private String tyywzSecret;

    @Value("${kangfuzi.tyznzd.appId}")
    private String tyznzdAppId;
    @Value("${kangfuzi.tyznzd.appSecret}")
    private String tyznzdSecret;

    // 生成康夫子链接
    public List<Map<String, Object>> getKfzLinks(long userId) throws Exception {


        User user = userService.getUserByUserId(userId);
        UserData userData = userDataService.getUserDataByUserId(userId);

        List<Map<String, Object>> kangFuZiLinkList = new ArrayList<>();

        String userEncStr = generateLink(user,userData,tyzndzSecret);
        String kfzUrl = url + "?app_id=" + tyzndzAppId + "&user_enc=" + userEncStr;
        Map<String, Object> result = new HashMap();
        result.put("name", "智能导诊");
        result.put("url", kfzUrl);
        kangFuZiLinkList.add(result);

        userEncStr = generateLink(user, userData,tyywzSecret);
        String kfzUrlYwz = url + "?app_id=" + tyywzAppId + "&user_enc=" + userEncStr;
        result = new HashMap();
        result.put("name", "预问诊");
        result.put("url", kfzUrlYwz);
        kangFuZiLinkList.add(result);

        userEncStr = generateLink(user,userData, tyznzdSecret);
        String kfzUrlZnzd = url + "?app_id=" + tyznzdAppId + "&user_enc=" + userEncStr;
        result = new HashMap();
        result.put("name", "智能诊断");
        result.put("url", kfzUrlZnzd);
        kangFuZiLinkList.add(result);




        return kangFuZiLinkList;
    }

    public void callback(KfzResult kfzResult){

        kfzResultDao.add(kfzResult);
    }

    // 生成康夫子一条链接
    private String generateLink(User user,UserData userData, String appSecret) throws Exception {

        if(userData==null){
            userData = new UserData();
        }
        int age=0;
        if(userData.getBirthday()!=null) {
           age=getAge(userData.getBirthday());
        }
        UserEnc one_user = new UserEnc();

        try{
            one_user.age =userData.getBirthday()==null?"":(age+"");
        }catch (Exception ex){
            one_user.age="";
        }

        try{
            one_user.gender=userData.getSex()==null?"":userData.getSex().name();
        }catch(Exception ex){
            one_user.gender="";
        }

        try{
            one_user.patient_name=user.getRealName()==null?user.getMobile():user.getRealName();
        }catch(Exception ex){
            one_user.patient_name="";
        }

        one_user.timestamp = new Date().getTime();
        one_user.department="";
        one_user.user_id=user.getId()+"";

        return Encrypt(one_user, appSecret);
    }

    // 加密
    private String Encrypt(UserEnc one_user, String sKey) throws Exception {

        try {
            java.lang.reflect.Field field = Class.forName("javax.crypto.JceSecurity").getDeclaredField("isRestricted");
            field.setAccessible(true);
            field.set(null, Boolean.FALSE);
        } catch (Exception ex) {

        }

        Gson gson = new Gson();
        String sSrc = gson.toJson(one_user);

        MessageDigest sha = MessageDigest.getInstance("SHA-256");

        sha.update(sKey.getBytes());
        byte[] raw = sha.digest();
        byte[] raw_half = new byte[16];

        System.arraycopy(raw, 0, raw_half, 0, 16);

        MessageDigest iv_init = MessageDigest.getInstance("MD5");
        iv_init.update(sSrc.getBytes("UTF-8"));
        byte[] iv_raw = iv_init.digest();


        SecretKeySpec skeySpec = new SecretKeySpec(raw_half, "AES");

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");//"算法/模式/补码方式"
        IvParameterSpec iv = new IvParameterSpec(iv_raw);//使用CBC模式，需要一个向量iv，可增加加密算法的强度
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
        byte[] encrypted = cipher.doFinal(sSrc.getBytes("UTF-8"));

        byte[] encrypted_sum = new byte[encrypted.length + 16];
        System.arraycopy(iv_raw, 0, encrypted_sum, 0, 16);
        System.arraycopy(encrypted, 0, encrypted_sum, 16, encrypted.length);


        String result = new BASE64Encoder().encode(encrypted_sum).toString().trim().replace("+", "-").replace("/", "_").replace("\r", "").replace("\n", "");//此处使用BASE64做转码功能，同时能起到2次加密的作用。
        while (result.endsWith("=")) {
            result = result.substring(0, result.length() - 1);
        }
        return result;
    }

    // 解密
    public UserEnc Decrypt(String sSrc, String sKey) throws Exception {

        try {
            java.lang.reflect.Field field = Class.forName("javax.crypto.JceSecurity").getDeclaredField("isRestricted");
            field.setAccessible(true);
            field.set(null, Boolean.FALSE);
        } catch (Exception ex) {

        }

        try {

            int trim_number = (4 - sSrc.length() % 4) % 4;
            String trim_str = "";
            for (int i = 0; i < trim_number; i++) {
                trim_str += "=";
            }
            sSrc = (sSrc + trim_str).replace("_", "/").replace("-", "+");

            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            sha.update(sKey.getBytes());
            byte[] raw = sha.digest();

            byte[] raw_half = new byte[16];
            System.arraycopy(raw, 0, raw_half, 0, 16);

            byte[] encrypted_init = new BASE64Decoder().decodeBuffer(sSrc);//先用base64解密

            byte[] iv_raw = new byte[16];
            System.arraycopy(encrypted_init, 0, iv_raw, 0, 16);

            byte[] encrypted = new byte[encrypted_init.length - 16];
            System.arraycopy(encrypted_init, 16, encrypted, 0, encrypted_init.length - 16);

            SecretKeySpec skeySpec = new SecretKeySpec(raw_half, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(iv_raw);
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

            try {
                byte[] original = cipher.doFinal(encrypted);
                String originalString = new String(original, "UTF-8");
                Gson gson = new Gson();
                UserEnc one_user = gson.fromJson(originalString, UserEnc.class);
                return one_user;
            } catch (Exception e) {
                System.out.println(e.toString());
                return null;
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }

    //由出生日期获得年龄
    private static  int getAge(Date birthDay) throws Exception {
        Calendar cal = Calendar.getInstance();
        if (cal.before(birthDay)) {
            throw new IllegalArgumentException(
                    "The birthDay is before Now.It's unbelievable!");
        }
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH);
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(birthDay);

        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;

        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) age--;
            }else{
                age--;
            }
        }
        return age;
    }
}
