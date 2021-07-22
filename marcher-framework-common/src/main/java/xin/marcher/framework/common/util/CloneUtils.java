package xin.marcher.framework.common.util;

import xin.marcher.framework.common.exception.UtilException;

import java.io.*;

/**
 * 拷贝工具类
 * 该工具类使用序列化方式, 需要实现序列化接口 Serializable
 *
 * 浅拷贝问题点: 继承 Cloneable
 * clone() 方法是使用 Object 类的 clone() 方法，但是该方法存在一个缺陷，它并不会将对象的所有属性全部拷贝过来，而是有选择性的拷贝
 * 基本规则如下:
 *  1. 基本类型: 如果变量是基本类型, 则拷贝其值, 比如 int float 等
 *  2. 对象: 如果变量是一个对象, 则拷贝其地址引用, 就是说此时新对象与原来的对象是公用该实例变量
 *  3. String 字符串: 若变量为 String 字符串, 则拷贝其地址引用. 但是在修改时, 它会从字符串池中生成一个新的字符串, 原有字符串对象保持不变
 *
 * @author marcher
 */
public class CloneUtils {

    @SuppressWarnings("unchecked")
    public static <T extends Serializable> T clone(T obj) {
        T cloneObj = null;
        try {
            // 写入字节流
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(obj);
            objectOutputStream.close();

            // 分配内存, 写入原始对象, 生成新对象
            ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            cloneObj = (T) objectInputStream.readObject();
            objectInputStream.close();
        } catch (Exception e) {
            throw new UtilException("Failed, clone obj exception");
        }
        return cloneObj;
    }

}
