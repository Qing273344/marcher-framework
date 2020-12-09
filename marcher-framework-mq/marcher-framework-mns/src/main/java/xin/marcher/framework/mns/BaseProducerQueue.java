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

/**
 * producer queue
 *
 * @author marcher
 */
@Slf4j
@Component
public abstract class BaseProducerQueue {

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

    public void sendMessage(String messageBody) {
        try {
            //发送消息
            CloudQueue queue = getQueueRef();
            Message message = new Message();
            message.setMessageBody(messageBody);
            Message putMsg = queue.putMessage(message);
            log.info("Send message id is: " + putMsg.getMessageId());
        } catch (ClientException ce) {
            log.error("Something wrong with the network connection between client and MNS service."
                    + "Please check your network and DNS availablity.", ce);
        } catch (ServiceException se) {
            log.error("Business dynamic producer exception requestId:" + se.getRequestId(), se);
            if (se.getErrorCode() != null) {
                if ("QueueNotExist".equals(se.getErrorCode())) {
                    log.error("Queue is not exist.Please create before use", se);
                } else if ("TimeExpired".equals(se.getErrorCode())) {
                    log.error("The request is time expired. Please check your local machine timeclock", se);
                }
            }
        } catch (Exception e) {
            log.error("send Failed in ProducerQueue.sendMessage", e);
        }
//        finally {
//            mnsClient.close();
//        }
    }
}
