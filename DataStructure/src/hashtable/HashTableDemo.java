package hashtable;

import java.util.Scanner;

public class HashTableDemo {
    public static void main(String[] args) {
        HashTable hashTable = new HashTable(5);

        Scanner scanner = new Scanner(System.in);
        String key;
        boolean loop = true;

        do{
            System.out.println("1.add");
            System.out.println("2.list");
            System.out.println("3.findById");
            System.out.println("4.deleteById");
            System.out.println("5.exit");
            System.out.print("please input key: ");

            key = scanner.next();
            switch (key){
                case "add":
                    System.out.print("id: ");
                    int id = Integer.parseInt(scanner.next());

                    System.out.print("name: ");
                    String name = scanner.next();

                    Node node = new Node(id,name);
                    hashTable.add(node);

                    break;
                case "list":
                    hashTable.list();
                    break;
                case "findById":
                    System.out.print("Input id: ");
                    int findId = scanner.nextInt();
                    hashTable.findById(findId);
                    break;
                case "deleteById":
                    System.out.print("Input id: ");
                    int deleteId = scanner.nextInt();
                    hashTable.deleteById(deleteId);
                    break;
                case "exit":
                    scanner.close();
                    loop = false;
                    break;
                default:
                    System.out.println("Invalid input!");
            }
        }while (loop);



    }
}

class Node {
    public int id;
    public String name;

    // next是定义在节点中的，而不是定义在链表中的
    public Node next;

    public Node(int id, String name) {
        this.id = id;
        this.name = name;
    }
}

class LinkedList{
    // 链表中应该只定义一个head 或者是 dummy head
    // head指向第一个元素，注意这个不是dummy head, 默认为null
    public Node head;   // = null可以写也可以不写，成员变量会被默认初始化

    // 添加节点到链表中去（Id自增长，尾插）
    public void add(Node node){
        if(head == null){
            head = node;
//            node.next = null; 看情况写不写吧
            return;
        }

        Node curr = head;
        while (curr.next != null){
            curr = curr.next;
        }
        curr.next = node;
    }

    public void list(){
        if(head == null){
            System.out.println("The list is null!");
            return;
        }

        Node curr = head;
        System.out.print("[");
        do{
            System.out.println(curr.id + ", " + curr.name);
            curr = curr.next;
        }while (curr != null);
        System.out.print("]");
    }

    public Node findById(int id){
        Node curr = head;

        while (curr != null ){
            if(curr.id == id){
                return curr;
            }
            curr = curr.next;
        }
        return null;
    }

    public boolean deleteById(int id){
        if(head == null){
            return false;
        }

        Node prev = head;   // not null
        Node curr = head.next;

        // 要删除的节点为curr，即 prev.next
        while (curr != null){
            if(curr.id == id){
                prev.next = curr.next;
                return true;
            }
            prev = curr;
            curr = curr.next;
        }

        return false;
    }
}

// 哈希表本质是一个数组，然后数组的每一个元素是一个链表
// 即同时管理多条链表
class HashTable{
    // 使用哈希表通过相关函数完成，所以这里的数组可见属性为private
    private LinkedList[] linkedListArray; // 链表数组
    private int size;   // 表示数组的大小，即有多少条链表

    public HashTable(int size){
        // 初始化数组
        linkedListArray = new LinkedList[size];

        // 分别初始化各条链表
        for (int i = 0; i < size; i++) {
            linkedListArray[i] = new LinkedList();
        }
        this.size = size;
    }

    // 编写散列函数确定值应该加入那一条链表
    // 这里使用取模法（除留余数法）
    public int hashFunc(int id){
        return id % size;
    }

    public void add(Node node){
        // 根据 Id 确定应该加入哪一条链表
        int linkedListNo = hashFunc(node.id);

        linkedListArray[linkedListNo].add(node);
    }

    public void list(){
        // list每一条链表
        for (int i = 0; i < size; i++) {
            System.out.println("list " + i + ": ");
            linkedListArray[i].list();
            System.out.println();
        }
    }

    public void findById(int id){
        int linkedListNo = hashFunc(id);

        Node node = linkedListArray[linkedListNo].findById(id);

        if(node == null){
            System.out.println("Not found the id in the hashtable!");
        }else {
            System.out.println("the id in the list-" + linkedListNo + ", and the name of the id is " + node.name);
        }
    }

    public void deleteById(int id){
        int linkedListNo = hashFunc(id);

        if(linkedListArray[linkedListNo].deleteById(id)){
            System.out.println("The id equals to " + id + " had been deleted in list-" + linkedListNo);
        }else {
            System.out.println("Not found the id:" + id + "in the hashtable!");
        }

    }
}