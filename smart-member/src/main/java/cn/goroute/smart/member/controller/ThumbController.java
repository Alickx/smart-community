package cn.goroute.smart.member.controller;

import cn.goroute.smart.common.entity.ThumbEntity;
import cn.goroute.smart.common.utils.R;
import cn.goroute.smart.member.service.ThumbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;


/**
 * 点赞记录表
 *
 * @author Alickx
 * @email llwstu@gmail.com
 * @date 2022-02-26 16:17:45
 */
@RestController
@RequestMapping("/member/thumb")
public class ThumbController {
    @Autowired
    private ThumbService thumbService;

    /**
     * 列表
     */
//    @RequestMapping("/list")
//    public R list(@RequestParam Map<String, Object> params) {
//        PageUtils page = thumbService.queryPage(params);
//
//        return R.ok().put("page", page);
//    }

    /**
     * 信息
     */
    @RequestMapping("/info/{uid}")
    public R info(@PathVariable("uid") String uid) {
        ThumbEntity thumb = thumbService.getById(uid);

        return R.ok().put("thumb", thumb);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody ThumbEntity thumb) {
        thumbService.save(thumb);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody ThumbEntity thumb) {
        thumbService.updateById(thumb);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody String[] uids) {
        thumbService.removeByIds(Arrays.asList(uids));

        return R.ok();
    }

}
