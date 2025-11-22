package algorithms.kmp;

import java.util.Arrays;

/**
 * author: Galaxy Violet
 * date: 2025/11/16, 18:56
 */
public class KMPAlgorithm {
    public static void main(String[] args) {
        String string = "BBC ABCDAB ABCDABCDABDE";
        String subStr = "ABCDABD";

        int res = kmpSearch(string, subStr);
        System.out.println("res = " + res);

    }

    // 获得子串的部分匹配表
    public static int[] getNext(String string){
        int length = string.length();
        int[] next = new int[length];

        // 字符串的长度为1时，没有前缀和后缀
        next[0] = 0;

        int j = 0;  // j 表示最长相等前后缀的长度
        for (int i = 1; i < length; i++) {
            // 如果当前字符串不匹配，回退j到next[j - 1]
            // 此时的j是当前已经匹配的前缀长度，即next[i - 1]
            // 注意这里 j > 0,
            while (j > 0 && string.charAt(i) != string.charAt(j)){
                j = next[j - 1];
                // j--; // j--也可以实现回退，但是效率太低，
            }
            // 如果当前字符匹配，最长前后缀长度 + 1
            if(string.charAt(i) == string.charAt(j)){
                j++;
            }
            next[i] = j;
        }
        return next;
    }

    public static int kmpSearch(String string, String subStr) {
        int[] next = getNext(subStr);
        System.out.println("next = " + Arrays.toString(next));

        int length = string.length();
        int subLength = subStr.length();

        int i = 0;
        int j = 0;  // 子串的index
        while (i < length && j < subLength){
            if(string.charAt(i) == subStr.charAt(j)){
                i++;
                j++;
            }else{  // 匹配不成功
                if(j != 0){
                    j = next[j - 1];
                }else {
                    i++;
                }
            }
        }
        if(j == subLength){
            return i - j;
        }
        return -1;
    }
}
