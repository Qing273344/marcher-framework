package xin.marcher.framework.rabbit.config;

import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * mq config
 *
 * @author marcher
 */
@Configuration
public class RabbitMqConfig {

    /**
     * 监听消息 - 消费者
     * 消息数据转成json数据
     *
     * @param connectionFactory     connectionFactory
     * @return
     *      SimpleRabbitListenerContainerFactory
     */
    @Bean(name = {"rabbitListenerContainerFactory"})
    @ConditionalOnMissingBean(name = {"rabbitListenerContainerFactory"})
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        return factory;
    }
}
