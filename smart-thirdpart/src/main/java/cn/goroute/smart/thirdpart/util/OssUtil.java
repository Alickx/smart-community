package cn.goroute.smart.thirdpart.util;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class OssUtil {

    @Value("${oss.accessId}")
    private String accessId;

    @Value("${oss.accessKey}")
    private String accessKey;

    @Value("${oss.endpoint}")
    private String endpoint;

    @Value("${oss.bucket}")
    private String bucket;

    @Value("${oss.dir}")
    private String dir;

}
