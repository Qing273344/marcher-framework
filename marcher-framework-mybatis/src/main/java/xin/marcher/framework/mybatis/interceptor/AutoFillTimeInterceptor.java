package xin.marcher.framework.mybatis.interceptor;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.springframework.stereotype.Component;
import xin.marcher.framework.constants.GlobalConstant;
import xin.marcher.framework.mybatis.annotation.CreateTime;
import xin.marcher.framework.mybatis.annotation.InsertDeleted;
import xin.marcher.framework.mybatis.annotation.ModifyTime;
import xin.marcher.framework.util.DateUtil;
import xin.marcher.framework.util.EmptyUtil;

import java.lang.reflect.Field;
import java.util.*;

/**
 * Mybatis 拦截器: 给实体对象在save update时, 自动添加操作属性信息.
 * Signature: 标注要拦截的操作
 *
 * @author marcher
 */
@Component
@Intercepts({@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})})
public class AutoFillTimeInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];

        // 获取SQL
        SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();

        // 获取参数
        // 情况一 --> Object
        Object parameter = invocation.getArgs()[1];
        // 情况二 --> Map (1)使用@Param多参数传入, 由Mybatis包装成map. (2) 原始传入Map
        if (invocation.getArgs()[1] instanceof Map) {
            Map map = (Map) invocation.getArgs()[1];
            parameter = map.values().iterator().next();
        }

        if (EmptyUtil.isNotEmpty(parameter)) {
            // 获取私有成员变量
            Class<?> ormClass = parameter.getClass();

            List<Field> fields = new ArrayList<>(Arrays.asList(ormClass.getDeclaredFields()));
            Class<?> parentClass = ormClass.getSuperclass();
            for (int i = 0; i <= 1; i++) {
                if (parentClass != null) {
                    fields.addAll(Arrays.asList(parentClass.getDeclaredFields()));
                    parentClass = parentClass.getSuperclass();
                }
            }

            long now = DateUtil.now();
            for (Field field : fields) {
                // inset 语句 --> 更新 createTime
                if (null != field.getAnnotation(CreateTime.class)) {
                    if (SqlCommandType.INSERT.equals(sqlCommandType)) {
                        setProperty(field, parameter, now);
                    }
                }
                // insert 或 update --> 更新 modifyTime
                if (null != field.getAnnotation(ModifyTime.class)) {
                    if (SqlCommandType.INSERT.equals(sqlCommandType) || SqlCommandType.UPDATE.equals(sqlCommandType)) {
                        setProperty(field, parameter, now);
                    }
                }
                // inset 语句 --> 逻辑删除字段默认赋值
                if (null != field.getAnnotation(InsertDeleted.class)) {
                    if (SqlCommandType.INSERT.equals(sqlCommandType)) {
                        setProperty(field, parameter, GlobalConstant.NO_DELETED);
                    }
                }
            }
        }

        return invocation.proceed();
    }

    /**
     * 为对象的操作属性赋值
     *
     * @param field     属性
     * @param parameter 参数
     * @param value     属性值
     */
    private void setProperty(Field field, Object parameter, Object value) throws IllegalAccessException {
        field.setAccessible(true);
        field.set(parameter, value);
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof Executor) {
            return Plugin.wrap(target, this);
        }
        return target;
    }

    @Override
    public void setProperties(Properties properties) {
    }
}
