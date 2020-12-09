package xin.marcher.framework.common.exception;

import lombok.extern.slf4j.Slf4j;
import xin.marcher.framework.common.core.IEnumNorm;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * {@link BusinessException} 工具类
 *
 * 目的在于，格式化异常信息提示。
 * 考虑到 String.format 在参数不正确时会报错，因此使用 {} 作为占位符，并使用 {@link #doFormat(int, String, Object...)} 方法来格式化
 *
 * 因为 {@link #messages} 里面默认是没有异常信息提示的模板的，所以需要使用方自己初始化进去。目前想到的有几种方式：
 *
 * 1. 异常提示信息，写在枚举类中，例如说，cn.iocoder.oceans.user.api.constants.ErrorCodeEnum 类 + ServiceExceptionConfiguration
 * 2. 异常提示信息，写在 .properties 等等配置文件
 * 3. 异常提示信息，写在 Apollo 等等配置中心中，从而实现可动态刷新
 * 4. 异常提示信息，存储在 db 等等数据库中，从而实现可动态刷新
 *
 * @author YunaiV
 */
@Slf4j
public class ServiceExceptionUtil {

    /**
     * 错误码提示模板
     * 若要使用message获取内容功能, 需在项目启动时加载进message中
     */
    private static ConcurrentMap<Integer, String> messages = new ConcurrentHashMap<>();

    public static void put(Integer code, String message) {
        ServiceExceptionUtil.messages.put(code, message);
    }

    public static void putAll(Map<Integer, String> messages) {
        ServiceExceptionUtil.messages.putAll(messages);
    }

    /**
     * 创建指定编号的 ServiceException 的异常
     *
     * @param code 编号
     * @return 异常
     */
    public static BusinessException exception(Integer code) {
        return new BusinessException(code, messages.get(code));
    }

    /**
     * 创建指定编号的 ServiceException 的异常
     *
     * @param codeEnum  异常枚举
     * @return  异常
     */
    public static BusinessException exception(IEnumNorm codeEnum) {
        return new BusinessException(codeEnum.getRealCode(), codeEnum.getRealDesc());
    }

    /**
     * 创建指定编号的 ServiceException 的异常
     *
     * @param code 编号
     * @param params 消息提示的占位符对应的参数
     * @return 异常
     */
    public static BusinessException exception(Integer code, Object... params) {
        String message = doFormat(code, messages.get(code), params);
        return new BusinessException(code, message);
    }

    public static BusinessException exception(Integer code, String messagePattern, Object... params) {
        String message = doFormat(code, messagePattern, params);
        return new BusinessException(code, message);
    }

    /**
     * 将错误编号对应的消息使用 params 进行格式化。
     *
     * @param code           错误编号
     * @param messagePattern 消息模版
     * @param params         参数
     * @return 格式化后的提示
     */
    private static String doFormat(int code, String messagePattern, Object... params) {
        StringBuilder sbuf = new StringBuilder(messagePattern.length() + 50);
        int i = 0;
        int j;
        int l;
        for (l = 0; l < params.length; l++) {
            j = messagePattern.indexOf("{}", i);
            if (j == -1) {
                log.error("[doFormat][参数过多：错误码({})|错误内容({})|参数({})", code, messagePattern, params);
                if (i == 0) {
                    return messagePattern;
                } else {
                    sbuf.append(messagePattern.substring(i));
                    return sbuf.toString();
                }
            } else {
                sbuf.append(messagePattern, i, j);
                sbuf.append(params[l]);
                i = j + 2;
            }
        }
        if (messagePattern.indexOf("{}", i) != -1) {
            log.error("[doFormat][参数过少：错误码({})|错误内容({})|参数({})", code, messagePattern, params);
        }
        sbuf.append(messagePattern.substring(i));
        return sbuf.toString();
    }
}
