package cn.goroute.smart.common.config;

import com.alibaba.fastjson2.JSONReader;
import com.alibaba.fastjson2.JSONWriter;
import com.alibaba.fastjson2.support.config.FastJsonConfig;
import com.alibaba.fastjson2.support.spring6.http.converter.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;

import java.util.List;

/**
 * @Author: Alickx
 * @Date: 2023/02/01/21:19
 * @Description:
 */
@Configuration
public class FastJson2Config {

    @Bean
    public FastJsonHttpMessageConverter fastJsonHttpMessageConverter(){
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        FastJsonConfig config = new FastJsonConfig();
        config.setDateFormat("yyyy-MM-dd HH:mm:ss");
        config.setReaderFeatures(JSONReader.Feature.FieldBased, JSONReader.Feature.SupportArrayToBean);
        config.setWriterFeatures(JSONWriter.Feature.WriteMapNullValue, JSONWriter.Feature.PrettyFormat);
        config.setWriterFeatures(JSONWriter.Feature.BrowserCompatible);
        converter.setFastJsonConfig(config);
        converter.setSupportedMediaTypes(getSupportedMediaTypes());
        return converter;
    }

    private List<MediaType> getSupportedMediaTypes() {
        return List.of(MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML,
                MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_OCTET_STREAM, MediaType.APPLICATION_PDF,
                MediaType.APPLICATION_RSS_XML, MediaType.APPLICATION_XHTML_XML, MediaType.APPLICATION_XML,
                MediaType.IMAGE_GIF, MediaType.IMAGE_JPEG, MediaType.IMAGE_PNG, MediaType.TEXT_EVENT_STREAM,
                MediaType.TEXT_HTML, MediaType.TEXT_MARKDOWN, MediaType.TEXT_PLAIN, MediaType.TEXT_XML);
    }

}
