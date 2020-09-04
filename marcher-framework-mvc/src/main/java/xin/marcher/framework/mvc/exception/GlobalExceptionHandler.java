package xin.marcher.framework.mvc.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;
import xin.marcher.framework.exception.HintException;
import xin.marcher.framework.exception.ServiceException;
import xin.marcher.framework.mvc.response.BaseResult;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.Set;

/**
 * 统一异常处理, 只能处理从 Controller 开始抛出的异常, Filter 中的异常需要另行处理
 *
 * @author marcher
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 自定义异常(提示)封装返回
     *
     * @param ex 异常
     * @return 异常提示
     */
    @ExceptionHandler(HintException.class)
    @ResponseBody
    public BaseResult handleHintException(HintException ex) {
        return BaseResult.error(ex.getCode(), ex.getMsg());
    }

    /**
     * 自定义异常封装返回
     *
     * @param ex 异常
     * @return 异常提示
     */
    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    public BaseResult handleServiceException(ServiceException ex) {
        log.error(ex.getMessage(), ex);
        return BaseResult.error(ex.getCode(), ex.getMessage());
    }

    /**
     * Hibernate Validated 参数提示封装JSON
     *
     * @param ex 异常
     * @return 异常提示
     */
    @ExceptionHandler(value = {MethodArgumentNotValidException.class, ValidationException.class})
    @ResponseBody
    public BaseResult handleMethodArgumentNotValidException(Exception ex) {
        String errorMessage = "";
        if (ex instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException methodArgumentNotValidException = (MethodArgumentNotValidException) ex;
            BindingResult bindingResult = methodArgumentNotValidException.getBindingResult();
            if (bindingResult.getFieldErrors().size() > 0) {
                FieldError fieldError = bindingResult.getFieldErrors().get(0);
                errorMessage = fieldError.getDefaultMessage();
            }
        }

        if (ex instanceof ValidationException) {
            ConstraintViolationException constraintViolationException = (ConstraintViolationException) ex;
            Set<ConstraintViolation<?>> constraintViolations = constraintViolationException.getConstraintViolations();
            if (!constraintViolations.isEmpty()) {
                ConstraintViolation<?> constraintViolation = constraintViolations.iterator().next();
                errorMessage = constraintViolation.getMessage();
            }
        }

        return BaseResult.error(HttpStatus.BAD_REQUEST.value(), errorMessage);
    }

    /**
     * 404 异常
     *
     * @param ex 异常
     * @return 异常提示
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseBody
    public BaseResult handlerNoFoundException(Exception ex) {
        log.error(ex.getMessage(), ex);
        return BaseResult.error(HttpStatus.NOT_FOUND.value(), "你好像访问到了其它地方...");
    }

    /**
     * 顶级异常
     *
     * @param ex 异常
     * @return 异常提示
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public BaseResult handleException(Exception ex) {
        log.error(ex.getMessage(), ex);
        return BaseResult.error();
    }

}
