package xin.marcher.framework.z.util;

import java.io.*;

/**
 * @author: Jack
 * @Description: Base64工具类
 * @Date: Create by 14:19 2017/11/30
 * @Modified By: marcher
 */
public final class Base64Util {
    private static final int BASE_LENGTH = 255;

    private static final int LOOK_UP_LENGTH = 64;

    private static final int TWENTY_FOUR_BIT_GROUP = 24;

    private static final int EIGHT_BIT = 8;

    private static final int SIX_TEEN_BIT = 16;

    // private static final int SIXBIT = 6;
    private static final int FOUR_BYTE = 4;

    // private static final int TWOBYTE = 2;
    private static final int SIGN = -128;

    private static final byte PAD = (byte) '=';
    /**
     * 文件读取缓冲区大小
     */
    private static final int CACHE_SIZE = 1024;

    private static byte[] base64Alphabet = new byte[BASE_LENGTH];

    private static byte[] lookUpBase64Alphabet = new byte[LOOK_UP_LENGTH];

    static {
        for (int i = 0; i < BASE_LENGTH; i++) {
            base64Alphabet[i] = -1;
        }
        for (int i = 'Z'; i >= 'A'; i--) {
            base64Alphabet[i] = (byte) (i - 'A');
        }
        for (int i = 'z'; i >= 'a'; i--) {
            base64Alphabet[i] = (byte) (i - 'a' + 26);
        }
        for (int i = '9'; i >= '0'; i--) {
            base64Alphabet[i] = (byte) (i - '0' + 52);
        }
        base64Alphabet['+'] = 62;
        base64Alphabet['/'] = 63;

        for (int i = 0; i <= 25; i++) {
            lookUpBase64Alphabet[i] = (byte) ('A' + i);
        }
        for (int i = 26, j = 0; i <= 51; i++, j++) {
            lookUpBase64Alphabet[i] = (byte) ('a' + j);
        }

        for (int i = 52, j = 0; i <= 61; i++, j++) {
            lookUpBase64Alphabet[i] = (byte) ('0' + j);
        }
        lookUpBase64Alphabet[62] = (byte) '+';
        lookUpBase64Alphabet[63] = (byte) '/';
    }

    public static boolean isBase64(String isValidString) {
        return isArrayByteBase64(isValidString.getBytes());
    }

    public static boolean isBase64(byte octect) {
        // shall we ignore white space? JEFF??
        return (octect == PAD || base64Alphabet[octect] != -1);
    }

