package cn.goroute.smart.thirdpart.service.impl;

import cn.goroute.smart.common.entity.resp.Response;
import cn.goroute.smart.thirdpart.service.OssService;
import cn.goroute.smart.thirdpart.util.OssUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.UUID;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
@Slf4j
public class OssServiceImpl implements OssService {


    @Autowired
    OssUtil ossUtil;

    public Response getPolicy() {
        String host = "https://" + ossUtil.getBucket() + "." + ossUtil.getEndpoint();
        String fileName = UUID.fastUUID().toString(true);
        String dir = ossUtil.getDir() + "/" + DateUtil.today() + "/" + fileName;

        Map<String, String> respMap = new LinkedHashMap<>();

        OSS ossClient = new OSSClientBuilder().build(ossUtil.getEndpoint(), ossUtil.getAccessId(), ossUtil.getAccessKey());
        try {
            long expireTime = 30;
            long expireEndTime = System.currentTimeMillis() + expireTime * 1000;
            Date expiration = new Date(expireEndTime);
            // PostObject请求最大可支持的文件大小为5 GB，即CONTENT_LENGTH_RANGE为5*1024*1024*1024。
            PolicyConditions policyConds = new PolicyConditions();
            policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1048576000);
            policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);

            String postPolicy = ossClient.generatePostPolicy(expiration, policyConds);
            byte[] binaryData = postPolicy.getBytes(StandardCharsets.UTF_8);
            String encodedPolicy = BinaryUtil.toBase64String(binaryData);
            String postSignature = ossClient.calculatePostSignature(postPolicy);

            respMap.put("accessid", ossUtil.getAccessId());
            respMap.put("policy", encodedPolicy);
            respMap.put("signature", postSignature);
            respMap.put("dir", dir);
            respMap.put("host", host);
            respMap.put("fileName", fileName);
            respMap.put("expire", String.valueOf(expireEndTime / 1000));

        } catch (Exception e) {
            log.error("oss上传异常:{}", e.getMessage());
        } finally {
            ossClient.shutdown();
        }
        return Response.ok().put("data",respMap);
    }
}
