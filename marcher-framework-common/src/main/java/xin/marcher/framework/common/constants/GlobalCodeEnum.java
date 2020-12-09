package xin.marcher.framework.common.constants;

import xin.marcher.framework.common.core.IEnumNorm;

/**
 * 异常错误码定义, 解决: 各模块错误码定义, 避免重复
 *
 * 一共 10 位，分成四段
 *
 * 第一段，1 位，类型
 * 	    1 - 业务级别
 *      x - 预留
 * 第二段，3 位，系统类型
 *      001 - 用户系统
 *      002 - 管理系统
 *      ... - ...
 * 第三段，3 位，模块
 *      不限制规则。
 *      一般建议，每个系统里面，可能有多个模块，可以再去做分段。以用户系统为例子：
 *          001 - OAuth2 模块
 *          002 - User 模块
 * 第四段，3 位，错误码
 *       不限制规则。
 *       一般建议，每个模块自增
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

    GL_400(400, "你打错字啦..."),
    GL_401(401, "是想访问么? 让我想想..."),
    GL_403(403, "是想访问么? 不给..."),
    GL_404(404, "飞到看不见的地方去了..."),
    GL_405(405, "请求姿势不对..."),

    GL_SERVER_ERROR(500, "服务器开小差了, 请稍后再试"),

    GL_UNKNOWN(999, "不知道为啥, 就是好像有点问题..."),
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
