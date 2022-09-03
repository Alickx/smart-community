package cn.goroute.smart.post.controller;

import cn.goroute.smart.common.entity.resp.Response;
import cn.goroute.smart.post.entity.pojo.PostTag;
import cn.goroute.smart.post.service.PostTagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("smart/post/post_tag")
@Api(tags = "文章标签接口")
public class PostTagController {
    @Autowired
    private PostTagService postTagService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @ApiOperation("列表")
    public Response list(){
        List<PostTag> list = postTagService.list();

        return Response.success(list);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @ApiOperation("信息")
    public Response info(@PathVariable("id") String uid){
		PostTag postTag = postTagService.getById(uid);

        return Response.success(postTag);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @ApiOperation(value = "保存", notes = "保存",httpMethod = "POST")
    public Response save(@RequestBody PostTag postTag){
		postTagService.save(postTag);

        return Response.success();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @ApiOperation(value = "修改", notes = "修改",httpMethod = "POST")
    public Response update(@RequestBody PostTag postTag){
		postTagService.updateById(postTag);

        return Response.success();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    @ApiOperation(value = "删除", notes = "删除",httpMethod = "POST")
    public Response delete(@RequestBody String[] uids){
		postTagService.removeByIds(Arrays.asList(uids));

        return Response.success();
    }

}
