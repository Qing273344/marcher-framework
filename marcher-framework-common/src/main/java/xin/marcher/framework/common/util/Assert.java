package xin.marcher.framework.common.util;

import java.util.Collection;

/**
 * 断言某些对象或值是否符合规定，否则抛出异常。经常用于做变量检查
 *
 * @author marcher
 */
public class Assert extends cn.hutool.core.lang.Assert {

    public static void isNullOrLtZero(Number number, String msg) {
        if (EmptyUtil.isEmpty(number) || number.longValue() <= 0) {
            throw new IllegalArgumentException(msg);
        }
    }

    public static void isEmpty(Collection<?> collection, String msg) {
        if (EmptyUtil.isEmpty(collection)) {
            throw new IllegalArgumentException(msg);
        }
    }


}
