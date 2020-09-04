package xin.marcher.framework.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * 自定义异常(提示)
 * 
 * @author marcher
 */
@Getter
@Setter
public class HintException extends RuntimeException {

	private static final long serialVersionUID = 1L;

    private String msg;
    private int code = 400;

    public HintException(String msg) {
		super(msg);
		this.msg = msg;
	}

	public HintException(String msg, Throwable e) {
		super(msg, e);
		this.msg = msg;
	}

	public HintException(int code, String msg) {
		super(msg);
		this.msg = msg;
		this.code = code;
	}

	public HintException(int code, String msg, Throwable e) {
		super(msg, e);
		this.msg = msg;
		this.code = code;
	}

}