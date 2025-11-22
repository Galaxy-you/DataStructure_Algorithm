package array;

import java.util.Arrays;

/**
 * author: Galaxy Violet
 * date: 2025/11/17, 11:47
 */

/* 简要解释
    二维数组表示一个表格或者矩阵结构，本质是一维数组的数组
 */

/* 使用场景
    矩阵运算（转置、旋转）
    网格类问题（岛屿数量、最短路径）
    二维前缀和
    图论（邻接矩阵）
    动态规划（背包、最长公共子序列等）
 */
public class TwoDimensionalArray {
    // ========== 创建和初始化 ==========
    // 1. 基本创建方式
    public static void createArray() {
        // 方式1: 指定行列（矩形数组）
        int[][] arr1 = new int[3][4]; // 3行4列，默认值为0

        // 方式2: 直接初始化,注意使用的是大括号{}，而不是方括号[]
        int[][] arr2 = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };

        // 方式3: 锯齿数组（每行长度可不同）
        int[][] arr3 = new int[3][];
        arr3[0] = new int[2]; // 第一行2个元素
        arr3[1] = new int[3]; // 第二行3个元素
        arr3[2] = new int[4]; // 第三行4个元素

        // 方式4: new 关键字初始化(下面的例子也是一个锯齿数组)
        int[][] arr4 = new int[][] {
                {1, 2},
                {3, 4, 5},
                {6}
        };
    }

    // 2. 填充二维数组
    public static int[][] fillArray(int rows, int cols, int value) {
        int[][] arr = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            // Arrays.fill() 不能直接填充二维数组
            Arrays.fill(arr[i], value);
        }
        return arr;
    }

    // 3. 创建单位矩阵（对角线为1，其余为0）
    public static int[][] identityMatrix(int n) {
        int[][] matrix = new int[n][n];
        for (int i = 0; i < n; i++) {
            matrix[i][i] = 1;   // [i][i]即为对角线
        }
        return matrix;
    }

    // 4. 复制二维数组（深拷贝）
    public static int[][] deepCopy(int[][] arr) {
        if (arr == null) return null;

        int[][] copy = new int[arr.length][];   // 二维数组初始化的时候可以只指定行数，而不指定列数
        for (int i = 0; i < arr.length; i++) {
            copy[i] = arr[i].clone(); // 每行单独复制
        }
        return copy;

        // 注意不能直接使用 int[][] copy = arr.clone();因为行数组是同一个引用
    }

    // 5、获取行数
    public static int getRows(int[][] arr){
        return arr.length;  // arr.length即为二维数组的行数
    }

    // 6、获取列数（非锯齿矩阵）
    public static int getCols(int[][] arr){
        if(arr.length == 0 || arr[0] == null){
            return 0;
        }else {
            return arr[0].length;
        }
    }

    // 7. 螺旋遍历 ⭐
    public static void spiralTraverse(int[][] arr) {
        if (arr.length == 0) return;

        int top = 0, bottom = arr.length - 1;
        int left = 0, right = arr[0].length - 1;

        while (top <= bottom && left <= right) {
            // 从左到右
            for (int j = left; j <= right; j++) {
                System.out.print(arr[top][j] + " ");
            }
            top++;

            // 从上到下
            for (int i = top; i <= bottom; i++) {
                System.out.print(arr[i][right] + " ");
            }
            right--;

            // 从右到左
            if (top <= bottom) {    // 注意这里有一个判断，因为上面top++
                for (int j = right; j >= left; j--) {
                    System.out.print(arr[bottom][j] + " ");
                }
                bottom--;
            }

            // 从下到上
            if (left <= right) {
                for (int i = bottom; i >= top; i--) {
                    System.out.print(arr[i][left] + " ");
                }
                left++;
            }
        }
        System.out.println();
    }

    // ========== 常用操作 ==========

    // 1. 转置矩阵（行列互换）
    public static int[][] transpose(int[][] arr) {
        int rows = arr.length;
        int cols = arr[0].length;
        int[][] result = new int[cols][rows];   // 建立一个辅助数组

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[j][i] = arr[i][j];
            }
        }

        //  在原数组上操作（只能是方阵）
