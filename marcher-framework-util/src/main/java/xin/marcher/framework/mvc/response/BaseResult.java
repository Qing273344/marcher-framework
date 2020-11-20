package xin.marcher.framework.mvc.response;

import lombok.Data;
import lombok.ToString;
import xin.marcher.framework.constants.GlobalCodeEnum;
import xin.marcher.framework.mvc.request.PageParam;
import xin.marcher.framework.util.EmptyUtil;
import xin.marcher.framework.wrapper.PageWO;

import java.io.Serializable;

/**
 * result
 *
 * @author marcher
 */
@Data
@ToString
public class BaseResult<T> implements Serializable {

    private T data;

    /**
     * 错误码
     */
    private Integer code;

    /**
     * 错误提示
     */
    private String message;

    BaseResult() {
        this(null);
    }

    BaseResult(T data) {
        this(data, GlobalCodeEnum.OK.getRealCode(), GlobalCodeEnum.OK.getRealDesc());
    }

    public BaseResult(T data, Integer code, String message) {
        this.data = data;
        this.code = code;
        this.message = message;
    }

    public static BaseResult<Boolean> success() {
        return success(Boolean.TRUE);
    }

    public static <T> BaseResult<T> success(T data) {
        return success(data, GlobalCodeEnum.OK.getRealCode(), GlobalCodeEnum.OK.getRealDesc());
    }

    public static <T> BaseResult<PageResult<T>> success(PageWO<T> pageWo, PageParam pageParam) {
        return success(PageResult.rest(pageWo, pageParam));
    }

    public static <T> BaseResult<T> success(T data, int code, String msg) {
        return new BaseResult<>(data, code, msg);
    }

    public static <T> BaseResult<T> error() {
        return error(GlobalCodeEnum.GL_SERVER_ERROR.getRealDesc());
    }

    public static <T> BaseResult<T> error(String msg) {
        return error(GlobalCodeEnum.GL_SERVER_ERROR.getRealCode(), msg);
    }

    public static <T> BaseResult<T> error(int code, String msg) {
        return new BaseResult<>(null, code, msg);
    }

    public boolean isSuccess() {
        return this.code == GlobalCodeEnum.OK.getRealCode();
    }

    public boolean isSuccessData() {
        return isSuccess() && EmptyUtil.isNotEmpty(this.data);
    }

    public boolean isFail() {
        return !isSuccess();
    }
}
