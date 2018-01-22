package com.aerozhonghuan.hongyan.producer.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 编码类
 *
 * @author dhdai
 */
public class NumeralCoder {

    // encryption/whitening key
    private static final int DEFAULT_KEY = 6;
    private static final long INT_MASK = 0xFFFFFFFFL;
    private static final long SHORT_MASK = 0xFFFFL;

    /**
     * Mapbar encoding schemes
     *
     * @param input
     * @return
     */
    public static String encodeInt(int input) {
        return encodeNumberEx(input, DEFAULT_KEY, INT_MASK);
    }

    public static List<Integer> decodeInt(String strLine) {
        return decodeIntEx(strLine, DEFAULT_KEY);
    }

    /**
     * special shorten version for short values, range from -32K to 32K
     *
     * @param input
     * @return
     */
    public static String encodeShort(short input) {
        return encodeNumberEx(input, DEFAULT_KEY, SHORT_MASK);
    }

    public static List<Short> decodeShort(String strLine) {
        return decodeShortEx(strLine, DEFAULT_KEY);
    }

    /**
     * internal implementation reference
     *
     * @param input
     * @param cmkey
     * @return
     */
    private static String encodeNumberEx(int input, int cmkey, long mask) {
        StringBuffer encodeString = new StringBuffer();
        long num = input & mask;

        while (num >= 0x20) {
            long currValue = (((num ^ (cmkey++)) & 0x1f) << 1) + 63;
            encodeString.append((char) (currValue));
            num >>= 5;
        }

        int result = ((((int) num ^ cmkey) & 0x1f) << 1) + 64;

        encodeString.append((char) (result));
        return encodeString.toString();
    }

    private static List<Integer> decodeIntEx(String strLine, int cmkey) {
        int index = 0;
        List<Integer> list = new ArrayList<Integer>();

        if (strLine.length() <= 0) {
            return list;
        }

        while (index < strLine.length()) {
            int b = 0;
            int shift = 0;
            int result = 0;

            int currkey = cmkey;
            do {
                b = ((int) strLine.charAt(index++)) - 63;
                int currValue = (((b >> 1) ^ (currkey++)) & 0x1f);
                result |= currValue << shift;
                shift += 5;
            } while ((b & 0x1) == 0);
            list.add(result);
        }
        return list;
    }

    private static List<Short> decodeShortEx(String strLine, int cmkey) {
        int index = 0;
        List<Short> list = new ArrayList<Short>();

        if (strLine.length() <= 0) {
            return list;
        }

        while (index < strLine.length()) {
            int b = 0;
            int shift = 0;
            int result = 0;

            int currkey = cmkey;
            do {
                b = ((int) strLine.charAt(index++)) - 63;
                int currValue = (((b >> 1) ^ (currkey++)) & 0x1f);
                result |= currValue << shift;
                shift += 5;
            } while ((b & 0x1) == 0);

            list.add((short) result);
        }
        return list;
    }

    /**
     * 数字编码
     *
     * @param input 需要编码的数字
     * @return 编码后的字符
     */
    public static String encodeNumber(int input) {
        return encodeNumberByMapbar(input, DEFAULT_KEY);
    }

    /**
     * 数字编码
     *
     * @param input 需要编码的数字
     * @return 编码后的字符
     */
    public static String encodeNumber(Long input) {
        return encodeNumberByMapbar(input, DEFAULT_KEY);
    }


    /**
     * 数字解码
     *
     * @param strLine 需要解码的字符串
     * @return 解码后的数字集合
     */
    public static List<Integer> decodeNumber(String strLine) {
        return decodeNumberByMapbar(strLine, DEFAULT_KEY);
    }
    /**
     * 数字编码
     *
     * @param input 编码输入
     * @param cmkey 编码key
     * @return 编码后的字符串
     */
    private static String encodeNumberByMapbar(Long input, int cmkey) {
        StringBuffer encodeString = new StringBuffer();
        long num = input << 1;
        if (input < 0) {
            num = ~num;
        }

        while (num >= 0x20) {
            long currValue = (((num ^ (cmkey++)) & 0x1f) << 1) + 63;
            encodeString.append((char) (currValue));
            num >>= 5;
        }

        int result = ((((int) num ^ cmkey) & 0x1f) << 1) + 64;

        encodeString.append((char) (result));
        return encodeString.toString();
    }

