package com.cxp.personalmanage.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author : cheng
 * @date : 2019-11-13 22:16
 */
public class GsonUtil {

    private static Gson gson ;

    static {
        if (gson == null){
            gson = new GsonBuilder().create();
        }
    }

    private GsonUtil() {
    }

    /**
     * 将object对象转成json字符串
     *
     * @param t
     * @return
     */
    public static <T> String gsonString(T t) {
        String gsonString = null;
        if (gson != null) {
            gsonString = gson.toJson(t);
        }
        return gsonString;
    }


    /**
     * 将gsonString转成泛型bean
     *
     * @param gsonString
     * @param cls
     * @return
     */
    public static <T> T gsonToBean(String gsonString, Class<T> cls) {
        T t = null;
        if (gson != null) {
            t = gson.fromJson(gsonString, cls);
        }
        return t;
    }
}
