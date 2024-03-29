package cn.goroute.smart.common.domain;

import lombok.Data;

import java.util.Collections;
import java.util.List;

/**
 * 分页返回结果
 */
@Data
public class PageResult<T> {

	/**
	 * 查询数据列表
	 */
	protected List<T> records = Collections.emptyList();

	/**
	 * 总数
	 */
	protected Long total = 0L;

	public PageResult() {
	}

	public PageResult(long total) {
		this.total = total;
	}

	public PageResult(List<T> records, long total) {
		this.records = records;
		this.total = total;
	}

}
