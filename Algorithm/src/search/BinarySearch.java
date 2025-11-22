package search;

import java.util.ArrayList;

public class BinarySearch {
    public static void main(String[] args) {
        // 使用二分查找的数组要求必须是有序的
        int[] array = new int[]{1,3,4,5,7,8,8,8,8,9,10,13};

        int index1 = binarySearch(array, 99);
        System.out.println("index1 = " + index1);

        int index2 = binarySearchRecursion(array,  0 , array.length- 1, 99);
        System.out.println("index2 = " + index2);

        ArrayList<Integer> resIndexList1 = binarySearchAllRecursion(array, 0, array.length - 1, 8);

        // Unnecessary 'toString()' call
        System.out.println(resIndexList1.toString());

        ArrayList<Integer> resIndexList2 = binarySearchAllRecursion(array, 0, array.length - 1, 1000);
        System.out.println(resIndexList2);  // []
    }

    // 二分查找非递归实现
    public static int binarySearch(int[] array, int findValue){
        int left = 0;
        int right = array.length - 1;
        int mid = 0;    // Variable 'mid' initializer '0' is redundant // redundant adj.多余的，不需要的，被裁减的
        int findIndex = -1;

        while (left <= right){  // 注意这里有 =
            mid = (left + right) / 2;
            if(array[mid] == findValue){
                findIndex = mid;
                break;
            } else if (array[mid] < findValue) {
                left = mid + 1;
            }else {
                right= mid - 1;
            }
        }
        return findIndex;
    }

    // 二分查找递归实现
    public static int binarySearchRecursion(int[] array, int left, int right, int findValue){

        int mid = (right + left) / 2;
        int midValue = array[mid];

        // 递归出口
        if(midValue == findValue){
            return mid;
        }
        else if (left > right){
            return  -1;
        }

        if (midValue > findValue) { // 向左递归
            return binarySearchRecursion(array, left, mid - 1, findValue);
        }else { // 向右递归
            return  binarySearchRecursion(array,mid + 1, right, findValue);
        }

    }


    // 二分查找递归实现
    // 这里会有查找所有的 findValue,并返回一个ArrayList
    public static ArrayList<Integer> binarySearchAllRecursion(int[] array, int left, int right, int findValue){

        int mid = (right + left) / 2;
        int midValue = array[mid];

        // 递归出口
        if(midValue == findValue){
            ArrayList<Integer> resList   = new ArrayList<>();
            // 自动装箱
            resList.add(mid);

            int temp = mid - 1;
            while (true){
                // 注意这里要考虑下标的问题
                if(temp < 0 || array[temp] != findValue){
                    break;
                }
                else {
                    resList.add(temp--);
                }
            }
            temp = mid + 1;
            while (true){
                if(temp >= array.length || array[temp] != findValue){
                    break;
                }else {
                    resList.add(temp++);
                }
            }
            return resList;
        }
        else if (left > right){
            return  new ArrayList<>();
        }

        if (midValue > findValue) { // 向左递归
            return binarySearchAllRecursion(array, left, mid - 1, findValue);
        }else { // 向右递归
            return  binarySearchAllRecursion(array,mid + 1, right, findValue);
        }
    }
}
