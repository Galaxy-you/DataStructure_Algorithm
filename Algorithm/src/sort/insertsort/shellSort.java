package sort.insertsort;

import java.util.Arrays;

/**
 * author: galaxy-violet
 * date: 2025/10/16,15:45
 */
public class shellSort {
    public static void main(String[] args) {
        int[] arr = new int[10];
        for(int i = 0; i < arr.length; i++){
            arr[i] = (int)(10 * Math.random());
        }
        System.out.println("before sort: " + Arrays.toString(arr));

        shellSortMove(arr);

        System.out.println("after sort: " + Arrays.toString(arr));

    }

    // 希尔排序，“插入”操作通过直接交换相邻元素完成，效率较低
    public static void shellSortSwap(int[] array){
        int temp;

        // gap 即为增量,不断缩小
        for(int gap = array.length / 2; gap > 0; gap /= 2){
            // i 用来遍历数组
            for(int i = gap; i < array.length; i++){
                for(int j = i -gap; j >= 0; j -= gap){
                    if(array[j] > array[j + gap]){
                        temp = array[j];
                        array[j] = array[j + gap];
                        array[j + gap] = temp;
                    }
                }
            }
        }
    }


    // 希尔排序，“插入”操作通过循环右移实现，效率较高
    public static void shellSortMove(int[] array){
        // gap 为当前分组间隔
        for(int gap = array.length / 2; gap > 0; gap /= 2){

            // 从array[gap]开始对所在的组进行直接插入排序，注意这里的array[gap]为每一组的第二个元素（因为只有一个元素是有序的，所以从第二个元素开始进行插入排序）
            // 分别对每组进行直接插入排序
            for(int i = gap; i < array.length; i++){
                int insertIndex = i;    // insertIndex 是 insertValue 插入到数组中的下标
                int insertValue = array[insertIndex];

                // 当前值小于组内的前一个值 => 进行插入排序
                if(insertValue < array[insertIndex - gap]){
                    while(insertIndex - gap  >= 0 && insertValue < array[insertIndex - gap]){
                        array[insertIndex] = array[insertIndex - gap];  // 循环右移为之后的插入进行准备
                        insertIndex -= gap;
                    }
                    array[insertIndex] = insertValue;
                }
            }
        }
    }
}


