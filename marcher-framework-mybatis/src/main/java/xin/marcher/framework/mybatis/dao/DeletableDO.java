package xin.marcher.framework.mybatis.dao;

import com.baomidou.mybatisplus.annotation.TableLogic;
import xin.marcher.framework.mybatis.annotation.InsertDeleted;

/**
 * extends BaseDO 扩展 delete 操作
 *
 * @author marcher
 */
public class DeletableDO extends BaseDO {

    @TableLogic
    @InsertDeleted
    public Integer deleted;

    public Integer getDeleted() {
        return deleted;
    }

    public DeletableDO setDeleted(Integer deleted) {
        this.deleted = deleted;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append("{");
        sb.append("deleted=").append(deleted);
        sb.append("}");
        return sb.toString();
    }
}
