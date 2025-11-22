package algorithms.dp;

/**
 * author: Galaxy Violet
 * date: 2025/11/14, 22:13
 */

/*
    完全背包问题
 */
public class UnboundedKnapsack {
    public static void main(String[] args) {
        int W = 13;
        int[] weights = {2, 3, 4};
        int[] values  = {3, 4, 5};
        int maxValue = solveUnboundedKnapsack(W, weights, values);
        System.out.println("maxValue = " + maxValue);

    }

    public static int solveUnboundedKnapsack(int W, int[] weights, int[] values){
        int N = weights.length; // quantity of items

        int[][] dp = new int[N + 1][W + 1];

        // 遍历所有物品
        for (int i = 1; i <= N; i++) {
            int currWeight = weights[i - 1];
            int currValue = values[i - 1];

            for(int j = 1; j <= W; j++){
                // 不装入currItem
                dp[i][j] = dp[i - 1][j];

                // 当前的容量 j 可以放入 currItem
                if(j >= currWeight){
                    // 这里是 dp[i][j - currWeight]（可以选择多次currItem） 而不是 dp[i - 1][j - currWeight]（只选择一次currItem）
                    // dp[i][j - currItem]即少选一次currItem的最大价值，但是仍然可以选currItem，所以这里是 i
                    int valueIfIncluded = currValue + dp[i][j - currWeight];

                    // 这里写dp[i][j]或者dp[i - 1][j]都可以，因为在循环开始时进行了赋值
                    // 为了便于理解，这里写dp[i - 1][j]，表示不选当前物品的情况
                    dp[i][j] = Math.max(dp[i - 1][j], valueIfIncluded);
                }
            }
        }

        // 回溯，确定选择了哪些物品
        System.out.println("选择的物品：");
        for (int i = N, j = W; i > 0 && j > 0; i--) {

            // 当前值 != 上一行的值，说明选择了物品 i
            if(dp[i][j] != dp[i - 1][j]){
                int count = 0;

                // 计算选择了多少物品 i
                while (j >= weights[i - 1] &&
                        dp[i][j] == dp[i][j - weights[i - 1]] + values[i - 1]){
                    count++;
                    j -= weights[i - 1];
                }
                if(count > 0){
                    System.out.printf("物品%d: 重量=%d, 价值=%d, 数量=%d\n",
                            i, weights[i - 1], values[i - 1], count);
                }
            }
        }
        return dp[N][W];
    }

}
