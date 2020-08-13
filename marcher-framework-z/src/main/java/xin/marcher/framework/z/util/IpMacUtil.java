package xin.marcher.framework.z.util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

/**
 * @author: Jack
 * @Description: Ip工具类
 * @Date: Create by 14:19 2017/11/30
 * @Modified By: marcher
 */
public class IpMacUtil {

	/**
	 * 隐藏IP的最后一段
	 * @param ip	需要进行处理的IP
	 * @return
	 * 		隐藏后的IP
	 */
	public static String hideIp(String ip) {
		if (StringUtil.isEmpty(ip)) {
			return "";
		}

		int pos = ip.lastIndexOf(".");
		if (pos == -1) {
			return ip;
		}

		ip = ip.substring(0, pos + 1);
		ip = ip + "*";
		return ip;
	}

	/**
	 * 判断该字串是否为IP
	 * @param ipStr	IP字串
	 * @return
	 */
	public static boolean isIP(String ipStr) {
		String ip = "(25[0-5]|2[0-4]\\d|1\\d\\d|\\d\\d|\\d)";
		String ipDot = ip + "\\.";
		return ipStr.matches(ipDot + ipDot + ipDot + ip);
	}

	/**
	 * 获取客户端Mac
	 * @param ip
	 * @return
	 */
	public static String getMACAddress(String ip) {
		String str = "";
		String macAddress = "";
		try {
			Process p = Runtime.getRuntime().exec("nbtstat -A " + ip);
			InputStreamReader ir = new InputStreamReader(p.getInputStream());
			LineNumberReader input = new LineNumberReader(ir);
			for (int i = 1; i < 100; i++) {
				str = input.readLine();
				if (str != null) {
					if (str.indexOf("MAC Address") > 1) {
						macAddress = str.substring(str.indexOf("MAC Address") + 14, str.length());
						break;
					}
				}
			}
		} catch (IOException e) {
			return "";
		}
		return macAddress;
	}
}
