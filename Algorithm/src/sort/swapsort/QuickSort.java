package sort.swapsort;

import java.util.Arrays;

public class QuickSort {
    public static void main(String[] args) {
        int[] arr = new int[10];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int)(Math.random() * 10);
        }
        System.out.println("before sort: " + Arrays.toString(arr));

        // quickSortHoare(arr,0,arr.length - 1);
        quickSort(arr,0,arr.length - 1);

        System.out.println("after sort: " + Arrays.toString(arr));
    }

    // 老韩写法的精简版本
    // 设置形参 right,left => 便于递归
    public static void quickSort(int[] arr, int left, int right) {
        if (left >= right) return;

        // 选择枢轴
        int pivot = arr[(left + right) / 2];

        // 三路划分：< pivot | == pivot | > pivot
        int lt = left;      // 小于区域的右边界 lt => less than,[right,lt - 1]全为 < pivot 的元素
        int i = left;       // 当前处理元素
        int gt = right;     // 大于区域的左边界 gt => greater than，[gt + 1,left]全为 >pivot的元素

        while (i <= gt) {
            if (arr[i] < pivot) {
                swap(arr, lt, i);
                lt++;
                // 这里i要自增，因为lt <= i永远成立，即刚刚交换过来的arr[lt]已经和pivot比较过大小了，且arr[lt] <= pivot
                i++;
            } else if (arr[i] > pivot) {
                swap(arr, i, gt);
                // 注意这里 i不用自增，因为还需要比较刚刚交换过来的arr[gt]和pivot的大小
                // arr[gt]不一定比pivot大，因为gt初始化为right，只有排完之后，arr[gt]才会比pivot大
                gt--;
            } else {
                i++;
            }
        }

        //此时array的情况为 left...lt pivot*n gt...right
        // 递归排序小于和大于枢轴的部分
        quickSort(arr, left, lt - 1);
        quickSort(arr, gt + 1, right);
    }


    /**
     * 快速排序主函数
     * @param arr 待排序数组
     * @param low 排序区间的起始位置
     * @param high 排序区间的结束位置
     */
    public static void quickSortHoare(int[] arr, int low, int high) {
        if (low < high) {
            // pivot n.支点，枢纽，中心，中心点 v.（使）在枢纽上旋转
            // 获取分区点的位置
            int pivotIndex = partition(arr, low, high);

            // 递归排序分区点左侧的子数组
            quickSortHoare(arr, low, pivotIndex - 1);

            // 递归排序分区点右侧的子数组
            quickSortHoare(arr, pivotIndex + 1, high);
        }
    }




    /**
     *
     * @param arr   待排序数组
     * @param low   起始位置（下标）
     * @param high  结束位置（下标）
     * @return      返回 pivot 在已划分数组中的下标位置，即pivotIndex
     */
    // partition n.隔墙，隔板，分割，分治  v.隔开，分裂，使分治
    public static int partition(int[] arr, int low, int high) {
        // 选择最右边的元素作为枢轴
        int pivot = arr[high];

        // i表示小于等于pivot的区域的边界
        int i = low - 1;    // low - 1（子数组实际开始位置的前一位）表示这个区域是空的

        // 遍历low到high-1的元素，
        for (int j = low; j < high; j++) {
            // 如果当前元素小于等于枢轴值，扩展小于区域并交换元素
            if (arr[j] <= pivot) {
                i++;
                // 把小于等于 pivot 的元素swap到arr的前面，实现 [low,i]均为 <= pivot 的元素
                swap(arr, i, j);
            }
        }

        // 将枢轴放到其最终位置 pivotIndex = i + 1
        swap(arr, i + 1, high);

        // 返回枢轴的最终位置
        return i + 1;
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

}