package xin.marcher.framework.z.util;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author: Jack
 * @Description: http请求工具类
 * @Date: Create by 14:19 2017/11/30
 * @Modified By: marcher
 */
public class HttpHelper {

	/**
	 * 4kB
	 */
	private static final int DEFAULT_INITIAL_BUFFER_SIZE = 4 * 1024;

	private HttpHelper() {}

	/**
	 * 返回对应URL地址的内容，注意，只返回正常响应(状态响应代码为200)的内容
	 * @param urlPath	需要获取内容的URL地址
	 * @return
	 * 		获取的内容字节数组
	 */
	public static byte[] getURLContent(String urlPath) {
		HttpURLConnection conn = null;
		InputStream inStream = null;
		byte[] buffer = null;

		try {
			URL url = new URL(urlPath);
			HttpURLConnection.setFollowRedirects(false);
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			conn.setDefaultUseCaches(false);
			// 10秒
			conn.setConnectTimeout(10000);
			// 60秒
			conn.setReadTimeout(60000);

			conn.connect();

			int repCode = conn.getResponseCode();
			if (repCode == 200) {
				inStream = conn.getInputStream();
				int contentLength = conn.getContentLength();
				buffer = getResponseBody(inStream, contentLength);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			System.err.println("获取内容失败");
		} finally {
			try {
				if (inStream != null) {
					inStream.close();
				}

				if (conn != null) {
					conn.disconnect();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				System.err.println("关闭连接失败");
			}
		}
		return buffer;
	}

	/**
	 * 获取BODY部分的字节数组
	 * @param instream
	 * @param contentLength
	 * @return
	 * @throws Exception
	 */
	private static byte[] getResponseBody(InputStream instream, int contentLength) throws Exception {
		if (contentLength == -1) {
			System.out.println("Going to buffer response body of large or unknown size. ");
		}
		ByteArrayOutputStream outstream = new ByteArrayOutputStream(
				contentLength > 0 ? (int) contentLength : DEFAULT_INITIAL_BUFFER_SIZE);
		byte[] buffer = new byte[4096];
		int len;
		while ((len = instream.read(buffer)) > 0) {
			outstream.write(buffer, 0, len);
		}
		outstream.close();
		return outstream.toByteArray();
	}

	@SuppressWarnings("unused")
	private static void readFixedLenToBuffer(InputStream inStream, byte[] buffer) throws Exception {
		int count = 0;
		int remainLength = buffer.length;
		int bufLength = buffer.length;
		int readLength = 0;
		do {
			count = inStream.read(buffer, readLength, remainLength);
			// 已经到达末尾
			if (count == -1) {
				// 若实际读取的数据和需要读取的数据不匹配，则报错
				if (readLength != bufLength) {
					throw new Exception("读取数据出错，不正确的数据结束");
				}
			}

			readLength += count;

			// 已经读取完，则返回
			if (readLength == bufLength) {
				return;
			}

			remainLength = bufLength - readLength;
		} while (true);
	}

	/**
	 * 返回对应URL地址的内容，注意，只返回正常响应(状态响应代码为200)的内容
	 * @param urlPath	需要获取内空的URL地址
	 * @param charset	字符集编码方式
	 * @return
	 * 		获取的内容字串
	 */
	public static String getURLContent(String urlPath, String charset) {
		BufferedReader reader = null;
		HttpURLConnection conn = null;
		StringBuffer buffer = new StringBuffer();
		try {
			URL url = new URL(urlPath);
			HttpURLConnection.setFollowRedirects(false);
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			conn.setDefaultUseCaches(false);
			// 10秒
			conn.setConnectTimeout(10000);
			// 60秒
			conn.setReadTimeout(60000);

			conn.connect();

			int repCode = conn.getResponseCode();
			if (repCode == 200) {
				int count = 0;
				char[] chBuffer = new char[1024];
				BufferedReader input = new BufferedReader(new InputStreamReader(conn.getInputStream(), charset));
				while ((count = input.read(chBuffer)) != -1) {
					buffer.append(chBuffer, 0, count);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			System.err.println("获取内容失败");
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
				if (conn != null) {
					conn.disconnect();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				System.err.println("关闭连接失败");
			}
		}

		return buffer.toString();
	}

	public static String getURLContent(String urlPath, String requestData, String charset) {
		BufferedReader reader = null;
		HttpURLConnection conn = null;
		StringBuffer buffer = new StringBuffer();
		OutputStreamWriter out = null;
		try {
			URL url = new URL(urlPath);
			conn = (HttpURLConnection) url.openConnection();

			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			conn.setDefaultUseCaches(false);
			// 10秒
			conn.setConnectTimeout(10000);
			// 60秒
			conn.setReadTimeout(60000);

			out = new OutputStreamWriter(conn.getOutputStream(), charset);
			out.write(requestData);
			out.flush();

			int repCode = conn.getResponseCode();
			if (repCode == 200) {
				int count = 0;
				char[] chBuffer = new char[1024];
				BufferedReader input = new BufferedReader(new InputStreamReader(conn.getInputStream(), charset));
				while ((count = input.read(chBuffer)) != -1) {
					buffer.append(chBuffer, 0, count);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			System.err.println("获取内容失败");
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (reader != null) {
					reader.close();
				}
				if (conn != null) {
					conn.disconnect();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				System.err.println("关闭连接失败");
			}
		}
		return buffer.toString();
	}
}
