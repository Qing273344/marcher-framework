package xin.marcher.framework.mvc.cors;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Collections;

/**
 * 跨域配置
 *
 * @author marcher
 */
@Configuration
@Order(0)
public class CorsConfig implements WebMvcConfigurer {

    /**
     * 此种方式有个坑, OPTIONS 会被拦截, 导致请求异常
     */
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        // 添加映射路径
//        registry.addMapping("/**")
//                // 放行哪些原始域(头部信息)
//                .allowedHeaders("*")
//                // 放行哪些原始域
//                .allowedOrigins("*")
//                // 放行哪些原始域(请求方式)
//                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
//                // 是否发送Cookie信息
//                .allowCredentials(true)
//                .maxAge(3600);
//    }


    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilter() {
        // 创建 CorsConfiguration 配置，相当于 CorsRegistration 注册信息
        final CorsConfiguration config = new CorsConfiguration();
        // 允许向该服务器提交请求的 URI，* 表示全部允许，在 SpringMVC 中，如果设成 *，会自动转成当前请求头中的Origin
        config.setAllowedOrigins(Collections.singletonList("*"));
        // 允许发送 Cookie
        config.setAllowCredentials(true);
        // 允许所有请求 Method
        config.addAllowedMethod("*");
        // 允许所有请求 Header
        config.setAllowedHeaders(Collections.singletonList("*"));
        // 允许所有响应 Header
        // config.setExposedHeaders(Collections.singletonList("*"));
        // 预检请求的缓存时间（秒），即在这个时间段里，对于相同的跨域请求不会再预检了
        config.setMaxAge(1800L);

        // 创建 UrlBasedCorsConfigurationSource 配置源，类似 CorsRegistry 注册表
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        // 创建 FilterRegistrationBean 对象
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(
                // 创建 CorsFilter 过滤器
                new CorsFilter(source));
        // 设置 order 排序。这个顺序很重要哦，为避免麻烦请设置在最前
        bean.setOrder(0);
        return bean;
    }
}
