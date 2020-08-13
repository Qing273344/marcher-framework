package xin.marcher.framework.z.util;

import java.util.List;

/**
 * @author: Jack
 * @Description: 数据类型转换
 * @Date: Create by 14:19 2017/11/30
 * @Modified By: marcher
 */
public class ConvertUtil {

	/**
	 * 把字串转化为整数,若转化失败，则返回0
	 * @param str	字符串
	 * @return
	 */
	public static int strToInt(String str) {
		if (str == null) {
			return 0;
		}

		try {
			return Integer.parseInt(str);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.err.println(str + "转换成int类型失败，请检查数据来源");
		}
		return 0;
	}

	/**
	 * 把字串转化为长整型数,若转化失败，则返回0
	 * @param str	字符串
	 * @return
	 */
	public static long strToLong(String str) {
		if (str == null) {
			return 0;
		}

		try {
			return Long.parseLong(str);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.err.println(str + "转换成long类型失败，请检查数据来源");
		}
		return 0;
	}

	/**
	 * 把字串转化为Float型数据,若转化失败，则返回0
	 * @param str	字符串
	 * @return
	 */
	public static float strToFloat(String str) {
		if (str == null) {
			return 0;
		}
		try {
			return Float.parseFloat(str);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.err.println(str + "转换成float类型失败，请检查数据来源");
		}
		return 0;
	}

	/**
	 * 把字串转化为Double型数据，若转化失败，则返回0
	 * @param str	字符串
	 * @return
	 */
	public static double strToDouble(String str) {
		if (str == null) {
			return 0;
		}
		try {
			return Double.parseDouble(str);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.err.println(str + "转换成double类型失败，请检查数据来源");
		}
		return 0;
	}

	/**
	 * 字符转为一个元素的Object数组
	 * @param str
	 * @return
	 */
	public static Object[] strToArray(String str) {
		if (str == null) {
			return null;
		} else {
			return new Object[] { str };
		}
	}

	/**
	 * 对于一个字符串数组，把字符串数组中的每一个字串转换为整数. 返回一个转换后的整型数组，对于每一个字串若转换失败，则对 应的整型值就为0
	 * @param strArray	要转化的数组
	 * @return
	 */
	public static int[] strArrayToIntArray(String[] strArray) {
		int[] intArray = new int[strArray.length];
		for (int i = 0; i < strArray.length; i++) {
			intArray[i] = strToInt(strArray[i]);
		}
		return intArray;
	}

	/**
	 * 数组转换为字符串
	 * @param array	数组
	 * @return
	 */
	public static String arrToString(Object[] array) {
		if (array == null) {
			return "";
		}
		return arrToString(array, ",");
	}

	/**
	 * 数据转换为字符串
	 * @param array	数组
	 * @param num	取数组个数
	 * @return
	 */
	public static String arrToString(Object[] array, int num) {
		if (array == null) {
			return "";
		}
		return arrToString(array, ",", num);
	}

	/**
	 * 数据转换为字符串
	 * @param array	数组
	 * @param symbol	间隔符号
	 * @return
	 */
	public static String arrToString(Object[] array, String symbol) {
		return arrToString(array, symbol, 0);
	}

	/**
	 * 数据转换为字符串
	 * @param array	数组
	 * @param symbol	间隔符号
	 * @param num	取数组个数
	 * @return
	 */
	public static String arrToString(Object[] array, String symbol, int num) {
		if (array == null || array.length == 0) {
			return "";
		} else {
			StringBuffer sb = new StringBuffer();
			int length = array.length;
			if (num != 0) {
				length = num;
			}
			for (int i = 0; i < length; i++) {
				if (symbol == null) {
					symbol = "";
				}
				sb.append(array[i]).append(symbol);
			}
			sb.delete(sb.lastIndexOf(symbol), sb.length());
			return sb.toString();
		}
	}

	/**
	 * List转换为字符串
	 * @param list	List数据
	 * @return
	 */
	public static String listToString(List<?> list) {
		//separation: 间隔符
		return listToString(list, ",");
	}

	/**
	 * List转换为字符串
	 * @param list	List数据
	 * @param separation	间隔符
	 * @return
	 */
	public static String listToString(List<?> list, String separation) {
		return arrToString(listToStringArray(list), separation);
	}

