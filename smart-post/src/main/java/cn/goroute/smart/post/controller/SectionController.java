package cn.goroute.smart.post.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.goroute.smart.post.entity.SectionEntity;
import cn.goroute.smart.post.service.SectionService;
import cn.goroute.smart.common.utils.PageUtils;
import cn.goroute.smart.common.utils.R;



/**
 * 板块表
 *
 * @author Alickx
 * @email llwstu@gmail.com
 * @date 2022-02-25 09:44:39
 */
@RestController
@RequestMapping("post/section")
public class SectionController {
    @Autowired
    private SectionService sectionService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = sectionService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{uid}")
    public R info(@PathVariable("uid") String uid){
		SectionEntity section = sectionService.getById(uid);

        return R.ok().put("section", section);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody SectionEntity section){
		sectionService.save(section);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody SectionEntity section){
		sectionService.updateById(section);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody String[] uids){
		sectionService.removeByIds(Arrays.asList(uids));

        return R.ok();
    }

}
