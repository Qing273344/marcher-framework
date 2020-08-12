package xin.marcher.framework.rabbit;

import lombok.Getter;
import lombok.Setter;

/**
 * mq message
 *
 * @author marcher
 */
@Getter
@Setter
public class MqMessage<T> {

    private MqHeader mqHeader;
    private T body;

    public MqMessage() {
    }

    public MqMessage(MqHeader mqHeader, T body) {
        this.mqHeader = mqHeader;
        this.body = body;
    }
}
