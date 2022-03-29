package cn.goroute.smart.member.service.impl;


import cn.goroute.smart.common.dao.MemberConchDao;
import cn.goroute.smart.common.dao.MemberDao;
import cn.goroute.smart.common.dao.TransactionRecordDao;
import cn.goroute.smart.common.entity.pojo.MemberConch;
import cn.goroute.smart.common.entity.pojo.Member;
import cn.goroute.smart.common.entity.pojo.TransactionRecord;
import cn.goroute.smart.common.entity.vo.MemberPayConchVO;
import cn.goroute.smart.common.exception.ServiceException;
import cn.goroute.smart.common.utils.Result;
import cn.goroute.smart.member.service.MemberConchEntityService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
* @author Alickx
* @description 针对表【t_member_conch(用户贝壳余额)】的数据库操作Service实现
* @createDate 2022-03-19 15:02:35
*/
@Service
public class MemberConchEntityServiceImpl extends ServiceImpl<MemberConchDao, MemberConch>
    implements MemberConchEntityService {

    @Resource
    MemberConchDao memberConchDao;

    @Resource
    TransactionRecordDao transactionRecordDao;

    @Resource
    MemberDao memberDao;

    /**
     * 购买商品扣除用户贝壳余额
     * @param memberPayConchVO 商品支付信息VO
     * @return 扣除结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result decrConchByPay(MemberPayConchVO memberPayConchVO) {

        //检查用户余额是否足够
        MemberConch memberConch = memberConchDao.selectOne(new LambdaQueryWrapper<MemberConch>()
                .eq(MemberConch::getMemberUid, memberPayConchVO.getMemberUid()));
        if (memberConch.getConch().compareTo(memberPayConchVO.getPrice()) < 0) {
            return Result.error();
        }

        Member member = memberDao.selectById(memberPayConchVO.getMemberUid());

        BigDecimal beforeConch = memberConch.getConch();

        //开始扣除余额
        memberConch.setConch(memberConch.getConch().subtract(memberPayConchVO.getPrice()));
        int result = memberConchDao.updateById(memberConch);
        if (result != 1) {
            return Result.error();
        }

        BigDecimal transactionConch = memberConch.getConch();

        TransactionRecord transactionRecord = new TransactionRecord();
        BeanUtils.copyProperties(memberPayConchVO, transactionRecord);
        transactionRecord.setMemberTransactionConch(transactionConch);
        transactionRecord.setMemberConch(beforeConch);
        transactionRecord.setMemberNickName(member.getNickName());
        transactionRecord.setCost(memberPayConchVO.getPrice());
        int insert = transactionRecordDao.insert(transactionRecord);
        if (insert != 1) {
            throw new ServiceException("交易记录错误");
        }

        return Result.ok();

    }
}




