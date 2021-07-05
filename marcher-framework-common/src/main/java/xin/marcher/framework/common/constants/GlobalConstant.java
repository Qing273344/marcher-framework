package xin.marcher.framework.common.constants;

/**
 * common constant
 *
 * @author marcher
 */
public class GlobalConstant {

    public static final int ZERO = 0;

    /** 数据逻辑删除定义 */
    public static final int DELETED = 1;
    public static final int NO_DELETED = 0;

    /** is */
    public static final int YES = 1;
    public static final int NO = 0;

    /** CHAR */
    public static final String CHAR_BLANK = "";
    public static final String CHAR_SPACE = " ";
    public static final String CHAR_COMMA = ",";
    public static final String CHAR_COMMA_SPACE = ", ";
    public static final String CHAR_DIT = ".";
    public static final String CHAR_UNDER_LINE = "_";
    public static final String CHAR_CENTRE_LINE = "-";
    public static final String CHAR_IP_SEPARATOR = "\\.";

    /** 业务状态, 用于 Filter 异常拦截返回特定的状态码 */
    public static final String COOKIE_DOMAIN = "marcher.xin";

    public static final String BUSINESS_STATUS = "business.status";
}
