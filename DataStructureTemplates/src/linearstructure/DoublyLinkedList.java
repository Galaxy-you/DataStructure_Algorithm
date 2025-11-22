package linearstructure;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * author: Galaxy Violet
 * date: 2025/11/17, 21:39
 */

/* 简要解释
    双向链表的每个节点有两个指针：一个指向前一个节点（prev），一个指向后一个节点（next）。
    与单链表相比，可以双向遍历。
 */
public class DoublyLinkedList {
    public static void main(String[] args) {
        LinkedList<Integer> list = new LinkedList<>();

        // ========== 添加操作 ==========

        // 尾部添加
        list.add(1);           // [1]
        list.addLast(2);       // [1, 2]

        // 头部添加
        list.addFirst(0);      // [0, 1, 2]

        // 指定位置添加
        list.add(1, 99);       // [0, 99, 1, 2]


        // ========== 获取操作 ==========

        // 获取头尾
        int first = list.getFirst();  // 0
        int last = list.getLast();    // 2

        // 获取指定位置
        int val = list.get(2);        // 1

        // 大小
        int size = list.size();       // 4

        // 是否为空
        boolean empty = list.isEmpty(); // false


        // ========== 删除操作 ==========

        // 删除头尾
        list.removeFirst();    // [99, 1, 2]
        list.removeLast();     // [99, 1]

        // 删除指定位置
        list.remove(0);        // [1]

        // 删除指定值（第一个匹配）
        list.add(1);           // [1, 1]
        list.remove(Integer.valueOf(1)); // [1]（删除第一个1）


        // ========== 遍历操作 ==========
        // 不要使用for-i + list.get(i)进行遍历
        // 使用iterator或者增强for循环进行遍历

        list.clear();
        list.addAll(Arrays.asList(1, 2, 3, 4, 5));

        // 正向遍历
        for (int num : list) {
            System.out.print(num + " "); // 1 2 3 4 5
        }
        System.out.println();

        Iterator<Integer> iterator = list.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next() + " ");
        }
        System.out.println();

        // 反向遍历（双向链表特有）⭐
        Iterator<Integer> it = list.descendingIterator();
        while (it.hasNext()) {
            System.out.print(it.next() + " "); // 5 4 3 2 1
        }
        System.out.println();


        // ========== 其他常用方法 ==========

        // 查找 参数均为Object
        int index = list.indexOf(3);      // 2
        boolean contains = list.contains(3); // true

        // 查看但不删除
        int peekFirst = list.peekFirst(); // 1（不删除）
        int peekLast = list.peekLast();   // 5（不删除）

        // 查看并删除（类似队列操作）
        int pollFirst = list.pollFirst(); // 1（删除并返回）
        int pollLast = list.pollLast();   // 5（删除并返回）

        System.out.println(list); // [2, 3, 4]
    }
}
/*
add(E)	尾部添加	O(1)
addFirst(E)	头部添加	O(1)
addLast(E)	尾部添加	O(1)
add(index, E)	指定位置添加	O(n)
 */

/*
如果参数是int --> 删除对应索引对应的元素
如果参数是Object --> 删除对应值
removeFirst()	删除头部	O(1)
removeLast()	删除尾部	O(1)
remove(index)	删除指定位置	O(n)
remove(Object)	删除指定值	O(n)
pollFirst()	删除头部（返回null而不抛异常）	O(1)
pollLast()	删除尾部（返回null而不抛异常）	O(1)
 */

/*
getFirst()	获取头部	O(1)
getLast()	获取尾部	O(1)
get(index)	获取指定位置	O(n)
peekFirst()	查看头部（不删除）	O(1)
peekLast()	查看尾部（不删除）	O(1)
indexOf(Object)	查找索引	O(n)
contains(Object)	是否包含	O(n)
 */