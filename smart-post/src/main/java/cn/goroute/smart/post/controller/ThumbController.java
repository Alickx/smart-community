package cn.goroute.smart.post.controller;

import cn.goroute.smart.common.entity.CommentVo;
import cn.goroute.smart.common.utils.R;
import cn.goroute.smart.post.service.ThumbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("post/thumb")
public class ThumbController {

    @Autowired
    ThumbService thumbService;

    @PostMapping("/save")
    public R save(@RequestBody CommentVo thumbVo){
        //存入缓存
        return thumbService.thumbSave(thumbVo);

    }

    @PostMapping("/cancel")
    public R cancel(@RequestBody CommentVo thumbVo){

        return thumbService.thumbCancel(thumbVo);
    }

}
