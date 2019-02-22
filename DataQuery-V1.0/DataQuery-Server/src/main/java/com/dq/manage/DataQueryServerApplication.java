package com.dq.manage;


import com.iov.common.framework.spring.ServiceBeanContext;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

/**
 * @author weijun.yu
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.dq.manage.provider"})
@ImportResource(locations = {"classpath:configs/provider/*.xml"})
@MapperScan(basePackages = {"com.dq.manage.mapper"})
public class DataQueryServerApplication {

    public static void main(String[] args) {

          // start embedded zookeeper server
        new EmbeddedZooKeeper(12181, false).start();

        ApplicationContext context = new SpringApplicationBuilder(DataQueryServerApplication.class)
                .web(false)
                .run(args);

        ServiceBeanContext.loadContext(context);
    }
}
