package search;

import java.util.Arrays;

public class SequenceSearch {
    public static void main(String[] args) {
        int[] arr = new int[10];

        for (int i = 0; i < 10; i++) {
            arr [i] = (int)(Math.random() * 10);
        }

        System.out.println("arr: " + Arrays.toString(arr));
        int index = sequenceSearch(arr, 4);

        if(index == -1){
            System.out.println("Not found 4 in the arr!");
        }else {
            System.out.println("the index of 4 in the arr is " + index);
        }

    }

    // 返回value在数组中第一次出现的下标值，若没有则返回-1
    public static int sequenceSearch(int[] array, int value){
        // 线性查找，发现有相同值，就返回下标
        for (int i = 0; i < array.length; i++) {
            if(array[i] == value){
                return i;
            }
        }
        return -1;
    }
}
