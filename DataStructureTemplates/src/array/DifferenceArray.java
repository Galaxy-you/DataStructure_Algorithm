package array;

import java.util.Arrays;

/**
 * author: Galaxy Violet
 * date: 2025/11/17, 20:18
 */

/* 简要解释
   差分数组是一种处理区间修改问题的技巧。
   diff[0] = arr[0]
   diff[i] = arr[i] - arr[i - 1] ( i > 0)
   如果需要对数组的某个区间 [left, right] 内的所有元素都加上一个值val，
   差分数组可以将 O(n) 的操作优化到 O(1)。
   diff[left] += val;
   if(right + 1 < n) diff[right + 1] -= val;

    最后通过前缀和还原得到修改后的数组
    arr[0] = diff[0]
    arr[1] = arr[0] + diff[1]
    arr[2] = arr[1] + diff[2]
    arr[n - 1] = arr[n - 2] + diff[n - 1]
 */

/* 使用场景
    多次区间修改，最后查询结果
    区间加减操作
    公交车上下车问题
    航班预订统计
 */
public class DifferenceArray {
    // 构建差分数组
    public static long[] buildDiffArray(int[] arr){
        int length = arr.length;
        long[] diff = new long[length];
        diff[0] = arr[0];

        for (int i = 1; i < length; i++) {
            diff[i] = arr[i] - arr[i - 1];
        }

        return diff;
    }

    // 区间修改：给[left, right] 加上va
    public static void rangeAdd(long[] diff, int left, int right, int val){
        diff[left] += val;
        if(right + 1 < diff.length){
            diff[right + 1] -= val;
        }
    }

    // 通过前缀和还原数组
    public static int[] restore(long[] diff){
        int length = diff.length;
        int[] arr = new int[length];
        arr[0] = (int) diff[0]; // 强制转型

        for(int i = 1; i < length; i++){
            arr[i] = (int)(arr[i - 1] + diff[i]);   // diff[i] 的值可能会比arr[i]的值大，因为diff[i]相当于两个arr[i],要整体强转
        }
        return arr;
    }

    public static void main(String[] args) {
        int[] arr = {1, 3, 5, 7, 9};
        System.out.println("原数组: " + Arrays.toString(arr));

        // 构建差分数组
        long[] diff = buildDiffArray(arr);
        System.out.println("差分数组: " + Arrays.toString(diff));

        // 区间修改
        System.out.println("\n操作: [1, 3] 加 10");
        rangeAdd(diff, 1, 3, 10);
        System.out.println("修改后差分: " + Arrays.toString(diff));

        // 还原
        int[] result = restore(diff);
        System.out.println("还原数组: " + Arrays.toString(result));

        // 再做一次修改
        System.out.println("\n操作: [0, 2] 加 5");
        rangeAdd(diff, 0, 2, 5);
        System.out.println("修改后差分: " + Arrays.toString(diff));

        result = restore(diff);
        System.out.println("还原数组: " + Arrays.toString(result));
    }
}

// 使用差分数组解决公交车上下车（LeetCode 1109）
/*
    有 n 个站点，m 个预订记录 [first, last, seats]，
    表示从 first 站到 last 站有 seats 人上车，求每个站点的总人数
    奇怪的题目，可能是题目描述有点不对
 */
class CarPooling{

    public static int[] corpFlightBookings(int[][] bookings, int n){
        long[] diff = new long[n];

        // 多次区间修改
        for (int[] booking : bookings) {
            int first = booking[0] - 1; // 转为 0-indexed
            int last = booking[1] - 1;
            int seats = booking[2];

            diff[first] += seats;
            if (last + 1 < n) {
                diff[last + 1] -= seats;
            }
        }

        // 还原（前缀和）
        int[] result = new int[n];
        result[0] = (int) diff[0];
        for (int i = 1; i < n; i++) {
            result[i] = (int) (result[i - 1] + diff[i]);
        }

        return  result;
    }


    public static void main(String[] args){
        int[][] bookings = {
                {1, 2, 10},  // 站点1-2，10人
                {2, 3, 20},  // 站点2-3，20人
                {2, 5, 25}   // 站点2-5，25人
        };

        int n = 5;  // 5个站点
        int[] result = corpFlightBookings(bookings, n);
        System.out.println(Arrays.toString(result));

    }
}