    public static boolean isArrayByteBase64(byte[] arrayOctect) {
        int length = arrayOctect.length;
        if (length == 0) {
            // shouldn't a 0 length array be valid base64 data?
            // return false;
            return true;
        }
        for (byte b : arrayOctect) {
            if (!isBase64(b)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 编码字符串对象
     * @param src   被编码字符串
     * @return
     *      编码字符串
     */
    public static String encodeString(String src) {
        return encode(src);
    }

    public static String encodeBytes(byte[] src) {
        if (src == null || src.length == 0) {
            return null;
        }
        byte[] bytes = encode(src);
        return new String(bytes);
    }

    /**
     * 编码字符串对象
     * @param src   被编码字符串
     * @return
     *      编码字符串
     */
    public static String encode(String src) {
        String target = null;
        if (src != null) {
            byte[] bts1 = src.getBytes();
            byte[] bts2 = encode(bts1);
            if (bts2 != null) {
                target = new String(bts2);
            }
        }
        return target;
    }

    /**
     * 十六进制编码到 base64
     * @param binaryData    二进制数组
     * @return
     *      Base64-encoded data
     */
    public static byte[] encode(byte[] binaryData) {
        int lengthDataBits = binaryData.length * EIGHT_BIT;
        int fewerThan24bits = lengthDataBits % TWENTY_FOUR_BIT_GROUP;
        int numberTriplets = lengthDataBits / TWENTY_FOUR_BIT_GROUP;
        byte[] encodedData = null;

        if (fewerThan24bits != 0) {
            // data not divisible by 24 bit
            encodedData = new byte[(numberTriplets + 1) * 4];
        }
        else {
            // 16 or 8 bit
            encodedData = new byte[numberTriplets * 4];
        }

        byte k = 0, l = 0, b1 = 0, b2 = 0, b3 = 0;

        int encodedIndex = 0;
        int dataIndex = 0;
        int i = 0;
        for (i = 0; i < numberTriplets; i++) {
            dataIndex = i * 3;
            b1 = binaryData[dataIndex];
            b2 = binaryData[dataIndex + 1];
            b3 = binaryData[dataIndex + 2];

            l = (byte) (b2 & 0x0f);
            k = (byte) (b1 & 0x03);

            encodedIndex = i * 4;
            byte val1 = ((b1 & SIGN) == 0) ? (byte) (b1 >> 2) : (byte) ((b1) >> 2 ^ 0xc0);
            byte val2 = ((b2 & SIGN) == 0) ? (byte) (b2 >> 4) : (byte) ((b2) >> 4 ^ 0xf0);
            byte val3 = ((b3 & SIGN) == 0) ? (byte) (b3 >> 6) : (byte) ((b3) >> 6 ^ 0xfc);

            encodedData[encodedIndex] = lookUpBase64Alphabet[val1];
            encodedData[encodedIndex + 1] = lookUpBase64Alphabet[val2 | (k << 4)];
            encodedData[encodedIndex + 2] = lookUpBase64Alphabet[(l << 2) | val3];
            encodedData[encodedIndex + 3] = lookUpBase64Alphabet[b3 & 0x3f];
        }

        // form integral number of 6-bit groups
        dataIndex = i * 3;
        encodedIndex = i * 4;
        if (fewerThan24bits == EIGHT_BIT) {
            b1 = binaryData[dataIndex];
            k = (byte) (b1 & 0x03);
            byte val1 = ((b1 & SIGN) == 0) ? (byte) (b1 >> 2) : (byte) ((b1) >> 2 ^ 0xc0);
            encodedData[encodedIndex] = lookUpBase64Alphabet[val1];
            encodedData[encodedIndex + 1] = lookUpBase64Alphabet[k << 4];
            encodedData[encodedIndex + 2] = PAD;
            encodedData[encodedIndex + 3] = PAD;
        }
        else if (fewerThan24bits == SIX_TEEN_BIT) {

            b1 = binaryData[dataIndex];
            b2 = binaryData[dataIndex + 1];
            l = (byte) (b2 & 0x0f);
            k = (byte) (b1 & 0x03);

            byte val1 = ((b1 & SIGN) == 0) ? (byte) (b1 >> 2) : (byte) ((b1) >> 2 ^ 0xc0);
            byte val2 = ((b2 & SIGN) == 0) ? (byte) (b2 >> 4) : (byte) ((b2) >> 4 ^ 0xf0);

            encodedData[encodedIndex] = lookUpBase64Alphabet[val1];
            encodedData[encodedIndex + 1] = lookUpBase64Alphabet[val2 | (k << 4)];
            encodedData[encodedIndex + 2] = lookUpBase64Alphabet[l << 2];
            encodedData[encodedIndex + 3] = PAD;
        }

        return encodedData;
    }

    public static String decode(String src) {
        String target = null;
        if (src != null) {
            byte[] bts1 = src.getBytes();
            byte[] bts2 = decode(bts1);
            if (bts2 != null) {
                target = new String(bts2);
            }
        }
        return target;
    }

    public static String decode(String src, String charSet) throws UnsupportedEncodingException {
        String target = null;
        if (src != null) {
            byte[] bts1 = src.getBytes();
            byte[] bts2 = decode(bts1);
            if (bts2 != null) {
                target = new String(bts2, charSet);
            }
        }
        return target;
    }

    /**
     * Decodes Base64 data into octects
     *
     * @param base64Data Byte array containing Base64 data
     * @return Array containing decoded data.
     */
    public static byte[] decode(byte[] base64Data) {
        // handle the edge case, so we don't have to worry about it later
        if (base64Data.length == 0) {
            return null;
        }

        int numberQuadruple = base64Data.length / FOUR_BYTE;
        byte decodedData[] = null;
        byte b1 = 0, b2 = 0, b3 = 0, b4 = 0, marker0 = 0, marker1 = 0;

        // Throw away anything not in base64Data

        int encodedIndex = 0;
        int dataIndex = 0;{
            // this sizes the output array properly - rlw
            int lastData = base64Data.length;
            // ignore the '=' padding
            while (base64Data[lastData - 1] == PAD) {
                if (--lastData == 0) {
                    return new byte[0];
                }
            }
            decodedData = new byte[lastData - numberQuadruple];
        }

        for (int i = 0; i < numberQuadruple; i++) {
            dataIndex = i * 4;
            marker0 = base64Data[dataIndex + 2];
            marker1 = base64Data[dataIndex + 3];

            b1 = base64Alphabet[base64Data[dataIndex]];
            b2 = base64Alphabet[base64Data[dataIndex + 1]];

            if (marker0 != PAD && marker1 != PAD) {
                // No PAD e.g 3cQl
                b3 = base64Alphabet[marker0];
                b4 = base64Alphabet[marker1];

                decodedData[encodedIndex] = (byte) (b1 << 2 | b2 >> 4);
                decodedData[encodedIndex + 1] = (byte) (((b2 & 0xf) << 4) | ((b3 >> 2) & 0xf));
                decodedData[encodedIndex + 2] = (byte) (b3 << 6 | b4);
            }
            else if (marker0 == PAD) {
                // Two PAD e.g. 3c[Pad][Pad]
                decodedData[encodedIndex] = (byte) (b1 << 2 | b2 >> 4);
            }
            else if (marker1 == PAD) {
                // One PAD e.g. 3cQ[Pad]
                b3 = base64Alphabet[marker0];

                decodedData[encodedIndex] = (byte) (b1 << 2 | b2 >> 4);
                decodedData[encodedIndex + 1] = (byte) (((b2 & 0xf) << 4) | ((b3 >> 2) & 0xf));
            }
            encodedIndex += 3;
        }
        return decodedData;
    }

    /**
     * 隐藏工具类的构造方法
     */
    protected Base64Util() {
        throw new UnsupportedOperationException();
    }

    /**
     * BASE64字符串解码为二进制数据
     *
     * @param base64
     * @return
     * @throws Exception
     */
    public static byte[] decodeString(String base64) throws Exception {
        return Base64Util.decode(base64.getBytes());
    }

    /**
     * 二进制数据编码为BASE64字符串
     *
     * @param bytes 二进制数据
     * @return
     * @throws Exception
     */
    public static String encodeByte(byte[] bytes) throws Exception {
        return new String(Base64Util.encode(bytes));
    }

    /**
     * 将文件编码为BASE64字符串, (大文件慎用，可能会导致内存溢出)
     *
     * @param filePath  文件绝对路径
     * @return
     * @throws Exception
     */
    public static String encodeFile(String filePath) throws Exception {
        byte[] bytes = fileToByte(filePath);
        return encodeByte(bytes);
    }

    /**
     * BASE64字符串转回文件
     * @param filePath  文件绝对路径
     * @param base64    编码字符串
     * @throws Exception
     */
    public static void decodeToFile(String filePath, String base64) throws Exception {
        byte[] bytes = decodeString(base64);
        byteArrayToFile(bytes, filePath);
    }

    /**
     * 文件转换为二进制数组
     *
     * @param filePath     文件路径
     * @return
     * @throws Exception
     */
    public static byte[] fileToByte(String filePath) throws Exception {
        byte[] data = new byte[0];
        File file = new File(filePath);
        if (file.exists()) {
            FileInputStream in = new FileInputStream(file);
            ByteArrayOutputStream out = new ByteArrayOutputStream(2048);
            byte[] cache = new byte[CACHE_SIZE];
            int nRead = 0;
            while ((nRead = in.read(cache)) != -1) {
                out.write(cache, 0, nRead);
                out.flush();
            }
            out.close();
            in.close();
            data = out.toByteArray();
        }
        return data;
    }

    /**
     * 二进制数据写文件
     *
     * @param bytes        二进制数据
     * @param filePath      文件生成目录
     * @throws Exception
     */
    public static void byteArrayToFile(byte[] bytes, String filePath) throws Exception {
        InputStream in = new ByteArrayInputStream(bytes);
        File destFile = new File(filePath);
        if (!destFile.getParentFile().exists()) {
            destFile.getParentFile().mkdirs();
        }
        destFile.createNewFile();
        OutputStream out = new FileOutputStream(destFile);
        byte[] cache = new byte[CACHE_SIZE];
        int nRead = 0;
        while ((nRead = in.read(cache)) != -1) {
            out.write(cache, 0, nRead);
            out.flush();
        }
        out.close();
        in.close();
    }
}