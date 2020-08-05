package xin.marcher.framework.generator.dao;

import java.util.List;
import java.util.Map;

/**
 * 数据库接口
 */
public interface GeneratorDao {

    Map<String, String> queryTable(String tableName);

    List<Map<String, String>> queryColumns(String tableName);
}
