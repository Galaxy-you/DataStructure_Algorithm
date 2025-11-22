package stack;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * author: galaxy-violet
 * date: 2025/10/14,08:49
 * notation n.记谱法，标记法
 */
public class PolandNotation {
    // 中缀表达式 => 后缀表达式
    public static void main(String[] args) {

        String infixExpression = "1+((2+3)*4)-5";

        // 中缀表达式（String） => list(ArrayList<String>)
        List<String> infixExpressionList = toInfixExpressionList(infixExpression);
        System.out.println("infixExpression: " + infixExpressionList);

        // 中缀表达式 => 后缀表示式
        // [1, +, (, (, 2, +, 3, ), *, 4, ), -, 5] => [1,2,3,+,4,*,+,5,-]
        List<String> suffixExpressionList = parseSuffixExpression(infixExpressionList);
        System.out.println("suffixExpression: " + suffixExpressionList);

        // 逆波兰表达式（后缀表达式）求值
        // suffix n.后缀  v.加……作为后缀，把……附在后头


//        // 后缀表达式中操作数和操作符以空格间隔
//        String suffixExpression = "30 4 + 5 * 6 -";
//        // 1、先将表达式放入ArrayList
//        List<String> suffixExpressionList = toSuffixExpressionList(suffixExpression);
//        System.out.println(suffixExpressionList);


        // 逆波兰式的计算
        /*
        从左到右扫描
            => 遇到数字直接入数字栈
            => 遇到运算符，弹出两个数字，第一个数字为num2,第二个数字为num1，然后计算并将结果入栈
         */
        int res = calc(suffixExpressionList);
        System.out.println("result = " + res);
    }


    // 中缀表达式（String） => list(ArrayList<String>)
    public static List<String> toInfixExpressionList(String infixExpression){
        ArrayList<String> infixList = new ArrayList<>();

        int i = 0;
        String keepNum ; // 拼接多位操作数
        char c;
        int infixExpressionLength = infixExpression.length();

        // 不是用for - i循环，因为多位数的拼接过程中会改变i的值，不规范，考虑使用do-while循环
        do{
            c = infixExpression.charAt(i);

            if(c == '+' || c == '-' || c == '*' || c == '/' || c == '(' || c == ')'){
                infixList.add(String.valueOf(c));
                i++;
            }else if(c == ' ' || c == '\t'){
                i++;
            }else {
                // 首先清空keepNum
                keepNum = "";

                // 考虑多位数的拼接
                while(i < infixExpressionLength && (((c = infixExpression.charAt(i)) >= '0' && c <= '9') || c == '.') ){
                    keepNum += c;
                    i++;
                }
                infixList.add(keepNum);
            }
        }while(i < infixExpressionLength);

        return infixList;
    }

    // 中缀 => 后缀
    public static List<String> parseSuffixExpression(List<String> infixExpressionList){
        ArrayList<String> suffixExpression;

        Stack<String> s1 = new Stack<>();
        // 栈s2在整个转换过程中没有pop操作，直接使用 ArrayList
        ArrayList<String> s2 = new ArrayList<>();

        for(String item : infixExpressionList){
            // 数字（整数或者小数）直接入s2
            if(item.matches("\\d+\\.?\\d*|\\.\\d+")){
                s2.add(item);
            }else if(item.equals("(")){
                s1.push(item);
            } else if (item.equals(")")) {
                // 字符串的不等于操作不要写 != ,!= 比较的是两个引用的地址，而应该使用 !string1.equals(string2)
                while (!s1.peek().equals("(")){
                    String topItem = s1.pop();
                    s2.add(topItem);
                }
                // pop "("
                s1.pop();
            }else{
                // s1栈为空时，直接入栈
                if(s1.isEmpty()){
                    s1.push(item);
                }
                // 当符号栈顶为"("时或者，直接入栈
                else if(s1.peek().equals("(")){
                    s1.push(item);
                }else{
                    // 栈顶操作符优先级 >= currPriority => 循环弹出s1中的符号入s2直至topPriority < currPriority,然后curr入s1
                    // topPriority < currPriority => curr直接入s1
                    while(!s1.isEmpty() && Operator.getPriority(s1.peek()) >= Operator.getPriority(item)){
                        s2.add(s1.pop());
                    }
                    s1.push(item);
                }
            }
        }

        // 将s1中剩余的运算符压入s2中
        while (!s1.isEmpty()){
            s2.add(s1.pop());
        }

        // 由于是存放到list中去，所以直接顺序输出即为后缀表达
        suffixExpression = s2;

        return suffixExpression;
    }

    // 后缀表达式（以空格间隔） => list(ArrayList<String>)
    public static List<String> toSuffixExpressionList(String suffixExpression){
        // 以空格分割字符串，使用split函数
        String[] split = suffixExpression.split(" ");
        ArrayList<String> suffixList = new ArrayList<>();

        // 增强for循环
        for(String ele: split){
            suffixList.add(ele);
        }
        return suffixList;
    }


    // 后缀表达式求值
    public static int calc(List<String> list){
        // 创建栈（存储操作数）
        Stack<String> stack = new Stack<>();

        // 遍历 list
        for(String item : list){
            // 使用正则表达式取出数
            if(item.matches("\\d+")){ // 匹配多位数
                stack.push(item);
            }else {
                // 运算符，pop出两个数并将运算结果入栈
                int num2 =Integer.parseInt(stack.pop());
                int num1 =Integer.parseInt(stack.pop());
                int res ;
                switch (item){
                    case "+":
                        res = num1 + num2;
                        break;
                    case "-":
                        res = num1 - num2;
                        break;
                    case "*":
                        res = num1 * num2;
                        break;
                    case "/":
                        res = num1 / num2;
                        break;
                    default:
                        throw new RuntimeException("error operator");
                }
                // 数值类型数据转化为字符串类型
                // 方法1：String.valueOf(value)
                // 方法2：Integer.toString(value)
                // 方法3： value + ""
                stack.push(String.valueOf(res));
            }
        }
        // 字符串转换为数值类型：使用包装类的parse方法
        return Integer.parseInt(stack.pop());
    }

}


class Operator{
    private static final int ADD_SUB = 1;
    private static final int MUL_DIV = 2;

    public static int getPriority(String operator){
        switch (operator){
            case "+": case "-":
                return ADD_SUB;
            case "*": case "/":
                return MUL_DIV;
            default:
                throw new RuntimeException("Invalid operator!");

        }
    }
}