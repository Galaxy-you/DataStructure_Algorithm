package linearstructure;

import java.util.ArrayDeque;

/**
 * author: Galaxy Violet
 * date: 2025/11/21, 10:43
 */
/* 双端队列 ：两端都可以入队和出队，栈 + 队列
    addFirst(e)      -- 头部加入
    addLast(e)       -- 尾部加入
    removeFirst()   -- 从头取出
    removeLast()    -- 从尾取出
    peekFirst()/peekLast()  -- 查看两端元素，不删除

 */
public class ArrayDequeDemo {
    // 滑动窗口最大值 LetCode 239  slide-slid-slid v.（使）滑行，滑落
    /*
    给定数组 nums 和窗口大小 k，求每个窗口的最大值。
    目标：O(n) 时间。
     */
    public int[] maxSlidingWindow(int[] nums, int k){
        if(nums == null || k < 0){
            return new int[0];
        }

        int n = nums.length;
        int[] ans = new int[n - k + 1]; // answer
        ArrayDeque<Integer> deque = new ArrayDeque<>(); // 存储索引，对应的值递减

        for (int i = 0; i < n; i++) {
            // 1、丢弃队头已出窗口的索引,如果可以形成窗口（i >= k - 1），则对应的窗口为[i - k + 1, i]
            // addLast(),removeFirst() --> 队头的元素先不在窗口范围内
            while (!deque.isEmpty() && deque.peekFirst() < i - k + 1){
                deque.removeFirst();
            }

            // 2、保持队内值递减，当前值大则弹出尾部较小值
            while (!deque.isEmpty() && nums[i] >= nums[deque.peekLast()]){
                deque.removeLast();
            }

            // 3、入队当前索引
            deque.addFirst(i);

            // 4、当前窗口形成
            if(i >= k - 1){
                ans[i - k + 1] = nums[deque.peekFirst()];
            }
        }
        return ans;
    }

    // 0-1 BFS
    // 在有向/无向图中，边的权重只有 0 或 1，求从源点到各点的最短距离。
    // 可以在 O(V+E) 时间内完成（比 Dijkstra 更快）。
}
