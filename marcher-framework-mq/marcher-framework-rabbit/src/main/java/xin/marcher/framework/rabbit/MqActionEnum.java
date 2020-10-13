package xin.marcher.framework.rabbit;

/**
 * mq action
 *
 * @author marcher
 */
public enum MqActionEnum {

    /**
     * insert
     */
    INSERT("insert"),

    /**
     * update
     */
    UPDATE("update"),

    /**
     * delete
     */
    DELETE("delete")
    ;

    private final String action;

    MqActionEnum(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }

}
