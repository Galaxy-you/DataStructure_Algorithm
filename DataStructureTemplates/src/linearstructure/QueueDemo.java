package linearstructure;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * author: Galaxy Violet
 * date: 2025/11/18, 10:51
 */

/* 简要介绍
    队列是一种先进先出（FIFO: First In First Out）的线性数据结构。
    从队尾入队，从队首出队，先进先出（FIFO）
    主要操作：offer（入队）、poll（出队）、peek（查看队首）
    offer,向队列提供一个元素，成功返回true，失败（队列已满）返回false，不会报异常
    offer = offerLast

    poll，有“提取，征集，投票”的意思，成功返回元素，失败返回null
    poll = pollFirst
 */
/* 适用场景
    广度优先搜索（BFS）⭐⭐⭐
    层序遍历
    任务调度
    滑动窗口（双端队列）
    最近请求次数
 */
public class QueueDemo {
    // java中实现Queue可以使用ArrayDeque和LinKedList,推荐使用ArrayDeque实现queue

    public static void main(String[] args) {
        // ========== 基本操作 ==========

        Queue<Integer> queue = new ArrayDeque<>();

        // 入队（添加到队尾）
        // offer队列不满时成功入队，返回true，入队失败返回false
        // 不过ArrayDeque会自动扩容，所以永远不会返回false
        queue.offer(1);
        queue.offer(2);
        queue.offer(3);
        System.out.println("队列: " + queue); // [1, 2, 3]

        // 查看队首（不删除）
        // peek队列为空时返回null
        int front = queue.peek();
        System.out.println("队首: " + front); // 1

        // 出队（删除队首并返回）
        // 队列为空时poll返回null
        int removed = queue.poll();
        System.out.println("出队: " + removed); // 1
        System.out.println("队列: " + queue);   // [2, 3]

        // 是否为空
        System.out.println("是否为空: " + queue.isEmpty()); // false

        // 大小
        System.out.println("大小: " + queue.size()); // 2
    }
}

// 层次遍历，详见BinaryTree

// BFS（广度优先搜索）
    /* 在网格中寻找最短路径，letCode 1091
    从左上角到右下角的最短路径,0表示可走，1表示障碍物
    可以8个方向移动（上下左右+4个对角）
     */
class ShortestPath{

    public static int getShortestPath(int[][] grid){
        int n = grid.length;

        // 起点或者终点为障碍物，返回-1
        if(grid[0][0] == 1 || grid[n - 1][n -1 ] == 1){
            return -1;
        }

        // 方向数组
        int[][] directions = {
                {0,-1},{0,1},{1,0},{1,-1},
                {-1,-1},{-1,1},{1,-1},{1,1}
        };

        Queue<int[]> queue = new ArrayDeque<>();    // queue用来存储已经走过的点,分别表示row,col,dist
        grid[0][0] = 1; // 标记起点已经访问
        queue.offer(new int[]{0,0,0});

        while (!queue.isEmpty()){
            int[] curr = queue.poll();
            int row = curr[0];
            int col = curr[1];
            int dist = curr[2];

            if(row == n - 1 && col == n -1){
                return dist;
            }
            for(int[] dir: directions){
                int newRow = row + dir[0];
                int newCol = col + dir[1];

                // 这里需要检查是否越界
                if(newRow >= 0 && newRow < n && newCol >= 0 && newCol < n
                    && grid[newRow][newCol] == 0){
                    queue.offer(new int[]{newRow, newCol, dist + 1});
                    grid[newRow][newCol] = 1;
                }
            }
        }
        return -1;  // 无法到达终点
    }

}

// 滑动窗口
// 统计最近3000毫秒内的请求次数， LetCode 933
class RecentCounter{
    private Queue<Integer> queue;

    public RecentCounter(){
        this.queue = new ArrayDeque<>();
    }

    public int ping(int t){
        queue.offer(t);
        while (!queue.isEmpty() && t - queue.peek() > 3000){
            queue.poll();   // 从队首移除3000ms之外的请求
        }
        return queue.size();
    }
}
/*
时间轴：
0────1────100─────────────3001─3002
     ↑    ↑               ↑    ↑
   请求1  请求2          请求3 请求4

在时间3002时：
窗口范围：[3002-3000, 3002] = [2, 3002]

请求1(时间1): 1 < 2，不在窗口内 ✗
请求2(时间100): 100 ≥ 2，在窗口内 ✓
请求3(时间3001): 3001 ≥ 2，在窗口内 ✓
请求4(时间3002): 3002 ≥ 2，在窗口内 ✓

所以返回 3
 */