package sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class RadixSort {
    public static void main(String[] args) {
        int[] arr = new int[10];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int)(Math.random() * 10000);
        }
        System.out.println("before sort: " + Arrays.toString(arr));

        radixSort(arr);

        System.out.println("after sort: " + Arrays.toString(arr));
    }


    public static void radixSort(int[] array){
        // 数组中的最大数（不考虑负数）
        int max = array[0];
        for(int i = 1; i < array.length; i++){
            max = array[i] > max ? array[i] : max;
        }

        int digitCount = 0; // 最大数有多少位
        while (max != 0){
            max /= 10;
            digitCount++;
        }
//         int digitCount = (max + "").length();    使用字符串快速获得数字的位数

        // 使用二维数组，包含10个一维数组（桶），但是一维数组的大小不确定，且每个桶要频繁的删除
        // 所以二维数组选择ArrayList, 一维数组徐选择 LinkedList
        ArrayList<LinkedList> buckets = new ArrayList<>(10);
        // 初始化10个空的内部 LinkedList
        for (int i = 0; i < 10; i++) {
            buckets.add(new LinkedList());
        }

        // 循环处理每一位数
        for(int count = 1; count <= digitCount; count++){
            // 循环处理每一位数：取出倒数第count位，然后对该位进行排序
            for(int elem : array){
                // 取出对应位的数字,即该数字应该放入的桶号
                int digitOfElement = (elem / (int)Math.pow(10,count - 1)) % 10;

                // 放入到对应的桶中
                buckets.get(digitOfElement).add(elem);
            }

            // 遍历原数组的下标
            int index = 0;

            // 按照桶的顺序，从一维数组中依次取出数据放入原数组array
            for (int k = 0; k <  buckets.size(); k++) {
                // 从每个桶（一维数组）中取出数据放入到原数组
                LinkedList<Integer> bucket = buckets.get(k);
                while (!bucket.isEmpty()){
                    // ArrayList 的 remove(index)方法会返回被移除的第一个元素，并从ArrayList中删除这个元素，同时自动将所有的后续元素向前移动一位，同时size自减
                    // LinkedList的 remove()或者removeFirst()方法会移除并返回列表的第一个元素
                    array[index++] = bucket.remove();
                }
            }
        }
    }
}
