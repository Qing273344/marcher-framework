package xin.marcher.framework.mvc.request;

import lombok.*;

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
