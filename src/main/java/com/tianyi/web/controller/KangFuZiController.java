package com.tianyi.web.controller;

import com.tianyi.bo.KfzResult;
import com.tianyi.bo.User;
import com.tianyi.bo.kangfuzi.UserEnc;
import com.tianyi.service.KfzService;
import com.tianyi.service.UserService;
import com.tianyi.web.AuthRequired;
import com.tianyi.web.util.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by 安辉 on 2018/2/28.
 */
@RestController
@RequestMapping(value = "/kfz", method = RequestMethod.GET)
public class KangFuZiController {


    @Autowired
    UserService userService;

    @Autowired
    KfzService kfzService;

    @Value("${kangfuzi.url}")
    private String url;

    @Value("${kangfuzi.result.url}")
    private String kfzResultUrl;

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

    // @AuthRequired
    @RequestMapping("link")
    public List<Map<String, Object>> kfuLink(@RequestParam(value = "userId", required = true) String userId, HttpServletRequest request) {

        String token = request.getHeader("X-Token");
        User user =userService.getUserByToken(token);

        try {
            return kfzService.getKfzLinks(Long.parseLong(userId));
        } catch (Exception e) {
            return null;
        }
    }

    @RequestMapping("callback/znzd")
    public void tyznzd(@RequestParam(value = "user_enc", required = false) String user_enc) throws Exception {


        Map<String,Object> param = new HashMap();
        param.put("app_id",tyznzdAppId);
        param.put("action","get_user_info");
        param.put("user_enc",user_enc);

        String result= HttpUtil.post(kfzResultUrl,param);
        UserEnc userEnc= kfzService.Decrypt(user_enc,tyznzdSecret);

        // 1 保存康夫子，问诊结果
        KfzResult kfzResult = new KfzResult();
        kfzResult.setUserId(Long.parseLong(userEnc.user_id));
        kfzResult.setAppId(tyznzdAppId);
        kfzResult.setResult(result);

        kfzService.callback(kfzResult);
    }


    @RequestMapping("callback/ywz")
    public void tyywz(@RequestParam(value = "user_enc", required = false) String user_enc) throws Exception {



        Map<String,Object> param = new HashMap();
        param.put("app_id",tyywzAppId);
        param.put("action","get_user_info");
        param.put("user_enc",user_enc);

        String result= HttpUtil.post(kfzResultUrl,param);
        UserEnc userEnc= kfzService.Decrypt(user_enc,tyywzSecret);

        // 1 保存康夫子，问诊结果
        KfzResult kfzResult = new KfzResult();
        kfzResult.setUserId(Long.parseLong(userEnc.user_id));
        kfzResult.setAppId(tyywzAppId);
        kfzResult.setResult(result);

        kfzService.callback(kfzResult);
    }

    @RequestMapping("callback/zndz")
    public void zndz(@RequestParam(value = "user_enc", required = false) String user_enc) throws Exception {



        Map<String,Object> param = new HashMap();
        param.put("app_id",tyzndzAppId);
        param.put("action","get_user_info");
        param.put("user_enc",user_enc);

        String result= HttpUtil.post(kfzResultUrl,param);
        UserEnc userEnc= kfzService.Decrypt(user_enc,tyzndzSecret);

        // 1 保存康夫子，问诊结果
        KfzResult kfzResult = new KfzResult();
        kfzResult.setUserId(Long.parseLong(userEnc.user_id));
        kfzResult.setAppId(tyzndzAppId);
        kfzResult.setResult(result);

        kfzService.callback(kfzResult);
    }



    @RequestMapping("qa")
    public void addQuestionAndAnswer(@RequestParam(value = "user_enc", required = false) String user_enc) throws Exception {

        Map<String,Object> params= new HashMap();
        params.put("app_id",tyznzdAppId);
        params.put("action","get_user_info");
        params.put("user_enc",user_enc);

        String result= HttpUtil.post(url,params);
        UserEnc userEnc= kfzService.Decrypt(user_enc,tyznzdSecret);
    }




}
