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

    GL_FAIL(-1, "失败"),
    GL_EXCEPTION(-2, "系统内部异常"),
    GL_BUSINESSFAIL(-3, "业务操作失败"),

    GL_PARAMETER_EMPTY(1999999001, "参数不能为空"),
    GL_PARAMETER_ERROR(1999999002, "参数错误"),
    GL_OPERATION_TOO_FAST(1999999003, "点慢点行不啦"),
    GL_OBJECT_NOT_EXISTS(1999999004, "对象不存在"),
    GL_OBJECT_ALREADY_EXISTS(1999999005, "对象已存在"),

    GL_401(1999999401, "是想访问么? 让我想想..."),
    GL_403(1999999403, "是想访问么? 不给..."),
    GL_404(1999999404, "飞到看不见的地方去了..."),

    GL_SERVER_ERROR(500, "服务器开小差了, 请稍后再试"),
    ;

    private final Integer code;
    private final String message;

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
