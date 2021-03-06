package xin.marcher.framework.common.mvc.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * query data
 *
 * @author marcher
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BaseQuery extends PageParam {

    private String keyword;
}
