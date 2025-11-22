package queue;

import java.util.Scanner;

/**
 * author: galaxy-violet
 * date: 2025/9/25,21:10
 */
// imitate v.模范，作滑稽模仿
public class ArrayImitateQueue {
    // 类命名规范：大驼峰式
    public static void main(String[] args) {
        ArrayQueue arrayQueue = new ArrayQueue(3);
        char key = ' ';
        Scanner scanner = new Scanner(System.in);
        boolean loop = true;
        while (loop){
            System.out.println("s - show");
            System.out.println("e - exit");
            System.out.println("a - add");
            System.out.println("g - get");
            System.out.println("p - peek");
            System.out.print("please input key:");
            key = scanner.next().charAt(0);     // 接收一个字符
            switch(key){
                case 's':
                    arrayQueue.showQueue();
                    break;
                case 'a':
                    System.out.print("input value:");
                    int value = scanner.nextInt();
                    arrayQueue.addQueue(value);
                    break;
                case 'g':
                    try {
                        int res = arrayQueue.getQueue();
                        System.out.println("value = " + res);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'p':
                    try {
                        int res = arrayQueue.peekQueue();
                        System.out.println("head value = " + res);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'e':
                    scanner.close();
                    loop = false;
                    break;
            }
        }
        System.out.println("program exit.");

    }
}

// use array imitate queue,no loop(circle queue)
class ArrayQueue{
    // 类成员命名规范：小驼峰式
    private int maxSize;    // 表示数组的最大容量
    private int front;      // 队首,指向第一个元素之前
    private int rear;       // 队尾，直接直线最后一个元素   // rear adj.后面的，后补的 n.后部，臀部 v.抚养，饲养
    private int[] arr;      // 用来存储数据的数组，imitate queue

    // 类方法命名规范：小驼峰式
    public ArrayQueue(int arrMaxSize){
        this.maxSize = arrMaxSize;
        arr = new int[maxSize];
        front = rear = -1; // notice : index starts from negative one
    }

    public boolean isFull(){
       return rear == maxSize - 1;
    }

    public boolean isEmpty(){
        return rear == front;
    }

    public void addQueue(int value){
        if(isFull()){
            System.out.println("The queue is full,failing to add!");
            return;
        }
        // rear指向d是最后一个数据
        rear++;
        arr[rear]  = value;
    }

    // 获取队列数据并且出队
    public int getQueue(){
        if(isEmpty()){
            throw new RuntimeException("The queue is empty,failing to get element");
            // throw 会导致直接终止函数的运行，没有必要继续return
        }
        front++;    // 由于约定front指向有效数据的前一个位置，所以此处要自增以实现出队
        return arr[front];
    }

    // 输出queue数据
    public void showQueue(){
        if(isEmpty()){
            System.out.println("The queue is empty!");
            return;
        }
        for(int i = 0; i < arr.length; i++){
            // 只有使用printf才能使用
            System.out.printf("%d\t",arr[i]);
        }
        System.out.println();
    }

    // 显示队列的首个数据，但是不出队
    public int peekQueue(){
        if(isEmpty()){
            throw new RuntimeException("The queue is empty!");
        }
        return arr[front + 1];
    }
}
