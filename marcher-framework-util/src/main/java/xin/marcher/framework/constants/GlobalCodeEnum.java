package xin.marcher.framework.constants;

import xin.marcher.framework.core.IEnumNorm;

/**
 * 返回code
 *
 * @author marcher
 */
public enum GlobalCodeEnum implements IEnumNorm {

    /** ok */
    OK(0, "ok"),

    SERVER_ERROR(500, "服务器开小差了, 请稍后再试"),
    ;

    private Integer code;
    private String message;

    GlobalCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }


    @Override
    public int getRealCode() {
        return code;
    }

    @Override
    public String getRealDesc() {
        return message;
    }
}
