package xin.marcher.framework.mybatis.dao;

import lombok.Data;
import xin.marcher.framework.mybatis.annotation.CreateTime;
import xin.marcher.framework.mybatis.annotation.ModifyTime;

import java.util.Date;

/**
 * 基础实体对象
 *
 * @author marcher
 */
@Data
public class BaseDO {

    @CreateTime
    public Date createTime;

    @ModifyTime
    public Date modifyTime;

}
