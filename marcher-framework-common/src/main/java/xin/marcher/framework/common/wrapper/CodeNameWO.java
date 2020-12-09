package xin.marcher.framework.common.wrapper;

import lombok.Data;

import java.io.Serializable;

/**
 * code name wrapper
 *
 * @author marcher
 */
@Data
public class CodeNameWO implements Serializable {

    private static final long serialVersionUID = -4589506606377148362L;

    /**
     * code
     */
    private Integer code;

    /**
     * name
     */
    private String name;

}
