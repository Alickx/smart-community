package cn.goroute.smart.common.entity.resp;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Author: Alickx
 * @Date: 2022/09/03/14:57
 * @Description: 统一分页返回结果
 */
@Data
@Accessors(chain = true)
public class PageResponse<T> {

    private long code = 200;

    private String message = "success";

    /**
     * 总量
     */
    private long total;

    /**
     * 当前所在页码
     */
    private long current;

    /**
     * 页码数量
     */
    private long pageSize;

    /**
     * 数据集合
     */
    protected List<T> data;

    public PageResponse(IPage<T> page) {
        this.total = page.getTotal();
        this.data = page.getRecords();
        this.current = page.getCurrent();
        this.pageSize = page.getPages();
    }


    public static <T> PageResponse<T> success(IPage<T> page){
        return new PageResponse<>(page);
    }
}
