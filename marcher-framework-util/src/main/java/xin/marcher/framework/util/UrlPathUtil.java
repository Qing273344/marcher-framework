package xin.marcher.framework.util;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * 文件路径工具类
 *
 * @author marcher
 */
public class UrlPathUtil {

    /**
     * 获取url路径
     *
     * @param url url
     * @return ossKey
     */
    public static String getPathNoStartSeparate(String url) {
        String ossKey = getPath(url);
        if (ossKey.startsWith("/")) {
            ossKey = ossKey.substring(1);
        }
        return ossKey;
    }


    /**
     * 获取url路径
     *
     * @param url    url
     * @return
     *      url路径
     */
    public static String getPath(String url) {
        String path = "";
        if (EmptyUtil.isEmpty(url)) {
            return path;
        }

        try {
            path = new URL(url).getPath();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return path;
    }

    /**
     * ge文件名
     * 栗子: 123.jpg
     *
     * @param srcUrl url
     * @return
     *      文件名(包含后缀)
     */
    public static String getFileNameAsUrl(String srcUrl) {
        String path = getPath(srcUrl);
        return FileUtil.getFileName(path);
    }

    /**
     * get文件类型
     * 栗子: jpg, MP4
     *
     * @param srcUrl url
     * @return
     *      文件后缀
     */
    public static String getExtensionAsUrl(String srcUrl) {
        String fileName = getPath(srcUrl);
        return FileUtil.getExtension(fileName);
    }


    /**
     * get ossKey
     *
     * @param srcFileName 源文件url
     * @param directorys  目录参数
     * @return
     *      返回文件key
     */
    public static String createPath(String srcFileName, String... directorys) {
        String fileName = FileUtil.replaceFileName(srcFileName);
        String directoryPath = FileUtil.createBasePath(directorys);
        return directoryPath + fileName;
    }
}
