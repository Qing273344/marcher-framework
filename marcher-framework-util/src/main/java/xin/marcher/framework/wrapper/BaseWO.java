package xin.marcher.framework.wrapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * base wrapper
 *
 * @author marcher
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseWO {

    private Long id;

    public static BaseWO singleton(Long id) {
        return new BaseWO(id);
    }
}
