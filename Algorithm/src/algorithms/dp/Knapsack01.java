package algorithms.dp;

/**
 * author: Galaxy Violet
 * date: 2025/11/14, 20:46
 */

import java.util.Arrays;

/**
 * 0/1 knapsack problem
 * 时间复杂度为 O(N * M)，空间复杂度为 O(N * M)
 * N: quantity of items
 * W: capacity of knapsack
 */

public class Knapsack01 {
    public static void main(String[] args) {
        int[] weights = {1, 4, 3};
        int[] values = {1500, 3000, 2000};
        int knapsackCapacity = 4;

        int maxValue = solveKnapsackPro(knapsackCapacity, weights, values);

        System.out.println("maxValue = " + maxValue);

    }
    /* 时间复杂度为 O(N * M)，空间复杂度为 O(N * M)
     * N: quantity of items
     * W: capacity of knapsack
     */
    public static int solveKnapsack(int W, int[] weights, int[] values){
        int N = weights.length; // quantity of items

        // dp[i][j]表示考虑前i个物品，背包容量为j时的最大价值
        int[][] dp = new int[N + 1][W + 1];

//        边界条件，默认为0，可不写
//        dp[0][j] = 0;
//        dp[i][0] = 0;

        // 遍历所有物品，从第一个物品开始(i == 0的子问题已经解决了)
        for (int i = 1; i <= N; i++) {
            int currentWeight = weights[i - 1];
            int currentValue = values[i - 1];

            // 遍历容量，从容量为1到最大容量N（不从容量为0开始因为容量为0的子问题已经解决了）
            for(int j = 1; j <= W; j++) {
                if(j < currentWeight){  // 当前容量不能放入物品i
                    dp[i][j] = dp[i - 1][j];
                }else { // 可以放入， j >= currentWeight
                    // 计算装入当前物品的价值
                    int valueIfIncluded = currentValue + dp[i - 1][j - currentWeight];
                    dp[i][j] = Math.max(valueIfIncluded, dp[i - 1][j]);
                }
            }
        }
        // 查看dp数组
        System.out.println("dp[][]");
        for (int i = 0; i < dp.length; i++) {
            System.out.println(Arrays.toString(dp[i]));
        }


        System.out.println("放入情况：");
        // 通过回溯查看具体选择的物品
        /*
            从右下角(i = N, j = W)开始回溯
            dp[i][j] == dp[i - 1][j] => 没有选择第i个物品，i--
            dp[i][j] ！= dp[i - 1][j] => 选择第了i个物品, i--,j = j - weights[i - 1]

         */
        int[] isSelected = new int[N];  // 1代表最终最终选择了该物品
        int n = N;
        int w = W;
        while (n > 0 && w > 0){
            // 没有选择第n个物品
            if(dp[n][w] == dp[n - 1][w]){
                n--;
            }else { // 选择了第n个物品
                isSelected[n - 1] = 1;
                w -= weights[n - 1];
                n--;
            }
        }

        for (int i = 0; i < N; i++) {
            if(isSelected[i] == 1){
                System.out.println("select item " + (i + 1));
            }
        }

        // 返回考虑所有物品，容量最大的情况
        return dp[N][W];
    }

    // 背包问题可以进行空间优化，即一维DP
    /*

     */
    public static int solveKnapsackPro(int W, int[] weights, int[] values){
        int N = weights.length; // quantity of items

        // 一维DP数组
        // dp[j]表示：在当前考虑 的物品集中，容量为j时能达到的最大价值
        int[] dp = new int[W + 1];

        // 遍历所有物品
        for (int i = 1; i <= N; i++) {
            int currValue = values[i - 1];
            int currWeight = weights[i - 1];

            // 倒序遍历容量j
            // 从W遍历到weight[i]，确保dp[j - weight[i - 1]]仍然是是上一轮的(i - 1)的值
            for(int j = W; j >= weights[i - 1]; j--){
                // 不装入物品： dp[j] 即为 dp[i - 1][j]
                // 装入物品：currValue + dp[j - currWeight],这里的dp[j - currWeight]等价于dp[i - 1][j - currWeight]
                dp[j] = Math.max(dp[j], currValue + dp[j - currWeight]);
            }
        }
        return dp[W];
    }
}
