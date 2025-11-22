package recursion;

/**
 * author: galaxy-violet
 * date: 2025/10/14,17:57
 */

// 8皇后问题一共有92种解法 -^_^-
public class Queens {
    public static final int MAX = 8;
    public static int count = 0;    // 存储

    // 保存皇后放置位置的结果
    public int[] array = new int[MAX];


    public static void main(String[] args) {
        Queens queens = new Queens();
        queens.check(0);
        System.out.println("The total amount of the methods is " + count);
    }
    
    public void print(){
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }
        System.out.println();
    }


    // 放置第n行皇后时（行数从0开始），检测该皇后和之前摆放的皇后是否冲突
    public boolean isLegal(int n){
        for(int i = 0; i < n; i++){
            // 在同一列
            if(array[i] == array[n]){
                return false;
            }
            // 在同一条对角线上
            else if(Math.abs(n - i) == Math.abs(array[n] - array[i])){
                return false;
            }
        }
        return true;
    }



    // 放置第n行的皇后
    // check中有一个for循环，穷举了所有的情况
    public void  check(int n){
        // 递归出口
        if(n == MAX){   // n == 8时代表放置的是第8行的皇后（行从0开始），即当前为第九个皇后，已经放置了8个皇后，此时问题已经解决
            print();
            count++;
            return;
        }


        for(int i = 0; i < MAX; i++){
            // 穷尽第n行的皇后在每一列的情况
            array[n] = i;

            // 判断该位置是否合法
            if(isLegal(n)){
                // 该位置合法，继续放后一个皇后，即开始递归
                check(n + 1);
            }

            // 冲突，执行 i++, array[n] = i，即重新为该行的皇后选择位置
        }

    }
}
