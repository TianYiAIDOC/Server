package com.tianyi.web.util;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.UUID;

/**
 * Created by 雪峰 on 2015/8/12.
 */

/**
 * 对OSS服务器进行上传删除等的处理
 * @ClassName: OSSManageUtil
 * @Description:
 * @author lxf4456
 * @date 2015/11/3.修改
 *
 */
@Component
public class OSSManageUtil {
    @Value("${oss.endpoint}")
    private  String endpoint;
    @Value("${oss.accessKey}")
    private   String accessKeyId;
    @Value("${oss.secretKey}")
    private   String accessKeySecret;
    @Value("${oss.bucketName}")
    private   String bucketName;
    @Value("${oss.accessUrl}")
    private   String accessUrl;

    /**
     * 上传OSS服务器文件
     * @Title: uploadFile
     * @Description:
     * @param @param ossConfigure
     * @param @param file
     * @param @param remotePath
     * @param @return
     * @param @throws Exception    设定文件
     * @return String    返回类型
     * @throws
     */
    public String uploadFile(File file,String remotePath) throws Exception{
        InputStream fileContent=null;
        fileContent=new FileInputStream(file);

        OSSClient ossClient=new OSSClient(endpoint, accessKeyId, accessKeySecret);
        String remoteFilePath = remotePath.substring(0, remotePath.length()).replaceAll("\\\\","/")+"/";
        //创建上传Object的Metadata
        ObjectMetadata objectMetadata=new ObjectMetadata();
        objectMetadata.setContentLength(fileContent.available());
        objectMetadata.setCacheControl("no-cache");
        objectMetadata.setHeader("Pragma", "no-cache");
        objectMetadata.setContentType(contentType(file.getName().substring(file.getName().lastIndexOf(".")+1)));


        String fileExtension = FilenameUtils.getExtension(file.getName());
        String fileKey = UUID.randomUUID().toString() +"."+ fileExtension;

        objectMetadata.setContentDisposition("inline;filename=" + fileKey);
        //上传文件
        ossClient.putObject(bucketName, remoteFilePath + fileKey, fileContent, objectMetadata);
        System.out.println(accessUrl+"/" +remoteFilePath + fileKey);
        return accessUrl+"/" +remoteFilePath + fileKey;
    }

    public  String uploadFile(File file) throws Exception{
        InputStream fileContent=null;
        fileContent=new FileInputStream(file);

        OSSClient ossClient=new OSSClient(endpoint, accessKeyId, accessKeySecret);

        //创建上传Object的Metadata
        ObjectMetadata objectMetadata=new ObjectMetadata();
        objectMetadata.setContentLength(fileContent.available());
        objectMetadata.setCacheControl("no-cache");
        objectMetadata.setHeader("Pragma", "no-cache");

        String fileExtension = FilenameUtils.getExtension(file.getName());
        String fileKey = UUID.randomUUID().toString() +"."+ fileExtension;

        objectMetadata.setContentType(contentType(file.getName().substring(file.getName().lastIndexOf(".") + 1)));
        objectMetadata.setContentDisposition("inline;filename=" + fileKey);
        //上传文件
        ossClient.putObject(bucketName, fileKey, fileContent, objectMetadata);

        return accessUrl+"/" + fileKey;
    }
    /**
     * 根据key删除OSS服务器上的文件
     * @Title: deleteFile
     * @Description:
     * @param @param ossConfigure
     * @param @param filePath    设定文件
     * @return void    返回类型
     * @throws
     */
    public  void deleteFile(String filePath){
        OSSClient ossClient = new OSSClient(endpoint,accessKeyId, accessKeySecret);
        ossClient.deleteObject(bucketName, filePath);

    }

    /**
     * Description: 判断OSS服务文件上传时文件的contentType
     * @Version1.0
     * @param FilenameExtension 文件后缀
     * @return String
     */
    public  String contentType(String FilenameExtension){
        if(FilenameExtension.equals("BMP")||FilenameExtension.equals("bmp")){return "image/bmp";}
        if(FilenameExtension.equals("GIF")||FilenameExtension.equals("gif")){return "image/gif";}
        if(FilenameExtension.equals("JPEG")||FilenameExtension.equals("jpeg")||
                FilenameExtension.equals("JPG")||FilenameExtension.equals("jpg")||
                FilenameExtension.equals("PNG")||FilenameExtension.equals("png")){return "image/jpeg";}
        if(FilenameExtension.equals("HTML")||FilenameExtension.equals("html")){return "text/html";}
        if(FilenameExtension.equals("TXT")||FilenameExtension.equals("txt")){return "text/plain";}
        if(FilenameExtension.equals("VSD")||FilenameExtension.equals("vsd")){return "application/vnd.visio";}
        if(FilenameExtension.equals("PPTX")||FilenameExtension.equals("pptx")||
                FilenameExtension.equals("PPT")||FilenameExtension.equals("ppt")){return "application/vnd.ms-powerpoint";}
        if(FilenameExtension.equals("DOCX")||FilenameExtension.equals("docx")||
                FilenameExtension.equals("DOC")||FilenameExtension.equals("doc")){return "application/msword";}
        if(FilenameExtension.equals("XML")||FilenameExtension.equals("xml")){return "text/xml";}
        return "text/html";
    }
}
