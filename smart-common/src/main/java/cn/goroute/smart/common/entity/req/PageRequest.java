package cn.goroute.smart.common.entity.req;

import cn.goroute.smart.common.utils.SqlHandlerUtils;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: Alickx
 * @Date: 2022/09/03/14:59
 * @Description: 统一分页请求参数
 */
@Data
@ApiModel(value = "统一分页请求")
@Slf4j
public class PageRequest<T,R> {


    /**
     * 当前请求页码
     */
    @ApiModelProperty("当前页码")
    private Long current;

    /**
     * 当前请求数量
     */
    @ApiModelProperty("请求数据数量")
    private Long size;

    /**
     * 数据库映射的实体类型
     */
    @ApiModelProperty("实际分页类型")
    private Class<T> destClass;

    /**
     * 当前请求数据
     */
    @ApiModelProperty("请求对象")
    private R reqInstance;

    public PageRequest() {
        this.current = 1L;
        this.size = 10L;
    }

    public PageRequest(Long current) {
        this.current = current;
        this.size = 10L;
    }

    public PageRequest(Long current, Long size) {
        this.current = current;
        this.size = size;
    }

    public PageRequest(Long current, Long size, R reqInstance) {
        this.current = current;
        this.size = size;
        this.reqInstance = reqInstance;
    }

    public WrapperConstructor wrapperConstructor() {
        return new WrapperConstructor();
    }

    /**
     * 普通分页查询
     *
     * @param service
     * @return
     */
    public IPage<T> startPage(IService<T> service) {
        return service.page(new Page<>(current, size));
    }

    /**
     * 支持给定返回类型 的分页查询
     *
     * @param service 分页服务对象
     * @param p       响应对象
     * @param <P>     返回类型
     * @return
     * @throws IllegalAccessException
     */
    public  <P> IPage<P> startPage(IService<T> service, Class<P> p) throws IllegalAccessException {
        return copyPageAndRsp(p, startPage(service));
    }

    /**
     * 根据 reqInstance实例属性值 进行分页查询
     *
     * @param service
     * @return
     */
    public IPage<T> startPageByParam(IService<T> service) {
        return service.page(new Page<>(current, size),wrapperConstructor().defaultWrapper());
    }

    /**
     * 支持指定类型返回进行转换
     *
     * @param service 分页服务对象
     * @param p       响应对象
     * @param <P>     返回类型
     * @return
     * @throws IllegalAccessException
     */
    public <P> IPage<P> startPageByParam(IService<T> service, Class<P> p) throws IllegalAccessException {
        return copyPageAndRsp(p, startPageByParam(service));
    }

    /**
     * 基于reqInstance实例属性值并可结合可扩展的wrapper构造使用
     *
     * @param service 分页服务对象
     * @return
     * @throws IllegalAccessException
     */
    private IPage<T> startPageByParam(IService<T> service, LambdaQueryWrapper<T> wrapper) throws IllegalAccessException {
        return service.page(new Page<>(current, size),wrapper);
    }


    /**
     * 结合可扩展的wrapper构造使用
     *
     * @param service 分页服务对象
     * @param p       响应对象
     * @param <P>     返回类型
     * @return
     * @throws IllegalAccessException
     */
    private <P> IPage<P> startPageByParam(IService<T> service, Class<P> p, LambdaQueryWrapper<T> wrapper) throws IllegalAccessException {
        return copyPageAndRsp(p, startPageByParam(service,wrapper));
    }


    /**
     * 返回指定类型的分页处理
     *
     * @param p    指定类型
     * @param page 分页对象
     * @return
     */
    private <P> Page<P> copyPageAndRsp(Class<P> p, IPage<T> page) {
        Page<P> rPage = new Page<>();
        BeanUtils.copyProperties(page, rPage);
        if (p != null) {
            rPage.setRecords(page.getRecords().stream().map(item -> JSONUtil.toBean(JSONUtil.toJsonStr(item), p)).collect(Collectors.toList()));
        }
        return rPage;
    }


    /**
     * 扩展分页查询逻辑
     */
    public class WrapperConstructor {

        private LambdaQueryWrapper<T> wrapper = defaultWrapper();


        @SneakyThrows(IllegalAccessException.class)
        public IPage<T> startPage(IService<T> service) {
            return PageRequest.this.startPageByParam(service,wrapper);
        }

        @SneakyThrows(IllegalAccessException.class)
        public <P> IPage<P> startPage(IService<T> service, Class<P> p) {
            return PageRequest.this.startPageByParam(service,p,wrapper);
        }

        /**
         * 默认分页参数
         *
         * @return
         */
        @SneakyThrows(IllegalAccessException.class)
        private LambdaQueryWrapper<T> defaultWrapper() {
            return parseParam().lambda();
        }

        /**
         * 扩展方法 排序
         * @param columns
         * @return
         */
        public WrapperConstructor orderByDesc(SFunction<T, ?> columns) {
            this.wrapper = wrapper.orderByDesc(columns);
            return this;
        }

        public WrapperConstructor orderByAsc(SFunction<T, ?> columns) {
            this.wrapper = wrapper.orderByAsc(columns);
            return this;
        }

        // 后续可以再次添加其他分页操作，建议不要太复杂，否则推荐使用原生分页sql


        /**
         * 根据实例 解析 wrapper参数
         *
         * @return
         * @throws IllegalAccessException
         */
        private QueryWrapper<T> parseParam() throws IllegalAccessException {
            QueryWrapper<T> wrapper = new QueryWrapper<>();
            Map<String, Object> map = SqlHandlerUtils.getInstanceKeyValue(PageRequest.this.reqInstance);
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                if (value instanceof CharSequence) {
                    wrapper = NumberUtil.isNumber((CharSequence) value) ? wrapper.eq(key, value) : wrapper.like(key, value);
                } else if (value instanceof Number) {
                    wrapper = wrapper.eq(key, value);
                } else {
                    wrapper = wrapper.like(key, value);
                }
            }
            return wrapper;
        }
    }

    public void setCurrent(Long current) {
        if (current == null || current == 0) {
            this.current = 1L;
            return;
        }
        this.current = current;
    }

    public void setSize(Long size) {
        if (size == null || size == 0) {
            this.size = 10L;
            return;
        }
        this.size = size;
    }


}
