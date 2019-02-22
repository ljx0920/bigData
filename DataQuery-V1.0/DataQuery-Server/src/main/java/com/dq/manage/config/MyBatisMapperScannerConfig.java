package com.dq.manage.config;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatis扫描接口，使用的tk.mybatis.spring.mapper.MapperScannerConfigurer <br/>
 * 如果你不使用通用Mapper，可以改为org.xxx...
 */
@Configuration
//TODO 注意，由于MapperScannerConfigurer执行的比较早，所以必须有下面的注解
@AutoConfigureAfter(MybatisAutoConfiguration.class)
public class MyBatisMapperScannerConfig {

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        mapperScannerConfigurer.setBasePackage("org.springboot.sample.mapper");
        return mapperScannerConfigurer;
    }
}