package xin.marcher.framework.core;

/**
 * 可生成 Int 数组的接口
 * 定义枚举类描述
 *
 * @author marcher
 */
public interface IEnumCodeWithIntro {

    /**
     * 收集枚举中的 int
     *
     * @return  int 数组
     */
    int[] array();

    /**
     * 简介, 说明枚举类是干哈的
     *
     * 栗子: 性别
     *
     * @return
     *      String
     */
    String intro();
}
