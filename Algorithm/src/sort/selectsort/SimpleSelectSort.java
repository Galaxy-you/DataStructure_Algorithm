package sort.selectsort;


import java.util.Arrays;

/**
 * author: galaxy-violet
 * date: 2025/10/16,14:15
 */
public class    SimpleSelectSort {
    public static void main(String[] args) {
        int[] arr = new int[10];
        for(int i = 0; i < arr.length; i++){
            arr[i] = (int)(10 * Math.random());
        }

        System.out.println("before sort: " + Arrays.toString(arr));

        simpleSelectSort(arr);

        System.out.println("after sort: " + Arrays.toString(arr));



    }

    public static void simpleSelectSort(int[] array){
        int size = array.length;

        // 外层循环，每次从未排序的部分中选择一个最小值与已排序的末尾交换
        // 需要进行 size - 1 次，最后剩下的一个是最大值
        for (int i = 0; i < size - 1; i++) {

            // 内层循环找到未排序部分的最小值
            int minIndex = i;
            int min = array[i];
            for(int j = i + 1; j < size; j++){
                if(array[j] < min){
                    minIndex = j;
                    min = array[j];
                }
            }
            array[minIndex] = array[i];
            array[i] = min;
        }
    }
}
