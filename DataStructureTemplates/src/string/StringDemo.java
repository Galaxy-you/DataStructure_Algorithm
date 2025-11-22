package string;

import java.util.*;

/**
 * author: Galaxy Violet
 * date: 2025/11/22, 19:10
 */
public class StringDemo {
    public static void main(String[] args) {
        /*
            创建字符串
         */
        String string = "hello";    // 字面量
        String s2 = new String("hello");    // new 关键字

        char[] chars = {'h', 'e', 'l', 'l', '0'};
        String s3 = new String(chars);  // 使用字符数组创建字符串

        byte[] bytes = {72, 101, 108, 108, 111};
        String s4 = new String(bytes);  // 使用字节数组创建字符串
        System.out.println(s4);

        // 通过StringBuilder/StringBuffer创建字符串

        /*
            字符串的长度
         */
        int len = string.length();
        System.out.println(string.isEmpty());   // 判断字符串是否为null


        /*
            访问字符串
         */
        // 获取指定位置的字符
        char ch = string.charAt(1);

        // 转化为字符数组
        char[] charArray = string.toCharArray();


        /*
            遍历字符串
         */
        // 1、使用charAt()方法进行下标遍历
        for (int i = 0; i < string.length(); i++) {
            char c = string.charAt(i);
        }

        // 2、使用toCharArray + 增强for循环进行遍历
        for(char c: string.toCharArray()){
            System.out.println(c);
        }


        /*
            字符串比较
         */
        // 1、比较内容是否相等
        // equals()             区分大小写
        // equalsIgnoreCase()   不区分大小写
        System.out.println(s2.equals(s3));
        System.out.println(s2.equalsIgnoreCase(s3));

        // 2、比较引用是否相等
        System.out.println(string == s2);   // false

        // 3、字典序比较
        // 不能直接使用 >, <进行比较，需要使用compareTo()方法进行比较
        // compareToIgnoreCase  忽略大小写的字典序比较
        System.out.println(string.compareTo(s2));


        /*
            查找字符/子串
         */
        // string.indexOf(c / subString)    返回字符 / 子串第一次出现的位置，没有找打返回-1
        // string.lastIndexOf(c / subString)    返回字符 / 子串最后一次出现的位置
        int idx1 = string.indexOf('a');
        int idx2 = string.indexOf("el");


        // contains(subStr)     是否包含子串
        // startWith(subStr)    是否以指定前缀开头
        // endWith(subStr)      是否以指定后缀结尾


        // subString(index)         从指定位置截取到末尾
        // subString(strat, end)    截取[start, end)范围内的子串

        // toLowerCase()        转为小写
        // toUpperCase()        转为大写

        // s.trim()     去除首尾空白   trim v.修剪，修整，点缀，苗条的，修长的
        // s.strip()    去除首尾空白（支持Unicode空白字符）   strip v.剥夺，扒光...的衣服 n.条，狭长地带
        // s.stripLeading()
        // s.stripTrailing()


        // replace(old, new)    old / new 可以为字符或者字符串
        // 注意replace方法会替换所有的old 字符/字符串
        System.out.println(s3.replace("l", "L"));

        // 拼接：
        // 1、使用 + 进行拼接
        // 2、使用concat()进行拼接     concat v.合并多个数组，合并多个字符串
        // 3、使用join()进行拼接。可以指定分隔符
        // 大量拼接使用StringBuilder的append()方法   append v.增补
        String s5 = String.join(", ", "a", "b", "c");  // "a, b, c"

        // split(String regex, int limit)   返回String[]
        // split v.劈开，切开，撕裂，分担  n.劈开，薄片
        // regex 正则表达式


        /*
            转换
            parse v.（对句子）做语法分析，做句法分析
         */
        // 字符串 → 整数
        int num1 = Integer.parseInt("123");     // 123
        int num2 = Integer.valueOf("123");      // 123

        // 字符串 → 长整数
        long num3 = Long.parseLong("123456789");

        // 字符串 → 浮点数
        double num4 = Double.parseDouble("3.14");

        // 字符串 → 布尔值
        boolean bool = Boolean.parseBoolean("true");  // true

        // 字符串 → 字符数组
        char[] chars1 = "hello".toCharArray();  // ['h', 'e', 'l', 'l', 'o']

        // 字符串 → 字节数组
        byte[] bytes1 = "hello".getBytes();  // [104, 101, 108, 108, 111]


        /*
        分类	    方法	            作用
        长度	    length()	    获取长度
                isEmpty()	    是否为空

        访问	    charAt(i)	    获取第 i 个字符
                toCharArray()	转为字符数组

        比较	    equals()	    内容相等
                compareTo()	    字典序比较

        查找	    indexOf()	    查找位置
                contains()	    是否包含
                startsWith()	是否以...开头
                endsWith()	    是否以...结尾

        截取	    substring(start, end)	截取子串

        转换	    toLowerCase()	    转小写
                toUpperCase()	    转大写
                trim()	            去除首尾空白

        替换	    replace()	        替换字符/子串
                replaceAll()	    正则替换

        分割	    split()	            分割字符串

        拼接	    concat(), +, join()	拼接字符串

        格式化	String.format()	格式化输出
         */

    }


