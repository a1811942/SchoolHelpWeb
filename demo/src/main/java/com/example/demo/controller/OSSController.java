package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.*;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.*;
import com.example.demo.common.R.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/demo/oSS")
public class OSSController {


    // Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
    @Value("${OSS.endpoint}")
    private String endpoint="oss-cn-hangzhou.aliyuncs.com";
    // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
    @Value("${OSS.accessId}")
    private String accessId="LTAI5t9a4okNdzHmaprgE4ck";
    @Value("${OSS.accessKey}")
    private String accessKey="A1vk4LlVbYWBtKL7mSaPPDq3kPC907";
    // 填写Bucket名称，例如examplebucket。
    @Value("${OSS.bucketName}")
    private String bucketName="emo-sutdent";

    // 填写Host地址，格式为https://bucketname.endpoint。
    String host = "https://" + bucketName + "." + endpoint;
    // 设置上传回调URL，即回调服务器地址，用于处理应用服务器与OSS之间的通信。OSS会在文件上传完成后，把文件上传信息通过此回调URL发送给应用服务器。
    String callbackUrl = "https://192.168.0.0:8888";
    // 设置上传到OSS文件的前缀，可置空此项。置空后，文件将上传至Bucket的根目录下。
    String format = new SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
    String dir = format;
     OSS ossClient = new OSSClientBuilder().build(endpoint, accessId, accessKey);

    @RequestMapping("/policy")
    public Result<Map<String, String>> policy(HttpServletRequest request, HttpServletResponse response) {

        Map<String, String> respMap = new HashMap<>();
        // 创建ossClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessId, accessKey);
        try {
            long expireTime = 30;
            long expireEndTime = System.currentTimeMillis() + expireTime * 1000;
            Date expiration = new Date(expireEndTime);
            PolicyConditions policyConds = new PolicyConditions();
            policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1048576000);
            policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);

            String postPolicy = ossClient.generatePostPolicy(expiration, policyConds);
            byte[] binaryData = postPolicy.getBytes("utf-8");
            String encodedPolicy = BinaryUtil.toBase64String(binaryData);
            String postSignature = ossClient.calculatePostSignature(postPolicy);

            respMap = new LinkedHashMap<String, String>();
            respMap.put("accessId", accessId);
            respMap.put("policy", encodedPolicy);
            respMap.put("signature", postSignature);
            respMap.put("dir", dir);
            respMap.put("host", host);
            respMap.put("expire", String.valueOf(expireEndTime / 1000));
            // respMap.put("expire", formatISO8601Date(expiration));

//                JSONObject jasonCallback = new JSONObject();
//                jasonCallback.put("callbackUrl", callbackUrl);
//                jasonCallback.put("callbackBody",
//                        "filename=${object}&size=${size}&mimeType=${mimeType}&height=${imageInfo.height}&width=${imageInfo.width}");
//                jasonCallback.put("callbackBodyType", "application/x-www-form-urlencoded");
//                String base64CallbackBody = BinaryUtil.toBase64String(jasonCallback.toString().getBytes());
//                respMap.put("callback", base64CallbackBody);

//                JSONObject ja1 = JSONObject.fromObject(respMap);
//                // System.out.println(ja1.toString());
//                response.setHeader("Access-Control-Allow-Origin", "*");
//                response.setHeader("Access-Control-Allow-Methods", "GET, POST");
//                response(request, response, ja1.toString());

        } catch (Exception e) {
            // Assert.fail(e.getMessage());
            System.out.println(e.getMessage());
        }
        return Result.getSuccessResult(respMap);

    }



}
