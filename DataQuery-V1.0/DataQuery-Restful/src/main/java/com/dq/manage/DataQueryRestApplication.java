package com.dq.manage;

import com.iov.common.framework.spring.ServiceBeanContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.MultipartConfigElement;

/**
 * @author weijun.yu
 */
@SpringBootApplication
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@EnableSwagger2
@ImportResource(locations = {"classpath:configs/consumer/*.xml"})
public class DataQueryRestApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(DataQueryRestApplication.class);
        ServiceBeanContext.loadContext(context);
    }

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory multipartConfigFactory = new MultipartConfigFactory();
        multipartConfigFactory.setMaxFileSize("102400MB");
        return multipartConfigFactory.createMultipartConfig();
    }
}
