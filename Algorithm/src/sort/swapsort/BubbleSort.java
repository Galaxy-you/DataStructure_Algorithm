package sort.swapsort;


import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * author: galaxy-violet
 * date: 2025/10/16,11:43
 */
public class BubbleSort {
    public static void main(String[] args) {
//        int[] array = new int[]{3,9,-1,10,-2};
//          int[] array = new int[]{-3,1,2,6,3,9,10};

        int[] arr = new int[20];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int)(Math.random() * 20); // 产生[0,20)之间的随机数
        }

        Date start = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String startStr = dateFormat.format(start);
        System.out.println("start:" + startStr);

        bubbleSort(arr);

        Date end = new Date();
        String endStr = dateFormat.format(end);
        System.out.println("end: " + endStr);

    }


    // bubbleSort()
    public static void bubbleSort(int[] array) {
        boolean flag;   // 判断是否排序完毕的标志

        // 外层每次把未排序的最值放置在已排序的开头
        // 最多只需要进行 length - 1 次，最后剩下的是最小的
        for (int i = 0; i < array.length - 1; i++) {

            flag = true;

            // 内层在未排序的部分0 ~ length - i 进行比较然后“冒泡”处理
            // 由于比较的是array[j] 与 array[j+1]所以只需要进行到 length - i -1
            for (int j = 0; j < array.length - 1 - i; j++) {
                if (array[j] > array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;

                    // 如果进行了交换，表明排序还没有结束
                    flag = false;
                }
            }

            if (flag) {
                break;
            }
        }
    }
}
