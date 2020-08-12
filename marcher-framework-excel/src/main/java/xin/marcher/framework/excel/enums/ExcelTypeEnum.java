package xin.marcher.framework.excel.enums;

import com.alibaba.excel.exception.ExcelCommonException;
import org.apache.poi.poifs.filesystem.FileMagic;
import xin.marcher.framework.exception.UtilException;

import java.io.BufferedInputStream;
import java.io.InputStream;

/**
 * excel type
 *
 * @author marcher
 */
public enum ExcelTypeEnum {

    /**
     * xls
     */
    XLS(".xls"),

    /**
     * xlsx
     */
    XLSX(".xlsx");

    private String value;

    ExcelTypeEnum(String value) {
        this.setValue(value);
    }

    public static ExcelTypeEnum valueOf(InputStream inputStream) {
        if (inputStream == null) {
            throw new UtilException("InputStream must be a non-null.");
        }

        if (!inputStream.markSupported()) {
            inputStream = new BufferedInputStream(inputStream);
        }
        try {
            return recognitionExcelType(inputStream);
        } catch (Exception e) {
            throw new UtilException("Convert excel format exception.You can try specifying the 'excelType' yourself", e);
        }
    }

    private static ExcelTypeEnum recognitionExcelType(InputStream inputStream) throws Exception {
        FileMagic fileMagic = FileMagic.valueOf(inputStream);
        if (FileMagic.OLE2.equals(fileMagic)) {
            return XLS;
        }
        if (FileMagic.OOXML.equals(fileMagic)) {
            return XLSX;
        }
        throw new ExcelCommonException(
                "Convert excel format exception.You can try specifying the 'excelType' yourself");
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
