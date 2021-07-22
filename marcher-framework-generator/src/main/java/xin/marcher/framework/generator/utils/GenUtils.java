package xin.marcher.framework.generator.utils;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springframework.stereotype.Component;
import xin.marcher.framework.common.util.DateUtil;
import xin.marcher.framework.generator.entity.ColumnEntity;
import xin.marcher.framework.generator.entity.TableEntity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.*;

/**
 * 代码生成器   工具类
 */
@Component
public class GenUtils {

    private static Configuration config;

    public static List<String> getTemplates() {
        List<String> templates = new ArrayList<>();
        templates.add("template/Entity.java.vm");
        templates.add("template/Mapper.java.vm");
        templates.add("template/Mapper.xml.vm");
        templates.add("template/Service.java.vm");
        templates.add("template/ServiceImpl.java.vm");
        templates.add("template/Controller.java.vm");

        return templates;
    }

    /**
     * 生成代码
     */
    public static void generatorCode(Map<String, String> table, List<Map<String, String>> columns) {
        // 配置信息
        initConfig();

        boolean hasBigDecimal = false;
        //表信息
        TableEntity tableEntity = new TableEntity();
        tableEntity.setTableName(table.get("tableName"));
        tableEntity.setComments(table.get("tableComment"));
        //表名转换成Java类名
        String className = tableToJava(tableEntity.getTableName(), config.getStringArray("tablePrefix"));
        tableEntity.setClassName(className);
        tableEntity.setClassname(StringUtils.uncapitalize(className));

        //列信息
        List<ColumnEntity> columsList = new ArrayList<>();
        for (Map<String, String> column : columns) {
            ColumnEntity columnEntity = new ColumnEntity();
            columnEntity.setColumnName(column.get("columnName"));
            columnEntity.setDataType(column.get("dataType"));
            columnEntity.setComments(column.get("columnComment"));
            columnEntity.setExtra(column.get("extra"));

            //列名转换成Java属性名
            String attrName = columnToJava(columnEntity.getColumnName());
            columnEntity.setAttrName(attrName);
            columnEntity.setAttrname(StringUtils.uncapitalize(attrName));

            //列的数据类型，转换成Java类型
            String attrType = config.getString(columnEntity.getDataType(), "unknowType");
            columnEntity.setAttrType(attrType);
            if (!hasBigDecimal && attrType.equals("BigDecimal")) {
                hasBigDecimal = true;
            }
            //是否主键
            if ("PRI".equalsIgnoreCase(column.get("columnKey")) && tableEntity.getPk() == null) {
                tableEntity.setPk(columnEntity);
            }

            columsList.add(columnEntity);
        }
        tableEntity.setColumns(columsList);

        //没主键，则第一个字段为主键
        if (tableEntity.getPk() == null) {
            tableEntity.setPk(tableEntity.getColumns().get(0));
        }

        //设置velocity资源加载器
        Properties prop = new Properties();
        prop.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        Velocity.init(prop);

        //封装模板数据
        Map<String, Object> map = new HashMap<>();
        map.put("tableName", tableEntity.getTableName());
        map.put("comments", tableEntity.getComments());
        map.put("pk", tableEntity.getPk());
        map.put("className", tableEntity.getClassName());
        map.put("classname", tableEntity.getClassname());
        map.put("pathName", tableEntity.getClassname().toLowerCase());
        map.put("columns", tableEntity.getColumns());
        map.put("hasBigDecimal", hasBigDecimal);
        map.put("package", config.getString("package"));
        map.put("moduleName", config.getString("moduleName"));
        map.put("author", config.getString("author"));
        map.put("datetime", DateUtils.format(new Date(), DateUtils.PATTERN_HYPHEN_TIME));
        VelocityContext context = new VelocityContext(map);

        //获取模板列表
        List<String> templates = getTemplates();
        for (String template : templates) {
            //渲染模板
            StringWriter stringWriter = new StringWriter();
            Template tpl = Velocity.getTemplate(template, "UTF-8");
            tpl.merge(context, stringWriter);


            String fileName = getFileName(template, tableEntity.getClassName(), config.getString("package"), config.getString("moduleName"));
            if (fileName == null) {
                continue;
            }

            // 创建目录, 目录无的情况下
            File targetFile = new File(fileName);
            if (!targetFile.getParentFile().exists()) {
                targetFile.getParentFile().mkdirs();
            }
            if (targetFile.exists()) {
                continue;
            }

            try {
                IOUtils.write(stringWriter.toString(), new FileOutputStream(fileName), "UTF-8");
                IOUtils.closeQuietly(stringWriter);
            } catch (IOException e) {
            }
        }
    }


    /**
     * 列名转换成Java属性名
     */
    public static String columnToJava(String columnName) {
        return WordUtils.capitalizeFully(columnName, new char[]{'_'}).replace("_", "");
    }

    /**
     * 表名转换成Java类名
     */
    public static String tableToJava(String tableName, String[] tablePrefixArray) {
        if (null != tablePrefixArray && tablePrefixArray.length > 0) {
            for (String tablePrefix : tablePrefixArray) {
                tableName = tableName.replace(tablePrefix, "");
            }
        }
        return columnToJava(tableName);
    }

    /**
     * 获取配置信息
     */
    private static void initConfig() {
        try {
            config = new PropertiesConfiguration("generator.properties");
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取文件名
     */
    public static String getFileName(String template, String className, String packageName, String moduleName) {
        String fileDirPath = config.getString("fileDirPath");
        String packagePath = fileDirPath + File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator;

        if (StringUtils.isNotBlank(packageName)) {
            packagePath += packageName.replace(".", File.separator) + File.separator + moduleName + File.separator;
        }

        if (template.contains("Entity.java.vm")) {
            return packagePath + "model" + File.separator + className + ".java";
        }

        if (template.contains("Mapper.java.vm")) {
            return packagePath + "mapper" + File.separator + className + "Mapper.java";
        }

        if (template.contains("Service.java.vm")) {
            return packagePath + "service" + File.separator + className + "Service.java";
        }

        if (template.contains("ServiceImpl.java.vm")) {
            return packagePath + "service" + File.separator + "impl" + File.separator + className + "ServiceImpl.java";
        }

        if (template.contains("Controller.java.vm")) {
            return packagePath + "controller" + File.separator + className + "Controller.java";
        }

        if (template.contains("Mapper.xml.vm")) {
            packagePath = fileDirPath + File.separator + "src" + File.separator;
            return packagePath + "main" + File.separator + "resources" + File.separator + "mapper" + File.separator
//                    + moduleName + File.separator
                    + className + "Mapper.xml";
        }

        if (template.contains("menu.sql.vm")) {
            return className.toLowerCase() + "_menu.sql";
        }

        return null;
    }
}
