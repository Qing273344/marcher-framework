package xin.marcher.framework.generator.service;

import org.springframework.stereotype.Service;
import xin.marcher.framework.generator.dao.GeneratorDao;
import xin.marcher.framework.generator.utils.GenUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 代码生成器
 * 
 */
@Service
public class GeneratorService {

	@Resource
	private GeneratorDao generatorDao;

	public Map<String, String> queryTable(String tableName) {
		return generatorDao.queryTable(tableName);
	}

	public List<Map<String, String>> queryColumns(String tableName) {
		return generatorDao.queryColumns(tableName);
	}

	public void generatorCode(String[] tableNames) {
		for(String tableName : tableNames){
			//查询表信息
			Map<String, String> table = queryTable(tableName);
			//查询列信息
			List<Map<String, String>> columns = queryColumns(tableName);
			//生成代码
			GenUtils.generatorCode(table, columns);
		}
	}
}
