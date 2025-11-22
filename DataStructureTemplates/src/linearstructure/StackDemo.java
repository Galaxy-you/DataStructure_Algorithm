package linearstructure;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

/**
 * author: Galaxy Violet
 * date: 2025/11/17, 21:58
 */

/* 简要介绍
    栈是一种后进先出（LIFO: Last In First Out）的线性数据结构。
    主要操作就三个:push, pop, peek
    peek v.偷看， 窥视，微露出，探出 n偷偷的一看，一瞥
    push = addFirst, pop = removeFirst
 */
/* 适用场景
    括号匹配问题 ⭐
    表达式求值（逆波兰表达式）
    函数调用栈
    深度优先搜索（DFS）
    单调栈问题 ⭐⭐⭐（找下一个更大/更小元素）
    浏览器前进后退
    撤销操作
 */

// pop前一定要检查是否为空栈，否则保存NoSuchElementException
// peek前也要检查一下，如果是空栈，peek()返回的是null
public class StackDemo {
    // java中有遗留类 Stack（继承自Stack），不推荐使用
    // java中实现栈的方法主要有两种：ArrayDeque 或者 LinkedList
    // deque 即 double ended queue 双端队列
    public static void main(String[] args) {
        Deque<Integer> stack = new ArrayDeque<>();

        // ========== 基本操作 ==========

        // 入栈
        stack.push(1);
        stack.push(2);
        stack.push(3);
        System.out.println("栈: " + stack); // [3, 2, 1]（3在顶部）

        // 查看栈顶（不删除）
        int top = stack.peek();
        System.out.println("栈顶: " + top); // 3

        // 出栈（删除并返回）
        int popped = stack.pop();
        System.out.println("出栈: " + popped); // 3
        System.out.println("栈: " + stack); // [2, 1]

        // 是否为空
        System.out.println("是否为空: " + stack.isEmpty()); // false

        // 大小
        System.out.println("大小: " + stack.size()); // 2


        // ========== 遍历（不常用，破坏栈结构）==========

        // 方式1：边出栈边处理
        while (!stack.isEmpty()) {
            System.out.println(stack.pop());
        }
        // 输出: 2, 1（从栈顶到栈底）

        // 方式2：迭代器（保留栈）（增强for循环的本质是迭代器）
        stack.push(1);
        stack.push(2);
        stack.push(3);

        for (int num : stack) {
            System.out.print(num + " "); // 3 2 1
        }
    }
}

// 括号匹配(letCode 20)
// parenthesis n.插入语，括号
class ValidParentheses{
    // s 中包含三种括号以及其他的字符 ( [ {
    public static boolean isValid(String s){
        char[] charArray = s.toCharArray();
        ArrayDeque<Character> stack = new ArrayDeque<>();

        for(char c: charArray){
            // 括号有三种
            if(c == '(' || c == '[' || c == '{'){
                stack.push(c);
            } else if(c == ')'){
                if(stack.isEmpty()){
                    return false;
                }
                Character top = stack.pop();    // pop 前需要判断栈是否为空
                return top == '(';
            }else if(c == ']'){
                if(stack.isEmpty()){
                    return false;
                }
                Character top = stack.pop();    // pop 前需要判断栈是否为空
                return top == '[';
            }else if(c == '}'){
                if(stack.isEmpty()){
                    return false;
                }
                Character top = stack.pop();    // pop 前需要判断栈是否为空
                return top == '{';
            }
        }
        return stack.isEmpty(); // 最后括号栈必须为空
    }

}

// 逆波兰表达式（后缀表达式）求值
// 中缀转后缀表达式或者直接计算中缀表达式需要考虑优先级
// 但是计算后缀表达式的时候不需要考虑优先级
class EvalRPN{
    public static int evalRPN(String expression){
        char[] charArray = expression.toCharArray();
        ArrayDeque<Integer> stack = new ArrayDeque<>();

        for(char c: charArray){
            // 如果是运算符，弹出两个数，并将计算结果入栈
            if(c == '+' || c == '-' || c == '*' || c == '/'){
                int num2 = stack.pop();
                int num1 = stack.pop();
                if(c == '+') stack.push(num1 + num2);
                else if(c == '-') stack.push(num1 - num2);
                else if(c == '*') stack.push(num1 * num2);
                else stack.push(num1 / num2);
            }
            else {  // 如果是数字直接入栈
                stack.push(Integer.parseInt(c + ""));
            }
        }
        return stack.pop(); // 最后一个元素即为运算结果
    }
}


// 下一个更大元素：给定数组，寻找每一个元素右边第一个比它大的元素，没有的话就写-1
// 单调栈，存储的是索引，而不是值
class NextGreaterElement{
    // 找每个元素右边第一个比它大的元素
    // 暴力解法，时间复杂度：O(n^2)
    public static int[] nextGreaterBrute(int[] nums){
        int length = nums.length;
        int[] result = new int[length];

        for(int i = 0; i < length; i++ ){
            result[i] = -1; // 默认没有，写入-1
            for(int j = i + 1; j < length; j++){
                if(nums[j] > nums[i]){
                    result[i] = nums[j];    // i右边第一个比它大的元素
                    break;
                }
            }
        }
        return result;
    }

    // 单调递减栈（栈顶元素最小，从栈底到栈顶越来越小）
    // 遇到比栈顶大的元素，就不断弹出栈顶
    // 直到栈顶比当前元素大（或栈为空）
    public static int[] nextGreaterElement(int[] nums){
        int length = nums.length;
        int[] result = new int[length];
        Arrays.fill(result, -1);

        ArrayDeque<Integer> stack = new ArrayDeque<>(); // 存储索引

        for (int i = 0; i < length; i++) {
            // [2,1,2,4,3]
            // 当前元素比栈顶索引对应的元素大
            // 说明栈顶索引对应的元素nums[stack.pop()]的下一个更大元素即为nums[i],将其存放到result中
            while (!stack.isEmpty() && nums[i] > nums[stack.peek()]){
                int index = stack.pop();
                result[index] = nums[i];
            }
            stack.push(i);  // 注意这里push的是index而不是nums[i]
        }
        return result;
    }
    /*
    使用单调递减栈，在遍历nums时，
    当前的nums[i]可能是之前的num[0]~nums[i - 1]对应的下一个最大值
    由于按照递减顺序进行之前的num[0]~nums[i-1]的保存，
    所以可以通过出栈为这些元素的result[i]赋当前的nums[i]
     */
    // 下一个更小元素只需要把 179行的大于号改成小于号，变成单调递增栈
    // 上一个更大元素仍然使用单调递减栈，不过递归的方向需要改变，
    /*
        for(int i = n - 1; i >= 0; i--){
            while(!stack.isEmpty() && nums[i] > nums[stack.peek()]){
                int index =  stack.pop();
                result[index] = nums[i]
            }
        }
        stack.push(i);
     */
}
