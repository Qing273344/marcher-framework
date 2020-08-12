package xin.marcher.framework.rabbit;

import lombok.Getter;
import lombok.Setter;

/**
 * mq header
 *
 * @author marcher
 */
@Getter
@Setter
public class MqHeader {

    private String action;
    private String from;
    private String key;

    public MqHeader() {
    }

    public MqHeader(String action, String from, String key) {
        this.action = action;
        this.from = from;
        this.key = key;
    }
}
