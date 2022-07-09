package cn.goroute.smart.notify.service.impl;

import cn.goroute.smart.common.constant.NotificationConstant;
import cn.goroute.smart.common.dao.EventRemindDao;
import cn.goroute.smart.common.entity.dto.EventRemindDto;
import cn.goroute.smart.common.entity.dto.MemberDto;
import cn.goroute.smart.common.entity.pojo.EventRemind;
import cn.goroute.smart.common.feign.MemberFeignService;
import cn.goroute.smart.common.service.AuthService;
import cn.goroute.smart.common.utils.PageUtils;
import cn.goroute.smart.common.utils.Query;
import cn.goroute.smart.common.utils.QueryParam;
import cn.goroute.smart.common.utils.Result;
import cn.goroute.smart.notify.service.EventRemindService;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Alickx
 * @description 针对表【t_event_remind(事件提醒表)】的数据库操作Service实现
 * @createDate 2022-03-26 08:45:27
 */
@Service
public class EventRemindServiceImpl extends ServiceImpl<EventRemindDao, EventRemind>
        implements EventRemindService {

    @Resource
    EventRemindDao eventRemindDao;

    @Autowired
    MemberFeignService memberFeignService;

    @Autowired
    AuthService authService;

    /**
     * 查询事件提醒
     *
     * @param queryParam 查询参数
     * @return 事件集合
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result queryEventRemind(QueryParam queryParam) {

        /*
          查询未读提醒
           根据未读消息和时间排序
           第一排序条件为未读 -> 已读
           第二排序条件为提醒时间
         */
        IPage<EventRemind> page = eventRemindDao.selectPage(new Query<EventRemind>()
                .getPage(queryParam), new LambdaQueryWrapper<EventRemind>()
                .ne(EventRemind::getSenderUid, authService.getLoginUid())
                .eq(EventRemind::getRecipientUid, authService.getLoginUid())
                .orderByAsc(EventRemind::getState)
                .eq(EventRemind::getActionType, queryParam.getType()));


        // 判断是否有数据，无数据则直接返回
        if (CollUtil.isEmpty(page.getRecords())) {
            return Result.ok().put("data", new PageUtils(page));
        }

        //获取用户信息
        List<EventRemind> records = page.getRecords();
        List<Long> senderIds = records.stream()
                .map(EventRemind::getSenderUid)
                .collect(Collectors.toList());

        List<MemberDto> infoByMemberUids = memberFeignService.batchQueryUsers(senderIds);

        List<EventRemindDto> eventReminds = new ArrayList<>(records.size());

        // 将用户信息添加到事件集合中
        for (int i = 0; i < records.size(); i++) {
            EventRemindDto eventRemindDTO = new EventRemindDto();
            eventRemindDTO.setSender(infoByMemberUids.get(i));
            BeanUtils.copyProperties(records.get(i), eventRemindDTO);
            eventReminds.add(eventRemindDTO);
            records.get(i).setState(NotificationConstant.STATE_HAVE_READ);
        }

        Page<EventRemindDto> pageDTO = new Page<>();
        BeanUtils.copyProperties(page, pageDTO);
        pageDTO.setRecords(eventReminds);

        // 更新通知状态
        boolean b = this.updateBatchById(records);
        if (b) {
            return Result.ok().put("data", new PageUtils(pageDTO));
        } else {
            log.error("更新通知状态失败");
            return Result.error();
        }

    }

    /**
     * 查询每个提醒的未读数量
     *
     * @return 查询结果
     */
    @Override
    public Result queryUnreadCountRemind() {

        Long memberUid = authService.getLoginUid();
        List<EventRemind> eventReminds = eventRemindDao.selectList(new LambdaQueryWrapper<EventRemind>()
                .eq(EventRemind::getState, NotificationConstant.STATE_UNREAD)
                .ne(EventRemind::getSenderUid, memberUid)
                .eq(EventRemind::getRecipientUid, memberUid)
                .orderByDesc(EventRemind::getCreatedTime));

        //按照操作类型进行分组
        Map<Integer, List<EventRemind>> eventRemindGroup =
                eventReminds.parallelStream()
                        .collect(Collectors.groupingBy(EventRemind::getActionType));

        //未读通知总数
        int count = eventReminds.size();
        //存储统计map
        Map<Integer, Integer> statistical = new HashMap<>(7);

        //遍历map，统计每种提醒的未读数量
        for (int i : NotificationConstant.REMIND_ARRAY) {
            statistical.put(i, eventRemindGroup.get(i) == null ? 0 : eventRemindGroup.get(i).size());
        }

        return Objects.requireNonNull(Result.ok().put("count", count)).put("data", statistical);
    }

}




