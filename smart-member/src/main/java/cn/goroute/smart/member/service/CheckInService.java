package cn.goroute.smart.member.service;

import cn.goroute.smart.member.entity.pojo.CheckIn;
import cn.goroute.smart.common.entity.resp.Response;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Alickx
* @description 针对表【t_check_in(签到表)】的数据库操作Service
* @createDate 2022-07-09 11:29:00
*/
public interface CheckInService extends IService<CheckIn> {

    Response checkIn();

    Response getCheckInInfo();
}
