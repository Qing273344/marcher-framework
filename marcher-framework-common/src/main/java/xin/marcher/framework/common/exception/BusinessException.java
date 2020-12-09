package xin.marcher.framework.common.exception;

import lombok.Getter;
import lombok.Setter;
import xin.marcher.framework.common.core.IEnumNorm;

/**
 * 业务异常
 *
 * @author marcher
 */
@Getter
@Setter
public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * 错误码
	 */
    private Integer code;


	/**
	 * 空构造, 避免反序列化问题
	 */
	public BusinessException() {
	}


	public BusinessException(Throwable cause) {
		super(cause);
	}

	public BusinessException(String message) {
		super(message);
	}
    
	public BusinessException(Integer code, String message) {
		super(message);
		this.code = code;
	}
	
	public BusinessException(Integer code, String message, Throwable cause) {
		super(message, cause);
		this.code = code;
	}

	public BusinessException(Integer code, String msgFormat, Object... args) {
    	super(String.format(msgFormat, args));
    	this.code = code;
	}

	public BusinessException(IEnumNorm codeEnum, Object... args) {
    	super(String.format(codeEnum.getRealDesc(), args));
    	this.code = codeEnum.getRealCode();
	}

}