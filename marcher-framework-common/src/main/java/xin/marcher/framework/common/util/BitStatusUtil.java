package xin.marcher.framework.common.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Bit
 *
 * @author marcher
 */
public class BitStatusUtil {

    /**
     * 是否拥有状态
     *
     * @param status 所有状态值
     * @param value  需要判断状态值
     * @return 是否存在
     */
    public static boolean hasStatus(int status, int value) {
        return (status & value) != 0;
    }

    /**
     * 添加状态
     *
     * @param status 已有状态值
     * @param value  需要添加状态值
     * @return 新的状态值
     */
    public static int addStatus(int status, int value) {
        if (hasStatus(status, value)) {
            return status;
        }
        return (status | value);
    }

    /**
     * 添加状态
     *
     * @param status    已有状态值
     * @param values    需要添加状态值
     * @return 新的状态值
     */
    public static int addStatus(int status, int... values) {
        for (int value : values) {
            status = addStatus(status, value);
        }
        return status;
    }

    /**
     * 添加状态
     *
     * @param values    需要添加状态值
     * @return 新的状态值
     */
    public static int addStatus(List<Integer> values) {
        int status = 0;
        for (int value : values) {
            status = addStatus(status, value);
        }
        return status;
    }

    /**
     * 删除状态
     * @param status 已有状态值
     * @param value  需要删除状态值
     * @return 新的状态值
     */
    public static int removeStatus(int status, int value) {
        if (!hasStatus(status, value)) {
            return status;
        }
        return status ^ value;
    }

    /**
     * 过滤该 bit 所拥有的状态
     * @param status        已有状态值
     * @param statusList    状态值 List
     * @return
     */
    public static List<Integer> filterStatus(int status, List<Integer> statusList) {
        if (EmptyUtil.isEmpty(statusList)) {
            return Collections.emptyList();
        }

        List<Integer> withStatusList = new ArrayList<>();
        for (Integer toStatus : statusList) {
            if (hasStatus(status, toStatus)) {
                withStatusList.add(toStatus);
            }
        }
        return withStatusList;
    }
}
