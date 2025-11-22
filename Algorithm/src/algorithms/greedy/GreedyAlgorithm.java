package algorithms.greedy;

import java.util.*;

/**
 * author: Galaxy Violet
 * date: 2025/11/16, 20:45
 */
public class GreedyAlgorithm {
    public static void main(String[] args) {
        // 需要覆盖的地区-使用HashSet实现
        Set<String> allAreas = new HashSet<>(Arrays.asList(
                "北京", "上海", "天津", "广州", "深圳", "成都", "杭州", "大连"
        ));

        // 广播电台, 这里覆盖的地区仍然使用HashSet,无序存放不重复的元素
        Map<String, HashSet<String>> stations = new HashMap<>();
        stations.put("K1", new HashSet<String>(Arrays.asList("北京", "上海", "天津")));
        stations.put("K2", new HashSet<>(Arrays.asList("广州", "北京", "深圳")));
        stations.put("K3", new HashSet<>(Arrays.asList("成都", "上海", "杭州")));
        stations.put("K4", new HashSet<>(Arrays.asList("上海", "天津")));
        stations.put("K5", new HashSet<>(Arrays.asList("杭州", "大连")));

        Set<String> selectedStations = findMinimumStations(allAreas, stations);
        System.out.println("选择的广播站点：");
        for(String station : selectedStations){
            System.out.print(station);
            System.out.println("\t覆盖地区：" + stations.get(station));
        }

    }

    // 函数的参数中也需要写明相关泛型
    public static Set<String> findMinimumStations(Set<String> allAreas, Map<String, HashSet<String>> stations){
        HashSet<String> areas = new HashSet<>(allAreas);    // 需要覆盖的区域,备份一份防止对参数造成修改
        Set<String> selectedStations = new HashSet<>();     // 选择的广播站

        while (!areas.isEmpty()){
            String maxNumStation = null;
            int maxAreasNum = 0;

            //
            for(Map.Entry<String, HashSet<String>> station : stations.entrySet()){
                String stationName = station.getKey();
                Set<String> coveredAreas = station.getValue();

                // retainAll() --> removes from this set all of its elements that are not contained in the specified collection
                // 会改变当前的set，所以需要一个tempSet放置改变当前广播站覆盖的地区集合
                Set<String> tempAreas = new HashSet<>(coveredAreas);
                tempAreas.retainAll(areas); // intersection of coveredAreas and areas

                // greedy algorithm
                //
                if(tempAreas.size() > maxAreasNum){
                    maxAreasNum = tempAreas.size();
                    maxNumStation = stationName;
                }
            }

            if (maxNumStation != null){
                selectedStations.add(maxNumStation);
                // 从areas中去除已选择广播站点所覆盖的地区
                areas.removeAll(stations.get(maxNumStation));
            }
        }
        return selectedStations;
    }
}



/*  小结一下有关于hash的集合
一、Map（键值对映射）
    1、HashMap implements/extends AbstractMap, Map, Cloneable, Serializable
        是最常用的Map实现，允许null键和null值，性能高，线程不安全
    2、Hashtable implements/extends Dictionary, Map, Cloneable, Serializable
        遗留类，线程安全，现在已经被 ConcurrentHashMap替代
    3、LinkedHashMap extends HashMap
        维护元素的插入顺序或访问顺序（可配置）。常用于实现 LRU 缓存。
        非线程安全	保持插入顺序（或访问顺序

二、Set(不重复元素)
    1、HashSet implements/extends AbstractSet, Set, Cloneable, Serializable
        最常用的 Set 实现。底层是基于 HashMap 实现的（只使用了 HashMap 的键，值是一个固定的占位符对象）。
        允许 null 元素。非线程安全无序（迭代顺序不保证）
    2、LinkedHashSet extends HashSet
        维护元素的插入顺序。底层是基于 LinkedHashMap 实现的。非线程安全保持插入顺序

三、线程安全替代方案 - Concurrent包
     如果需要在多线程环境中使用高性能的 Hash Map，应该使用 java.util.concurrent 包中的类，
     而不是使用 Collections.synchronizedMap() 或 Hashtable。
 */
