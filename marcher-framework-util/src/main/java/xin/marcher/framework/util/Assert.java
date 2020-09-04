package xin.marcher.framework.util;

/**
 * @author marcher
 */
public class Assert extends cn.hutool.core.lang.Assert {

    public static void isNullOrLtZero(Number number, String msg) {
        if (EmptyUtil.isEmpty(number) || number.longValue() <= 0) {
            throw new IllegalArgumentException(msg);
        }
    }
}
