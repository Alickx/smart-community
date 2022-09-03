package cn.goroute.smart.post.controller;

import cn.goroute.smart.common.annotations.ParamLog;
import cn.goroute.smart.common.annotations.RateLimiter;
import cn.goroute.smart.common.entity.resp.Response;
import cn.goroute.smart.post.entity.pojo.Tag;
import cn.goroute.smart.post.service.TagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
@RequestMapping("smart/post/tag")
@Api(tags = "标签接口")
public class TagController {
    @Autowired
    private TagService tagService;

    /**
     * 列表
     */
    @GetMapping("/list")
    @ParamLog
    @RateLimiter(time = 5, count = 3)
    @ApiOperation(value="列表", notes = "列表", httpMethod = "GET")
    public Response list() {
        List<Tag> list = tagService.list();
        return Response.success(list);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    @ApiOperation(value = "信息",notes = "信息", httpMethod = "GET")
    @ApiParam(name = "id", value = "主键", required = true)
    public Response info(@PathVariable("id") String id) {
        Tag tag = tagService.getById(id);

        return Response.success(tag);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @ApiOperation(value = "保存",notes = "保存", httpMethod = "POST")
    @ApiParam(name = "tag", value = "标签", required = true)
    public Response save(@Valid @RequestBody Tag tag) {
        tagService.save(tag);

        return Response.success();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @ApiOperation(value = "修改",notes = "修改", httpMethod = "POST")
    @ApiParam(name = "tag", value = "标签", required = true)
    public Response update(@RequestBody Tag tag) {
        tagService.updateById(tag);

        return Response.success();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    @ApiOperation(value = "删除",notes = "删除", httpMethod = "POST")
    @ApiParam(name = "ids", value = "主键集合", required = true)
    public Response delete(@RequestBody String[] ids) {
        tagService.removeByIds(Arrays.asList(ids));

        return Response.success();
    }

}
