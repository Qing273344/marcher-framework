package xin.marcher.framework.z.util;

import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifIFD0Directory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

/**
 * ios图片翻转复原
 *
 * @author marcher
 */
public class ImageRotateUtil {

	 /**
     * 判断图片是否需要旋转
     */
    public static File rotate(File file) {
        //获取图片旋转角度
        int angel = getRotateAngleForPhoto(file);
        if (angel > 0 && angel < 360) {
           return rotateImage(file, angel);
        }
        return file;
    }

	/**
     * 图片翻转时，计算图片翻转到正常显示需旋转角度
     */
    private static int getRotateAngleForPhoto(File file) {
    	System.setProperty("javax.xml.parsers.DocumentBuilderFactory", "com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl");
        int angel = 0;
        try {
            //核心对象操作对象
            Metadata metadata = ImageMetadataReader.readMetadata(file);
            //获取所有不同类型的Directory，如ExifSubIFDDirectory, ExifInteropDirectory, ExifThumbnailDirectory等，这些类均为ExifDirectoryBase extends Directory子类
            //分别遍历每一个Directory，根据Directory的Tags就可以读取到相应的信息
            int orientation = 0;
            Iterable<Directory> iterable = metadata.getDirectories();
            for (Iterator<Directory> iter = iterable.iterator(); iter.hasNext(); ) {
                Directory dr = iter.next();
                if (dr.getString(ExifIFD0Directory.TAG_ORIENTATION) != null) {
                    orientation = dr.getInt(ExifIFD0Directory.TAG_ORIENTATION);
                }
            }
            if (orientation == 0 || orientation == 1) {
                angel = 360;
            } else if (orientation == 3) {
                angel = 180;
            } else if (orientation == 6) {
                angel = 90;
            } else if (orientation == 8) {
                angel = 270;
            }
        } catch (Exception e) {

        }
        return angel;
    }
    

    /**
     * 旋转图片为指定角度
     *
     * @param file  目标图像
     * @param angel 旋转角度
     * @return
     */
    private static File rotateImage(File file, final int angel) {
        BufferedImage src = null;
        try {
            src = ImageIO.read(file);
        } catch (IOException e) {

        }
        if (src == null){
        	return file;
        }
        int srcWidth = src.getWidth(null);
        int srcHeight = src.getHeight(null);
        Rectangle rectDes = calcRotatedSize(new Rectangle(new Dimension(srcWidth, srcHeight)), angel);

        BufferedImage bi = new BufferedImage(rectDes.width, rectDes.height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = bi.createGraphics();

        g2.translate((rectDes.width - srcWidth) / 2, (rectDes.height - srcHeight) / 2);
        g2.rotate(Math.toRadians(angel), srcWidth / 2, srcHeight / 2);

        g2.drawImage(src, null, null);
        try {
        	String fileSuffix = getFileTypFromName(file.getName());
            ImageIO.write(bi, fileSuffix, file);
        } catch (IOException e) {

        }
        // 调用方法输出图片文件
        // outImage(file.getPath(), bi, (float) 0.5);
        return file;
    }

    /**
     * 计算旋转参数
     */
    private static Rectangle calcRotatedSize(Rectangle src, int angel) {
        // if angel is greater than 90 degree,we need to do some conversion.
        if (angel > 90) {
            if (angel / 9 % 2 == 1) {
                int temp = src.height;
                src.height = src.width;
                src.width = temp;
            }
            angel = angel % 90;
        }

        double r = Math.sqrt(src.height * src.height + src.width * src.width) / 2;
        double len = 2 * Math.sin(Math.toRadians(angel) / 2) * r;
        double angelAlpha = (Math.PI - Math.toRadians(angel)) / 2;
        double angelDaltaWidth = Math.atan((double) src.height / src.width);
        double angelDaltaHeight = Math.atan((double) src.width / src.height);

        int lenDaltaWidth = (int) (len * Math.cos(Math.PI - angelAlpha - angelDaltaWidth));
        int lenDaltaHeight = (int) (len * Math.cos(Math.PI - angelAlpha - angelDaltaHeight));
        int desWidth = src.width + lenDaltaWidth * 2;
        int desHeight = src.height + lenDaltaHeight * 2;
        return new Rectangle(new Dimension(desWidth, desHeight));
    }

    
    private static String getFileTypFromName(String fileName){
		String fileType = "jpeg";
		String[]  fileNameSplits = fileName.split("\\.");
		if (fileNameSplits.length > 0){
			fileType = fileNameSplits[fileNameSplits.length -1];
		}
		return fileType;
	}

}
