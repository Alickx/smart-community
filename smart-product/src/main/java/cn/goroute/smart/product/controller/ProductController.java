package cn.goroute.smart.product.controller;

import cn.goroute.smart.common.entity.vo.ProductPayVO;
import cn.goroute.smart.common.utils.QueryParam;
import cn.goroute.smart.common.utils.Result;
import cn.goroute.smart.product.service.ProductEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Alickx
 * @Date: 2022/03/19/15:24
 * @Description: 商品Api
 */
@RestController
@RequestMapping("product")
public class ProductController {

    @Autowired
    ProductEntityService productEntityService;

    /**
     * 分页查询商品
     * @param queryParam 分页参数
     * @return 商品集合
     */
    @PostMapping("/query_list")
    public Result queryList(@RequestBody QueryParam queryParam){
        return productEntityService.queryList(queryParam);
    }

    @PostMapping("/pay")
    public Result buyProduct(@RequestBody ProductPayVO productPayVO) {
        return productEntityService.buyProduct(productPayVO);
    }


}
