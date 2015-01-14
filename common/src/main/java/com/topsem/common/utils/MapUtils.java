package com.topsem.common.utils;

import com.google.common.collect.Lists;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Chen on 14-6-16.
 */
public class MapUtils {

    public static <V> List<Map<String, V>> toLowCaseKey(List<Map<String, V>> maps) {
        List<Map<String, V>> result = Lists.newArrayList();
        for (Map<String, V> map : maps) {
            result.add(toLowCaseKey(map));
        }
        return result;
    }

    public static <V> Map<String, V> toLowCaseKey(Map<String, V> map) {
        Map<String, V> newMap = new HashMap<String, V>();
        for (Map.Entry<String, V> entry : map.entrySet()) {
            newMap.put(entry.getKey().toLowerCase(), entry.getValue());
        }
        return newMap;
    }

//    public static void main(String[] args) {
//        Map<String, User> map = new HashMap<String, User>();
//        map.put("XXAXX", null);
//        map = MapUtils.toLowCaseKey(map);
//        for (Map.Entry<String, User> entry : map.entrySet()) {
//            String key = entry.getKey();
//            System.out.println(key);
//        }
//    }

}
