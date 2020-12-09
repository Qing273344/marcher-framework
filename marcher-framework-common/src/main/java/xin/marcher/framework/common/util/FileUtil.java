package xin.marcher.framework.common.util;

import cn.hutool.core.util.IdUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;
import xin.marcher.framework.common.constants.GlobalConstant;
import xin.marcher.framework.common.exception.UtilException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

/**
 * 文件工具类
 *
 * @author marcher
 */
public class FileUtil {

	public static String createBaseName() {
		return IdUtil.simpleUUID();
	}

	/**
	 * 连接路径和文件名称，组成最后的包含路径的文件名
	 * 栗子: basePath: 123/456/或123/456  fullFilenameToAdd: 789.jpg --> 123/456/789.jpg
	 *
	 * @param basePath	文件路径
	 * @param fullFilenameToAdd	文件名称
	 * @return
	 * 		包含路径的文件名
	 */
	public static String concat(String basePath, String fullFilenameToAdd) {
		return FilenameUtils.concat(basePath, fullFilenameToAdd);
	}

	/**
	 * 获得不带文件扩展名的文件名称
	 * 栗子: 123/456/789.jpg --> 789
	 *
	 * @param filename	文件完整路径
	 * @return
	 * 		不带扩展名的文件名称
	 */
	public static String getBaseName(String filename) {
		return FilenameUtils.getBaseName(filename);
	}

	/**
	 * 获得带扩展名的文件名称
	 * 栗子: 123/456/789.jpg --> 789.jpg
	 *
	 * @param filename	文件完整路径
	 * @return
	 * 		文件名称
	 */
	public static String getFileName(String filename) {
		return FilenameUtils.getName(filename);
	}

	/**
	 * 获得文件的完整路径，包含最后的路径分隔条
	 * 栗子: 123/456/789.jpg --> 123/456/
	 *
	 * @param filename	文件完整路径
	 * @return
	 * 		目录结构
	 */
	public static String getFullPath(String filename) {
		return FilenameUtils.getFullPath(filename);
	}

	/**
	 * 获得文件的完整路径，不包含最后的路径分隔条
	 * 栗子: 123/456/789.jpg --> 123/456
	 *
	 * @param filename	文件完整路径
	 * @return
	 *
	 */
	public static String getFullPathNoEndSeparator(String filename) {
		return FilenameUtils.getFullPathNoEndSeparator(filename);
	}

	/**
	 * 判断文件是否有某扩展名
	 *
	 * @param filename	文件完整路径
	 * @param extension	扩展名名称
	 * @return
	 * 		若是，返回true，否则返回false
	 */
	public static boolean isExtension(String filename, String extension) {
		return FilenameUtils.isExtension(filename, extension);
	}

	/**
	 * 规范化路径，合并其中的多个分隔符为一个,并转化为本地系统路径格式
	 * @param filename	文件完整路径
	 * @return
	 */
	public static String normalize(String filename) {
		return FilenameUtils.normalize(filename);
	}

	/**
	 * 规范化路径，合并其中的多个分隔符为一个,并转化为本地系统路径格式,若是路径，则不带最后的路径分隔符
	 * @param filename	文件完整路径
	 * @return
	 */
	public static String normalizeNoEndSeparator(String filename) {
		return FilenameUtils.normalizeNoEndSeparator(filename);
	}

	/**
	 * 把文件路径中的分隔符转换为unix的格式，也就是"/"
	 * @param path	文件完整路径
	 * @return
	 * 		转换后的路径
	 */
	public static String separatorsToUnix(String path) {
		return FilenameUtils.separatorsToUnix(path);
	}

	/**
	 * 把文件路径中的分隔符转换为window的格式，也就是"\"
	 * @param path	文件完整路径
	 * @return
	 * 		转换后的路径
	 */
	public static String separatorsToWindows(String path) {
		return FilenameUtils.separatorsToWindows(path);
	}

	/**
	 * 把文件路径中的分隔符转换当前系统的分隔符
	 * @param path	文件完整路径
	 * @return
	 * 		转换后的路径
	 */
	public static String separatorsToSystem(String path) {
		return FilenameUtils.separatorsToSystem(path);
	}

	/**
	 * 提取文件的扩展名
	 * 栗子: 123/456/789.jpg --> jpg
	 *
	 * @param filename	文件名称
	 * @return
	 * 		文件扩展名，若没有扩展名，则返回空字符串
	 */
	public static String getExtension(String filename) {
		return FilenameUtils.getExtension(filename);
	}

	/**
     * 创建文件路径
	 * 栗子: [123,456] --> 123/456
	 *
	 * @param directoryList	路径参数
	 * @return
	 * 		文件路径
	 */
	public static String createBasePath(List<String> directoryList) {
		StringBuilder directoryPath = new StringBuilder();
		for (String directory : directoryList) {
			directoryPath.append(directory).append("/");
		}
		return directoryPath.toString();
	}

	/**
	 * 创建文件路径
	 * 栗子: [123,456] --> 123/456
	 *
	 * @param directorys	路径参数
	 * @return
	 * 		文件路径
	 */
	public static String createBasePath(String... directorys) {
		StringBuilder directoryPath = new StringBuilder();
		for (String directory : directorys) {
			directoryPath.append(directory).append("/");
		}
		return directoryPath.toString();
	}

	/**
	 * 创建文件名
	 *
	 * @return
	 *      文件名
	 */
	public static String replaceFileName(String fileName) {
		String baseName = createBaseName();
		String fileExtension = getExtension(fileName);
		if (fileExtension == null) {
			return "";
		}
		return baseName + GlobalConstant.CHAR_DIT + fileExtension;
	}

	/**
	 * 创建文件名
	 *
	 * @param baseName     新文件主名
	 * @param oldFileName  旧文件名
	 * @return
	 *      文件名
	 */
	public static String replaceFileName(String baseName, String oldFileName) {
		String fileExtension = getExtension(oldFileName);
		if (fileExtension == null) {
			return "";
		}
		return baseName + GlobalConstant.CHAR_DIT + fileExtension;
	}


	public static File multipartFile2File(MultipartFile multipartFile) {
		// 获取文件名
		String fileName = multipartFile.getOriginalFilename();
		// 获取文件后缀
		String prefix = fileName.substring(fileName.lastIndexOf("."));

		File file = null;
		try {
			file = File.createTempFile(fileName, prefix);
			FileUtils.copyInputStreamToFile(multipartFile.getInputStream(), file);
		} catch (IOException e) {
			throw new UtilException("multipartFile to File error", e);
		}
		return file;
	}

	/**
	 * 获取 resources 目录下的文件流
	 *
	 * @param fileName	文件名
	 * @return
	 * 		InputStream
	 */
	public static InputStream getResourcesFileInputStream(String fileName) {
		return Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
	}


	/**
	 * 通过 url 获取网络文件流
	 * @param urlPath
	 * @return
	 */
	public static InputStream getNetworkFileInputStream(String urlPath) {
		InputStream inputStream;
		try {
			// 构造URL
			URL url = new URL(urlPath);
			// 打开连接
			URLConnection con = url.openConnection();
			// 输入流
			inputStream = con.getInputStream();
		} catch (Exception ex) {
			throw new UtilException("获取网络文件错误", ex);
		}

		return inputStream;
	}
}
