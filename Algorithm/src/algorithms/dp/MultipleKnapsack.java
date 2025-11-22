package algorithms.dp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * author: Galaxy Violet
 * date: 2025/11/15, 19:38
 */
public class MultipleKnapsack {
    public static void main(String[] args) {
        int[] weights = {1,2,3,4,5};
        int[] values = {10,3,4,2,3};
        int[] counts = {1,2,2,3,3};
        int W = 8;

        int maxValue = solveMultipleKnapsack(W, weights, values, counts);
        System.out.println("maxValue = " + maxValue);

    }


    /**
     * 第一步：进行二进制拆分，将多重背包问题转化为 0/1 背包问题
     * @param weights 原始物品重量数组
     * @param values 原始物品价值数组
     * @param counts 原始物品数量数组
     * @return 包含拆分后所有 "复合物品" 的列表
     */
    private static List<Item> binarySplit(int[] weights, int[] values, int[] counts){
        ArrayList<Item> simplifiedItems = new ArrayList<>();
        int N = weights.length; // the quantity of items' categories    category n.种类，类别

        for(int i = 0; i < N; i++){
            int w = weights[i];
            int v = values[i];
            int c = counts[i];

            // 把 c 拆成 1 + 2 + 4 + 8 + ...
            for(int k = 1; c > 0; k *= 2){
                int num = Math.min(k, c);

                simplifiedItems.add(new Item(w * num, v * num, i, num));

                c -= num;
            }
        }
        return simplifiedItems;
    }

    public static int solveMultipleKnapsackPro(int W, int[] weights, int[] values, int[] counts){
        List<Item> items = binarySplit(weights, values, counts);

        // 一维DP
        // dp[j]表示在当前考虑的物品种类中，容量为j时的最大价值
        int[] dp = new int[W + 1];

        // 遍历所有物品,构建DP数组
        for(Item item: items){
            int currWeight = item.weight;
            int currValue = item.value;

            // 一维DP中容量必须倒序遍历
            for(int j = W; j >= currWeight; j--){
                // 注意这里是dp[j]，由于还没有对dp[j]进行分更新，所有dp[j]等价于二维dp中的dp[i-1][j]
                // dp[j -currWeight]等价于dp[i-1][j - currWeight]
                dp[j] = Math.max(dp[j], dp[j - currWeight] + currValue);
            }
        }

        // traceItem

        return dp[W];
    }

    /*
    如果要使用二维DP进行求解的话，物品的种类int N = items.size()
    int[][] dp = new int[N + 1][W + 1];

    if(..){
        int valueIfIncluded = dp[i - 1][j - currWeight] + currValue;
    }

     */
    public static int solveMultipleKnapsack(int W, int[] weights, int[] values, int[] counts){
        List<Item> items = binarySplit(weights, values, counts);
        int N = items.size();   // 使用二进制划分后的新的物品种类

        int[][] dp = new int[N + 1][W + 1];

        for (int i = 1; i <= N; i++) {
            Item currItem = items.get(i - 1);
            int currWeight = currItem.weight;
            int currValue = currItem.value;

            for(int j = 1; j <= W; j++){
                dp[i][j] = dp[i - 1][j];
                if(j >= currWeight){
                    int valueIfIncluded = currValue + dp[i - 1][j - currWeight];
                    dp[i][j] = Math.max(dp[i - 1][j], valueIfIncluded);
                }
            }
        }

        // traceItem
        // 使用hashMap来存储选择的情况， key 为 originIndex, value 为 count
        HashMap<Integer, Integer> itemSelected = new HashMap<>();
        int n = N;
        int w = W;
        while (w >0 && n > 0){
            if(dp[n][w] != dp[n - 1][w]){   // 选择了物品n
                Item item = items.get(n - 1);
                int originIndex = item.originItemIndex;
                int count = item.count;
                int weight = item.weight;

                if(itemSelected.containsKey(originIndex)){
                    itemSelected.replace(originIndex, itemSelected.get(originIndex) + count);
                }else {
                    itemSelected.put(originIndex, count);
                }
                // 上面的if-else可以替换为一条语句，因为put()相同的key，value会进行覆盖
//                itemSelected.put(originIndex,
//                                itemSelected.getOrDefault(originIndex, 0) + count);
                w -= weight;
            }
            n--;
        }

        System.out.println("物品选择情况：");
        // 遍历hash表
        for(Map.Entry<Integer, Integer> entry: itemSelected.entrySet()){
            System.out.println("选择物品种类：" + entry.getKey() + " 个数：" + entry.getValue());
        }
        return dp[N][W];
    }
}


class Item{
    int weight;
    int value;
    int originItemIndex;
    int count;

    public Item(int weight, int value, int originItemIndex, int count) {
        this.weight = weight;
        this.value = value;
        this.originItemIndex = originItemIndex;
        this.count = count;
    }
}
