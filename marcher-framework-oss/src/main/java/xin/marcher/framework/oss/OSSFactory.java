package xin.marcher.framework.oss;

import xin.marcher.framework.oss.property.OssProperties;
import xin.marcher.framework.oss.service.OssService;
import xin.marcher.framework.oss.service.impl.OssServiceImpl;

/**
 * OSSFactory
 *
 * @author marcher
 */
public final class OSSFactory {

    /**
     * build
     *
     * @param config    配置
     * @return
     *      oss操作对象
     */
    public static OssService build(OssProperties config) {
        return build(config, true);
    }

    /**
     * build
     *
     * @param config        配置
     * @param isExternal    是否外网环境
     * @return
     *      oss操作对象
     */
    public static OssService build(OssProperties config, boolean isExternal) {
        return new OssServiceImpl().setOssManager(config, isExternal);
    }

}
