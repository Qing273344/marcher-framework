package xin.marcher.framework.common.constants;

import xin.marcher.framework.common.core.IEnumNorm;

/**
 * 是否描述枚举
 *
 * @author marcher
 */
public enum WhetherEnum implements IEnumNorm {

    /** */
    NO(0, "NO"),
    YES(1, "YES"),
    ;

    private final Integer value;
    private final String name;

    WhetherEnum(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    @Override
    public int getRealCode() {
        return value;
    }

    @Override
    public String getRealDesc() {
        return name;
    }
}
