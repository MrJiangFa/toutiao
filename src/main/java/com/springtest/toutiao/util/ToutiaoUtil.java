package com.springtest.toutiao.util;

import com.alibaba.fastjson.JSONObject;
import com.springtest.toutiao.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.util.Map;

public class ToutiaoUtil {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private static final String[] IMAGE_FILE_EXT = new String[]{"png", "bmp", "jpg", "jpeg"};
    public static String IMAGE_DIR = "D:/upload/";
    public static String TOUTIAO_DOMAIN = "http://10.128.0.122:8080/";
    public static String QINIU_DOMAIN_PREFIX = "http://plomau12w.bkt.clouddn.com/";

    /**
     * @param code:反应服务器入库情况，是否成功
     * @return
     */
    public static String getJSONString(int code, Map<String, Object> map) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", code);
        map.forEach((key, value) -> jsonObject.put(key, value));
//        for (Map.Entry<String, Object> entry : map.entrySet()) {
//            jsonObject.put(entry.getKey(), entry.getValue());
//        }
        return jsonObject.toJSONString();
    }

    public static String getJSONString(int code) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", code);
        return jsonObject.toJSONString();
    }

    public static String getJSONString(int code, String msg) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", code);
        jsonObject.put("msg", msg);
        return jsonObject.toJSONString();
    }

    //md5加密工具
    public static String MD5(String key) {
        char hexDigits[] = {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
        };
        try {
            byte[] btInput = key.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            logger.error("生成MD5失败", e);
            return null;
        }
    }

    public static boolean isFileAllowed(String fileExt) {
        for (String ext : IMAGE_FILE_EXT) {
            if (ext.equals(fileExt)) {
                return true;
            }
        }
        return false;
    }
}
