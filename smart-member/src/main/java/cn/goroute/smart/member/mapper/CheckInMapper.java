package cn.goroute.smart.member.mapper;

import cn.goroute.smart.member.entity.pojo.CheckIn;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author Alickx
* @description 针对表【t_check_in(签到表)】的数据库操作Mapper
* @createDate 2022-07-09 11:29:00
* @Entity cn.goroute.smart.member.entity.pojo.CheckIn
*/
public interface CheckInMapper extends BaseMapper<CheckIn> {

    /**
     * 获取用户今天已经是否签到了
     * @param memberUid 用户uid
     * @return 签到实体
     */
    CheckIn getMemberIsCheckInToday(@Param("memberUid") Long memberUid);

    /**
     * 获取用户这个月的签到数据
     * @param memberUid
     * @return
     */
    List<CheckIn> getMemberCheckInMonth(@Param("memberUid") Long memberUid);
}




