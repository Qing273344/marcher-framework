package xin.marcher.framework.common.wrapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * base wrapper
 *
 * @author marcher
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseWO implements Serializable {

    private static final long serialVersionUID = 966889966648443916L;

    private Long id;

    public static BaseWO singleton(Long id) {
        return new BaseWO(id);
    }
}
