package com.springtest.toutiao.service;

import com.alibaba.fastjson.JSONObject;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.springtest.toutiao.util.ToutiaoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class QiniuService {
    public static final Logger logger = LoggerFactory.getLogger(QiniuService.class);
    Configuration cfg = new Configuration(Zone.zone0());
    //创建上传对象
    UploadManager uploadManager = new UploadManager(cfg);
    String accessKey = "wIWZIQ37112fW_1SVF8Q7IydaaMOvxmJ1E8VGEcl";
    String secretKey = "D_5zjXsP1V3_SWksSCUmFtXvtWkWCn2naJ8u_g8D";
    String bucket = "toutiao";
    Auth auth = Auth.create(accessKey, secretKey);

    public String getUpToken() {
        return auth.uploadToken(bucket);
    }

    public String saveImage(MultipartFile file) throws IOException {
        try {
            int dotPos = file.getOriginalFilename().lastIndexOf(".");
            if (dotPos < 0)
                return null;
            String fileExt = file.getOriginalFilename().substring(dotPos + 1).toLowerCase();
            if (!ToutiaoUtil.isFileAllowed(fileExt))
                return null;
            String fileName = UUID.randomUUID().toString().replaceAll("-", "") + "." + fileExt;
            //上传关键语句
            Response response = uploadManager.put(file.getBytes(), fileName, getUpToken());
            //打印返回的信息
            System.out.println(response.toString());
            if(response.isOK()&&response.isJson()){
                String key = JSONObject.parseObject(response.bodyString()).get("key").toString();
                return ToutiaoUtil.QINIU_DOMAIN_PREFIX+key;
            }else{
                logger.error("七牛异常"+response.bodyString());
                return null;
            }
        } catch (QiniuException e) {
            logger.error("七牛异常" + e.getMessage());
            return null;
        }
    }
}
