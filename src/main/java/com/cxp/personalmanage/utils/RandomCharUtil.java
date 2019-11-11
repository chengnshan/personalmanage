package com.cxp.personalmanage.utils;

import java.util.Random;

/**
 * @author : cheng
 * @date : 2019-11-09 09:03
 */
public class RandomCharUtil {

    private static final String str = "0123456789abcdefghijklmnopqrstuvwxwzABCDEFGHIJKLMNOPQRSTUVWXWZ";

    private static final Random random = new Random();

    public static String getRandomChar(Integer length){
        if (length == null){
            length = 6;
        }
        StringBuffer flag = new StringBuffer();
        for (int j = 0; j < length; j++){
            flag.append(str.charAt(random.nextInt(62)));
        }
        return flag.toString();
    }

    public static void main(String[] args) {
        System.out.println(getRandomChar(8));
    }
}
