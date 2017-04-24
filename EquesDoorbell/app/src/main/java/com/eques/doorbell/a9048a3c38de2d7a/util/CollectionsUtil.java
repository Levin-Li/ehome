package com.eques.doorbell.a9048a3c38de2d7a.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CollectionsUtil {

    public static <K, V> List<V> mapConvertToList(Map<K, V> map) {
        List<V> list = new ArrayList<V>();
        for (V value : map.values()) {
            list.add(value);
        }
        return list;
    }

    public static <K> boolean isEquals(Collection<K> collection1,
                                       Collection<K> collection2) {

        if (collection1 == null && collection2 == null) {
            return true;
        }
        if (collection1 == null || collection2 == null || collection1.size() != collection2.size()) {
            return false;
        }
        boolean result = true;
        Iterator<K> ite2 = collection2.iterator();
        while (ite2.hasNext()) {
            if (!collection1.contains(ite2.next())) {
                result = false;
                break;
            }
        }
        return result;
    }
}
