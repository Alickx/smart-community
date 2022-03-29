package cn.goroute.smart.member.service;


import cn.goroute.smart.common.entity.pojo.MemberConch;
import cn.goroute.smart.common.entity.vo.MemberPayConchVO;
import cn.goroute.smart.common.utils.Result;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Alickx
* @description 针对表【t_member_conch(用户贝壳余额)】的数据库操作Service
* @createDate 2022-03-19 15:02:35
*/
public interface MemberConchEntityService extends IService<MemberConch> {

    Result decrConchByPay(MemberPayConchVO memberPayConchVO);
}
