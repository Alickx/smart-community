package cn.goroute.smart.member.config;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MybatisMetaObjectHandler implements MetaObjectHandler {

    public static final String defaultNickName = "新人_";

    public static final String defaultAvatar = "https://songtiancloud-1300061766.cos.ap-guangzhou.myqcloud.com/img/OIP.jpg";

    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "createdTime", LocalDateTime::now, LocalDateTime.class);
        this.strictInsertFill(metaObject, "updatedTime", LocalDateTime::now, LocalDateTime.class);
        this.strictInsertFill(metaObject, "nickName", () -> defaultNickName + RandomUtil.randomString(5), String.class);
        this.strictInsertFill(metaObject, "avatar", () -> defaultAvatar, String.class);
    }

    @Override
    public void updateFill(MetaObject metaObject) {

        this.strictUpdateFill(metaObject, "updatedTime", LocalDateTime::now, LocalDateTime.class);
    }
}
