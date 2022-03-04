package cn.goroute.smart.member.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import cn.goroute.smart.common.entity.CollectEntity;
import cn.goroute.smart.member.service.CollectService;
import cn.goroute.smart.common.utils.PageUtils;
import cn.goroute.smart.common.utils.R;



/**
 * 收藏表
 *
 * @author Alickx
 * @email llwstu@gmail.com
 * @date 2022-02-25 09:45:33
 */
@RestController
@RequestMapping("/member/collect")
public class CollectController {
    @Autowired
    private CollectService collectService;


    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = collectService.queryPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{uid}")
    public R info(@PathVariable("uid") String uid){
		CollectEntity collect = collectService.getById(uid);

        return R.ok().put("collect", collect);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody CollectEntity collect){
		collectService.save(collect);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody CollectEntity collect){
		collectService.updateById(collect);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody String[] uids){
		collectService.removeByIds(Arrays.asList(uids));

        return R.ok();
    }

}
