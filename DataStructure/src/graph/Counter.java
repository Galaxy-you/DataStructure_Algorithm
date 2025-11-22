package graph;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * author: Galaxy Violet
 * date: 2025/11/13, 21:18
 */
// 连通区域计数问题（LetCode上的“岛屿数量问题”）
public class Counter {
    public static void printGrid(int[][] grid){
        // 二维数组的遍历，这里使用增强for循环 + 工具类Arrays
        for (int[] row : grid) {
            System.out.println(Arrays.toString(row));
        }
    }

    // 从grid[i][j]开始探索连通区域(这里使用DFS)
    // 0-空白，1-存在元素，-1-已经探索
    public static void exploreGridDFS(int[][] grid, int row, int col, int i, int j){
        if(grid[i][j] == 1){
            grid[i][j] = -1;

            if(i - 1 >= 0 && grid[i - 1][j] == 1){  // up
                exploreGridDFS(grid, row, col, i - 1, j);
            }
            if(i + 1 < row && grid[i + 1][j] == 1){ // down
                exploreGridDFS(grid, row, col, i + 1, j);
            }
            if(j - 1 >= 0 && grid[i][j - 1] == 1){  // left
                exploreGridDFS(grid, row, col, i, j - 1);
            }
            if(j + 1 < col && grid[i][j + 1] == 1){ // right
                exploreGridDFS(grid, row, col, i, j + 1);
            }
        }
    }

    // 下面使用BFS进行探索
    public static void exploreGridBFS(int[][] grid, int row, int col, int i, int j){
        // 不能使用基础数据类型作为泛型
        // 这里的int[]是一个引用，然后只要是引用都可以作为泛型
        LinkedList<int[]> queue = new LinkedList<>();

        grid[i][j] = -1;
        // 队列的入队函数是 offer，即“提供，给予”的意思，
        // 底层调用为 offer(E e) --> add()/addLast() --> linkLast()
        queue.offer(new int[]{i,j});

        while (!queue.isEmpty()){
            // 队列的出队函数是poll，本意为“投票，民意调查”，这里引申为出队的意思
            // 底层调用为poll() --> (removeFirst() Deque接口的实现函数) --> unlinkFirst()
            int[] point = queue.poll();
            int x = point[0];
            int y = point[1];
            
            if(x - 1 >= 0 && grid[x - 1][y] == 1){  // up
               grid[x - 1][y] = -1;
               queue.offer(new int[]{x - 1, y});
            }
            if(x + 1 < row && grid[x + 1][y] == 1){ // down
                grid[x + 1][y] = -1;
                queue.offer(new int[]{x + 1, y});
            }
            if(y - 1 >= 0 && grid[x][y - 1] == 1){  // left
                grid[x][y - 1] = -1;
                queue.offer(new int[]{x, y - 1});
            }
            if(y + 1 < col && grid[x][y + 1] == 1){ // right
                grid[x][y + 1] = -1;
                queue.offer(new int[]{x, y + 1});
            }
        }
    }
    /*
    递归可以优化一下：避免重复的检查边界条件， 但是增加了递归的次数
    public static void exploreGrid(int[][] grid, int i, int j) {
    // 一次性检查所有边界条件
    if (i < 0 || i >= grid.length || j < 0 || j >= grid[0].length || grid[i][j] != 1) {
        return;
    }
    grid[i][j] = -1;
    // 直接递归，不需要再检查
    exploreGrid(grid, i - 1, j);
    // ...
}

     */

    public static int getCount(int[][] grid){
        int count = 0;
        int row = grid.length;
        int col = grid[0].length;

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if(grid[i][j] == 1){
                    // exploreGridDFS(grid, row, col, i, j);
                    exploreGridBFS(grid, row, col, i, j);
                    count++;
                }
            }
        }
        return count;
    }


    public static void main(String[] args) {
        // grid n.网格，格栅，方格
        int[][] grid = new int[][]{
                {1, 0, 1, 1, 1, 0, 0, 0, 0, 1, 1, 1, 0, 0},
                {0, 1, 1, 0, 0, 1, 1, 0, 0, 0, 1, 1, 0, 1},
                {0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 1, 1},
                {0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0},
                {0, 0, 0, 1, 1, 0, 0, 0, 1, 1, 1, 0, 0, 0},
                {0, 0, 1, 1, 1, 1, 0, 0, 0, 1, 0, 0, 1, 1},
                {1, 0, 0, 1, 1, 1, 0, 1, 1, 0, 0, 1, 1, 1},
                {1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {1, 0, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 0, 0},
                {1, 0, 0, 0, 1, 0, 0, 0, 0, 1, 1, 1, 1, 1}
        };
        int[] array = new int[3];
        System.out.println(array[1]);

        System.out.println("原始网络：");
        printGrid(grid);

        int count = getCount(grid);
        System.out.println("the count is " + count);
    }
}

/*
数组初始化的方法
1、静态初始化
    int[] array = {1,2,3}
2、动态创建并静态赋值（一般用于匿名数组）
    int[] array = new int[]{1,2,3}
3、动态创建（指定长度,分配空间并进行默认初始化），之后再进行赋值
    只要使用 new 关键字在 Java 堆内存中分配了空间（无论是对象还是数组），这块内存中的内容就一定会被自动初始化。
    int[] array = new int[3];
    array[1] = 1;
    // ..
 */

/*
public class LinkedList<E>
    extends AbstractSequentialList<E>
    implements List<E>, Deque<E>, Cloneable, java.io.Serializable

    LinkedList实现了双端队列接口:Deque,即 Double-ended queue,双端队列是一种允许在两端（即队列的头部和尾部）进行插入和删除操作的线性数据结构。
    所以LinkedList是一个双向链表，每一个节点包含 item, prec, next ,mvdsssssssssssssssssssssssssssdddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd
 */