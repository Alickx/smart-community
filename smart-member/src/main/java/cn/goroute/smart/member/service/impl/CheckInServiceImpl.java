package cn.goroute.smart.member.service.impl;

import cn.goroute.smart.member.mapper.CheckInMapper;
import cn.goroute.smart.common.entity.pojo.CheckIn;
import cn.goroute.smart.common.service.AuthService;
import cn.goroute.smart.common.utils.Result;
import cn.goroute.smart.member.service.CheckInService;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
* @author Alickx
* @description 针对表【t_check_in(签到表)】的数据库操作Service实现
* @createDate 2022-07-09 11:29:00
*/
@Service
public class CheckInServiceImpl extends ServiceImpl<CheckInMapper, CheckIn>
    implements CheckInService{

    @Autowired
    private CheckInMapper checkInMapper;

    @Autowired
    private AuthService authService;

    /**
     * 签到功能
     * @return 签到结果
     */
    @Override
    public Result checkIn() {

        Long loginUid = authService.getLoginUid();

        // 获取用户今天已经是否签到了
        CheckIn checkIn = checkInMapper.getMemberIsCheckInToday(loginUid);
        if (checkIn != null) {
            return Result.error("今天已经签到了");
        }

        // 进行签到操作
        checkIn = new CheckIn();
        checkIn.setMemberUid(loginUid);
        checkIn.setCheckInDate(LocalDateTimeUtil.now());
        checkInMapper.insert(checkIn);

        //TODO 签到后的逻辑，积分增加，站内信发送等等

        return Result.ok("签到成功");
    }

    /**
     * 获取用户这个月的签到数据
     * @return 签到数据
     */
    @Override
    public Result getCheckInInfo() {

        // 获取签到数据
        Long loginUid = authService.getLoginUid();
        List<CheckIn> memberCheckInMonth = checkInMapper.getMemberCheckInMonth(loginUid);

        // 获取签到天数
        int checkInDays = memberCheckInMonth.size();

        HashMap<Object, Object> map = MapUtil.newHashMap(2);
        map.put("checkInDays", checkInDays);
        map.put("checkInList", memberCheckInMonth);
        return Result.ok().put("data", map);
    }

}




