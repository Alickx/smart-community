package cn.goroute.smart.notify.service.impl;

import cn.goroute.smart.common.entity.resp.Response;
import cn.goroute.smart.common.feign.MemberFeignService;
import cn.goroute.smart.common.service.AuthService;
import cn.goroute.smart.common.utils.QueryParam;
import cn.goroute.smart.notify.constant.NotificationConstant;
import cn.goroute.smart.notify.entity.pojo.EventRemind;
import cn.goroute.smart.notify.mapper.EventRemindMapper;
import cn.goroute.smart.notify.service.EventRemindService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Alickx
 * @description 针对表【t_event_remind(事件提醒表)】的数据库操作Service实现
 * @createDate 2022-03-26 08:45:27
 */
@Service
public class EventRemindServiceImpl extends ServiceImpl<EventRemindMapper, EventRemind>
        implements EventRemindService {

    @Resource
    EventRemindMapper eventRemindMapper;

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
    public Response queryEventRemind(QueryParam queryParam) {

        // TODO 1.查询事件提醒

        return Response.success();

        /*
          查询未读提醒
           根据未读消息和时间排序
           第一排序条件为未读 -> 已读
           第二排序条件为提醒时间
         */
//        IPage<EventRemind> page = eventRemindMapper.selectPage(new Query<EventRemind>()
//                .getPage(queryParam), new LambdaQueryWrapper<EventRemind>()
//                .ne(EventRemind::getSenderId, authService.getLoginUid())
//                .eq(EventRemind::getRecipientId, authService.getLoginUid())
//                .orderByAsc(EventRemind::getState)
//                .eq(EventRemind::getActionType, queryParam.getType()));


        // 判断是否有数据，无数据则直接返回
//        if (CollUtil.isEmpty(page.getRecords())) {
//            return Response.ok().put("data", new PageUtils(page));
//        }
//
//        //获取用户信息
//        List<EventRemind> records = page.getRecords();
//        List<Long> senderIds = records.stream()
//                .map(EventRemind::getSenderId)
//                .collect(Collectors.toList());
//
//        List<MemberBo> memberBoList = memberFeignService.batchQueryUsers(senderIds);
//
//        List<EventRemindDto> eventReminds = new ArrayList<>(records.size());
//
//        // 将用户信息添加到事件集合中
//        for (int i = 0; i < records.size(); i++) {
//            EventRemindDto eventRemindDTO = new EventRemindDto();
//            MemberBo memberBo = memberBoList.get(i);
//            UserDto memberDto = ModelConverterUtils.convert(memberBo, UserDto.class);
//            eventRemindDTO.setSender(memberDto);
//            BeanUtils.copyProperties(records.get(i), eventRemindDTO);
//            eventReminds.add(eventRemindDTO);
//            records.get(i).setState(NotificationConstant.STATE_HAVE_READ);
//        }
//
//        Page<EventRemindDto> pageDTO = new Page<>();
//        BeanUtils.copyProperties(page, pageDTO);
//        pageDTO.setRecords(eventReminds);
//
//        // 更新通知状态
//        this.updateBatchById(records);
//
//        return Response.success();

    }

    /**
     * 查询每个提醒的未读数量
     *
     * @return 查询结果
     */
    @Override
    public Response queryUnreadCountRemind() {

        Long memberUid = authService.getLoginUid();
        List<EventRemind> eventReminds = eventRemindMapper.selectList(new LambdaQueryWrapper<EventRemind>()
                .eq(EventRemind::getState, NotificationConstant.STATE_UNREAD)
                .ne(EventRemind::getSenderId, memberUid)
                .eq(EventRemind::getRecipientId, memberUid)
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

        //TODO 未读消息数量

//        return Objects.requireNonNull(Response.ok().put("count", count)).put("data", statistical);
        return Response.success();
    }

}




