package com.cxp.personalmanage.utils;

/**
 * @author : cheng
 * @date : 2019-11-09 11:12
 */
public class StringUtil {

    /**
     * 页面中的$nbsp;(ASCII码160)与空格(ASCII码32)有区别
     *
     * @param str
     * @return
     */
    public static String conveterStr(String str) {
        if (str == null) {
            return "";
        }
        str = str.replaceAll("\\u00A0+", "").trim();
        if ("null".equals(str)) {
            return "";
        }
        return str;
    }

    public static void main(String[] args) {
        String str = "深圳-光明新区  ";
        String str1 = conveterStr(str);
        System.out.println(str1);
    }
}

