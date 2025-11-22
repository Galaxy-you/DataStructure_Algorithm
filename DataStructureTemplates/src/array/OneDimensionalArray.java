package array;

/**
 * author: Galaxy Violet
 * date: 2025/11/17, 10:37
 */

/* 简要解释
 * 一维定长数组，在内存中连续存储相同类型的元素集合
 * 数组通过索引（从0开始）来访问元素，支持 O(1) 时间的随机访问。
 */

import java.util.*;
import java.util.stream.IntStream;
/* Arrays是数组的工具类，常用的方法有
    Arrays.fill(arr, value)             填充数组
    Arrays.copyOf(arr, arr.length)      深拷贝数组
    Arrays.copyOfRange(arr, start, end) 数组切片
    Arrays.sort(arr)                    升序排序数组
    Arrays.toString(arr)                数组转换为字符串
    Arrays.equals(arr1, arr2)           判断两个数组是否相等
    Arrays.binarySearch(arr, target)    二分查找（需已有序）


 */

/* 适用场景
 * 存储输入数据
 * 动态规划的 dp 数组
 * 记录访问状态（visited 数组）
 * 计数、频率统计
 */
public class OneDimensionalArray {
    // ========== 创建和初始化 ==========
    // 1. 基本创建方式
    public static void createArray() {
        // 方式1: 指定长度（默认值：数字0，boolean false，对象null）
        int[] arr1 = new int[5];

        // 方式2: 直接初始化
        int[] arr2 = {1, 2, 3, 4, 5};

        // 方式3: new 关键字初始化（数组的长度可以省略）
        int[] arr3 = new int[]{1, 2, 3, 4, 5};
    }

    // 2. 填充数组（使用Arrays.fill()方法）
    public static int[] fillArray(int n, int value) {
        int[] arr = new int[n];
        Arrays.fill(arr, value);
        return arr;
    }

    // 3. 复制数组
    public static int[] copyArray(int[] arr) {
        // 方式1: Arrays.copyOf
        int[] copy1 = Arrays.copyOf(arr, arr.length);

        // 方式2: clone
        int[] copy2 = arr.clone();

        // 方式3: System.arraycopy (最快)
        int[] copy3 = new int[arr.length];
        System.arraycopy(arr, 0, copy3, 0, arr.length);

        return copy1;
    }

    // ========== 常用方法/技巧 ==========

    // 1. 反转数组
    public static void reverse(int[] arr) {
        int left = 0, right = arr.length - 1;
        while (left < right) {
            int temp = arr[left];
            arr[left] = arr[right];
            arr[right] = temp;
            left++;
            right--;
        }
    }

    // 2. 查找元素（线性查找）
    public static int linearSearch(int[] arr, int target) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == target) {
                return i; // 返回索引
            }
        }
        return -1; // 未找到
    }

    // 3. 二分查找（数组必须已排序）
    public static int binarySearch(int[] arr, int target) {
        int left = 0, right = arr.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (arr[mid] == target) {
                return mid;
            } else if (arr[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return -1; // 未找到
    }

    // 4. 排序
    public static void sort(int[] arr) {
        Arrays.sort(arr); // 升序排序
    }

    // 5. 降序排序（需要转换为 Integer[]）
    public static void sortDescending(Integer[] arr) {
        Arrays.sort(arr, (a, b) -> b - a);
    }

    // 6. 找最大值
    public static int findMax(int[] arr) {
        if (arr.length == 0) throw new IllegalArgumentException("数组为空");
        int max = arr[0];
        for (int num : arr) {
            max = Math.max(max, num);
        }
        return max;
    }

    // 7. 求和
    public static long sum(int[] arr) {
        long sum = 0;
        for (int num : arr) {
            sum += num;
        }
        return sum;
    }

    // 8. 数组切片（获取子数组）
    public static int[] slice(int[] arr, int start, int end) {
        return Arrays.copyOfRange(arr, start, end); // end 不包含
    }

    // 9. 数组旋转（向右旋转 k 步）
    public static void rotate(int[] arr, int k) {
        int n = arr.length;
        // 这里入如果k可以是的话负数，需要这样写 k = ((k % n) + n) % n; 这样可以确保k为正数
        // 对于 k < 0 不能直接写 k = k + n;因为如果k太小的话这样k还是负数
        k = k % n; // 处理 k > n 的情况
        reverse(arr, 0, n - 1); // 翻转整个数组
        reverse(arr, 0, k - 1); //
        reverse(arr, k, n - 1);
    }

    // 数组旋转的辅助方法：将数组从 [start, end]进行翻转
    private static void reverse(int[] arr, int start, int end) {
        while (start < end) {
            int temp = arr[start];
            arr[start] = arr[end];
            arr[end] = temp;
            start++;
            end--;
        }
    }

    // 10. 打印数组
    public static void print(int[] arr) {
        System.out.println(Arrays.toString(arr));
    }

    // 11.数组去重
    // （1）双指针 适用于数组是有序的情况，详见main函数

    // （2）要求保持去重后顺序仍然和原数组保持一致，使用LinkedHashSet
    public static int[] removeDuplicates(int[] arr){
        Set<Integer> set = new LinkedHashSet<>();   // 这里使用LinkedHashSet可以保持顺序，使用HashSet的顺序是由Hash函数确定的
        for(int num: arr){
            set.add(num);   // HashSet自动去重
        }
        int[] unique = new int[set.size()];
        int i = 0;
        for(int num: set){
            unique[i++] = num;
        }
        return unique;
    }

    // （3）只要求去重，不关心顺序，使用HashSet
    public static int[] removeDuplicatesWithHashSet(int[] arr){
        Set<Integer> set = new HashSet<>();

        for(int num: arr){
            set.add(num);
        }

        int[] unique = new int[set.size()];
        int i = 0;
        for(int num: set){
            unique[i++] = num;
        }
        return unique;
    }

    // （4）使用现有 Stream API
    public static int[] removeDuplicatesStream(int[] arr){
        IntStream distinct = Arrays.stream(arr).distinct();
        return distinct.toArray();
    }



    public static void main(String[] args) {
        int[] arr = {1,2,3,2,4,2,5};

        // 查找指定元素出现的次数
        int target = 2;
        int count = 0;
        for(int num: arr){
            if(num == target){
                count++;
            }
        }
        System.out.println("count = " + count);

        // 查找第二大的值
        Arrays.sort(arr);
        System.out.println("secondMax = " + arr[arr.length - 2]);


        // 数组去重 duplicate v.复制，重复 adj.复制的，成对的   n.复制品，副本
        int[] duplicate = {1, 2, 5, 4, 2, 3, 4, 4, 5};
        Arrays.sort(duplicate);
        int uniqueCount = 1;    // duplicate[0]唯一，之后从第二个元素开始去重; uniqueCount相当于是去重后的数组的索引
        for (int i = 1; i < duplicate.length; i++) {
            if (duplicate[i] != duplicate[i - 1]) {   // 后一个元素和前一个元素不相等
                duplicate[uniqueCount++] = duplicate[i];
            }
        }
        int[] unique = Arrays.copyOf(duplicate, uniqueCount);   // 这里的newLength: uniqueCount不需要减1,因为uniqueCount是index而不是length
        System.out.println("unique array : " + Arrays.toString(unique));


        int[] duplicate2 = {1, 2, 5, 4, 2, 3, 4, 4, 5};
        System.out.println(Arrays.toString(removeDuplicates(duplicate2)));
        System.out.println(Arrays.toString(removeDuplicatesWithHashSet(duplicate2)));
        System.out.println(Arrays.toString(removeDuplicatesStream(duplicate2)));
    }
}
