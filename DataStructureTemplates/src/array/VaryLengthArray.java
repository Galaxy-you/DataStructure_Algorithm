package array;

import java.util.*;
import java.util.stream.Collectors;

/**
 * author: Galaxy Violet
 * date: 2025/11/17, 14:40
 */

/* 简要解释
   ArrayList 是 Java 集合框架中的动态数组实现，底层基于数组，
   但可以自动扩容。与普通数组不同，ArrayList 的大小可以动态增长和收缩。
   大小可变，自动扩容（默认容量10，扩容为原来的1.5倍）
    只能存储对象类型（使用泛型）
    非线程安全
 */

/* 使用场景
    需要频繁访问元素（通过索引）
    不确定数据量大小
    需要在末尾频繁添加/删除元素
    不需要线程安全
 */

/* 常用方法
    add(E)	                末尾添加	        O(1) 均摊
    add(index, E)	        指定位置插入	    O(n)
    get(index)	            获取元素	        O(1)
    set(index, E)	        更新元素	        O(1)
    remove(index)	        删除元素	        O(n)
    size()	                获取大小	        O(1)
    contains(E)	            是否包含	        O(n)
    indexOf(E)	            查找索引	        O(n)
    Collections.sort(list)	排序	            O(n log n)
    Collections.reverse(list)	反转	        O(n)
    Collections.binarySearch(list, key)	二分查找	O(log n)

 */
public class VaryLengthArray {
    // ========== 创建和初始化 ==========

