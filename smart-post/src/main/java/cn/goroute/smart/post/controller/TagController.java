package cn.goroute.smart.post.controller;

import cn.goroute.smart.common.entity.pojo.Tag;
import cn.goroute.smart.common.utils.Result;
import cn.goroute.smart.post.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;


/**
 * 标签表
 *
 * @author Alickx
 * @email llwstu@gmail.com
 * @date 2022-02-25 09:44:39
 */
@RestController
@RequestMapping("post/tag")
public class TagController {
    @Autowired
    private TagService tagService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public Result list(){
        List<Tag> list = tagService.list();
        return Result.ok().put("data", list);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{uid}")
    public Result info(@PathVariable("uid") String uid){
		Tag tag = tagService.getById(uid);

        return Result.ok().put("tag", tag);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public Result save(@Valid @RequestBody Tag tag){
		tagService.save(tag);

        return Result.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public Result update(@RequestBody Tag tag){
		tagService.updateById(tag);

        return Result.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public Result delete(@RequestBody String[] uids){
		tagService.removeByIds(Arrays.asList(uids));

        return Result.ok();
    }

}
