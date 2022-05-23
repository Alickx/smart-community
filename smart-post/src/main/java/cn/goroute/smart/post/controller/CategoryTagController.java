package cn.goroute.smart.post.controller;

import cn.goroute.smart.common.utils.Result;
import cn.goroute.smart.post.service.CategoryTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Alickx
 */
@RestController
@RequestMapping("post/categoryTag")
public class CategoryTagController {

    @Autowired
    CategoryTagService categoryTagService;


    @GetMapping("/list")
    public Result getTagByCategory(@RequestParam Long categoryUid){

        return categoryTagService.getTagByCategory(categoryUid);

    }

}
