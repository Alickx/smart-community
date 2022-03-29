package cn.goroute.smart.product.service.impl;

import cn.goroute.smart.common.dao.ProductGroupDao;
import cn.goroute.smart.common.entity.pojo.ProductGroup;
import cn.goroute.smart.product.service.ProductGroupEntityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
* @author Alickx
* @description 针对表【t_product_group(商品组表)】的数据库操作Service实现
* @createDate 2022-03-19 15:00:21
*/
@Service
public class ProductGroupEntityServiceImpl extends ServiceImpl<ProductGroupDao, ProductGroup>
    implements ProductGroupEntityService {

}




