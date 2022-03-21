package cn.goroute.smart.product.service.impl;

import cn.goroute.smart.common.dao.ProductStockDao;
import cn.goroute.smart.common.entity.pojo.ProductStock;
import cn.goroute.smart.product.service.ProductStockService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
* @author Alickx
* @description 针对表【t_product_stock(商品库存表)】的数据库操作Service实现
* @createDate 2022-03-19 20:57:29
*/
@Service
public class ProductStockServiceImpl extends ServiceImpl<ProductStockDao, ProductStock>
    implements ProductStockService {

}




