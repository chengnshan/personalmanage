package com.cxp.personalmanage.utils;

/**
 * @author : cheng
 * @date : 2019-11-09 11:12
 */
public class StringUtil {

    public static String conveterStr(String str){
        if (str == null){
            return "";
        }
        str = str.trim();
        if ("null".equals(str)){
            return "";
        }
        return str;
    }
}
