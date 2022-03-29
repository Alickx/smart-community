package cn.goroute.smart.post.controller;

import cn.goroute.smart.common.entity.pojo.PostTag;
import cn.goroute.smart.common.utils.Result;
import cn.goroute.smart.post.service.PostTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;



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
    public Result list(){
        List<PostTag> list = postTagService.list();

        return Result.ok().put("data", list);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{uid}")
    public Result info(@PathVariable("uid") String uid){
		PostTag postTag = postTagService.getById(uid);

        return Result.ok().put("postTag", postTag);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public Result save(@RequestBody PostTag postTag){
		postTagService.save(postTag);

        return Result.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public Result update(@RequestBody PostTag postTag){
		postTagService.updateById(postTag);

        return Result.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public Result delete(@RequestBody String[] uids){
		postTagService.removeByIds(Arrays.asList(uids));

        return Result.ok();
    }

}
