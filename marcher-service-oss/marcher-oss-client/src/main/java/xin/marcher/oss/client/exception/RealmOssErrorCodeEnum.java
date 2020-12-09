package xin.marcher.oss.client.exception;

import xin.marcher.framework.common.core.IEnumNorm;

/**
 * OSS系统，使用 1-990-990-000 段
 *
 * @author marcher
 */
public enum RealmOssErrorCodeEnum implements IEnumNorm {

    /** ==========  模块 ========== */
    RA_TEMP(1990990001, "temp")
    ;

    private final Integer code;
    private final String message;

    RealmOssErrorCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int getRealCode() {
        return code;
    }

    @Override
    public String getRealDesc() {
        return message;
    }
}
