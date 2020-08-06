package com.soushi88.framework.excel.analysis;

import com.soushi88.framework.excel.enums.ExcelTypeEnum;
import com.soushi88.framework.exception.UtilException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;

/**
 * analysis
 *
 * @author marcher
 */
public class AnalysisType {

    private static Workbook analysisWorkbook(InputStream inputStream) {
        ExcelTypeEnum excelTypeEnum = ExcelTypeEnum.valueOf(inputStream);
        try {
            if (excelTypeEnum.equals(ExcelTypeEnum.XLS)) {
                return new HSSFWorkbook(inputStream);
            }
            if (excelTypeEnum.equals(ExcelTypeEnum.XLSX)) {
                return new XSSFWorkbook(inputStream);
            }
        } catch (IOException e) {
            throw new UtilException("excel workbook create error", e);
        }

        return null;
    }
}
