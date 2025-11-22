package stack;
import list.SingleLinkedList;
import list.Node;
import java.util.Scanner;

/**
 * author: galaxy-violet
 * date: 2025/10/10,08:27
 */
public class LinkedListImitateStack {
    public static void main(String[] args) {
        LinkedListStack linkedListStack = new LinkedListStack();
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
                    linkedListStack.show();
                    break;
                case "push":
                    System.out.print("input value:");
                    int input_value = scanner.nextInt();
                    linkedListStack.push(input_value);
                    break;
                case "pop":
                    try {
                        int pop_value = linkedListStack.pop();
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


class LinkedListStack{
    public SingleLinkedList list;   // 用列表来存储数据
    
    public  LinkedListStack(){
        list = new SingleLinkedList();
    }
    
    public boolean isEmpty(){
        return list.isEmpty();
    }
    
    public void push(int value){
        Node node = new Node(value,"","");
        list.addUnordered(node);
    }
    
    // 删除尾节点，并返回value
    public int pop(){
        Node dummyHead = list.getDummyHead();
        
        // 要删除的节点为curr,添加一个辅助指针 prev
        Node prev = dummyHead;
        Node curr = dummyHead.next;
        
        while (curr.next != null){
            prev = prev.next;
            curr = curr.next;
        }
        
        // 删除curr
        int value = curr.id;
        prev.next = null;
        return value;
        
    }
    
    public void show(){
        list.reversePrint();
    }
}
