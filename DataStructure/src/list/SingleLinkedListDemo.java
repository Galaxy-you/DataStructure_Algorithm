package list;
import java.util.Stack;

/**
 * author: galaxy-violet
 * date: 2025/9/26,08:57
 */
public class SingleLinkedListDemo {
     public static void main(String[] args) {
        Node node1 = new Node(1, "宋江", "及时雨");
        Node node2 = new Node(2, "卢俊义", "玉麒麟");
        Node node3 = new Node(3, "吴用", "智多星");
        Node node4 = new Node(4, "林冲", "豹子头");
        Node node5 = new Node(5, "林冲", "豹子头");
        Node node6 = new Node(6, "林冲", "豹子头");
        Node node7 = new Node(7, "林冲", "豹子头");

        // 类SingleLinkedList为public ,且位于同包，无需导入即可使用

         SingleLinkedList singleLinkedList = new SingleLinkedList();
         // 无序尾添加
//         singleLinkedList.addUnordered(node1);
//         singleLinkedList.addUnordered(node4);
//         singleLinkedList.addUnordered(node2);
//         singleLinkedList.addUnordered(node3);
//         singleLinkedList.addUnordered(node5);

         // 有序添加
         singleLinkedList.addOrdered(node1);
         singleLinkedList.addOrdered(node4);
         singleLinkedList.addOrdered(node2);
         singleLinkedList.addOrdered(node3);
         singleLinkedList.addOrdered(node5);
         singleLinkedList.addOrdered(node6);
         singleLinkedList.addOrdered(node7);

         singleLinkedList.show();

         int reverseIndex = 2;
         Node reverseIndexNode = singleLinkedList.findReverseIndexNode(reverseIndex);
         Node reverseIndexNodeOnlyOnce = singleLinkedList.findReverseIndexNodeOnlyOnce(reverseIndex);
         System.out.println("The last " + reverseIndex + " :" + reverseIndexNode);
         System.out.println("The last " + reverseIndex + " :" + reverseIndexNodeOnlyOnce);



         // 修改节点
         Node newNode = new Node(2, "卢~~", "玉~~");
         singleLinkedList.update(newNode);
         System.out.println("after update:");
         singleLinkedList.show();

         // 删除节点
//         singleLinkedList.delete(1);
//         singleLinkedList.delete(4);
//         System.out.println("after the first delete:");
//         singleLinkedList.show();
//         System.out.println("size = " + singleLinkedList.getSize());
//         singleLinkedList.delete(2);
//         singleLinkedList.delete(3);
//         System.out.println("after the second delete:");
//         singleLinkedList.show();

         // 翻转链表
         singleLinkedList.reverseSingleList();
         System.out.println("Reverse the list:");
         singleLinkedList.show();

         System.out.println("Reverse print the list:");
         singleLinkedList.reversePrint();


         System.out.println("==========merge list==========");
         Node nodea = new Node(1, "hhh", "hhh");
         Node nodeb = new Node(3, "hhh", "hhh");
         Node nodec = new Node(5, "hhh", "hhh");
         Node noded = new Node(7, "hhh", "hhh");
         Node nodee = new Node(2, "hhh", "hhh");
         Node nodef = new Node(4, "hhh", "hhh");
         Node nodeg = new Node(6, "hhh", "hhh");
         Node nodeh = new Node(8, "hhh", "hhh");
         Node nodei = new Node(10, "hhh", "hhh");

         SingleLinkedList list1 = new SingleLinkedList();
         SingleLinkedList list2 = new SingleLinkedList();
         list1.addUnordered(nodea);
         list1.addUnordered(nodeb);
         list1.addUnordered(nodec);
         list1.addUnordered(noded);
         list2.addUnordered(nodee);
         list2.addUnordered(nodef);
         list2.addUnordered(nodeg);
         list2.addUnordered(nodeh);
         list2.addUnordered(nodei);

         System.out.println("list 1");
         list1.show();
         System.out.println("list 2");
         list2.show();

         System.out.println("after merge:");
         SingleLinkedList mergedList = mergeSingleLinkedList(list1, list2);
         mergedList.show();

     }

     // 合并两个有序单列表，合并之后的链表依然有序
     // 由于没有实现深拷贝复制节点的函数，所以此处允许破坏原有的两条链表list1和list2的结构，拼接成一条链表然后返回。
     // 引入一个尾指针，避免了要连接两条链表的问题
     public static SingleLinkedList mergeSingleLinkedList(SingleLinkedList list1, SingleLinkedList list2) {
         // 三个if是一个检查，看题目要求写不写吧
         if (list1 == null) return list2;
         if (list2 == null) return list1;
         if (list1 == list2) return list1; // 同一个对象，直接返回

         // 这个还是检查，一般不会出现（AI给的健壮代码）
         Node d1 = list1.getDummyHead();
         Node d2 = list2.getDummyHead();
         if (d1 == null || d2 == null) throw new IllegalArgumentException("Both lists must have a dummy head");

         Node node1 = d1.next;
         Node node2 = d2.next;
         Node tail = d1; // merged list tail (start at dummy) 尾指针，指向mergedList的最后一个节点，初始化为list1的dummy node

         // 合并（就地复用节点）
         while (node1 != null && node2 != null) {
             if (node1.id <= node2.id) {
                 tail.next = node1;
                 node1 = node1.next;
                 tail = tail.next;      // 尾指针后移
             } else {
                 Node next2 = node2.next; // 先保存，避免在修改 node2.next 后丢失后续节点，AI的防御性写法，怕你断链，不过这里直接写 node2 = node2.next也可以
                 tail.next = node2;
                 tail = tail.next;      // 尾指针后移
                 node2 = next2;
             }
         }

         // 接上剩余部分
         tail.next = (node1 != null) ? node1 : node2;

         // 清空 list2 的头引用（因为节点被复用）
         d2.next = null;

         // 如果 SingleLinkedList 维护 size 字段，记得在这里更新：
         // list1.setSize(list1.size() + list2.size());
         // list2.setSize(0);

         return list1;
     }
    /*
    // 自己写的，没有引入尾指针，需要链接两条链表，比较麻烦
    public static SingleLinkedList mergeSingleLinkedList(SingleLinkedList list1,SingleLinkedList list2) {
         Node prev = list1.getDummyHead();      //把 list1 作为主体，prev 用来暂存节点防止断链
         Node node1 = prev.next;
         Node node2 = list2.getDummyHead().next;

         while (node1 != null && node2 != null){
             if(node1.id <= node2.id){
                 prev = node1;
                 node1 = node1.next;
             }
             else {
                 prev.next = node2;
                 prev = node2;      // prev 后移
                 node2 = node2.next;
                 prev.next = node1; // 链接两条链表
             }
         }
         if(node1 != null){
             // 由于是以 list1 为主体，所以遍历完list2之后不需要链接
         }
         else {
             prev.next = node2;
         }

         // 将头节点的next置为null
         list2.getDummyHead().next = null;

         return list1;
     }
     */
}





