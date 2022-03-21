package cn.goroute.smart.member.service.impl;


import cn.goroute.smart.common.dao.MemberConchEntityDao;
import cn.goroute.smart.common.dao.MemberDao;
import cn.goroute.smart.common.dao.TransactionRecordEntityDao;
import cn.goroute.smart.common.entity.pojo.MemberConchEntity;
import cn.goroute.smart.common.entity.pojo.MemberEntity;
import cn.goroute.smart.common.entity.pojo.TransactionRecordEntity;
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
public class MemberConchEntityServiceImpl extends ServiceImpl<MemberConchEntityDao, MemberConchEntity>
    implements MemberConchEntityService {

    @Resource
    MemberConchEntityDao memberConchEntityDao;

    @Resource
    TransactionRecordEntityDao transactionRecordDao;

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
        MemberConchEntity memberConchEntity = memberConchEntityDao.selectOne(new LambdaQueryWrapper<MemberConchEntity>()
                .eq(MemberConchEntity::getMemberUid, memberPayConchVO.getMemberUid()));
        if (memberConchEntity.getConch().compareTo(memberPayConchVO.getPrice()) < 0) {
            return Result.error();
        }

        MemberEntity member = memberDao.selectById(memberPayConchVO.getMemberUid());

        BigDecimal beforeConch = memberConchEntity.getConch();

        //开始扣除余额
        memberConchEntity.setConch(memberConchEntity.getConch().subtract(memberPayConchVO.getPrice()));
        int result = memberConchEntityDao.updateById(memberConchEntity);
        if (result != 1) {
            return Result.error();
        }

        BigDecimal transactionConch = memberConchEntity.getConch();

        TransactionRecordEntity transactionRecordEntity = new TransactionRecordEntity();
        BeanUtils.copyProperties(memberPayConchVO,transactionRecordEntity);
        transactionRecordEntity.setMemberTransactionConch(transactionConch);
        transactionRecordEntity.setMemberConch(beforeConch);
        transactionRecordEntity.setMemberNickName(member.getNickName());
        transactionRecordEntity.setCost(memberPayConchVO.getPrice());
        int insert = transactionRecordDao.insert(transactionRecordEntity);
        if (insert != 1) {
            throw new ServiceException("交易记录错误");
        }

        return Result.ok();

    }
}




