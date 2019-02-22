package com.dq.manage.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * @author tengj
 * @date 2017/3/13
 */
@Configuration
public class MvcConfiguration extends WebMvcConfigurerAdapter {
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/webapp/WEB-INF");
        resolver.setSuffix(".xml");
        registry.viewResolver(resolver);
    }
}

