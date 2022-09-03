package cn.goroute.smart.post.controller;

import cn.goroute.smart.common.entity.resp.Response;
import cn.goroute.smart.post.entity.pojo.Category;
import cn.goroute.smart.post.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;



/**
 * 板块表
 *
 * @author Alickx
 * @email llwstu@gmail.com
 * @date 2022-02-25 09:44:39
 */
@RestController
@RequestMapping("smart/post/category")
@Api(tags = "板块")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 列表
     */
    @GetMapping("/list")
    @ApiOperation(value = "列表", notes = "列表",httpMethod = "GET")
    public Response list(){
        List<Category> list = categoryService.list();
        return Response.success(list);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    @ApiOperation(value = "信息", notes = "信息",httpMethod = "GET")
    @ApiParam(name = "id", value = "主键", required = true)
    public Response info(@PathVariable("id") String id){
        Category category = categoryService.getById(id);

        return Response.success(category);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @ApiOperation(value = "保存", notes = "保存",httpMethod = "POST")
    @ApiParam(name = "category", value = "板块实体", required = true)
    public Response save(@RequestBody Category category){
		categoryService.save(category);

        return Response.success();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @ApiOperation(value = "修改", notes = "修改",httpMethod = "POST")
    @ApiParam(name = "Category", value = "板块实体", required = true)
    public Response update(@RequestBody Category category){
		categoryService.updateById(category);

        return Response.success();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    @ApiOperation(value = "删除", notes = "删除",httpMethod = "POST")
    @ApiParam(name = "ids", value = "主键集合", required = true)
    public Response delete(@RequestBody String[] uids){
		categoryService.removeByIds(Arrays.asList(uids));

        return Response.success();
    }

}
