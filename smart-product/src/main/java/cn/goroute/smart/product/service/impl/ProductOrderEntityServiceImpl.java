package cn.goroute.smart.product.service.impl;

import cn.goroute.smart.common.dao.ProductOrderDao;
import cn.goroute.smart.common.entity.pojo.ProductOrder;
import cn.goroute.smart.product.service.ProductOrderEntityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
* @author Alickx
* @description 针对表【t_product_order(商品订单表)】的数据库操作Service实现
* @createDate 2022-03-19 15:01:42
*/
@Service
public class ProductOrderEntityServiceImpl extends ServiceImpl<ProductOrderDao, ProductOrder>
    implements ProductOrderEntityService {

}




