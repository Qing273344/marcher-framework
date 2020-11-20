package xin.marcher.framework.z.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class ImageDown {

    public static void main(String[] args) throws Exception {
        String url = "http://soushi-temp.oss-cn-shenzhen.aliyuncs.com/190821/2/826e064f586bfe8e95230821bf771a95.png?x-oss-process=image/resize,w_300,h_300/watermark,type_d3F5LXplbmhlaQ,size_30,text_5L2g5aW95ZWK,color_FFFFFF,shadow_50,t_100,g_se,x_10,y_10";

        download(url, 10);
    }

    //java 通过url下载图片保存到本地
    public static void download(String urlString, int i) throws Exception {
        // 构造URL
        URL url = new URL(urlString);
        // 打开连接
        URLConnection con = url.openConnection();
        // 输入流
        InputStream is = con.getInputStream();
        // 1K的数据缓冲
        byte[] bs = new byte[1024];
        // 读取到的数据长度
        int len;
        // 输出的文件流
        String filename = "/Users/marcher/marcher_jar/" + i + ".jpg";  //下载路径及下载图片名称
        File file = new File(filename);
        FileOutputStream os = new FileOutputStream(file, true);
        // 开始读取
        while ((len = is.read(bs)) != -1) {
            os.write(bs, 0, len);
        }
        System.out.println(i);
        // 完毕，关闭所有链接
        os.close();
        is.close();
    }
}
