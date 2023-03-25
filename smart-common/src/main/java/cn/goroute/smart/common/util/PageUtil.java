package cn.goroute.smart.common.util;


import cn.goroute.smart.common.domain.PageParam;
import cn.goroute.smart.common.domain.PageResult;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * @author Hccake 2021/1/19
 * @version 1.0
 */
public final class PageUtil {

	private PageUtil() {
	}

	/**
	 * 根据 PageParam 生成一个 IPage 实例
	 * @param pageParam 分页参数
	 * @param <V> 返回的 Record 对象
	 * @return IPage<V>
	 */
	public static <V> IPage<V> prodPage(PageParam pageParam) {
		Page<V> page = new Page<>(pageParam.getPage(), pageParam.getSize());
		List<PageParam.Sort> sorts = pageParam.getSorts();
		for (PageParam.Sort sort : sorts) {
			OrderItem orderItem = sort.isAsc() ? OrderItem.asc(sort.getField()) : OrderItem.desc(sort.getField());
			page.addOrder(orderItem);
		}
		return page;
	}

	/**
	 * 根据 IPage 生成一个 PageResult 实例
	 * @param page 分页结果
	 * @return PageResult<V> 分页结果
	 * @param <V> 返回的 Record 对象
	 */
	public static <V>PageResult<V> prodPageResult(IPage<V> page){
		PageResult<V> pageResult = new PageResult<>();
		pageResult.setTotal(page.getTotal());
		pageResult.setRecords(page.getRecords());
		return pageResult;
	}

}
