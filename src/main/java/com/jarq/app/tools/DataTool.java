package com.jarq.app.tools;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.List;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.Map.Entry;

public class DataTool {

    public static <T> Boolean checkIfElementInArray(T[] array, T element) {
        for(T item : array){
            if(item.equals(element)){
                return true;
            }
        }
        return false;
    }

    public static <K,V extends Comparable<V>> LinkedHashMap<K,V> sortMapByValue(Map<K,V> map) {
        return map.entrySet().stream().sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
    }

    public static <K extends Comparable<K>,V> LinkedHashMap<K,V> sortMapByKey(Map <K,V> map){
        return map.entrySet().stream().sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
    }

    public static <K, V> K getKeyByValue(Map<K, V> map, V value) {
        for (Entry<K, V> entry : map.entrySet()) {
            if (Objects.equals(value, entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }

    public static String getMultipliedString(String string, int multiplier) {
        return new String(new char[multiplier]).replace("\0", string);
    }


    public static String getShortText(int cutIndex, String text) {
        if (text.length() > cutIndex) {
            return text.substring(0, cutIndex) + "...";
        }
        return text;
    }

    public static String removeWhitespacesFromString(String string) {
        String regex = "\\s+";
        String delimiter = " ";
        List<String> listToTrim = Arrays.asList(string.split(regex));
        listToTrim.stream().map(String :: trim);
        return String.join(delimiter, listToTrim);
    }
}
