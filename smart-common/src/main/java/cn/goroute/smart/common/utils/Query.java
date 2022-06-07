package cn.goroute.smart.common.utils;

import cn.goroute.smart.common.constant.Constant;
import cn.goroute.smart.common.xss.SQLFilter;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang.StringUtils;

/**
 * 查询参数
 */
public class Query<T> {

    public IPage<T> getPage(QueryParam queryParam) {
        return this.getPage(queryParam, null, false);
    }

    public IPage<T> getPage(String curPage,String pageSize) {
        QueryParam queryParam = new QueryParam();
        queryParam.setCurPage(curPage);
        queryParam.setLimit(pageSize);
        return this.getPage(queryParam);
    }


    public IPage<T> getPage(QueryParam queryParam, String defaultOrderField, boolean isAsc) {
        //分页参数
        long curPage = 1;
        long limit = 10;
        long maxLimit = 50;

        if (queryParam.getCurPage() != null) {
            curPage = Long.parseLong(queryParam.getCurPage());
        }
        if (queryParam.getLimit() != null) {
            if (Long.parseLong(queryParam.getLimit()) <= maxLimit) {
                limit = Long.parseLong(queryParam.getLimit());
            }
        }

        //分页对象
        Page<T> page = new Page<>(curPage, limit);

        //分页参数
//        params.put(Constant.PAGE, page);

        //排序字段
        //防止SQL注入（因为sidx、order是通过拼接SQL实现排序的，会有SQL注入风险）
        String orderField = SQLFilter.sqlInject(queryParam.getSidx());
        String order = queryParam.getOrder();


        //前端字段排序
        if (StringUtils.isNotEmpty(orderField) && StringUtils.isNotEmpty(order)) {
            if (Constant.ASC.equalsIgnoreCase(order)) {
                return page.addOrder(OrderItem.asc(orderField));
            } else {
                return page.addOrder(OrderItem.desc(orderField));
            }
        }

        //没有排序字段，则不排序
        if (StringUtils.isBlank(defaultOrderField)) {
            return page;
        }

        //默认排序
        if (isAsc) {
            page.addOrder(OrderItem.asc(defaultOrderField));
        } else {
            page.addOrder(OrderItem.desc(defaultOrderField));
        }

        return page;
    }
}
