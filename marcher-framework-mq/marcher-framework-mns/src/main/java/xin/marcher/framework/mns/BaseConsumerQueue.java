package xin.marcher.framework.mns;

import com.aliyun.mns.client.CloudQueue;
import com.aliyun.mns.client.MNSClient;
import com.aliyun.mns.common.ClientException;
import com.aliyun.mns.common.ServiceException;
import com.aliyun.mns.model.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xin.marcher.framework.common.util.EmptyUtil;
import xin.marcher.framework.common.util.SpringContextUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * consumer queue
 *
 * @author marcher
 */
@Slf4j
@Component
public abstract class BaseConsumerQueue {

    @Autowired
    private MNSClient mnsClient;

    protected MNSClient getMnsClient() {
        if (EmptyUtil.isNotEmpty(mnsClient)) {
            return this.mnsClient;
        }
        return SpringContextUtil.getBean(MNSClient.class);
    }

    /**
     * get Queue
     *
     * @return
     */
    abstract protected CloudQueue getQueueRef();

    /**
     * 实现消息
     *
     * @param jsonMsg   消息体(json格式)
     */
    abstract protected void execute(String jsonMsg);

    /**
     * 实现消息-批量
     *
     * @param jsonMsgList   消息体(json格式)
     */
    abstract protected void execute(List<String> jsonMsgList);

    public final void consumer() {
        // 消费消息
        CloudQueue queue = getQueueRef();
        Message message = queue.popMessage();
        try {
            if (EmptyUtil.isNotEmpty(message)) {
                String messageBodyAsString = message.getMessageBodyAsString();
                // 执行
                execute(messageBodyAsString);

                // 删除已经去除消费的消息
                queue.deleteMessage(message.getReceiptHandle());
            }
        } catch (ClientException ce) {
            log.error("Something wrong with the network connection between client and MNS service."
                    + "Please check your network and DNS availablity.", ce);
        } catch (ServiceException se) {
            log.error("MNS exception requestId:" + se.getRequestId()  + " in ConsumerQueue.consumer", se);
            if (se.getErrorCode() != null) {
                if ("QueueNotExist".equals(se.getErrorCode())) {
                    log.error("Queue is not exist.Please create before use", se);
                } else if ("TimeExpired".equals(se.getErrorCode())) {
                    log.error("The request is time expired. Please check your local machine timeclock", se);
                } else {
                    log.error("mns error code: " + se.getErrorCode() + " in ConsumerQueue.batchConsumer", se);
                }
            }
        } catch (Exception e) {
            log.error("send Failed in ConsumerQueue.consumer", e);
        }
//        finally {
//            mnsClient.close();
//        }
    }

    public final void batchConsumer(int batchSize) {
        // 消费消息
        CloudQueue queue = getQueueRef();
        List<Message> messageList = new ArrayList<>();
        // 从队列中取出 3 * 16 条数据
        for (int i = 0; i < 3; i++) {
            List<Message> messages = queue.batchPopMessage(batchSize);
            if (EmptyUtil.isEmpty(messages)) {
                break;
            }
            messages = messages.stream().filter(Objects::nonNull).collect(Collectors.toList());
            if (EmptyUtil.isEmpty(messages)) {
                break;
            }
            messageList.addAll(messages);
        }
        try {
            if (EmptyUtil.isNotEmpty(messageList)) {
                List<String> messageBodyAsStringList = messageList.stream().map(Message::getMessageBodyAsString).collect(Collectors.toList());
                List<String> receiptHandleList = messageList.stream().map(Message::getReceiptHandle).collect(Collectors.toList());

                // 执行
                execute(messageBodyAsStringList);

                // 删除已经消费的消息
                while (true) {
                    List<String> currentDelMessage = receiptHandleList.stream().limit(batchSize).collect(Collectors.toList());
                    if (currentDelMessage.size() <= 0) {
                        break;
                    }
                    queue.batchDeleteMessage(currentDelMessage);
                    receiptHandleList = receiptHandleList.stream().skip(batchSize).collect(Collectors.toList());
                }
            }
        } catch (ClientException ce) {
            log.error("Something wrong with the network connection between client and MNS service."
                    + "Please check your network and DNS availablity.", ce);
        } catch (ServiceException se) {
            log.error("MNS exception requestId:" + se.getRequestId() + " in ConsumerQueue.batchConsumer", se);
            if (se.getErrorCode() != null) {
                if ("QueueNotExist".equals(se.getErrorCode())) {
                    log.error("Queue is not exist.Please create before use", se);
                } else if ("TimeExpired".equals(se.getErrorCode())) {
                    log.error("The request is time expired. Please check your local machine timeclock", se);
                } else {
                    log.error("mns error code: " + se.getErrorCode() + " in ConsumerQueue.batchConsumer", se);
                }
            }
        } catch (Exception e) {
            log.error("send Failed in ConsumerQueue.batchConsumer", e);
        }
//        finally {
//            mnsClient.close();
//        }
    }
}
