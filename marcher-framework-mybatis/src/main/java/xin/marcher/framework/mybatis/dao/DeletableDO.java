package xin.marcher.framework.mybatis.dao;

import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import xin.marcher.framework.mybatis.annotation.InsertDeleted;

/**
 * extends BaseDO 扩展 delete 操作
 *
 * @author marcher
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
public class DeletableDO extends BaseDO {

    @TableLogic
    @InsertDeleted
    public Integer deleted;

}
