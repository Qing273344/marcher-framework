package xin.marcher.framework.z;

import lombok.Data;
import org.springframework.core.annotation.Order;

@Data
public class SS {

    @Order
    private String time;

    private String name;

    @TimeAnno
    private String nn;
}
