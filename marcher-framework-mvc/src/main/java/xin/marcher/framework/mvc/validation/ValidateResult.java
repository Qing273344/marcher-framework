package xin.marcher.framework.mvc.validation;

import javax.validation.constraints.NotNull;

/**
 * 校验结果
 *
 * @author marcher
 */
public class ValidateResult {

    /** 验证结果 */
    private boolean success;

    /** 提醒信息，失败会有提醒信息 */
    private String message;

    @NotNull
    public ValidateResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public static final ValidateResult SUCCESS = new ValidateResult(true, null);

    public static ValidateResult errorInstance(String message){
        return new ValidateResult(false,message);
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