    /*
    Sliding Window
    维护一个可变或固定大小的窗口，在数组/字符串上滑动，用于解决子数组/子串问题
    核心思想：通过移动窗口的左右边界，避免重复计算，将暴力 O(n²) 优化到 O(n)
    适用场景：
        ·最长/最短子串
        ·固定长度子数组
        ·满足某条件的子串/子数组
     */

    // 固定大小滑动窗口----模板
    // 窗口大小为k
    public void fixedWindowTemplate(int[] nums, int k){
        int n = nums.length;
        int windowSum = 0;  // 窗口内的状态，此处为求窗口的最大元素和

        // 初始化第一个窗口
        for (int i = 0; i < k; i++){
            windowSum += nums[i];
        }
        // 这里是对第一个窗口做验证/操作，不要到for循环里去做验证/操作
        int result = windowSum;

        // 这里从第二个窗口开始处理，所有循环内部上来就移动窗口的边界
        for(int right = k; right < n; right++){
            windowSum += nums[right];       // 右边界进入窗口
            windowSum -= nums[right - k];   // 左边界离开窗口

            // 对新窗口做验证 / 计算
            result = Math.max(result, windowSum);   // 更新结果
        }
    }

    // 给定数组和整数 k，找出所有长度为 k 的连续子数组中的最大和。
    public int maxSumSubarray(int[] nums, int k) {
        int n = nums.length;
        if (n < k) return -1;

        // 初始化第一个窗口的和
        int windowSum = 0;
        for (int i = 0; i < k; i++) {
            windowSum += nums[i];
        }

        int maxSum = windowSum;

        // 滑动窗口
        for (int right = k; right < n; right++) {
            windowSum += nums[right];       // 加入新元素
            windowSum -= nums[right - k];   // 移除旧元素
            maxSum = Math.max(maxSum, windowSum);
        }

        return maxSum;
    }

    // LetCode438: 给定字符串 s 和 p，找出 s 中所有 p 的字母异位词的起始索引
    // 思路分析：滑动窗口的大小为 k = p.length，然后在字符串s上滑动
    // 判断窗口内的字符串是p的字母异位词的方法：统计窗口内和p的所有字母的出现次数是否相等
    public List<Integer> findAnagrams(String s,  String p){
        ArrayList<Integer> result = new ArrayList<>();

        if(s.length() < p.length()){
            return result;
        }

        int[] pCount = new int[26]; // 字符串p的字符计数
        int[] windowCount = new int[26];    // 滑动窗口的字符计数

        for(char c: p.toCharArray()){
            pCount[c - 'a']++;
        }

        int k = p.length(); // 窗口大小为k， 然后在字符串s上进行滑动判断

        // 初始化第一个窗口
        for(int i = 0; i < k; i++){
            windowCount[s.charAt(i) - 'a']++;
        }

        // 下面的注释写法是错误的，会漏验证最后一个窗口是否符合条件
//        for(int right = k; right < s.length(); right++){
//            if(Arrays.equals(pCount, windowCount)){
//                result.add(right - k);  // 起始索引即为窗口的左边界
//            }
//            windowCount[s.charAt(right) - 'a']++;
//            windowCount[s.charAt(right - k) - 'a']--;
//        }
        // 验证第一个窗口是否符合条件
        if(Arrays.equals(windowCount, pCount)){
            result.add(0);
        }

        // 滑动窗口
        for(int right = k; right < s.length(); right++){
            windowCount[s.charAt(right - k) - 'a']--;
            windowCount[s.charAt(right) - 'a']++;

            if(Arrays.equals(windowCount, pCount)){
                result.add(right - k + 1);
            }
        }
        return result;
    }

