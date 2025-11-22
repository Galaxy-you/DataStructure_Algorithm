package stack;

import java.util.Scanner;

/**
 * author: galaxy-violet
 * date: 2025/10/7,20:41
 */
public class ArrayImitateStack {
    public static void main(String[] args) {
        ArrayStack arrayStack = new ArrayStack(4);
        String key = "";
        boolean loop = true;
        Scanner scanner = new Scanner(System.in);

        while(loop){
            System.out.println("show");
            System.out.println("push");
            System.out.println("pop");
            System.out.println("exit");
            System.out.print("please input key: ");
            key = scanner.next();
            switch (key){
                case "show":
                    arrayStack.show();
                    break;
                case "push":
                    System.out.print("input value:");
                    int input_value = scanner.nextInt();
                    arrayStack.push(input_value);
                    break;
                case "pop":
                    try {
                        int pop_value = arrayStack.pop();
                        System.out.println("pop value: " + pop_value);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "exit":
                    scanner.close();
                    loop = false;
                    break;
                default:
                    System.out.println("error input");
                    break;
            }
        }
    }
}

class ArrayStack{
    private int maxSize;    // 栈的大小
    private int[] stack;    // 存储数据
    private int top = -1;   // 初始化为-1

    public ArrayStack(int maxSize){
        this.maxSize = maxSize;
        stack = new int[maxSize];
    }

    public boolean isFull(){
        return top == maxSize - 1;
    }

    public boolean isEmpty(){
        return top == -1;
    }

    public void push(int value){
        if(isFull()){
            System.out.println("The stack is full");
            return;
        }
        top++;
        stack[top] = value;
    }

    public int pop(){
        if(isEmpty()){
            throw new RuntimeException("The stack is empty!");
            // 不需要return ,异常会终止函数的执行
        }
        int value = stack[top];
        top--;
        return value;
    }

    // 返回栈顶的值，但是不出栈
    public int peek(){
        return stack[top];
    }

    public void show(){
        if(isEmpty()){
            System.out.println("The stack is empty!");
            return;
        }
        for(int i = top; i >= 0; i--){
            System.out.printf("stack[%d] = %d\n",i,stack[i]);
        }
    }
}
