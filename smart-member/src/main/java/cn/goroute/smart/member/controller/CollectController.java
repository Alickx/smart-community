package cn.goroute.smart.member.controller;

import cn.goroute.smart.common.utils.Result;
import cn.goroute.smart.member.entity.pojo.Collect;
import cn.goroute.smart.member.service.CollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("smart/member/collect")
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
    @RequestMapping("/info/{uid}")
    public Result info(@PathVariable("uid") String uid){
		Collect collect = collectService.getById(uid);

        return Result.ok().put("collect", collect);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public Result save(@RequestBody Collect collect){
		collectService.save(collect);

        return Result.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public Result update(@RequestBody Collect collect){
		collectService.updateById(collect);

        return Result.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public Result delete(@RequestBody String[] uids){
		collectService.removeByIds(Arrays.asList(uids));

        return Result.ok();
    }

}
