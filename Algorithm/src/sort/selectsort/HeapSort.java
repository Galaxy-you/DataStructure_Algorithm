package sort.selectsort;

import java.util.Arrays;


public class HeapSort {
    public static void main(String[] args) {
        int[] array = new int[10];

        for (int i = 0; i < array.length; i++) {
            array[i] = (int)(Math.random() * 10);
        }

        System.out.println("before sort: " + Arrays.toString(array));

        heapSort(array);

        System.out.println("after sort: " + Arrays.toString(array));

    }

    // 使用大顶堆 --> 升序排列
    public static void heapSort(int[] arr){
        // 从左往右，从下往上构造大顶堆
        // 最后一个非叶子节点的下标是 arr.length / 2 -  1，然后通过 i-- 往前调整其他的叶子节点
        for (int i = arr.length / 2 - 1 ; i >= 0 ; i--) {
            adjustHeap(arr, i, arr.length);
        }

        // 只需要交换 length - 1 次即可，因为最后一次完成后最上面的一定是最小的
        for(int j = arr.length - 1; j > 0; j--){
            // 把堆顶元素和数组的最后一个元素交换然后重新调整堆为大顶堆
            int temp = arr[0];
            arr[0] = arr[j];
            arr[j] = temp;

            // index 形参的值写0，将整个堆都进行调整
            // heapSize的值写j,即对剩余的j个元素进行重新调整为大顶堆
            adjustHeap(arr, 0, j);
        }
    }

    /**
     * 将以 arr[index] 为根节点的子树调整为大顶堆
     * @param arr       待排序的数组
     * @param index     非叶子节点在数组中的索引(子树根节点在数组中的下标)
     * @param heapSize  表示对多少个元素进行继续调整
     */
    public static void adjustHeap(int[] arr, int index, int heapSize){
        int temp = arr[index];

        // 第一次调整的为arr[index]的左孩子，然后继续往下
        // k < heapSize确保了左子树的下标不会越界，所以在循环中只需要判断右子树的下标 k + 1 是否越界即可
        for (int k = index * 2 + 1; k < heapSize; k = k * 2 + 1){
            // temp = arr[index];  // 这里并不需要每次都暂存原始子树的根节点的值，

            // 经过下面这个if语句块之后 arr[k]即为arr[index]左右孩子中较大的那一个
            // k + 1 < heapSize 确保右子节点存在
            if(k + 1 < heapSize && arr[k] < arr[k + 1]){
                k = k + 1;
            }

            // 子节点比父节点大 --> 进行调整
            if(arr[k] > temp){
                arr[index] = arr[k];
                // arr[k] = temp;   这里不要交换，只需要“下沉”，在循环中找到合适的位置在进行交换
                index = k;  // 更新子树父节点下标，下一次进入循环时index仍为需要调整的子树的根节点
            }else{
                break;  // 找到了正确的位置
            }
        }
        arr[index] = temp;  //  这里不能使用arr[k] = temp,因为 k 可能已经越界了，超出了heapSize
    }


    /**
     * 维护大顶堆的性质 (Max-Heapify)，递归实现
     * @param arr 数组 (看作堆)
     * @param heapSize 当前堆的大小 (待调整的范围)
     * @param i 待调整节点 (通常是父节点) 的索引
     */
    private static void maxHeapify(int[] arr, int heapSize, int i) {
        int largest = i; // 假设当前节点是最大值
        int left = 2 * i + 1;  // 左子节点索引
        int right = 2 * i + 2; // 右子节点索引

        // 这个递归和上面的循环不太一样，这里的左子树也必须要验证是否存在，因为递归调用无法确定i是否为非叶子节点
        // 如果左子节点存在且大于当前最大值
        if (left < heapSize && arr[left] > arr[largest]) {
            largest = left;
        }

        // 如果右子节点存在且大于当前最大值
        if (right < heapSize && arr[right] > arr[largest]) {
            largest = right;
        }

        // 如果最大值不是当前节点 (说明需要调整)
        if (largest != i) {
            // 交换当前节点与最大值节点
            swap(arr, i, largest);

            // 递归地向下调整受影响的子树
            // 这里没有改变largest的值，所以largest仍为较大的子节点的下标
            maxHeapify(arr, heapSize, largest);
        }
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}

