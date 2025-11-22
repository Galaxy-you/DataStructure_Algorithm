package search;

import java.util.Arrays;

public class InsertSearch {
    public static int count = 0;

    public static void main(String[] args) {
        int[] array = new int[100];

        for (int i = 0; i < 100; i++) {
            array[i] = 2 * i;
        }

        // implicit adj.含蓄的，不直接言明的，成为一部分的，内含的
        // warning: implicit call to 'toString()' on array 'array'
        // 数组没有重写toString()方法，所以输出的是getClass().getName()} + '@' + Integer.toHexString(hashCode())
        System.out.println(Arrays.toString(array));

        int findIndex = insertSearch(array, 168, 0, array.length - 1);

        System.out.println(findIndex);

        System.out.println("search count: " + count);

    }

    public static int insertSearch(int[] array, int findValue, int left, int right){
        count++;

        // findValue < array[0] || findValue > array[array.length - 1]
        // 这两个条件必须要判断， 否则可能会导致计算出来的mid 触发数组越界错误
        if(left > right || findValue < array[0] || findValue > array[array.length - 1]){
            return -1;
        }
        // 下面的使用的是整数除，要注意顺序，应该先乘在去除，否则整数除可能会得到的结果为0
        // int mid = left + (findValue - array[left]) / (array[right] - array[left]) * (right - left);
        // 自适应的写法
        int mid = left + (right - left) * (findValue - array[left]) / (array[right] - array[left]) ;
        int midValue = array[mid];

        if(midValue == findValue){
            return mid;
        }else if(midValue > findValue){
            return insertSearch(array, findValue, left, mid - 1);
        }else {
            return insertSearch(array, findValue, mid + 1, right);
        }
    }
}
