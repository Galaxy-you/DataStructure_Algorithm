package stack;


/**
 * author: galaxy-violet
 * date: 2025/10/10,09:10
 */
public class Calculator {
    public static void main(String[] args) {
        // 所求计算表达式中只有数字和运算符号，没有括号，空格等其他字符

         String expression = "5*(10-4*2)-4/2+4-1";



        // 创建两个栈：符号栈，数栈
        CalcStack numStack = new CalcStack(10);
        CalcStack operatorStack = new CalcStack(10);

        int index = 0;  // 用于扫描字符串的索引
        int num1 = 0;   // pop两次得到的操作数
        int num2 = 0;   // pop一次得到的操作数
        char operator = ' ';
        int res = 0;
        char ch = ' ';  // 暂存表达式中的字符
        String keepNum = "";    // 用于多位操作数的拼接

        while(true){
            ch = expression.charAt(index);

            //理操作符有其他方法，老韩的方法有问题：比如 10-4/2+3，最后计算得到的结果是5，+只把/pop出来了，但是没有把-pop出来，所以最终计算的顺序是10-（4/2 + 3） = 5
            // 正确的写法是：当遇到优先级较低的运算符时，循环处理所有优先级更高的运算符（即弹出符号栈中所有优先级大于等于当前操作符的所有操作符），而不是只弹出一个
            // 老韩的写法：
            // 不包含左括号进行如下分析：
            // 栈不为空，需要与栈顶符号进行优先级比较
            // 如果栈顶符号优先级大于等于当前符号优先级，符号栈出栈栈顶符号（只弹出一个）
            //     同时数字栈弹出两个操作数，进行计算并将结果入数字栈，然后将当前符号入栈
            // 否则当前操作符直接入栈
            /*
            int curPrioriyt = operatorStack.priority(ch);
            int topPirority = operatorStack.priority((char) operatorStack.peek());

            if (topPirority >= curPrioriyt) {
                operator = (char) operatorStack.pop();
                num2 = numStack.pop();
                num1 = numStack.pop();
                res = numStack.cal(num1, num2, operator);
                numStack.push(res);
                operatorStack.push(ch);
            } else {
                operatorStack.push(ch);
            }
             */





            if(operatorStack.isOperator(ch)){
                // 符号栈为空，符号直接入栈
                if(operatorStack.isEmpty()){
                    operatorStack.push(ch);
                }

                else {
                    // 左括号直接入栈（根据优先级左括号无法直接入栈）
                    if(ch == '('){
                        operatorStack.push(ch);
                    }
                    // 如果为右括号,右括号不入栈，循环出栈，直至遇到左括号，同时出栈左括号
                    else if(ch == ')'){
                        while (operatorStack.peek() != '('){
                            operator = (char)operatorStack.pop();
                            num2 = numStack.pop();
                            num1 = numStack.pop();
                            res = numStack.cal(num1, num2, operator);
                            numStack.push(res);
                        }
                        // 弹出左括号
                        operatorStack.pop();
                    }

                    else {
                        int curPrioriyt = operatorStack.priority(ch);

                        // 循环处理所有优先级高于或等于当前运算符的栈内运算符
                        // currPriority <= topPriority => 符号栈持续出栈直至 operitorStack.isEmpty() || currPriority < topPrioriry
                        while (!operatorStack.isEmpty() && operatorStack.priority((char) operatorStack.peek()) >= curPrioriyt){
                            operator = (char) operatorStack.pop();
                            num2 = numStack.pop();
                            num1 = numStack.pop();
                            res = numStack.cal(num1, num2, operator);
                            numStack.push(res);
                        }
                        // 当前符号入符号栈
                        operatorStack.push(ch);
                    }
                }

                //
            }
            // 是数字，直接入数字栈 ==> 错误的思维，这样只有一位数字符合，而对于多位数字会无法等到正确结果
            // 改进思路：如果是数字，接着往后看一位，如果是符号则入栈，如果是数字则拼接并重复以上操作
            // 这里的拼接采用字符串进行拼接，当然使用 *10^n进行数值拼接也是可以的
            else{
                // 使用强制数据类型转换，ch --> int,强制数据类型转转换的语法为：(Type)value
                // 但是获得到的int数据是相应字符对应的ASCLL码所对应的整数值，并不是真正的字符所对应的字面量数值，需要减去偏移量 (int)'0'
                keepNum += ch;

                // 查看表达式中的下一个字符是不是操作符
                // 如果是操作符则操作数入数字栈，同时清空keepNum
                // 否则继续拼接
                // 这里要考虑越界问题，因为最后一个字符一定是操作数，此时index + 1一定会报错：String index out of range
                // 由于如果是最后一个字符，则 Integer.parseInt(keepString)一定需要入栈，所以单独拿出来说明

                // 最后一个字符 => 直接入栈
                if(index == expression.length() - 1){
                    int opNum = Integer.parseInt(keepNum);
                    numStack.push(opNum);
                }
                // 不是最后一个字符，则要根据下一位字符是否是操作符判断是否入栈
                else if( operatorStack.isOperator(expression.charAt(index + 1))){
                    int opNum = Integer.parseInt(keepNum);
                    numStack.push(opNum);
                    // 清空 keepNum
                    keepNum = "";
                }
            }

            index++ ;
            // index 从0开始，所以这里是大于等于号
            if(index >= expression.length()){
                break;
            }
        }

        // 当表达式扫描完成后，弹出一个操作符和两个操作数，然后将计算结果入数字栈，直至符号栈为空
        while (!operatorStack.isEmpty()){
            operator = (char)operatorStack.pop();
            num2 = numStack.pop();
            num1 = numStack.pop();
            res = numStack.cal(num1,num2,operator);
            numStack.push(res);
        }

        int finalRes = numStack.pop();
        System.out.println(expression + " = " + finalRes);

    }
}

