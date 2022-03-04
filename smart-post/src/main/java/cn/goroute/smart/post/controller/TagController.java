package cn.goroute.smart.post.controller;

import cn.goroute.smart.common.entity.TagEntity;
import cn.goroute.smart.common.utils.R;
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
    public R list(){
        List<TagEntity> list = tagService.list();
        return R.ok().put("data", list);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{uid}")
    public R info(@PathVariable("uid") String uid){
		TagEntity tag = tagService.getById(uid);

        return R.ok().put("tag", tag);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@Valid @RequestBody TagEntity tag){
		tagService.save(tag);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody TagEntity tag){
		tagService.updateById(tag);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody String[] uids){
		tagService.removeByIds(Arrays.asList(uids));

        return R.ok();
    }

}
