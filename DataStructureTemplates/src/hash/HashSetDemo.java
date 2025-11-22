package hash;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

/**
 * author: Galaxy Violet
 * date: 2025/11/21, 20:55
 */
public class HashSetDemo {
    public static void main(String[] args) {
        /*
            HashSet--去重集合
         */
        HashSet<Integer> set = new HashSet<>();

        // 1、添加元素--add（HashMap的添加方法是put(K,V）
        set.add(1);
        set.add(2);
        set.add(3);
        set.add(1); // 重复元素不会添加

        // 2、删除元素--remove
        set.remove(2);

        // 3、检查是否存在--contains
        boolean has = set.contains(1);

        // 4、大小--size()
        int size = set.size();

        // 5、清空
        set.clear();

        // 6、集合运算
        // Arrays.asList() 能把将一组数据转成List<T>
        HashSet<Integer> set1 = new HashSet<>(Arrays.asList(1, 2, 3, 4));
        HashSet<Integer> set2 = new HashSet<>(Arrays.asList(3, 4, 5, 6));

        // 并集 union
        // 通过addAll(list)方法实现
        HashSet<Integer> union = new HashSet<>(set1);
        union.addAll(set2);

        // 交集 intersection  n.交叉，相交，十字路口，交叉路口
        // 通过retainAll(list)方法实现        retain v.保留，保持，保存
        HashSet<Integer> intersection = new HashSet<>(set1);
        intersection.retainAll(set2);

        // 差集 difference
        // 通过removeAll()方式实现
        HashSet<Integer> difference = new HashSet<>(set1);
        difference.removeAll(set2);

        // 集合的遍历
        for(int num: set){
            System.out.println(num);
        }


        /*
            LinkedHashSet--保持插入顺序的集合
         */

        /*
            TreeSet--有序集合（底层基于红黑树实现），可以自动排序
         */
    }

    // HashSet的常用模式



}
