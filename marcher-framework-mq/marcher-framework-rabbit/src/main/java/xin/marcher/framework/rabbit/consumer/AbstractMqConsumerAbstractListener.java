package xin.marcher.framework.rabbit.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import xin.marcher.framework.rabbit.MqHeader;
import xin.marcher.framework.rabbit.MqMessage;
import xin.marcher.framework.rabbit.send.MqService;

/**
 * 消息监听器
 *
 * @author marcher
 */
public abstract class AbstractMqConsumerAbstractListener<T> {

    @Autowired
    private MqService mqService;

    public abstract void handlerMessage(@Payload T t);

    public void sendErrorMessage(MqMessage msg, String errorQueue, String exchange) {
        MqHeader header = new MqHeader("error", "consumer", errorQueue);
        msg.setMqHeader(header);
        this.mqService.convertAndSend(exchange, errorQueue, msg);
    }
}
