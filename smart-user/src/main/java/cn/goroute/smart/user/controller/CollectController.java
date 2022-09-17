package cn.goroute.smart.user.controller;

import cn.goroute.smart.common.entity.resp.Response;
import cn.goroute.smart.user.entity.pojo.Collect;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;



/**
 * 收藏表
 *
 * @author Alickx
 * @email llwstu@gmail.com
 * @date 2022-02-25 09:45:33
 */
@RestController
@RequestMapping("member/collect")
@Api(tags = "收藏")
public class CollectController {
    @Resource
    private CollectService collectService;


    /**
     * 列表
     */
//    @RequestMapping("/list")
//    public R list(@RequestParam Map<String, Object> params){
//        PageUtils page = collectService.queryPage(params);
//        return R.ok().put("page", page);
//    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    @ApiOperation(value = "查看收藏列表", notes = "查看收藏列表", httpMethod = "GET")
    @ApiParam(name = "id", value = "用户id", required = true)
    public Response info(@PathVariable("id") String id){
		Collect collect = collectService.getById(id);

        return Response.success(collect);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @ApiOperation(value = "保存收藏", notes = "保存收藏", httpMethod = "POST")
    @ApiParam(name = "collect", value = "收藏实体", required = true)
    public Response save(@RequestBody Collect collect){
		collectService.save(collect);

        return Response.success();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @ApiOperation(value = "修改收藏", notes = "修改收藏", httpMethod = "POST")
    @ApiParam(name = "collect", value = "收藏实体", required = true)
    public Response update(@RequestBody Collect collect){
		collectService.updateById(collect);

        return Response.success();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    @ApiOperation(value = "删除收藏", notes = "删除收藏", httpMethod = "POST")
    @ApiParam(name = "ids", value = "收藏id集合", required = true)
    public Response delete(@RequestBody String[] ids){
		collectService.removeByIds(Arrays.asList(ids));

        return Response.success();
    }

}
