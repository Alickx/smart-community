package cn.goroute.smart.post.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MybatisMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "createdTime", LocalDateTime::now, LocalDateTime.class);
        this.strictInsertFill(metaObject, "updatedTime", LocalDateTime::now, LocalDateTime.class);
        this.strictInsertFill(metaObject, "thumbCount", () -> 0, Integer.class);
        this.strictInsertFill(metaObject, "collectCount", () -> 0, Integer.class);
        this.strictInsertFill(metaObject, "clickCount", () -> 0, Integer.class);
        this.strictInsertFill(metaObject, "commentCount", () -> 0, Integer.class);
    }

    @Override
    public void updateFill(MetaObject metaObject) {

        this.strictUpdateFill(metaObject, "updatedTime", LocalDateTime::now, LocalDateTime.class);
    }
}
