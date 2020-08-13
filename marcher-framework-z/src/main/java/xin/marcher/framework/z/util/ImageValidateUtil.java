package xin.marcher.framework.z.util;

import org.apache.commons.io.IOUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 图片校验工具
 *
 * @author marcher
 */
public class ImageValidateUtil {

    public static void main(String[] args) throws Exception {
        File file = new File("/Users/marcher/WechatIMG1524.png");
//        File file = new File("/Users/marcher/adapter.jpg");
//        boolean legalImg = isLegalImgByStr(file);
        boolean legalImg = isLegalImgByStream(file);
        System.out.println(legalImg);
    }

    private static boolean isLegalImgByStream(File img) throws Exception {
        if (img.length() <= 0) {
            return false;
        }

        InputStream inputStream = new FileInputStream(img);

        BufferedImage src = ImageIO.read(inputStream);
        int old_w = src.getWidth();
        // 得到源图宽
        int old_h = src.getHeight();
        // 得到源图长
        BufferedImage newImg = null;
        // 判断输入图片的类型
        switch (src.getType()) {
            case 13:
                // png,gif
                newImg = new BufferedImage(old_w, old_h, BufferedImage.TYPE_4BYTE_ABGR);
                break;
            default:
                newImg = new BufferedImage(old_w, old_h, BufferedImage.TYPE_INT_RGB);
                break;
        }
        Graphics2D g = newImg.createGraphics();
        // 从原图上取颜色绘制新图
        g.drawImage(src, 0, 0, old_w, old_h, null);
        g.dispose();
        // 根据图片尺寸压缩比得到新图的尺寸
        newImg.getGraphics().drawImage(
                src.getScaledInstance(old_w, old_h, Image.SCALE_SMOOTH), 0, 0, null);

        String fileName = img.getName();
        File newFile = new File(fileName);
        String endName = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        ImageIO.write(newImg, endName, newFile);

        InputStream input = new FileInputStream(newFile);
        byte[] byt = new byte[input.available()];
        input.read(byt);

        FileOutputStream fileOutputStream = new FileOutputStream("/Users/marcher/WechatIMG15241.png");
        fileOutputStream.write(byt);
        fileOutputStream.close();

        return isLegalImgByStr(newFile);
    }

    /**
     * 是否合法图片
     *
     * @param img 文件
     */
    private static boolean isLegalImgByStr(File img) throws Exception {
        InputStream inputStream = new FileInputStream(img);

        byte[] byteArray = IOUtils.toByteArray(inputStream);
        String str = Base64Util.encodeByte(byteArray);
        System.out.println(str);
        // 匹配16进制中的 <% ( ) %>
        // 匹配16进制中的 <? ( ) ?>
        // 匹配16进制中的 <script | /script> 大小写亦可
        // 通过匹配十六进制代码检测是否存在木马脚本
        String pattern = "(3c25.*?28.*?29.*?253e)|(3c3f.*?28.*?29.*?3f3e)|(3C534352495054)|(2F5343524950543E)|(3C736372697074)|(2F7363726970743E)";
        Pattern mPattern = Pattern.compile(pattern);
        Matcher mMatcher = mPattern.matcher(str);

        // 查找相应的字符串
        boolean flag = true;
        if (mMatcher.find()) {
            flag = false;
            //过滤java关键字(java import String print write( read() php request alert system)（暂时先这样解决，这样改动最小，以后想后更好的解决方案再优化）
            String keywordPattern = "(6a617661)|(696d706f7274)|(537472696e67)|(7072696e74)|(777269746528)|(726561642829)|(706870)|(72657175657374)|(616c657274)|(73797374656d)";
            Pattern keywordmPattern = Pattern.compile(keywordPattern);
            Matcher keywordmMatcher = keywordmPattern.matcher(str);
            if (keywordmMatcher.find()) {
                flag = false;
            }
        }
        return flag;
    }
}
