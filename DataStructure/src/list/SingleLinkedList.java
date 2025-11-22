package list;

import java.util.Stack;

/**
 * author: galaxy-violet
 * date: 2025/10/10,08:31
 */
public class SingleLinkedList{
    private final Node linkList = new Node();

    public Node getDummyHead(){
        return linkList;
    }

    // 方法命名：小驼峰式
    // 尾添加
    // 这是一个浅拷贝函数，所以如果添加的节点是已存在于其它链表中的节点则可能导致出错（node还有next，所以其实添加的不是一个节点，除非node.next == null）
    public void addUnordered(Node node){
        // 不考虑id顺序，尾添加
        Node temp = linkList;   // 遍历指针
        while(temp.next != null){
            temp = temp.next;
        }
        temp.next = node;
    }

//    public void addUnordered(Node node){
//        Node temp = linkList.next;
//        while(true){
//            if(temp.next == null){
//                break;
//            }
//            temp = temp.next;
//        }
//        temp.next = node;
//    }

    // 按照Id顺序进行添加
    public void addOrdered(Node node){
        Node temp = linkList;

        // 由于是单向链表，所以实际上要比较的是temp.next.id与node.id，不然要回溯
        while(temp.next != null && temp.next.id < node.id){
            temp  = temp.next;
        }
        // 判断Id是否相等时要先判断是否为null(temp为最后一个节点的时候temp.next为null)
        if(temp.next != null && temp.next.id == node.id){
            System.out.println("The id " + node.id + " already exists in linkList!");
            return;
        }
        // 插入位置为temp之后
        node.next = temp.next;
        temp.next = node;

    }
    public void show(){
        // 头节点不显示信息，所以这里是linkList.next
        Node temp = linkList.next;
        if(temp == null){
            System.out.println("The list is empty!");
            return;
        }
        while (temp != null){
            System.out.println(temp);
            temp = temp.next;
        }
    }

    public boolean isEmpty(){
        return linkList.next == null;
    }

    // 根据Id修改node信息
    public void update(Node newNode){
        if(isEmpty()){
            System.out.println("The linklist is empty!");
        }
        Node temp = linkList;
        boolean isFound = false;
        // id 比较处理有两种方法，无序是 != ,然后有序是 <
//        while (temp.next != null && temp.next.id != newNode.id){
//            temp = temp.next;
//        }

        while(true){
            if(temp == null){
                break;
            }
            if(temp.id == newNode.id){
                isFound = true;
                break;
            }
            temp = temp.next;
        }
        if(isFound){
            temp.name = newNode.name;
            temp.nickname = newNode.nickname;
        }else{
            System.out.println("There is no id = " + newNode.id + " in the list!");
        }
    }

    public void delete(int delID){
        Node temp = linkList;
        boolean isFound = false;

        while (true){
            if(temp.next == null){
                break;
            }
            if(temp.next.id == delID){
                isFound = true;
                break;
            }
            temp = temp.next;
        }
        if(isFound){
            // 被删除的节点没有任何应用指向，会被垃圾回收机制回收
            temp.next = temp.next.next;
        }else{
            System.out.println("There is no id = " + delID + " in the list!");
        }
    }

    // 获取单链表中有效节点的个数(头节点不算)
    public int getSize(){
        Node temp = linkList.next;
        int size = 0;
        while(temp != null){
            size++;
            temp = temp.next;
        }
        return size;
    }

    // 查找单链表中倒数第k个节点
    // reverseIndex / endIndex 表示倒数索引/末尾索引
    // BF：首先得到长度size，然后从头节点遍历next size - reverseIndex + 1次
    public Node findReverseIndexNode(int reverseIndex){
        int size = getSize();
        if(reverseIndex > size || reverseIndex <= 0){
            System.out.println("Illegal reverseIndex!");
            return null;
        }
        Node temp = linkList;
        for(int i = 1; i <= size - reverseIndex + 1; i++){
            temp = temp.next;
        }
        return temp;
    }

    // Optimized:使用快慢指针遍历依次得到所需的节点
    public Node findReverseIndexNodeOnlyOnce(int reverseIndex){
        if(reverseIndex <= 0){
            System.out.println("Illegal reverseIndex!");
            return null;
        }
        Node fast = linkList.next;
        Node slow = linkList.next;

        // 快指针先走 reverIndex 步
        for(int i = 0; i < reverseIndex; i++){
            if(fast == null){
                System.out.println("Illegal reverseIndex!");
                return null;
            }
            fast = fast.next;
        }

        while (fast != null){
            fast = fast.next;
            slow = slow.next;
        }
        return slow;
    }

    // 下面的写法可以实现带头节点的链表翻转，但是存在问题
    // curr 一直指向原始的第一个节点，每次都把 curr.next 插到头结点后面
//    public void ReverseSingleList(){
//        Node curr = linkList.next;
//
//        // size = 0
//        if(curr == null){
//            return;
//        }
//
//        while (curr.next != null){
//            Node first = linkList.next;
//            Node next = curr.next;
//            linkList.next = next;
//            curr.next = next.next;
//            next.next = first;
//        }
//    }

    // 带头节点的单链表翻转
    // 时间复杂度 O(n)，每个节点只访问一次
    // 空间复杂度 O(1)，只用常数级指针变量
    // 原地操作，不需要额外链表，代码简洁，易于理解和维护
    // 头节点的英文是 dummyHead    dummy adj.假的 n.人体模仿，仿制品，仿照物
    public void reverseSingleList() {
        Node prev = null;
        Node curr = linkList.next;
        Node next = null;
        while (curr != null) {
            next = curr.next;       // 暂存下一个节点
            curr.next = prev;       // 当前节点指向前一个节点
            prev = curr;            // prev 前进
            curr = next;            // curr 前进
        }
        linkList.next = prev;
    }

    /*不带头节点的单链表翻转
    public ListNode reverseList(ListNode head) {
    ListNode prev = null;
    ListNode curr = head;
    while (curr != null) {
        ListNode next = curr.next; // 暂存下一个节点
        curr.next = prev;          // 当前节点指向前一个节点
        prev = curr;               // prev 前进
        curr = next;               // curr 前进
    }
    return prev; // prev 新头节点
    }
     */

    // 逆序输出列表
    // 1、先使用上面的reverseSingleList()将列表翻转，然后输出，不够此举破坏了列表的结构，不推荐
    // 2、使用栈，将各个节点入栈，利用栈--先入后出的特性实现逆序输出
    public void reversePrint(){
        // 空列表
        if(linkList.next == null){
            System.out.println("The single list is empty!");
        }

        Stack<Node> stack = new Stack<Node>();
        Node curr = linkList.next;

        // 将列表的所有节点压入栈中
        /**
         * 区分stack.push() 和 stack.add()方法
         * Stack extends Vector
         * stack.push()是标准的压栈操作，将元素添加到栈的顶部（即列表的尾部），同时返回被添加的元素
         * stack.add()是标准的List操作，将元素添加到列表的末尾（即栈的顶部），同时返回一个布尔值(true)，表示操作成功
         *
         * 推荐入栈操作：stack.push(E item)
         */
        while (curr != null){
            stack.push(curr);
            curr = curr.next;
        }

        while (stack.size() > 0){
            System.out.println(stack.pop());
        }
    }
}
