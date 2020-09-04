package xin.marcher.framework.rabbit.converter;

import com.alibaba.fastjson.JSON;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.AbstractMessageConverter;
import org.springframework.amqp.support.converter.MessageConversionException;

import java.io.UnsupportedEncodingException;

/**
 * 消息转换器 - fastjson
 *
 * @author marcher
 */
public class FastJsonMessageConverter<T> extends AbstractMessageConverter {

    private static final String DEFAULT_CHARSET = "UTF-8";

    private Class<T> tClass;
    private volatile String defaultCharset = DEFAULT_CHARSET;

    public FastJsonMessageConverter(Class<T> tClass) {
        System.setProperty("fastjson.parser.autoTypeSupport", "true");
        this.tClass = tClass;
    }

    public void setDefaultCharset(String defaultCharset) {
        this.defaultCharset = (defaultCharset != null) ? defaultCharset : DEFAULT_CHARSET;
    }

    /**
     * 将对象转换为消息
     *
     * @param object            转换的对象
     * @param messageProperties 消息属性
     * @return
     *      Message
     * @throws MessageConversionException   创建消息出现的异常
     */
    @Override
    protected Message createMessage(Object object, MessageProperties messageProperties) throws MessageConversionException {
        byte[] bytes;
        try {
            bytes = JSON.toJSONString(object).getBytes(DEFAULT_CHARSET);
        } catch (UnsupportedEncodingException e) {
            throw new MessageConversionException("Failed to convert Message content", e);
        }
        messageProperties.setContentType("application/json");
        messageProperties.setContentEncoding(this.defaultCharset);
        messageProperties.setContentLength(bytes.length);

        return new Message(bytes, messageProperties);
    }

    /**
     * 消息转换为对象
     *
     * @param message   消息
     * @return
     *      获取的消息对象
     */
    @Override
    public T fromMessage(Message message) {
        String messageJson = "";
        try {
            messageJson = new String(message.getBody(), this.defaultCharset);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return JSON.parseObject(messageJson, this.tClass);
    }
}