	/**
	 * 字串数据元素包装
	 * 
	 * @param strArray	字串数据
	 * @param pre	前缀
	 * @param aft	后缀
	 * @return
	 */
	public static String[] strArrDoPack(String[] strArray, String pre, String aft) {
		return strArrDoPack(strArray, pre, aft, 1, 0);
	}

	/**
	 * 字串数据元素包装
	 * @param strArray	字串数据
	 * @param pre		前缀
	 * @param aft		后缀
	 * @param num		生成个数
	 * @return
	 */
	public static String[] strArrDoPack(String[] strArray, String pre, String aft, int num) {
		return strArrDoPack(strArray, pre, aft, num, 0);
	}

	/**
	 * 字串数据元素包装
	 * @param strArray	字串数据
	 * @param pre		前缀
	 * @param aft		后缀
	 * @param num		生成个数
	 * @param step		数字值1：加，-1：减，0：不变
	 * @return
	 */
	public static String[] strArrDoPack(String[] strArray, String pre, String aft, int num, int step) {
		String[] arr = null;
		if (strArray != null) {
			boolean isAdd = false;
			if (step > 0) {
				isAdd = true;
			}

			if (num < 0) {
				num = 1;
			}

			arr = new String[strArray.length * num];
			int icount = 0;
			for (int i = 0; i < num; i++) {
				for (int j = 0; j < strArray.length; j++) {
					if (StringUtil.isNotEmpty(pre)) {
						arr[icount] = pre + strArray[j];
					}
					if (StringUtil.isNotEmpty(aft)) {
						arr[icount] += aft;
					}
					icount++;
				}

				boolean b = false;
				if (step != 0) {
					pre = stepNumInStr(pre, isAdd);
					b = true;
				}
				if (!b) {
					if (step != 0) {
						aft = stepNumInStr(aft, isAdd);
					}
				}
			}
		}
		return arr;
	}

	/**
	 * 生成字符串
	 * @param str	字符串元素
	 * @param num	生成个数
	 * @return
	 */
	public static String createStr(String str, int num) {
		if (str == null) {
			return "";
		}
		return createStr(str, num, ",");
	}

	/**
	 * 生成字符串
	 * @param str		字符串元素
	 * @param num		生成个数
	 * @param symbol	间隔符号
	 * @return
	 */
	public static String createStr(String str, int num, String symbol) {
		if (str == null) {
			return "";
		} else {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < num; i++) {
				if (symbol == null) {
					symbol = "";
				}
				sb.append(str).append(symbol);
			}
			if (sb.length() > 0) {
				sb.delete(sb.lastIndexOf(symbol), sb.length());
			}

			return sb.toString();
		}
	}

	/**
	 * 生成字符串数据
	 * @param str	字符串元素
	 * @param num	生成个数
	 * @return
	 */
	public static String[] createStrArr(String str, int num) {
		if (str == null) {
			return null;
		} else {
			String[] arr = new String[num];
			for (int i = 0; i < num; i++) {
				arr[i] = str;
			}
			return arr;
		}
	}

	/**
	 * 只保留字符串的英文字母和“_”号
	 * @param str	字符串
	 * @return
	 */
	public static String replaceAllSign(String str) {
		if (str != null && str.length() > 0) {
			str = str.replaceAll("[^a-zA-Z_]", "");
		}
		return str;
	}

	/**
	 * 字串中的数字值加1
	 * @param str	字符串
	 * @param isAdd	数字值true：加，false：减
	 * @return
	 */
	public static String stepNumInStr(String str, boolean isAdd) {
		String sNum = str.replaceAll("[^0-9]", ",").trim();
		if (sNum == null || sNum.length() == 0) {
			return str;
		}
		String[] sNumArr = sNum.split(",");

		for (int i = 0; i < sNumArr.length; i++) {
			if (sNumArr[i] != null && sNumArr[i].length() > 0) {
				int itemp = Integer.parseInt(sNumArr[i]);
				if (isAdd) {
					itemp += 1;
				} else {
					itemp -= 1;
				}
				str = str.replaceFirst(sNumArr[i], String.valueOf(itemp));
				break;
			}
		}
		return str;
	}

	/**
	 * list 转换为 String[]
	 * @param list
	 * @return
	 */
	public static String[] listToStringArray(List<?> list) {
		if (list == null || list.size() == 0) {
			return null;
		}
		return (String[]) list.toArray(new String[list.size()]);
	}
}