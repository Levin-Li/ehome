
package com.eques.doorbell.a9048a3c38de2d7a.util;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

    public static String appendLeft(String src, int len, char c) {
        StringBuffer sb = new StringBuffer();
        int srcLen = src.length();
        if (srcLen >= len) {
            sb.append(src);
        } else {
            int appendSize = len - srcLen;
            for (int i = appendSize; i > 0; i--) {
                sb.append(c);
            }
            sb.append(src);
        }
        return sb.toString();
    }
    /**
     * 判断是否是空字符串 null和"" 都返回 true
     *
     * @param s
     * @return
     */
    public static boolean isEmpty(String s) {
        if (s != null && !s.trim().equals("")) {
            return false;
        }
        return true;
    }

    /**
     * 判断两个字符串是否相等 如果都为null则判断为相等,一个为null另一个not null则判断不相等 否则如果s1=s2则相等
     *
     * @param s1
     * @param s2
     * @return
     */
    public static boolean equals(String s1, String s2) {
        if (StringUtil.isEmpty(s1) && StringUtil.isEmpty(s2)) {
            return true;
        } else if (!StringUtil.isEmpty(s1) && !StringUtil.isEmpty(s2)) {
            return s1.equals(s2);
        }
        return false;
    }

    /**
     * 用要通过URL传输的内容进行编码
     *
     * @param src
     * @return 经过编码的内容
     */
    public static String URLEncode(String src) {
        String return_value = "";
        try {
            if (src != null) {
                return_value = URLEncoder.encode(src, "GBK");

            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return_value = src;
        }

        return return_value;
    }
    /**
     * 把字节码转换成16进制
     */
    public static String byte2hex(byte bytes[]) {
        StringBuffer retString = new StringBuffer();
        for (int i = 0; i < bytes.length; ++i) {
            retString.append(Integer.toHexString(0x0100 + (bytes[i] & 0x00FF)).substring(1).toUpperCase());
        }
        return retString.toString();
    }

    /**
     * 把16进制转换成字节码
     *
     * @param hex
     * @return
     */
    public static byte[] hex2byte(String hex) {
        byte[] bts = new byte[hex.length() / 2];
        for (int i = 0; i < bts.length; i++) {
            bts[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return bts;
    }
    /**
     * 不为空
     *
     * @param str
     * @return
     */
    public static boolean isNotEmpty(String str) {
        if (str != null && !str.equals(""))
            return true;
        else
            return false;
    }
    public static String createKey() {
        String key = "";
        String s = getRandomNumber(10);
        key = new Date().getTime() + s;
        return key;
    }

    public static String getRandomNumber(int cnt) {
        int j = cnt;
        long[] random = new long[j];
        for (int i = 0; i < j; i++) {
            random[i] = Math.round(Math.floor((Math.random() * 10)));
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < random.length; i++) {
            String temp = Long.toString(random[i]);
            sb.append(temp);
        }
        return sb.toString();
    }
    public static String getUTF8String(String source){
        try {
            return new String(source.getBytes(), "UTF-8");
        }catch (Exception e){

        }
        return source;
    }
    public static String searchNumbers(String source){
        try {
            String regEx = "[^0-9]";
            Pattern p = Pattern.compile(regEx);
            Matcher m = p.matcher(source);
            String result = m.replaceAll("").trim();
            return result;
        }catch (Exception e){

        }
        return "";
    }
    /**
     * 格式化一个float
     *
     * @param format 要格式化成的格式 such as #.00, #.#
     */

    public static String formatFloat(float f, String format) {
        DecimalFormat df = new DecimalFormat(format);
        return df.format(f);
    }
    /**
     * 格式化一个float
     *
     * @param format 要格式化成的格式 such as #.00, #.#
     */

    public static String formatDouble(double d, String format) {
        DecimalFormat df = new DecimalFormat(format);
        return df.format(d);
    }
    public static String reChange(String from) {
        char[] froms = from.toCharArray();
        for (int i = 0; i < from.length() / 2; i++) {
            char temp = froms[i];
            froms[i] = froms[from.length() - 1 - i];
            froms[froms.length - 1 - i] = temp;
        }
        return String.valueOf(froms);
    }
}