    // 1. 基本创建方式
    public static void createArrayList() {
        // 方式1: 默认构造（初始容量10）
        ArrayList<Integer> list1 = new ArrayList<>();

        // 方式2: 指定初始容量（如果知道大概大小，可以减少扩容次数）
        ArrayList<Integer> list2 = new ArrayList<>(100);

        // 方式3: 从其他集合创建
        ArrayList<Integer> list3 = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));

        // 方式4: 使用 Collections.nCopies（创建重复元素）
        ArrayList<Integer> list4 = new ArrayList<>(Collections.nCopies(5, 0)); // [0,0,0,0,0]
    }

    // 2. 从数组创建
    public static ArrayList<Integer> fromArray(int[] arr) {
        ArrayList<Integer> list = new ArrayList<>();
        for (int num : arr) {
            list.add(num);
        }
        return list;

        // 或者使用 Stream（Java 8+）
        // return Arrays.stream(arr).boxed().collect(Collectors.toCollection(ArrayList::new));
    }

    // 3. 转换为数组
    public static int[] toArray(ArrayList<Integer> list) {
        // 方式1: 手动转换
        int[] arr = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            arr[i] = list.get(i);
        }


        // 方式2: Stream（推荐）
        // return list.stream().mapToInt(Integer::intValue).toArray();

        // 方式3：使用list的toArray()方法
        Object[] arr1 = list.toArray(); // 不过这是一个对象数组
        Integer[] arr2 = list.toArray(new Integer[0]);  // 这中便于操作
        return arr;
    }

    // ========== CRUD 操作 ==========

    // Create - 添加元素
    public static void addExamples() {
        ArrayList<Integer> list = new ArrayList<>();

        // 1. 末尾添加（最常用）⭐
        list.add(10);           // [10]
        list.add(20);           // [10, 20]

        // 2. 指定位置插入
        list.add(1, 15);        // [10, 15, 20]

        // 3. 批量添加
        list.addAll(Arrays.asList(30, 40, 50)); // [10, 15, 20, 30, 40, 50]

        // 4. 在指定位置批量添加
        list.addAll(0, Arrays.asList(1, 2)); // [1, 2, 10, 15, 20, 30, 40, 50]
    }

    // Read - 读取元素
    public static void readExamples() {
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(10, 20, 30, 40, 50));

        // 1. 通过索引获取
        int first = list.get(0);        // 10
        int last = list.get(list.size() - 1); // 50

        // 2. 获取大小
        int size = list.size();         // 5

        // 3. 检查是否为空
        boolean isEmpty = list.isEmpty(); // false

        // 4. 查找元素索引
        int index1 = list.indexOf(30);   // 2（第一次出现）
        int index2 = list.lastIndexOf(30); // 2（最后一次出现）
        int index3 = list.indexOf(99);   // -1（不存在）

        // 5. 检查是否包含
        boolean contains = list.contains(30); // true
    }

    // Update - 更新元素
    public static void updateExamples() {
        List<Integer> list = new ArrayList<>(Arrays.asList(10, 20, 30, 40, 50));

        // 替换指定位置的元素
        list.set(2, 99);  // [10, 20, 99, 40, 50]

        // 批量替换（Java 8+）
        list.replaceAll(x -> x * 2); // [20, 40, 198, 80, 100]
    }

    // Delete - 删除元素
    public static void deleteExamples() {
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(10, 20, 30, 40, 50));

        // 1. 按索引删除（返回被删除的元素）
        int removed = list.remove(2);  // removed = 30, list = [10, 20, 40, 50]

        // 2. 按对象删除（删除第一个匹配的）,这里是int类型的list，所以要将int改为Integer作为对象进行删除
        list.remove(Integer.valueOf(20)); // [10, 40, 50]

        // 3. 删除所有匹配的元素（Java 8+）
        list.removeIf(x -> x > 30);    // [10]

        // 4. 清空列表
        list.clear();                   // []

        // 5. 删除指定范围 [fromIndex, toIndex)
        ArrayList<Integer> list2 = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        list2.subList(1, 3).clear();   // 删除索引1和2，结果：[1, 4, 5]
    }

    // ========== 常用方法/技巧 ==========

    // 1. 排序
    public static void sortExamples() {
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(5, 2, 8, 1, 9));

        // 升序排序
        Collections.sort(list);  // [1, 2, 5, 8, 9]

        // 降序排序
        Collections.sort(list, Collections.reverseOrder()); // [9, 8, 5, 2, 1]

        // 自定义排序（Java 8+）
        list.sort((a, b) -> a - b);  // 升序
        list.sort((a, b) -> b - a);  // 降序

        // 绝对值排序
        list.sort(Comparator.comparingInt(Math::abs));
    }

    // 2. 反转
    public static void reverse(ArrayList<Integer> list) {
        Collections.reverse(list);
    }

    // 3. 去重（保持顺序）
    public static ArrayList<Integer> removeDuplicates(ArrayList<Integer> list) {
        // 方式1: 使用 LinkedHashSet
        // LinkedHashSet的构造函数可以接受一个Collection类型的参数
        // ArrayList的构造函数也可以接受一个Collection类型的参数，LinkedHashSet有实现Collection接口
        return new ArrayList<>(new LinkedHashSet<>(list));

        // 方式2: Stream（Java 8+）
        // return list.stream().distinct().collect(Collectors.toCollection(ArrayList::new));
    }


    // 4. 填充
    public static void fill(ArrayList<Integer> list, int value) {
        Collections.fill(list, value); // 所有元素替换为 value
    }

    // 5. 旋转
    public static void rotate(ArrayList<Integer> list, int distance) {
        Collections.rotate(list, distance); // 向右旋转 distance 步
    }


    // ========== 遍历方式 ==========

    public static void traverseExamples(ArrayList<Integer> list) {
        // 方式1: 普通 for 循环
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }

        // 方式2: 增强 for 循环（推荐）⭐
        for (int num : list) {
            System.out.println(num);
        }

        // 方式3: 迭代器
        Iterator<Integer> it = list.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }

    }

    // ========== 安全删除（遍历时删除）==========

    public static void safeRemove(ArrayList<Integer> list) {
        // ❌ 错误：ConcurrentModificationException
        // for (int num : list) {
        //     if (num > 50) list.remove(Integer.valueOf(num));
        // }
        // 增强for循环底层使用迭代器遍历，不能进行修改

        // 正序遍历
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i) > 50){
                list.remove(i);
                // 这里会出问题，remove当前元素后，后面的元素又会自动往前移动一位，然后同时i++
                // 所以会漏掉一个数
                // 但是如果加上 i--还是可以正确实现的
                i--;
            }
        }


        // ✅ 正确方式1: 使用迭代器
        Iterator<Integer> it = list.iterator();
        while (it.hasNext()) {
            if (it.next() > 50) {
                it.remove();
            }
        }

        // ✅ 正确方式2: removeIf（推荐）,参数为Lambda表达式
        list.removeIf(num -> num > 50);

        // ✅ 正确方式3: 倒序遍历
        for (int i = list.size() - 1; i >= 0; i--) {
            if (list.get(i) > 50) {
                list.remove(i);
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Integer> arrayList = new ArrayList<>();

        System.out.println("input number(the symbol of end -1):");
        while (true){
            int num = scanner.nextInt();
            if(num == -1)break;
            arrayList.add(num);
        }

        System.out.println("input = " + arrayList);

    }
}
