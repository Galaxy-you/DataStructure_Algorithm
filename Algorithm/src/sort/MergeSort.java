package sort;

import java.util.Arrays;

public class MergeSort {
    public static void main(String[] args) {
        int[] arr = new int[10];
        int[] temp = new int[10];

        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int)(Math.random() * 10);
        }
        System.out.println("before sort: " + Arrays.toString(arr));

        mergeSort(arr,0,arr.length - 1,temp);

        System.out.println("after sort: " + Arrays.toString(arr));
    }

    /**
     *
     * @param array 原始数组
     * @param left  左边有序序列的头索引
     * @param mid   中间索引（右边有序系列的尾索引）
     * @param right 右边有序序列的尾索引
     * @param temp  中转数组，存储两个数组merge后的有序数组，然后再copy到原来的数组array[left~right]中去
     */
    // 合并函数:将两个原本有序的数组合并成一个新的有序数组（先暂存到temp中，然后复制回array）
    public static void merge(int[] array,int left, int mid, int right, int[] temp){
        int i = left;
        int j = mid + 1;
        int t = 0;  // temp数组的索引

        // 选择左半部分和右半部分中较小的值加入temp
        while (i <= mid && j <= right){
            if(array[i] <= array[j]){
                temp[t++] = array[i++];
            }
            else {
                temp[t++] = array[j++];
            }
        }

        // 将剩余部分加入temp
        while (i <= mid){
            temp[t++] = array[i++];
        }
        while ((j <= right)){
            temp[t++] = array[j++];
        }

        // 将temp数复制到原数组array
        for(i = 0; i < t; i++){
            array[left + i] = temp[i];
        }
    }

    public static void mergeSort(int[] array,int left, int right, int[] temp){
        if(left < right){
            // 中间索引（右边有序系列的尾索引）
            int mid = (left + right) / 2;

            // 向左递归进行分解
            mergeSort(array, left, mid, temp);  // 将数组对半分
            // 向右递归进行分解
            mergeSort(array, mid + 1, right, temp);
            // 合并
            merge(array, left, mid, right, temp);
        }
    }
}
