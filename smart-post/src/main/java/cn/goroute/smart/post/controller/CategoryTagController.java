package cn.goroute.smart.post.controller;

import cn.goroute.smart.common.entity.resp.Response;
import cn.goroute.smart.post.service.CategoryTagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Alickx
 */
@RestController
@RequestMapping("smart/post/categoryTag")
@Api(tags = "分类标签接口")
public class CategoryTagController {

    @Autowired
    CategoryTagService categoryTagService;


    @GetMapping("/list")
    @ApiOperation(value = "获取分类标签列表", notes = "获取分类标签列表", httpMethod = "GET")
    @ApiParam(name = "categoryId", value = "分类id", required = true)
    public Response getTagByCategory(@RequestParam Long categoryUid){
        return categoryTagService.getTagByCategory(categoryUid);
    }

    @GetMapping("/listAll")
    public Response getTagByCategoryAll(){
        return categoryTagService.getCategoryTagAll();
    }

}
