package search;

import java.util.Arrays;

public class FibonacciSearch {
    public static final int MAX_SIZE = 20;

    public static void main(String[] args) {
        int[] arr = new int[]{1,8,10,89,1000,1234};

        int index = fibonacciSearch(arr, 9999);

        System.out.println(index);

    }

    // 构建 Fibonacci 序列
    public static int[] getFibArray(){
        int[] fibArray = new int[MAX_SIZE];

        fibArray[0] = 1;
        fibArray[1] = 1;

        for (int i = 2; i < MAX_SIZE; i++) {
            fibArray[i] = fibArray[i - 1] + fibArray[i - 2];
        }
        return fibArray;

    }

//    public static int fibonacciSearchRecursion(int[] array, int findValue, int left, int right, int k){
//
//        return 0;
//    }

    public static int fibonacciSearch(int[] array, int findValue){
        int low = 0;
        int high = array.length - 1;
        int k = 0;  // 表示斐波拉契分隔数值的下标
        int mid;
        int[] fibArray = getFibArray();

        // array.length = fib[k] - 1 = (fib[k - 1] - 1) + (fib[k - 2] - 1) +
        // 有上面的公式确定第一次划分数组的 k 值
        while (high + 1 > fibArray[k] - 1){
            k++;
        }

        /*
        Arrays.copyOf() 是 Java 中 java.util.Arrays 类提供的一个非常实用且常用的方法，用于快速创建一个数组的副本。
        常用的两种形式如下：
        1、对象数组版本（浅拷贝，修改新数组元素指向的对象的状态，会同时影响旧数组（因为它们指向同一个对象）。）
        public static <T> T[] copyOf(T[] original, int newLength)

        2、对象为基本数据类型版本
        public static int[] copyOf(int[] original, int newLength)

        newLength == original.length    创建一个完全相同的副本。
        newLength < original.length     截断 (Truncate)。只复制原始数组中的前 newLength 个元素。
        newLength > original.length}    填充 (Pad)。复制原始数组的所有元素，但新数组中多出来的部分会用默认值填充：
                                                * 数值类型 (int, double 等)：用 0 填充。
                                                * 布尔类型 (boolean)：用 false 填充。
                                                * 对象类型：用 null 填充。
         */
        // fibArray[k] > array.length => 构造新数组，并进行相关填充(copyOf()函数的默认填充是0)


        int[] temp = Arrays.copyOf(array, fibArray[k]); // fibArray[k]为新数组的长度
        
        // 使用 array[high] 填充扩充部分
        for (int i = high + 1; i < temp.length; i++) {
            temp[i] = array[high];
        }

        while (low <= high){
            mid = low + fibArray[k - 1] - 1;

            if(temp[mid] == findValue){
                // 这里mid 需要和 high做比较，因为高位padding都为array[high]
                if(mid <= high){    // 找到的部分为原数组array中本来就有的部分
                    return mid;
                }else { //  找到的值为temp中padding的部分
                    return high;
                }
            }
            else if(temp[mid] > findValue){    // 向左查找
                high = mid - 1;
                // f[k] - 1 =  (f[k-1] - 1) + (f[k - 2] - 1) + 1
                // 向左即向 f[k - 1] - 1这部分重新查找，并且low和high已经更新
                k--;
            }else { // 向右查找
                low = mid + 1;
                // 向右即向 f[k - 2] - 1 这部分重新查找，并且更新low和high
                k -= 2;
            }
        }
        return -1;
    }
}
