package com.cxp.personalmanage.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.Map;

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

    /**
     * json字符串转成list
     *
     * @param gsonString
     * @param cls
     * @return
     */
    public static <T> List<T> gsonToList(String gsonString, Class<T> cls) {
        List<T> list = null;
        if (gson != null) {
            //根据泛型返回解析指定的类型,TypeToken<List<T>>{}.getType()获取返回类型
            list = gson.fromJson(gsonString, new TypeToken<List<T>>() {
            }.getType());
        }
        return list;
    }

    /**
     * json字符串转成list中有map的
     *
     * @param gsonString
     * @return
     */
    public static <T> List<Map<String, T>> gsonToListMaps(String gsonString) {
        List<Map<String, T>> list = null;
        if (gson != null) {
            list = gson.fromJson(gsonString,
                    new TypeToken<List<Map<String, T>>>() {
                    }.getType());
        }
        return list;
    }

    /**
     * json字符串转成map的
     *
     * @param gsonString
     * @return
     */
    public static <T> Map<String, T> gsonToMaps(String gsonString) {
        Map<String, T> map = null;
        if (gson != null) {
            map = gson.fromJson(gsonString, new TypeToken<Map<String, T>>() {
            }.getType());
        }
        return map;
    }
}
