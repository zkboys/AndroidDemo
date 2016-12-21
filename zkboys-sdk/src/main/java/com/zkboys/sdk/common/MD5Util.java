package com.zkboys.sdk.common;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {
    private static final char HEX_DIGITS[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D',
            'E', 'F'};
    private static final String MD5_KEY = "2f9*p#omg";

    private static final int STREAM_BUFFER_LENGTH = 1024;

    public static String toHexString(byte[] b) {
        StringBuilder sb = new StringBuilder(b.length * 2);
        for (int i = 0; i < b.length; i++) {
            sb.append(HEX_DIGITS[(b[i] & 0xf0) >>> 4]);
            sb.append(HEX_DIGITS[b[i] & 0x0f]);
        }
        return sb.toString();
    }

    public static String md5Phone(final String phone) {
        if (phone != null) {
            return md5(phone + MD5_KEY);
        }
        return "";
    }

    /**
     * 调用SunAPI返回16位字节数组摘要
     *
     * @param text 明文
     * @return byte[] msgDigest.digest();
     */
    public static byte[] md5Bytes(String text) throws Exception {
        if (null == text || "".equals(text)) {
            return new byte[0];
        }

        MessageDigest msgDigest = null;
        try {
            msgDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("System doesn't support MD5 algorithm.");
        }
        byte[] bytes = null;
        synchronized (msgDigest) {
            msgDigest.update(text.getBytes());
            bytes = msgDigest.digest();
        }
        return bytes;
    }

    /**
     * 调用SunAPI返回16位字节数组摘要
     *
     * @param bytes 明文
     * @return byte[] msgDigest.digest();
     */
    public static byte[] md5Bytes(byte[] bytes) throws Exception {

        MessageDigest msgDigest = null;
        try {
            msgDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("System doesn't support MD5 algorithm.");
        }
        synchronized (msgDigest) {
            msgDigest.update(bytes);
            bytes = msgDigest.digest();
        }
        return bytes;
    }

    /**
     * 对字符串进行MD5加密
     *
     * @param text        明文
     * @param isReturnRaw 是否直接用API返回new String(byte[16])(注意不同系统间编码问题)
     * @return if(isReturnRaw) 32位密文 else 16位密文
     */
    public static String md5(String text, boolean isReturnRaw) {
        if (null == text || "".equals(text)) {
            return text;
        }

        byte[] bytes = null;
        try {
            bytes = md5Bytes(text);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        if (isReturnRaw) {
            return new String(bytes);
        }

        String md5Str = new String();
        byte tb;
        char low;
        char high;
        char tmpChar;

        for (int i = 0; i < bytes.length; i++) {
            tb = bytes[i];

            tmpChar = (char) ((tb >>> 4) & 0x000f);
            if (tmpChar >= 10) {
                high = (char) (('a' + tmpChar) - 10);
            } else {
                high = (char) ('0' + tmpChar);
            }
            md5Str += high;

            tmpChar = (char) (tb & 0x000f);
            if (tmpChar >= 10) {
                low = (char) (('a' + tmpChar) - 10);
            } else {
                low = (char) ('0' + tmpChar);
            }
            md5Str += low;
        }

        return md5Str;
    }

    private static byte[] digest(MessageDigest digest, InputStream data) throws IOException {
        byte[] buffer = new byte[STREAM_BUFFER_LENGTH];
        int read = data.read(buffer, 0, STREAM_BUFFER_LENGTH);

        while (read > -1) {
            digest.update(buffer, 0, read);
            read = data.read(buffer, 0, STREAM_BUFFER_LENGTH);
        }

        return digest.digest();
    }


    /**
     * 对字符串进行MD5加密
     *
     * @param bytes       明文
     * @param isReturnRaw 是否直接用API返回new String(byte[16])(注意不同系统间编码问题)
     * @return if(isReturnRaw) 32位密文 else 16位密文
     */
    public static String md5(byte[] bytes, boolean isReturnRaw) {
        try {
            bytes = md5Bytes(bytes);
        } catch (Exception e) {
            return "";
        }
        if (isReturnRaw) {
            return new String(bytes);
        }

        String md5Str = new String();
        byte tb;
        char low;
        char high;
        char tmpChar;

        for (int i = 0; i < bytes.length; i++) {
            tb = bytes[i];

            tmpChar = (char) ((tb >>> 4) & 0x000f);
            if (tmpChar >= 10) {
                high = (char) (('a' + tmpChar) - 10);
            } else {
                high = (char) ('0' + tmpChar);
            }
            md5Str += high;

            tmpChar = (char) (tb & 0x000f);
            if (tmpChar >= 10) {
                low = (char) (('a' + tmpChar) - 10);
            } else {
                low = (char) ('0' + tmpChar);
            }
            md5Str += low;
        }

        return md5Str;
    }

    /**
     * 对字符串进行MD5加密
     *
     * @param text 明文
     * @return 32位密文
     */
    public static String md5(String text) {
        return md5(text, false);
    }

    /**
     * 对文件进行MD5计算
     *
     * @param filename 文件名
     * @return 32位MD5Sum
     */
    public static String md5sum(String filename) {
        InputStream fis = null;
        byte[] buffer = new byte[1024];
        int numRead = 0;
        MessageDigest md5;
        try {
            fis = new FileInputStream(filename);
            md5 = MessageDigest.getInstance("MD5");
            if (null == md5)
                return "";
            byte[] md5dist = null;
            synchronized (md5) {
                while ((numRead = fis.read(buffer)) > 0) {
                    md5.update(buffer, 0, numRead);
                }
                md5dist = md5.digest();
            }
            return toHexString(md5dist);
        } catch (Exception e) {
            System.out.println("error");
            return null;
        } finally {
            try {
                if (null != fis)
                    fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
