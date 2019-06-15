package com.deyi.clock.config.core;

import com.deyi.clock.utils.log.LogUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author lyz
 * @version 1.0.0
 * @ClassName MyWebConfig
 * @Description TODO
 * @createTime 2019年06月14日 09:46
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {


    @Value("${web.upload-path}")
    private String path;
    @Value("${web.path-mapping}")
    private String pathMapping;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(pathMapping+"**").addResourceLocations("file:"+path);
    }

}
