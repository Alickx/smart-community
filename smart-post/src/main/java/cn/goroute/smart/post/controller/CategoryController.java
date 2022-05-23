package cn.goroute.smart.post.controller;

import cn.goroute.smart.common.entity.pojo.Category;
import cn.goroute.smart.common.utils.Result;
import cn.goroute.smart.post.service.CategoryService;
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
@RequestMapping("post/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public Result list(){
        List<Category> list = categoryService.list();
        return Result.ok().put("data", list);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{uid}")
    public Result info(@PathVariable("uid") String uid){
        Category category = categoryService.getById(uid);

        return Result.ok().put("data", category);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public Result save(@RequestBody Category category){
		categoryService.save(category);

        return Result.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public Result update(@RequestBody Category category){
		categoryService.updateById(category);

        return Result.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public Result delete(@RequestBody String[] uids){
		categoryService.removeByIds(Arrays.asList(uids));

        return Result.ok();
    }

}
