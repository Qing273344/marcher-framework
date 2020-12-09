package xin.marcher.framework.mybatis.config;

import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xin.marcher.framework.mybatis.injector.CustomSqlInject;

/**
 * mybatis-plus配置
 *
 * @author marcher
 */
@Configuration
public class MybatisPlusConfig {

    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    /**
     * 注入主键生成器
     */
//    @Bean
//    public IKeyGenerator keyGenerator(){
//        return new H2KeyGenerator();
//    }

    /**
     * 自定义方法扩展注入器
     *
     * @return ISqlInjector CustomSqlInject
     */
    @Bean
    @ConditionalOnMissingBean(ISqlInjector.class)
    public ISqlInjector sqlInjector(){
        return new CustomSqlInject();
    }

    /**
     * 乐观锁插件
     */
    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInterceptor();
    }

}
