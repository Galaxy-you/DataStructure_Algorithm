package algorithms.kmp;

/**
 * author: Galaxy Violet
 * date: 2025/11/15, 21:11
 */

// brute force（BF） 解决字符串匹配问题
public class BruteForce {
    public static void main(String[] args) {
        String string = "hai world,hello world";
        String subStr = "hello";

        System.out.println(violentMatch(string, subStr));

    }

    // bf
    // 返回子串在原字符串中出现的index，匹配失败返回 -1
    public static int violentMatch(String str, String subStr){
        char[] chars = str.toCharArray();
        int length = chars.length;

        char[] subChars = subStr.toCharArray();
        int subLength = subChars.length;

        int i = 0;
        int j = 0;

        while (i < length && j < subLength){
            if(chars[i] == subChars[j]){
                i++;
                j++;
            }else{
                i = i - j + 1;
                j = 0;
            }
        }

        if(j == subLength){ // 注意这里是 subLength，而不是 subLength - 1，毕竟退出循环了
            return i - j;   // 这里不要 +1，因为匹配成功后j++,同时i++
        }else {
            return -1;
        }
    }
}