class CalcStack extends ArrayStack{
    public CalcStack(int maxSize) {
        super(maxSize);
    }

    /* java 中 char 与 int 关联
    Java 中的字符（char 类型） 不是 int 类型，
    但它 可以 被当作一个无符号整数来看待，并且可以很容易地与 int 类型进行转换和运算。

    在 Java 中，char 是一种基本数据类型，用来表示一个 Unicode 字符。
    char 是 16 位无符号整数
    数据大小： char 类型占用 16 位（2 个字节） 内存空间。
    数据范围： 它的范围是从 0 到 65535（即 2^16 − 1）。
    本质： char 在底层存储的就是一个无符号整数，这个整数值就是该字符在 Unicode 字符集中的码点（Code Point）。

    char 自动提升为 int
    char c = 'A';
    int i = c;
    sout(i);    // output: 65

    int 强制转换为 char
    int num = 97;
    char c = (char)num;
    sout(c);    // output: 'a'

    算数运算：在算数运算中，char会自动提升为Int，再参与计算（自动类型转换）
    char ch = 'a';
    int result = ch + 1;
    sout(result);   // output: 98，输出是一个int类型
     */
    // 返回运算符的优先级，数字越大则优先级越高
    public int priority(char operator){

        if (operator == '*' || operator == '/'){
            return 1;
        }else if(operator == '+' || operator == '-'){
            return 0;
        }
        // 小括号的优先级比其余符号的优先级低：以确保其余符号可以入栈
        else if(operator == '(' || operator == ')'){
            return -2;
        }else {
            return -1;  // invalid operator
        }
    }

    public boolean isOperator(char val){
        return val == '+' || val == '-' || val == '*' || val == '/' || val == '(' || val == ')';
    }

    /**
     *
     * @param num1 第一个操作数（即pop两次的）
     * @param num2 第二个操作数（即pop一次的）
     * @param operator 操作符
     * @return
     */
    public int cal(int num1, int num2, char operator){
        int res = 0;
        switch (operator){
            case '+':
                res = num1 + num2;
                break;
            case '-':
                res = num1 - num2;
                break;
            case '*':
                res = num1 * num2;
                break;
            case '/':
                if(num2 == 0){
                    throw new ArithmeticException("the second operator num can't be zero!");
                }
                res = num1 / num2;
                break;
        }
        return res;
    }
}