    /**
     * 数字编码
     *
     * @param input 编码输入
     * @param cmkey 编码key
     * @return 编码后的字符串
     */
    private static String encodeNumberByMapbar(int input, int cmkey) {
        StringBuffer encodeString = new StringBuffer();
        long num = input << 1;
        if (input < 0) {
            num = ~num;
        }

        while (num >= 0x20) {
            long currValue = (((num ^ (cmkey++)) & 0x1f) << 1) + 63;
            encodeString.append((char) (currValue));
            num >>= 5;
        }

        int result = ((((int) num ^ cmkey) & 0x1f) << 1) + 64;

        encodeString.append((char) (result));
        return encodeString.toString();
    }

    /**
     * 数据解码
     *
     * @param strLine 需要解码的字符串
     * @param cmkey   解码key
     * @return 解码后的字符串
     */
    private static List<Integer> decodeNumberByMapbar(String strLine, int cmkey) {
        int index = 0;
        List<Integer> vRets = new ArrayList<Integer>();

        int strLen = strLine.length();
        if (strLen <= 0) return vRets;

        while (index < strLen) {
            int b = 0;
            int shift = 0;
            int result = 0;

            int currkey = cmkey;
            do {
                b = ((int) strLine.charAt(index++)) - 63;
                int currValue = (((b >> 1) ^ (currkey++)) & 0x1f);
                result |= currValue << shift;
                shift += 5;
            } while ((b & 0x1) == 0);

            result = ((result & 0x01) == 0x01) ? ~(result >> 1) : (result >> 1);
            vRets.add(result);
        }
        return vRets;
    }

    /**
     * google API tools for reference only
     *
     * @param input 需要编码的数字
     * @return 编码后的字符串
     */
    public static String encodeNumbeReferencer(int input) {
        StringBuffer encodeString = new StringBuffer();
        long num = input & 0xFFFFFFFFL;
        while (num >= 0x20) {
            long nextValue = (0x20 | (num & 0x1f)) + 63;
            encodeString.append((char) (nextValue));
            num >>= 5;
        }
        num += 63;
        encodeString.append((char) (num));
        return encodeString.toString();
    }

    /**
     * google API tools for reference only
     *
     * @param strLine 需要解码的字符串
     * @return 解码后的字符
     */
    public static List<Integer> decodeNumberReferencer(String strLine) {
        int index = 0;
        List<Integer> list = new ArrayList<Integer>();
        while (index < strLine.length()) {
            int b = 0;
            int shift = 0;
            int result = 0;

            do {
                b = ((int) strLine.charAt(index++)) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);

            int iValue = result;
            list.add(iValue);
        }
        return list;
    }

    /**
     * 编码数据,此算法来源于google
     *
     * @param num 需要编码的数字
     * @return 编码后的字符
     */
    public static String encodeSignedNumberByGoogle(int num) {
        int sgn_num = num << 1;
        if (num < 0) {
            // 如果是负数就取反
            sgn_num = ~(sgn_num);
        }
        return (encodeNumberByGoogle(sgn_num));
    }

    /**
     * 数据编码，此算法来源于google
     *
     * @param num 需要编码的数字
     * @return 编码后的字符
     */
    private static String encodeNumberByGoogle(int num) {
        StringBuffer encodeString = new StringBuffer();
        while (num >= 0x20) {
            int nextValue = (0x20 | (num & 0x1f)) + 63;
            encodeString.append((char) (nextValue));
            num >>= 5;
        }
        num += 63;
        encodeString.append((char) (num));
        return encodeString.toString();
    }

    /**
     * 解编码
     *
     * @param strLine 被解的编码字符串
     * @return 解码后的list<Integer>
     */
    public static List<Integer> decodeNumberByGoogle(String strLine) {
        int index = 0;
        List<Integer> list = new ArrayList<Integer>();
        int len = strLine.length();
        while (index < len) {
            int b = 0;
            int shift = 0;
            int result = 0;

            do {
                b = ((int) strLine.charAt(index++)) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int iValue = ((result & 0x01) == 0x01) ? ~(result >> 1) : (result >> 1);
            list.add(iValue);
        }
        return list;
    }

    public static void main(String[] args) {
        String strEnc = "";
        String strEnc4 = "";
        for (int i = -512; i <= 512; i++) {
            int iValue = i;
            strEnc += encodeNumber(iValue);
            strEnc4 += encodeSignedNumberByGoogle(iValue);
        }
        System.out.println("[MAPBAR][Length][" + strEnc.length() + "]" + strEnc);
        System.out.println("[GOOGLE][Length][" + strEnc4.length() + "]" + strEnc4);
        System.out.println("[MAPBAR]" + decodeNumber(strEnc));
        System.out.println("[GOOGLE]" + decodeNumberByGoogle(strEnc4));
    }
}
