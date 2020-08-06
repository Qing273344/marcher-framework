package com.soushi88.framework.excel.converters;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.alibaba.excel.util.NumberUtils;
import com.soushi88.framework.util.EmptyUtil;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

/**
 * Integer 判断, 包含 value 为空的情况
 * value 为空的时候, 类型是 String, 转为对象的数值类型会报错(没有做空处理).
 * value 不为空, 存储在 numberValue 中, 从 stringValue 是获取不到值的.
 *
 * @author marcher
 */
@Slf4j
public class IntegerStringWithEmptyConverter implements Converter<Integer> {

    @Override
    public Class supportJavaTypeKey() {
        return Integer.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public Integer convertToJavaData(CellData cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        // 有值
        String cellValue = cellData.getStringValue();
        if (EmptyUtil.isNotEmpty(cellValue)) {
            NumberUtils.parseInteger(cellValue, contentProperty);
        }

        // 为 number 类型
        BigDecimal numberValue = cellData.getNumberValue();
        if (EmptyUtil.isNotEmpty(numberValue)) {
            return numberValue.intValue();
        }

        return null;
    }

    @Override
    public CellData convertToExcelData(Integer value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        return NumberUtils.formatToCellData(value, contentProperty);
    }
}
