package cn.goroute.smart.product.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.goroute.smart.common.dao.ProductDao;
import cn.goroute.smart.common.dao.ProductStockDao;
import cn.goroute.smart.common.entity.dto.MemberConchDTO;
import cn.goroute.smart.common.entity.dto.ProductDTO;
import cn.goroute.smart.common.entity.pojo.Product;
import cn.goroute.smart.common.entity.pojo.ProductStock;
import cn.goroute.smart.common.entity.vo.MemberPayConchVO;
import cn.goroute.smart.common.entity.vo.ProductPayVO;
import cn.goroute.smart.common.exception.ServiceException;
import cn.goroute.smart.common.utils.PageUtils;
import cn.goroute.smart.common.utils.Query;
import cn.goroute.smart.common.utils.QueryParam;
import cn.goroute.smart.common.utils.Result;
import cn.goroute.smart.product.feign.MemberFeignService;
import cn.goroute.smart.product.service.ProductEntityService;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Alickx
 * @description 针对表【t_product(社区商品表)】的数据库操作Service实现
 * @createDate 2022-03-19 14:56:37
 */
@Service
public class ProductEntityServiceImpl extends ServiceImpl<ProductDao, Product>
        implements ProductEntityService {

    @Resource
    ProductDao productDao;

    @Resource
    ProductStockDao productStockDao;

    @Autowired
    MemberFeignService memberFeignService;

    @Autowired
    RedissonClient redissonClient;

    /**
     * 分页查询商品
     *
     * @param queryParam 分页参数
     * @return
     */
    @Override
    public Result queryList(QueryParam queryParam) {
        IPage<Product> productEntityIPage = productDao.selectPage(new Query<Product>().getPage(queryParam),
                new QueryWrapper<>());

        List<Product> records = productEntityIPage.getRecords();
        List<ProductDTO> productDTOList = new ArrayList<>(10);
        ProductDTO productDTO = new ProductDTO();
        records.forEach(r -> {
            BeanUtils.copyProperties(r, productDTO);

            ProductStock productStock = productStockDao.selectOne(new LambdaQueryWrapper<ProductStock>()
                    .eq(ProductStock::getProductUid, r.getUid()).select(ProductStock::getStock));

            productDTO.setStock(productStock.getStock());
            productDTOList.add(productDTO);
        });
        IPage<ProductDTO> productDTOIPage = new Page<>();

        BeanUtils.copyProperties(productEntityIPage, productDTOIPage);
        productDTOIPage.setRecords(productDTOList);

        PageUtils pageUtils = new PageUtils(productDTOIPage);
        return Result.ok().put("data", pageUtils);
    }

    /**
     * 购买商品
     *
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result buyProduct(ProductPayVO productPayVO) {

        //加锁
        RLock lock = redissonClient.getLock("product-lock");
        lock.lock();

        String memberUid = StpUtil.getLoginIdAsString();
        //检查用户的余额是否足够支付
        Result result = memberFeignService.queryConchInfoByMemberUid(memberUid);
        String memberConchDTOJson = JSONUtil.toJsonStr(result.get("data"));
        MemberConchDTO memberConchDTO = JSONUtil.toBean(memberConchDTOJson, MemberConchDTO.class);
        Product product = productDao.selectById(productPayVO.getProductUid());
        if (product != null) {
            if (memberConchDTO.getConch().compareTo(product.getPrice()) < 0) {
                return Result.error("用户贝壳余额不足");
            }
        } else {
            return Result.error("没有该商品");
        }

        //查询库存和扣减库存操作
        ProductStock productStock = checkStock(productPayVO.getProductUid());
        descStock(productStock);
        lock.unlock();

        //扣减用户贝壳余额
        MemberPayConchVO memberPayConchVO = new MemberPayConchVO();
        memberPayConchVO.setProductName(product.getName());
        memberPayConchVO.setPrice(product.getPrice());
        memberPayConchVO.setMemberUid(memberUid);
        memberPayConchVO.setType(productPayVO.getType());
        memberPayConchVO.setProductUid(product.getUid());
        if (productPayVO.getType() == 1) {
            memberPayConchVO.setToUid(productPayVO.getToUid());
        }
        Result conchResult = memberFeignService.decrConchByPay(memberPayConchVO);

        int code = (int) conchResult.get("code");
        if (code == 0) {
            return Result.ok();
        }
        throw new ServiceException("用户扣除余额失败!");
    }

    /**
     * 检查库存
     *
     * @param sid 商品库存主键
     * @return
     */
    private ProductStock checkStock(int sid) {
        ProductStock productStock = productStockDao.selectOne(new LambdaQueryWrapper<ProductStock>()
                .eq(ProductStock::getUid, sid));
        if (productStock.getStock() <= 0) {
            throw new ServiceException("商品库存不足");
        }
        return productStock;
    }

    /**
     * 扣除库存
     *
     * @param productStock 商品库存实体类
     */
    @Transactional(rollbackFor = Exception.class)
    public void descStock(ProductStock productStock) {
        Integer stock = productStock.getStock();
        productStock.setStock(stock - 1);
        int i = productStockDao.updateById(productStock);
        if (i != 1) {
            throw new ServiceException("扣除库存失败");
        }
    }
}




