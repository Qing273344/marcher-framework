package xin.marcher.framework.mybatis.dao;

import xin.marcher.framework.mybatis.annotation.CreateTime;
import xin.marcher.framework.mybatis.annotation.ModifyTime;

import java.io.Serializable;

/**
 * 基础实体对象
 *
 * @author marcher
 */
public class BaseDO implements Serializable {

    @CreateTime
    public Long createTime;

    @ModifyTime
    public Long modifyTime;

    public Long getCreateTime() {
        return createTime;
    }

    public BaseDO setCreateTime(Long createTime) {
        this.createTime = createTime;
        return this;
    }

    public Long getModifyTime() {
        return modifyTime;
    }

    public BaseDO setModifyTime(Long modifyTime) {
        this.modifyTime = modifyTime;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append("{");
        sb.append("createTime=").append(createTime);
        sb.append(", modifyTime=").append(modifyTime);
        sb.append("}");
        return sb.toString();
    }
}
