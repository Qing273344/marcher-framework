package xin.marcher.framework.z.util;

/**
 * 版本比较工具
 *
 * @author marcher
 */
public class VersionUtil {

    /**
     * 比较版本号的大小,前者大则返回一个正数,后者大返回一个负数,相等则返回0
     *
     * @param newVersion    新版本
     * @param oldVersion    旧版本
     */
    public static int compareVersion(String newVersion, String oldVersion) {
        // 注意此处为正则匹配，不能用"."；
        String[] newVersionArray = newVersion.split("\\.");
        String[] oldVersionArray = oldVersion.split("\\.");
        int idx = 0;
        //取最小长度值
        int minLength = Math.min(newVersionArray.length, oldVersionArray.length);
        int diff = 0;
        while (idx < minLength
                //先比较长度
                && (diff = newVersionArray[idx].length() - oldVersionArray[idx].length()) == 0
                //再比较字符
                && (diff = newVersionArray[idx].compareTo(oldVersionArray[idx])) == 0) {
            ++ idx;
        }
        //如果已经分出大小，则直接返回，如果未分出大小，则再比较位数，有子版本的为大；
        diff = (diff != 0) ? diff : newVersionArray.length - oldVersionArray.length;
        return diff;
    }
}
