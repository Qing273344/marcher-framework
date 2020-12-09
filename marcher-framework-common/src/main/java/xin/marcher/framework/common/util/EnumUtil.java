package xin.marcher.framework.common.util;

import xin.marcher.framework.common.core.IEnumNorm;
import xin.marcher.framework.common.wrapper.CodeNameWO;

import java.util.ArrayList;
import java.util.List;

/**
 * enum util
 *
 * @author marcher
 */
public class EnumUtil {

    /**
     * 通过code获取枚举
     *
     * @param code  code
     * @param clazz 枚举class
     * @param <T>   <T>
     * @return
     *      枚举
     */
    public static <T extends IEnumNorm> T get(Integer code, Class<T> clazz) {
        if (code == null || !isEnum(clazz)) {
            return null;
        }

        if (clazz.getEnumConstants().length <= 0) {
            return null;
        }

        for (T ele : clazz.getEnumConstants()) {
            if (code.equals(ele.getRealCode())) {
                return ele;
            }
        }
        return null;
    }

    public static <T extends IEnumNorm> String getDesc(Integer code, Class<T> clazz) {
        if (code == null || !isEnum(clazz)) {
            return "";
        }

        if (clazz.getEnumConstants().length <= 0) {
            return "";
        }

        for (T ele : clazz.getEnumConstants()) {
            if (code.equals(ele.getRealCode())) {
                return ele.getRealDesc();
            }
        }

        return "";
    }

    /**
     * 是否匹配枚举值
     *
     * @param code      code
     * @param enumNorm  枚举类
     * @return
     *      code与枚举类枚举项匹配:true, 反之:false
     */
    public static boolean isEq(Integer code, IEnumNorm enumNorm) {
        if (code == null || enumNorm == null) {
            return false;
        }

        return code.equals(enumNorm.getRealCode());
    }

    /**
     * 枚举类中是否存在该值
     *
     * @param code  枚举code
     * @param clazz 枚举类class
     */
    public static <T extends IEnumNorm> boolean isExist(Integer code, Class<T> clazz) {
        if (code == null || !isEnum(clazz)) {
            return false;
        }

        if (clazz.getEnumConstants().length <= 0) {
            return false;
        }

        for (T ele : clazz.getEnumConstants()) {
            if (code.equals(ele.getRealCode())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 枚举类中不存在该值
     *
     * @param code  枚举code
     * @param clazz 枚举类class
     */
    public static <T extends IEnumNorm> boolean isNotExist(Integer code, Class<T> clazz) {
        return !isExist(code, clazz);
    }

    /**
     * 是否枚举类型
     *
     * @param clazz 枚举class
     * @param <T>   <T>
     * @return
     *      是:true, 否:false
     */
    public static <T> boolean isEnum(Class<T> clazz) {
        return clazz.isEnum();
    }

    /**
     * 获取所有枚举属性值
     *
     * @param clazz 枚举class
     * @param <T>   T
     * @return  result
     */
    public static <T> List<IEnumNorm> getEnumList(Class<T> clazz) {
        List<IEnumNorm> list = new ArrayList<>();
        if (!isEnum(clazz)) {
            return list;
        }
        if (clazz.getEnumConstants().length <= 0) {
            return list;
        }

        for (T ele : clazz.getEnumConstants()) {
            IEnumNorm data = (IEnumNorm) ele;
            list.add(data);
        }
        return list;
    }

    /**
     * 获取枚举类所有值 code name 组合
     *
     * @param clazz 枚举 class
     * @param <T>   T
     * @return  result
     */
    public static <T> List<CodeNameWO> getList(Class<T> clazz) {
        List<IEnumNorm> enumList = getEnumList(clazz);

        List<CodeNameWO> list = new ArrayList<>();
        for (IEnumNorm iEnumNorm : enumList) {
            CodeNameWO codeNameWo = new CodeNameWO();
            codeNameWo.setCode(iEnumNorm.getRealCode());;
            codeNameWo.setName(iEnumNorm.getRealDesc());
            list.add(codeNameWo);
        }
        return list;
    }
}
