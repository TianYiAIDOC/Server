package com.tianyi.web.controller;

import com.alibaba.fastjson.JSON;
import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.OSSClient;
import com.tianyi.web.AuthRequired;
import com.tianyi.web.util.JsonPathArg;
import com.tianyi.web.util.OSSManageUtil;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.*;

/**
 * Created by 雪峰 on 2018/1/1.
 */
@RestController
public class OSSController {
    @Value("${oss.accessKey}")
    private String accessKey;
    @Value("${oss.secretKey}")
    private String secretKey;
    @Value("${oss.bucketName}")
    private String bucketName;
    @Value("${oss.accessUrl}")
    private String accessUrl;

    @Value("${oss.endpoint}")
    private String endpoint;


    @Resource
    private OSSManageUtil ossManageUtil;

    @AuthRequired
    @RequestMapping("/lib/file/upload_url")
    public Map<String, Object> upload(@JsonPathArg("file_name") String file_name) {

        OSSClient ossClient = new OSSClient(endpoint, accessKey, secretKey);
        String fileExtension = FilenameUtils.getExtension(file_name);
        String fileKey = UUID.randomUUID().toString() + "." + fileExtension;
        Date expiredOn = new Date(new Date().getTime() + 3600 * 1000);
        String upload_url = ossClient.generatePresignedUrl(bucketName, fileKey, expiredOn, HttpMethod.PUT).toString();

        Map<String, Object> result = new HashMap<>();
        result.put("upload_url", upload_url);
        result.put("fileKey", fileKey);
        result.put("pic_url", accessUrl + fileKey);
        return result;
    }


    @RequestMapping("/lib/file/uploads")
    public Map<String, Object> uploadFile(MultipartHttpServletRequest request) throws Exception {
        String temporaryPath = "/data/temp/";
        String[] keys = {"visitReason", "visitResult", "doctorAdvice", "doctorInfo", "visitRepost", "casePhoto"};
        List<Map<String,Object>> resultList= new ArrayList<>();

        for (String key : keys) {

            List<MultipartFile> files = request.getFiles(key);
            if(files==null || files.size()==0){
                continue;
            }

            Map<String, Object> result = new HashMap<>();
            String urls = "";
            List<String> urlList = new ArrayList<>();
            for (MultipartFile imgFile : files) {
                String fileName = imgFile.getOriginalFilename();
                String imageUrl = null;
                if (!fileName.equals("")) {
                    File file = new File(temporaryPath + fileName);
                    imgFile.transferTo(file);
                    imageUrl = ossManageUtil.uploadFile(file);// 将图片上传到阿里云
                }
                urlList.add(imageUrl);
            }


            result.put("urls", urlList);
            result.put("name",key);

            resultList.add(result);
        }
        Map<String,Object> resultMap= new HashMap();
        resultMap.put("attachFileKeyJson", JSON.toJSONString(resultList));

        return resultMap;
    }

    //    @AuthRequired
    @RequestMapping("/lib/file/upload")
    public Map<String, Object> uploadFile(@RequestParam("file") MultipartFile imgFile) throws Exception {
        String temporaryPath = "/data/temp/";

        String fileName = imgFile.getOriginalFilename();


        String imageUrl = null;
        if (!fileName.equals("")) {
            File file = new File(temporaryPath + fileName);
            imgFile.transferTo(file);
            imageUrl = ossManageUtil.uploadFile(file);// 将图片上传到阿里云
        }

        Map<String, Object> result = new HashMap<>();
        result.put("url", imageUrl);
        return result;
    }

    //    @AuthRequired
    @RequestMapping("/lib/file/editor")
    public Map<String, Object> editor(@RequestParam("upfile") MultipartFile imgFile) throws Exception {
        String temporaryPath = "/data/temp/";
        String fileName = imgFile.getOriginalFilename();

        String imageUrl = null;

        File file = new File(temporaryPath + fileName);
        imgFile.transferTo(file);
        imageUrl = ossManageUtil.uploadFile(file);// 将图片上传到阿里云
        BufferedImage sourceImg = ImageIO.read(file);


        Map<String, Object> result = new HashMap<>();
        result.put("originalName", fileName);
        result.put("name", fileName);
        result.put("url", imageUrl);
        result.put("size", sourceImg.getWidth() + "x" + sourceImg.getHeight());
        result.put("type", fileName.substring(fileName.lastIndexOf(".")));
        result.put("state", "SUCCESS");

        return result;
    }


}
