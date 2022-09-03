package cn.goroute.smart.thirdpart.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.goroute.smart.common.entity.resp.Response;
import cn.goroute.smart.thirdpart.service.impl.OssServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("smart/thirdpart/oss")
@Api(tags = "三方对象存储接口")
public class OssController {


    @Autowired
    OssServiceImpl ossService;

    @SaCheckLogin
    @PostMapping("getPolicy")
    @ApiOperation(value = "获取上传密钥", notes = "获取上传密钥", httpMethod = "POST")
    public Response getPolicy (){
        return ossService.getPolicy();
    }


}
