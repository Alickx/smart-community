package cn.goroute.smart.post.controller;

import cn.goroute.smart.common.entity.SectionEntity;
import cn.goroute.smart.common.utils.R;
import cn.goroute.smart.post.service.SectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;



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
    @GetMapping("/list")
    public R list(){
        List<SectionEntity> list = sectionService.list();
        return R.ok().put("data", list);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{uid}")
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
