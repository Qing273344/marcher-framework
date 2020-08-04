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
    PARAM_MISS(1, "请提交"),
    PARAM_ILLEGAL(2, "错误的参数"),
    NOT_PERMISSION(3, "没有访问权限"),
    NOT_RESOURCE(5, "没有找到你想要的"),

    NOT_LOGIN(10, "您当前未登录, 请登录"),
    LOGIN_TOKEN_INVALID(11, "登录失效咯, 请重新登录"),

    SERVER_ERROR(500, "发生异常，请稍后重试"),

    COMMON_NOT_EMPTY(999, "请选择要操作的数据"),
    COMMON_SHOW_NOT_EMPTY(998, "请选择要查看的数据"),
    COMMON_OPTIONS_INEXISTENCE(997, "操作项不存在"),
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
