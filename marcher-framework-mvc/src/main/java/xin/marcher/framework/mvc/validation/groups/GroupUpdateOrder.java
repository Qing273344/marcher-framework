package xin.marcher.framework.mvc.validation.groups;

import javax.validation.GroupSequence;
import javax.validation.groups.Default;

/**
 * update group 用于数据编辑时
 * Default校验配置: @Validated在分组验证时并没有添加Default.class的分组，而其他字段默认都是Default分组.
 *
 * @author marcher
 */
@GroupSequence({GroupUpdate.class, Default.class})
public interface GroupUpdateOrder {

}
