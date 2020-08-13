package xin.marcher.framework.z.util;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

/**
 * 不重复组合
 *
 * @author Jack
 */
public class AssembleUtil {

    /**
     * 有序的不重复的 Set 集合。
     */
    private static final Set<String> set = new TreeSet<String>();

    /**
     * 根据字符串 “1,2,3,4,5,6,7” 的格式计算出来一共有多少组组合
     *
     * @param sourceStr 初始化的字符串源信息
     * @param max       每种组合是几个数字
     * @return String[]
     */
    public static String[] getAssemble(String sourceStr, int max) {
        String[] sourceList = sourceStr.split(",");
        return getAssemble(sourceList, max);
    }

    /**
     * 根据字符串数组形式来计算一共有多少种组数 { "1", "2", "3", "4", "5","6","7" }
     *
     * @param sourceArray 初始化的字符串数组信息
     * @param max         每种组合是几个数字
     * @return String[]
     */
    public static String[] getAssemble(String[] sourceArray, int max) {
        for (String s : sourceArray) {
            doSet(s, sourceArray, max);
        }
        String[] arr = new String[set.size()];
        String[] array = set.toArray(arr);
        set.clear();
        return array;
    }

    /**
     * 计算组数
     *
     * @param start      索引
     * @param sourceList 初始化的字符串数组信息
     * @param max        每种组合是几个数字
     * @return
     *      set<String>
     */
    private static Set<String> doSet(String start, String[] sourceList, int max) {
        String[] olds = start.split("_");
        if (olds.length == max) {
            set.add(start.replaceAll("_", "").trim());
        } else {
            for (String value : sourceList) {
                if (!Arrays.asList(olds).contains(value)) {
                    doSet(start + "_" + value, sourceList, max);
                }
            }
        }
        return set;
    }

    /**
     * 测试方法
     *
     * @param args
     */
    public static void main(String[] args) {

        String[] sourceArr = new String[]{"1", "2", "3", "4", "5", "6", "7"};
        String[] resultArr = getAssemble(sourceArr, 3);
        System.out.println("累计组合：" + resultArr.length + "," + Arrays.toString(resultArr));

        String sourceStr = "1,2,3,4,5,6,7";
        String[] resultArr2 = getAssemble(sourceStr, 3);
        System.out.println("累计组合：" + resultArr2.length + "," + Arrays.toString(resultArr2));
    }
}