    // 可变滑动窗口----模板
    // 有两个while循环，while(right < nums.length) + while(...){//压缩窗口...}
    public void variableWindowTemplate(int[] nums){
        int left = 0, right = 0;
        int windowState = 0;    // 窗口状态

        while (right < nums.length){
            // 扩大窗口：右边界右移，加入 nums[right]
            windowState += nums[right];
            right++;

            // 收缩窗口：当窗口不满足条件时，左边界右移
            while (windowState > 100){
                windowState -= nums[left];
                left--;
            }

            // 在窗口收缩后更新结果
            // ...
        }
    }

    // LeetCode 3 最长无重复字符子串
    // 给定字符串，找出最长的不含重复字符的子串长度。
    public int lengthOfLongestSubstring(String s){
        if(s == null){
            return 0;
        }

        int left = 0,right = 0;
        Set<Character> window = new HashSet<>();    // 窗口内的字符
        int result = 0;

        while (right < s.length()){
            char rightChar = s.charAt(right);

            //  如果字符已存在，收缩窗口直到移除重复字符
            while (window.contains(rightChar)){
                window.remove(s.charAt(left));
                left++;
            }
            // 扩大窗口
            window.add(rightChar);
            right++;    // 不要忘记++

            result = Math.max(result, right - left);    // 这里 right - left == set.size()，但是还是建议使用 left - right ，因为这是所有滑动窗口问题通用的窗口长度
        }
        return result;
    }

    // LeetCode 76
    // 最小覆盖子串：给定字符串 s 和 t，找出 s 中包含 t 所有字符的最小子串。
    public String minWindow(String s, String t){
        if(s.length() < t.length()){
            return null;
        }
        // 统计t中字符的频率
        HashMap<Character, Integer> need = new HashMap<>();
        for(char c: t.toCharArray()){
            need.put(c, need.getOrDefault(c, 0) + 1);
        }

        HashMap<Character, Integer> window = new HashMap<>();
        int left = 0, right = 0;
        int valid = 0;  // 已经满足的字符种类数
        int start = 0;
        int minLen = Integer.MAX_VALUE;

        while (right < s.length()){
            char c = s.charAt(right);
            right++;

            // c是需要的字符
            if(need.containsKey(c)){
                window.put(c, window.getOrDefault(c, 0) + 1);
                if(window.get(c).equals(need.get(c))){  // !!! 这里使用的是equals,比较的对象是Integer，是引用类型， 不能直接使用 ==
                    valid++;    // 字符c的数量已经满足
                }
            }

            // 当所有的字符都满足是收缩窗口
            // 收缩窗口后可能会使当前窗口不符合条件，但是没有问题，start和minLen已经记录
            // 可以滑动窗口查看之后有没有更小的minLen符合条件
            while(valid == need.size()){
                // 更新最小窗口   right在之前已经自增，所以这里right - left即为窗口的大小
                if(right - left < minLen){
                    start = left;
                    minLen = right - left;
                }

                // 移动窗口左边界，去尝试寻找更小的符合条件的窗口
                char d = s.charAt(left);
                left++;

                if(need.containsKey(d)){
                    if(window.get(d).equals(need.get(d))){
                        valid--;
                    }
                    window.put(d, window.get(d) - 1);
                }
            }
        }
        return minLen == Integer.MAX_VALUE ? null : s.substring(start, start + minLen);
    }

    // 最长连续1的个数（最多翻转k个0，这k个0可以不连续）
    // LeetCode 1004:给定二进制数组和整数 k，最多可以将 k 个 0 翻转为 1，求最长连续 1 的个数。
    public int longestOnes(int[] nums, int k){
        int left = 0, right = 0;
        int zeros = 0;  // 窗口内 0 的个数
        int maxLen = 0;

        while(right < nums.length){
            if(nums[right] == 0){
                zeros++;
            }
            right++;

            // 窗口内 0 的个数多于 k，移动左边界减少窗口内0的个数
            while (zeros > k){
                if(nums[left] == 0){
                    zeros--;
                }
                left++;
            }

            maxLen = Math.max(maxLen, right - left);
        }
        return maxLen;
    }

}
