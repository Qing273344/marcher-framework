package xin.marcher.framework.oss;

import xin.marcher.framework.core.IEnumNorm;

/**
 * oss 地域节点类型
 *
 * @author marcher
 */
public enum OssEndPointTypeEnum implements IEnumNorm {

	/**
	 * 外网环境
	 */
	END_POINT_EXTERNAL(1, "外网"),
	/**
	 * 内网环境
	 */
	END_POINT_INTERNAL(2, "内网"),
	;

	private final Integer value;

	private final String name;

	OssEndPointTypeEnum(Integer value, String name) {
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
