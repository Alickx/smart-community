package cn.goroute.smart.post.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.goroute.smart.post.entity.PostTagEntity;
import cn.goroute.smart.post.service.PostTagService;
import cn.goroute.smart.common.utils.PageUtils;
import cn.goroute.smart.common.utils.R;



/**
 * 文章标签表
 *
 * @author Alickx
 * @email llwstu@gmail.com
 * @date 2022-02-25 09:44:39
 */
@RestController
@RequestMapping("post/posttag")
public class PostTagController {
    @Autowired
    private PostTagService postTagService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = postTagService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{uid}")
    public R info(@PathVariable("uid") String uid){
		PostTagEntity postTag = postTagService.getById(uid);

        return R.ok().put("postTag", postTag);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody PostTagEntity postTag){
		postTagService.save(postTag);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody PostTagEntity postTag){
		postTagService.updateById(postTag);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody String[] uids){
		postTagService.removeByIds(Arrays.asList(uids));

        return R.ok();
    }

}