//        for(int i = 0; i < rows; i++){
//            for (int j = 0; j < i; j++) {
//                int temp = result[i][j];
//                arr[i][j] = arr[j][i];
//                arr[j][i] = temp;
//            }
//        }
        return result;
    }

    // 2. 原地转置（仅适用于方阵）
    public static void transposeInPlace(int[][] arr) {
        int n = arr.length;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) { // j = i 即为对角线，对角线不用换，所以从 i + 1开始
                int temp = arr[i][j];
                arr[i][j] = arr[j][i];
                arr[j][i] = temp;
            }
        }
    }

    // 3. 顺时针旋转 90 度 ⭐
    public static void rotate90Clockwise(int[][] arr) {
        // 方法：先转置，再每行反转
        transposeInPlace(arr);
        for (int i = 0; i < arr.length; i++) {
            reverseRow(arr[i]);
        }
    }

    // 4. 逆时针旋转 90 度
    public static void rotate90CounterClockwise(int[][] arr) {
        // 方法：先每行反转，再转置
        for (int i = 0; i < arr.length; i++) {
            reverseRow(arr[i]);
        }
        transposeInPlace(arr);
    }

    // 5. 旋转 180 度
    public static void rotate180(int[][] arr) {
        // 方法：上下翻转，再左右翻转
        reverseRows(arr);
        for (int i = 0; i < arr.length; i++) {
            reverseRow(arr[i]);
        }
    }

    // 辅助：反转一行
    private static void reverseRow(int[] row) {
        int left = 0, right = row.length - 1;
        while (left < right) {
            int temp = row[left];
            row[left] = row[right];
            row[right] = temp;
            left++;
            right--;
        }
    }

    // 辅助：上下翻转
    private static void reverseRows(int[][] arr) {
        int top = 0, bottom = arr.length - 1;
        while (top < bottom) {
            // 这里交换的是数组的引用
            int[] temp = arr[top];
            arr[top] = arr[bottom];
            arr[bottom] = temp;
            top++;
            bottom--;
        }
    }

    // 6. 查找元素
    public static int[] findElement(int[][] arr, int target) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                if (arr[i][j] == target) {
                    return new int[]{i, j}; // 返回 [行, 列]
                }
            }
        }
        return new int[]{-1, -1}; // 未找到
    }

    // 7. 二维数组求和
    public static long sum(int[][] arr) {
        long sum = 0;
        // row: int[] col: int
        for (int[] row : arr) {
            for (int num : row) {
                sum += num;
            }
        }
        return sum;
    }

    // 8. 找最大值
    public static int findMax(int[][] arr) {
        int max = Integer.MIN_VALUE;    // -2^31
        for (int[] row : arr) {
            for (int num : row) {
                max = Math.max(max, num);
            }
        }
        return max;
    }

    // 9. 找最小值
    public static int findMin(int[][] arr) {
        int min = Integer.MAX_VALUE;    // 2^31 - 1
        for (int[] row : arr) {
            for (int num : row) {
                min = Math.min(min, num);
            }
        }
        return min;
    }

    // 10. 打印二维数组
    public static void print(int[][] arr) {
        for (int[] row : arr) {
            // 使用Arrays.toString()方法打印每一行
            System.out.println(Arrays.toString(row));
        }
    }

    // 11. 四个方向的移动（上下左右）⭐ 网格问题必备
    private static final int[][] DIRECTIONS = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};   // 详见主函数
    // 或者八个方向（包括对角线）
    private static final int[][] EIGHT_DIRECTIONS = {
            {-1, 0}, {1, 0}, {0, -1}, {0, 1},     // 上下左右
            {-1, -1}, {-1, 1}, {1, -1}, {1, 1}    // 四个对角
    };

    // 检查坐标是否有效
    public static boolean isValid(int[][] arr, int row, int col) {
        return row >= 0 && row < arr.length && col >= 0 && col < arr[row].length;
    }

    // 表格问题中的dfs,用于解决岛屿问题，岛屿是1，水域是0
    // 然后下面的函数是从一个点开始向四周进行探索完整的一个岛屿
    public static void gridBFS(int[][] grid, int row, int col){
        if(!isValid(grid, row, col) || grid[row][col] == 0){
            return;
        }

        // 当前位置为岛屿，标记当前位置为0,沉没这块陆地
        grid[row][col] = 0;

        // 方向数组
        int[][] direction ={{-1,0}, {1,0}, {0,-1}, {0,1}};
        for(int[] dir: direction){
            gridBFS(grid, row + dir[0], col + dir[1]);
        }
    }

    public static int numIslands(int[][] grid){
        if(grid == null || grid.length == 0){
            return 0;
        }

        int count= 0;
        int rows = grid.length;
        int cols = grid[0].length;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if(grid[i][j] == 1){
                    count++;
                    gridBFS(grid, i, j);    // 沉没整块陆地
                }
            }
        }
        return count;
    }

    // 12. 填充边界
    public static void fillBorder(int[][] arr, int value) {
        int rows = arr.length;
        int cols = arr[0].length;

        // 填充第一行和最后一行
        for (int j = 0; j < cols; j++) {
            arr[0][j] = value;
            arr[rows - 1][j] = value;
        }

        // 填充第一列和最后一列
        for (int i = 0; i < rows; i++) {
            arr[i][0] = value;
            arr[i][cols - 1] = value;
        }
    }

    public static void main(String[] args) {
        int[][] grid = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };

        int row = 1, col = 1; // 从中心点 (1,1) 开始
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}}; // 上下左右

        System.out.println("中心点: " + grid[row][col]);
        System.out.println("四个方向的邻居:");
        for (int[] dir : directions) {
            int newRow = row + dir[0];  // 新的横坐标
            int newCol = col + dir[1];  // 新的列坐标
            // 检查边界
            if (TwoDimensionalArray.isValid(grid, newRow, newCol)) {
                System.out.println("(" + dir[0] + "," + dir[1] + "): "+ grid[newRow][newCol]);
            }
        }
        // 输出: 2(上) 8(下) 4(左) 6(右)

    }
}
