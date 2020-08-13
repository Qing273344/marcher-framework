package xin.marcher.framework.mvc.stream;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

/**
 * @author marcher
 */
@Configuration
public class StreamFilterConfig {

    /**
     * 注册过滤器
     *
     * @return  FilterRegistrationBean
     */
    @Bean
    public FilterRegistrationBean<Filter> someFilterRegistration() {
        // 通过 FilterRegistrationBean 实例设置优先级可以生效
        // 通过 @WebFilter 无效
        FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<>();
        registration.setFilter(replaceStreamFilter());
        registration.addUrlPatterns("/*");
        registration.setName("streamFilter");
        // 优先级, 1: 最顶级
        registration.setOrder(1);
        return registration;
    }

    /**
     * 实例化 StreamFilter
     *
     * @return  Filter
     */
    @Bean
    public Filter replaceStreamFilter() {
        return new ReplaceStreamFilter();
    }
}
