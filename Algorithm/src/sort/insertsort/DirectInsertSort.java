package sort.insertsort;

import java.util.Arrays;

/**
 * author: galaxy-violet
 * date: 2025/10/16,14:42
 */
public class DirectInsertSort {
    public static void main(String[] args) {
        int[] arr = new int[10];
        for(int i = 0; i < arr.length; i++){
            arr[i] = (int)(10 * Math.random());
        }

        System.out.println("before sort: " + Arrays.toString(arr));

        directInsertSort(arr);

        System.out.println("after sort: " + Arrays.toString(arr));


    }

    public static void directInsertSort(int[] array){

        int size = array.length;
        // 在for循环中建议不要进行变量的定义，有一些开销。
        int insertValue;
        int insertIndex;

        // 直接插入排序
        // 外层循环：将未排序的序列的第一个元素插入已排序的序列中，使得已排序序列仍然有序
        // 第一个元素已经有序了，所以外层循环次数为 array.length - 1
        for(int i = 1; i < size; i++){
            insertValue = array[i]; // 待插入有序序列的值
            insertIndex = i - 1;    // insertValue 插入到有序序列中的下标是 insertIndex + 1,insertIndex 的范围是[-1,i -1]，初始化为 i - 1

            // 内层循环确定insertIndex,insertValue应该插入位置为insertIndex + 1
            while(insertIndex >= 0 && insertValue < array[insertIndex]){
                // 由于插入，所以需要移动，这里使用循环右移
                array[insertIndex + 1] = array[insertIndex];
                insertIndex--;
            }

            // 插入insertValue
            // 这里可以判断是否需要判断，只有insertIndex + 1 != i 时才执行插入
            array[insertIndex + 1] = insertValue;
        }
    }
}
