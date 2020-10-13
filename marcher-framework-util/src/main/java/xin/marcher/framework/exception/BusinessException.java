package xin.marcher.framework.exception;

import lombok.Getter;
import lombok.Setter;
import xin.marcher.framework.core.IEnumNorm;

/**
 *
 * 一共 10 位，分成四段
 *
 * 第一段，1 位，类型
 * 	    1 - 业务级别
 *      2 - 系统级别
 * 第二段，3 位，系统类型
 *      001 - 用户系统
 *      002 - 管理系统
 *      ... - ...
 * 第三段，3 位，模块
 *      不限制规则。
 *      一般建议，每个系统里面，可能有多个模块，可以再去做分段。以用户系统为例子：
 *          001 - OAuth2 模块
 *          002 - User 模块
 *          003 - MobileCode 模块
 * 第四段，3 位，错误码
 *       不限制规则。
 *       一般建议，每个模块自增
 *
 * @author marcher
 */
@Getter
@Setter
public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
    private int code = 500;

	public BusinessException() {
	}

	public BusinessException(Throwable cause) {
		super(cause);
	}

	public BusinessException(String message) {
		super(message);
	}
    
	public BusinessException(int code, String message) {
		super(message);
		this.code = code;
	}
	
	public BusinessException(int code, String message, Throwable cause) {
		super(message, cause);
		this.code = code;
	}

	public BusinessException(int code, String msgFormat, Object... args) {
    	super(String.format(msgFormat, args));
    	this.code = code;
	}

	public BusinessException(IEnumNorm codeEnum, Object... args) {
    	super(String.format(codeEnum.getRealDesc(), args));
    	this.code = codeEnum.getRealCode();
	}

}