## marcher-framework-generator
代码生成器, 生成的风格是 mybatis-plus

## 一步步来
#### 1. 配置文件修改
项目 resources 目录下 generator.properties 配置修改

#### 2. 运行
项目 test 下有个脚本, 输入表名, 执行即可
```
String[] tableArray = new String[]{"table_name"};

generatorService.generatorCode(tableArray);
```

## 敲重点
敲重点, 敲重点, 敲重点, 使用后删除表名, 使用后删除表名, 使用后删除表名 