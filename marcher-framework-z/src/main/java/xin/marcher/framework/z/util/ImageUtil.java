package xin.marcher.framework.z.util;

import net.coobird.thumbnailator.Thumbnails;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;

/**
 * @author: Jack
 * @Description: 图片处理工具类
 * @Date: Create by 14:19 2017/11/30
 * @Modified By: marcher
 */
public class ImageUtil {

	/**
	 * Base64解码并生成图片
	 * @param imgStr
	 * @param imgFile
	 * @throws IOException
	 */
	public static void generateImage(String imgStr, String imgFile) throws IOException {
		BASE64Decoder decoder = new BASE64Decoder();
		// Base64解码
		byte[] bytes;
		OutputStream out = null;
		try {
			bytes = decoder.decodeBuffer(imgStr);
			for (int i = 0; i < bytes.length; ++i) {
				if (bytes[i] < 0) {// 调整异常数据
					bytes[i] += 256;
				}
			}
			// 生成图片
			out = new FileOutputStream(imgFile);
			out.write(bytes);
			out.flush();
		} catch (IOException e) {
			throw new IOException();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 根据路径得到base编码后图片
	 * 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
	 * @param imgFilePath
	 * @return
	 * @throws IOException
	 */
	public static String getImageStr(String imgFilePath) throws IOException {
		byte[] data = null;

		// 读取图片字节数组
		try {
			InputStream in = new FileInputStream(imgFilePath);
			data = new byte[in.available()];
			in.read(data);
			in.close();
		} catch (IOException e) {
			throw new IOException();
		}

		// 对字节数组Base64编码
		BASE64Encoder encoder = new BASE64Encoder();
		// 返回Base64编码过的字节数组字符串
		return encoder.encode(data);
	}

	/**
	 * 图片旋转
	 * @param base64In	传入的图片base64
	 * @param angle		图片旋转度数
	 * @return
	 * 		传出的图片base64
	 * @throws IOException
	 */
	public static String imgAngleRevolve(String base64In, int angle) throws IOException {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			Thumbnails.of(base64ToIo(base64In)).scale(1.0).rotate(angle).toOutputStream(os);
		} catch (IOException e) {
			throw new IOException();
		}
		byte[] bs = os.toByteArray();
		String s = new BASE64Encoder().encode(bs);
		return s;
	}

	/**
	 * base64转为io流
	 * @param strBase64
	 * @return
	 * @throws IOException
	 */
	public static InputStream base64ToIo(String strBase64) throws IOException {
		// 1.解码，然后将字节转换为文件 2.将字符串转换为byte数组
		byte[] bytes = new BASE64Decoder().decodeBuffer(strBase64);
		return new ByteArrayInputStream(bytes);
	}
}