package cn.goroute.smart.product.service;

import cn.goroute.smart.common.entity.pojo.Product;
import cn.goroute.smart.common.entity.vo.ProductPayVO;
import cn.goroute.smart.common.utils.QueryParam;
import cn.goroute.smart.common.utils.Result;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Alickx
* @description 针对表【t_product(社区商品表)】的数据库操作Service
* @createDate 2022-03-19 14:56:37
*/
public interface ProductEntityService extends IService<Product> {

    Result queryList(QueryParam queryParam);

    Result buyProduct(ProductPayVO productPayVO);
}
