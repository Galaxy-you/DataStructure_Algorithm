package algorithms.divideandconquer;

/**
 * author: Galaxy Violet
 * date: 2025/11/14, 12:18
 */
public class HanoiTower {
    public static int moveCount = 0;    // 移动总次数

    public static void main(String[] args) {
        // 汉诺塔问题的最少移动次数为：2^n - 1
        int numOfPlate = 10;

        hanoi(10, 'A', 'B', 'C');

        System.out.println("moveCount = " + moveCount);

    }

    // 直接输出移动序列，并且统计总共的移动次数
    // n 个盘子从a移动到c,借助b
    public static void hanoi(int n, char a, char b, char c){
        if(n == 1){
            System.out.println(a + " --> " + c);
            moveCount++;
            return;
        }

        // 将上方的 n - 1个盘子: a --> b
        hanoi(n - 1, a, c, b);

        // 将最底部的一个盘子:a --> c
        System.out.println(a + " --> " + c);
        moveCount++;

        // 剩余的 n - 1个盘子: b --> c
        hanoi(n - 1, b, a, c);
    }
}
