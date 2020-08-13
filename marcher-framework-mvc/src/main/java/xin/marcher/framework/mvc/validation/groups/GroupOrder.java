package xin.marcher.framework.mvc.validation.groups;

import javax.validation.GroupSequence;
import javax.validation.groups.Default;

/**
 * 排序校验
 * 栗子: @Validated({GroupOrder.class}) Object obj
 *
 * @author marcher
 */
@GroupSequence({GroupA.class, GroupB.class, GroupC.class, GroupD.class, GroupE.class, GroupUpdateOrder.class, Default.class})
public interface GroupOrder {

}
