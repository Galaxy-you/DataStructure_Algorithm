package hash;

import java.util.*;

/**
 * author: Galaxy Violet
 * date: 2025/11/21, 20:55
 */

/*
    通过哈希函数将key映射到数组索引
 */
public class HashMapDemo {
    public static void main(String[] args) {
        /*
            HashMap基础操作
         */
        HashMap<String, Integer> map = new HashMap<>();

        // 1、添加元素--put(K,V)
        map.put("apple", 1);
        map.put("banana", 1);
        map.put("orange", 1);
        // If the map previously contained a mapping for the key, the old value is replaced.
        map.put("apple", 10);

        // 2、根据key获取元素--get(K)
        int res1 = map.get("apple");
        // 不存在K对应的V，则返回默认的V
        int res2 = map.getOrDefault("watermelon", 0);

        // 3、检查K/V是否存在--containsKey(K)/containsValue(V)
        boolean hasKey = map.containsKey("apple");
        boolean hasValue = map.containsValue(3);

        // 4、删除元素--remove(K) / remove(K, V)
        map.remove("banana");
        map.remove("apple", 10);    // 只有当 K == "apple" && V == 10 时才会remove

        // 5、大小--size()
        int size = map.size();
        boolean isEmpty = map.isEmpty();

        // 6、清空
        map.clear();

        // putIfAbsent(K, V)
        map.putIfAbsent("carrot", 10);

        /*
            HashMap的高级操作
            compute, computeIfAbsent, merge
         */
        // compute: 根据键和旧值计算新值
        // k-当前的键， oldValue-键对应的旧值，newValue-键对应的新值
        // map.compute(key,(k,oldValue) -> newValue)

        // computeIfAbsent: 键不存在时才计算
        // 如果键不存在，才执行计算并插入，如果键已存在，直接返回旧值
        // map.computeIfAbsent(key, k -> newValue)

        // merge: 如果键不存在，直接插入新值；如果键已存在，合并旧值和新值
        // map.merge(key, valueToAdd, (oldValue, valueToAdd) -> mergedValue)
        map.merge("apple", 2, Integer::sum);



        // 遍历键值对
        for(Map.Entry<String, Integer>entry: map.entrySet()){
            System.out.println(entry.getKey() + "  " + entry.getValue());
        }

        /*
            LinkedHashMap--保持插入顺序
         */

        LinkedHashMap<String, Integer> linkedMap = new LinkedHashMap<>();
        linkedMap.put("a", 1);
        linkedMap.put("b", 2);
        linkedMap.put("c", 3);

        // 可以按照插入顺序遍历以及按照访问顺序遍历

        /*
            treeMap--自动按照键排序
         */
    }

    // 常用模式

    // 计数器模式--统计字符串中各个字符出现的个数
    public static void counter(String string){
        HashMap<Character, Integer> counter = new HashMap<>();

//        for(char c: string.toCharArray()){
//            counter.put(c, counter.getOrDefault(c, 0) + 1);
//        }

        // 使用compute简化
        for(char c: string.toCharArray()){
            counter.compute(c, (key, oldValue) -> oldValue == null ? 1 : oldValue + 1);
        }

        System.out.println(counter);
    }

    // 查找模式--两数之和问题
    // 查找数组nums[]中是否存在两个数num1,num2使得 num1 + num2 == target
    // 如果有则返回num1,num2对应的下标数组，否则返回[-1,-1]
    // 这里的hashMap还起到了索引映射的功能：map.get(nums[index]) = index;
    public int[] twoSum(int[] nums, int target){
        // K - nums[index], V - index
        HashMap<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            if(map.containsKey(complement)){
                return new int[]{map.get(complement), i};
            }
            map.put(nums[i], i);

        }
        return new int[]{-1, -1};
    }

    // 分组模式--按字母异位词
    //String[] words = {"eat", "tea", "tan", "ate", "nat", "bat"};
    // 结果: [[eat, tea, ate], [tan, nat], [bat]]
    // 判断两个字符串是否是字母异位词：排序后进行比较即可
    public List<List<String>> groupAnagrams(String[] strs){
        HashMap<String, List<String>> groups = new HashMap<>();

        for(String string: strs){
            // 使用数组的排序方法排序字符串对应的字符数组
            char[] chars = string.toCharArray();
            Arrays.sort(chars);
            String key = new String(chars);

            if(!groups.containsKey(key)){
                groups.put(key, new ArrayList<>());
            }else {
                groups.get(key).add(string);
            }
        }

        // 返回所有分组
        // ArrayList 构造函数接受一个collection
        return new ArrayList<>(groups.values());
    }

}
