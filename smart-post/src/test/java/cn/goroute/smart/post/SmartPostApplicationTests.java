package cn.goroute.smart.post;

import cn.goroute.smart.common.utils.RedisKeyConstant;
import cn.goroute.smart.post.service.impl.ThumbServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.io.IOException;

@SpringBootTest
class SmartPostApplicationTests {

    @Autowired
    ThumbServiceImpl thumbService;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    private static Cursor<String> scan(StringRedisTemplate stringRedisTemplate, String match, int count){
        ScanOptions scanOptions = ScanOptions.scanOptions().match(match).count(count).build();
        RedisSerializer<String> redisSerializer = (RedisSerializer<String>) stringRedisTemplate.getKeySerializer();
        return (Cursor) stringRedisTemplate.executeWithStickyConnection((RedisCallback) redisConnection ->
                new ConvertingCursor<>(redisConnection.scan(scanOptions), redisSerializer::deserialize));
    }


    @Test
    void contextLoads() throws IOException {
        Cursor<String> cursor = scan(stringRedisTemplate, RedisKeyConstant.POST_COUNT_KEY + "*", 1000);
        while (cursor.hasNext()) {
            String next = cursor.next();
            System.out.println(next);
            String[] split = next.split(":");
            System.out.println("文章id为" + split[split.length - 1]);
        }
        cursor.close();
    }

}
