package linearstructure;

/**
 * author: Galaxy Violet
 * date: 2025/11/17, 20:54
 */

/* 简要介绍
    单链表是由一系列节点组成的线性数据结构，每个节点包含数据和指向下一个节点的指针。
    与数组不同，链表的元素在内存中不连续存储。
 */
public class SingleLinkedList {
    private Node head;  // head节点也存储数据，即不带头节点的链表
    private int size;   // 链表长度

    // 一般不会手敲单链表，毕竟有java.util.LinkedList
    // 虽然LinkedList是双向链表，但是问题不大

    // 反转单链表
    public void reverse(){
        Node prev = null;
        Node curr = head;
        while (curr != null){  // 这里不能写curr.next != null否则会导致最后一个节点没有被反转,如果写curr.next可以单独反转最后一个节点
            Node temp = curr.next;
            curr.next = prev;
            prev = curr;
            curr = temp;
        }
        head = prev;    // 这里返回prev
    }

    // 检测环（快慢指针）
    // 这里使用快慢指针的原理是：在一个环形中快的一定会“追上”慢的
    public boolean hasCycle(){
        if(head == null){
            return false;
        }

        Node slow = head;
        Node fast = head;

        while (fast != null && fast.next != null){
            slow = slow.next;
            fast = fast.next.next;

            // 快的会追赶上慢的，终有相逢的时候
            // 相对速度为1，两者进入环后距离每次减1
            if(slow == fast){
                return true;
            }
        }
        return false;
    }

    // 中中点（快慢指针）
    public Node findMiddle(){
        if(head == null){
            return null;
        }

        Node slow = head;
        Node fast = head;

        // fast 的步长是 slow的两倍，所以fast到到链表尾部的时候slow恰好位于链表的中点
        while(fast != null && fast.next.next != null){
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    // 找倒数第k个节点
    public Node findKthFromEnd(int k){
        if(head == null || k <= 0){
            return null;
        }

        Node slow = head;
        Node fast = head;

        for(int i = 1; i <= k - 1; i++){
            if(fast == null){
                return null;
            }else{
                fast = fast.next;
            }
        }

        while (fast.next != null){
            fast = fast.next;
            slow = slow.next;
        }

        return slow;
    }


}

class Node{
    int val;
    Node next;

    public Node(int val, Node next) {
        this.val = val;
        this.next = next;
    }
}
