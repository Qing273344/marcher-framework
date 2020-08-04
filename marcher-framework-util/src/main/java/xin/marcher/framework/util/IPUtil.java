package xin.marcher.framework.util;

/**
 * ip util
 *
 * @author marcher
 */
public class IPUtil {

    private static final String IP_SEPARATOR = "\\.";

    private static final String CHAR_DIT = ".";

    public static long ip2Long(String ipStr) {
        if (EmptyUtil.isEmptyTrim(ipStr)) {
            return 0;
        }
        String[] ipSegmentArray = ipStr.split(IP_SEPARATOR);
        return (parseLong(ipSegmentArray[0]) << 24) + (parseLong(ipSegmentArray[1]) << 16)
                + (parseLong(ipSegmentArray[2]) << 8) + parseLong(ipSegmentArray[3]);
    }

    public static String long2Ip(long ipLong) {
        return ((ipLong >>> 24) + CHAR_DIT)
                + (((ipLong >>> 16) & 0xFF) + CHAR_DIT)
                + (((ipLong >>> 8) & 0xFF) + CHAR_DIT)
                + ((ipLong & 0xFF));
    }

    private static long parseLong(String numberStr) {
        return Long.parseLong(numberStr);
    }
}
