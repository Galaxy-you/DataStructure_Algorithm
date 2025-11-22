package recursion;

/**
 * author: galaxy-violet
 * date: 2025/10/14,15:51
 */

/*
JVM空间划分：
    1、栈：函数
    2、堆
    3、代码区/常量区
 */

public class RecursionDemo {
    public static void main(String[] args) {
        // 递归调用机制
        /*
        1、当程序执行到一个方法时，会开辟一个独立的栈空间
        2、当方法运行完成时，会销毁栈空间，然后返回上一个函数的栈继续运行
         */

        test1(4);
        test2(4);

        int res1 = factorial(1);
        int res2 = factorial(5);
        System.out.println("res1 = " + res1);
        System.out.println("res1 = " + res2);

    }

    public static void test1(int n){
        if(n > 2){
            test1(n - 1);
        }
        System.out.println("n = " + n);
    }

    public static void test2(int n){
        if(n > 2){
            test2(n - 1);
        }else{
            System.out.println("n = " + n);
        }
    }

    // 阶乘问题
    // factorial n.阶乘   adj.因素的，阶乘的
    public static int factorial(int n){
        if(n == 1 || n == 0){
            return 1;
        }
        return n * factorial(n -1);
    }
}

