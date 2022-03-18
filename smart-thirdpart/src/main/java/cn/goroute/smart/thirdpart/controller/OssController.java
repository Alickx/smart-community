package cn.goroute.smart.thirdpart.controller;

import cn.goroute.smart.common.utils.Result;
import cn.goroute.smart.thirdpart.service.impl.OssServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("thirdpart/oss")
public class OssController {


    @Autowired
    OssServiceImpl ossService;

    @PostMapping("getPolicy")
    public Result getPolicy (){
        return ossService.getPolicy();
    }


}
