package cn.goroute.smart.mybatis.config;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

/**
 * @Author: Alickx
 * @Date: 2022/09/03/17:47
 * @Description: 全局Id生成器
 */
@Component
public class GlobalIdGenerator implements MetaObjectHandler {


    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "id", IdUtil::getSnowflakeNextId,Long.class);
    }

    @Override
    public void updateFill(MetaObject metaObject) {

    }
}
