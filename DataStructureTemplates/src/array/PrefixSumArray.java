package array;

import java.util.HashMap;

/**
 * author: Galaxy Violet
 * date: 2025/11/17, 16:32
 */

/* 介绍：
    前缀和是一种预处理技巧，用于快速计算数组中任意区间的和。
    通过一次预处理，可以将区间求和的时间复杂度从 O(n) 降到 O(1)。
    prefixSum[i] 表示原数组前 i 个元素的和
    区间 [left, right] 的和 = prefixSum[right] - prefixSum[left - 1]
 */
/* 典型应用
    频繁查询区间和（不修改数组）
    子数组和问题
    判断是否存在和为 k 的子数组
    二维矩阵的区域和查询
 */
public class PrefixSumArray {
    // 一维前缀和 prefixSum[i]表示原数组中前i个元素的和，0 <= i <= arr.length
    // prefixSum[i] = prefixSum[i - 1] + arr[i - 1]
    public static long[] buildPrefixSum1D(int[] arr){
        int length = arr.length;
        long[] prefixSum = new long[length + 1];    // 前缀和数组的长度比原数组的长度多1,prefixSum[0] = 1

        // prefixSum[0] = 0, 所以前缀和的计算直接从1开始
        // 这里的i是arr的下标
        for(int i = 0; i < length; i++){
            prefixSum[i + 1] = prefixSum[i] + arr[i];
        }
        return prefixSum;
    }

    // 查询区间[left, right]的和
    public static long rangeSum(long[] prefixSum, int left, int right){
        return prefixSum[right + 1] - prefixSum[left];
    }


    // 判断是狗存在和为 k 的连续子数组
    // BF：找出所有的子数组和，查看是否存在
//    public static boolean hasSubarraySum(int[] arr, int k){
//        long[] prefixSum = buildPrefixSum1D(arr);
//        int length = arr.length;
//
//        for (int i = 0; i < length; i++) {
//            for (int j = i; j < length; j++) {
//                if(rangeSum(prefixSum, i, j) == k){
//                    return true;
//                }
//            }
//        }
//        return  false;
//    }
    /* 推导过程
       存在子数组[i,j]的和为 k,则 prefix[j + 1] - prefix[i] = k
       即 prefix[j + 1](当前的sum) - k = prefix[i]
     */
    public static boolean hasSubarraySum(int[] arr, int k) {
        HashMap<Long, Integer> map = new HashMap<>();
        // 整数后面加上L表示这是一个long
        // key -- 前缀和   value -- 该前缀和最后一次出现的索引
        // 也就是数组的前value个元素的和是key
        map.put(0L, 0);

        long sum = 0;
        for (int i = 0; i < arr.length; i++){
            sum += arr[i];

            if(map.containsKey(sum - k)){
                // 子数组和对应的数组下标范围为：[map.get(sum - k), i - 1]
                // 打印子数组
                for (int j = map.get(sum - k) ; j <= i; j++) {
                    System.out.print( arr[j] + " ");
                }
                return true;
            }

            map.put(sum, i + 1);
        }
        return false;
    }


    // 二维前缀和
    // prefix[i][j] 表示从 (0,0) 到 (i-1, j-1) 的矩形区域所包含的元素的和
    // 矩形 (r1,c1) 到 (r2,c2) 的和:
    //sum = prefix[r2+1][c2+1] - prefix[r1][c2+1] - prefix[r2+1][c1] + prefix[r1][c1]
    // 即 上 + 左 - 左上 + 当前

    // 查询矩形 (r1,c1) - (r2,c2)
    // sum = prefix[r2+1][c2+1] - prefix[r1][c2+1] - prefix[r2+1][c1] + prefix[r1][c1]
    // 即 大 - 上 - 左 + 左上（容斥原理）
    public static long[][] buildPrefixSum2D(int [][] matrix){
        int row = matrix.length;
        int col = matrix[0].length;
        long[][] prefixSum = new long[row + 1][col + 1];

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                prefixSum[i][j] = prefixSum[i - 1][j]   // 上
                                + prefixSum[i][j -1]    // 左
                                - prefixSum[i - 1][j -1]// 左上
                                + matrix[i - 1][j - 1]; // 当前元素

            }
        }
        return prefixSum;
    }

    public static long query(long[][] prefixSum, int r1, int c1, int r2, int c2){
        return prefixSum[r2 + 1][c2 + 1]
                - prefixSum[r1][c2 + 1]
                - prefixSum[r2 + 1][c1]
                + prefixSum[r1][c1];
    }

    public static void main(String[] args) {
        int[] arr = new int[10];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = i;
        }
        // 10 次查询，比如说{0, 2}的意思就是 查询arr[0] + arr[1] + arr[2]
        int[][] queries = {
                {0, 2}, {1, 3}, {2, 4},
                {0, 4}, {1, 1}, {3, 4},
                {0, 0}, {2, 2}, {1, 4}, {0, 3}
        };

        long[] prefixSum1D = buildPrefixSum1D(arr);
        for(int[] query: queries){
            // System.out.println("sum = " + (prefixSum1D[query[1] + 1] - prefixSum1D[query[0]]));
            // 调用之前写好的方法
            System.out.println("sum = " + rangeSum(prefixSum1D, query[0], query[1]));
        }

        boolean res = hasSubarraySum(arr, 6);

    }
}
