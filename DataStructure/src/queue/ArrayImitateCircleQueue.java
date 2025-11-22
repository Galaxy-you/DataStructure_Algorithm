package queue;

import java.util.Scanner;

/**
 * author: galaxy-violet
 * date: 2025/9/25,21:10
 */
// imitate v.模范，作滑稽模仿
public class ArrayImitateCircleQueue {
    // 类命名规范：大驼峰式
    public static void main(String[] args) {
        ArrayCircleQueue arrayCircleQueue = new ArrayCircleQueue(4);  // 有效数据为3，会空余一个位置
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
                    arrayCircleQueue.showQueue();
                    break;
                case 'a':
                    System.out.print("input value:");
                    int value = scanner.nextInt();
                    arrayCircleQueue.addQueue(value);
                    break;
                case 'g':
                    try {
                        int res = arrayCircleQueue.getQueue();
                        System.out.println("value = " + res);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'p':
                    try {
                        int res = arrayCircleQueue.peekQueue();
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
class ArrayCircleQueue{
    // 类成员命名规范：小驼峰式
    private int maxSize;    // 表示数组的最大容量
    private int front;      // 队首,指向队列的第一个元素
    private int rear;       // 队尾，指向最后一个元素后一个位置，留一个空位置进行判断是否未满   // rear adj.后面的，后补的 n.后部，臀部 v.抚养，饲养
    private int[] arr;      // 用来存储数据的数组，imitate queue

    // 类方法命名规范：小驼峰式
    public ArrayCircleQueue(int arrMaxSize){
        this.maxSize = arrMaxSize;
        arr = new int[maxSize];
        front = rear = 0; // notice : index starts from 0
    }

    public boolean isFull(){
        return (rear + 1) % maxSize == front;
    }

    public boolean isEmpty(){
        return rear == front;
    }

    public void addQueue(int value){
        if(isFull()){
            System.out.println("The queue is full,failing to add!");
            return;
        }
        // rear指向最后一个数据“后面”一个位置，所以先赋值再移动指针
        arr[rear]  = value;
        rear = (rear + 1) % maxSize;
    }

    // 获取队列数据并且出队
    public int getQueue(){
        if(isEmpty()){
            throw new RuntimeException("The queue is empty,failing to get element");
            // throw 会导致直接终止函数的运行，没有必要继续return
        }
        int tempValue = arr[front];
        front = (front + 1) % maxSize;    // 由于约定front指向第一个有效数据
        return tempValue;
    }

    // 输出queue数据
    public void showQueue(){
        if(isEmpty()){
            System.out.println("The queue is empty!");
            return;
        }
        // 从front开始遍历直到rear的前一个位置
        int size = getSize();
        for(int i = front; i < front + size; i++){
            // 只有使用printf才能使用
            int index = i % maxSize;
            System.out.println("arr[" + index + "] = " + arr[index] );
        }
    }

    // 显示队列的首个数据，但是不出队
    public int peekQueue(){
        if(isEmpty()){
            throw new RuntimeException("The queue is empty!");
        }
        return arr[front];
    }

    public int getSize(){
        return (rear + maxSize - front) % maxSize;
    }
}
