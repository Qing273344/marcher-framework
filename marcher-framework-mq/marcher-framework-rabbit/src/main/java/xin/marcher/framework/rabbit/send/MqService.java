package xin.marcher.framework.rabbit.send;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import xin.marcher.framework.rabbit.converter.FastJsonMessageConverter;

/**
 * mq service
 *
 * @author marcher
 */
@Component
public class MqService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private void setExchange(String exchange) {
        rabbitTemplate.setExchange(exchange);
        rabbitTemplate.afterPropertiesSet();
    }

    /**
     * RabbitTemplate配置, 主要用于消息发送
     *
     * @param connectionFactory 连接工厂
     * @return
     *      RabbitTemplate
     */
    @Bean
    @Scope("prototype")
    @Primary
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(new FastJsonMessageConverter<>(Object.class));
        return rabbitTemplate;
    }

    /**
     * 发送消息
     *
     * @param routingKey    路由键 exchange与queue关联routingKey
     * @param object        消息对象
     */
    public void convertAndSend(String routingKey, Object object) {
        rabbitTemplate.convertAndSend(routingKey, object);
    }

    /**
     * 发送消息
     *
     * @param exchange      交换器
     * @param routingKey    路由键 exchange与queue关联routingKey
     * @param object        消息对象
     */
    public void convertAndSend(String exchange, String routingKey, Object object) {
        rabbitTemplate.convertAndSend(exchange, routingKey, object);
    }

    /**
     *
     * @param exchange          交换器
     * @param routingKey        路由键 exchange与queue关联routingKey
     * @param object            消息对象
     * @param correlationData   消息唯一id
     */
    public void converAndSend(String exchange, String routingKey, Object object, CorrelationData correlationData) {
        rabbitTemplate.convertAndSend(exchange, routingKey, object, correlationData);
    }
}
