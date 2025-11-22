# 单列表

![image-20250926085918869](D:\code\DataStructure_Algorithm\DataStructure\src\list\assert\image-20250926085918869.png)

## 翻转单列表

> single list with the dummy head

```java
  // 带头节点的单链表翻转
  // 时间复杂度 O(n)，每个节点只访问一次
  // 空间复杂度 O(1)，只用常数级指针变量
  // 原地操作，不需要额外链表，代码简洁，易于理解和维护
  // 头节点的英文是 dummyHead    
  //dummy adj.假的 n.人体模仿，仿制品，仿照物
    public void reverseSingleList() {
        Node prev = null;
        Node curr = linkList.next;
        Node next = null;
        while (curr != null) {
            next = curr.next;  		// 暂存下一个节点
            curr.next = prev;       // 当前节点指向前一个节点
            prev = curr;            // prev 前进
            curr = next;            // curr 前进
        }
        linkList.next = prev;
    }
```

> single list without the dummy head

```java 
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
```

# 双向链表

单向链表不能自我删除，需要辅助节点暂存要删除节点的前一个节点

而双向链表可以实现**自我删除**



# 约瑟夫环问题（**环形链表**）

![image-20251007172554621](./assert/image-20251007172554621.png)

使用**环形链表**解决，当然使用**数组取模**也可以解决这个问题。

