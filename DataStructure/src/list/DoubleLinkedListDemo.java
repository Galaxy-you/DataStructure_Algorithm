package list;

/**
 * author: galaxy-violet
 * date: 2025/10/7,16:33
 */
public class DoubleLinkedListDemo {
    public static void main(String[] args) {
        DoubleLinkedList doubleLinkedList = new DoubleLinkedList();

        DoubleNode node1 = new DoubleNode(1);
        DoubleNode node2 = new DoubleNode(3);
        DoubleNode node3 = new DoubleNode(5);
        DoubleNode node4 = new DoubleNode(9);
        DoubleNode node5 = new DoubleNode(2);
        DoubleNode node6 = new DoubleNode(4);
        DoubleNode node7 = new DoubleNode(7);
        DoubleNode node8 = new DoubleNode(10);

        doubleLinkedList.orderedAdd(node1);
        doubleLinkedList.orderedAdd(node2);
        doubleLinkedList.orderedAdd(node3);
        doubleLinkedList.orderedAdd(node4);
        doubleLinkedList.orderedAdd(node5);
        doubleLinkedList.orderedAdd(node6);
        doubleLinkedList.orderedAdd(node7);
        doubleLinkedList.orderedAdd(node8);

        doubleLinkedList.show();

        System.out.println("after delete:");
        doubleLinkedList.delete(1);
        doubleLinkedList.delete(2);
        doubleLinkedList.delete(3);
        doubleLinkedList.delete(4);
        doubleLinkedList.show();
    }
}

// java中类前面省略访问修饰符即为package-private
class DoubleNode{
    public int id;

    // java中类属性的默认初始值
    // 整数-0     小数 0.0      boolean-false       引用类型-null
    // 局部变量（定义在方法、构造器或者代码块内部的变量）没有初始值而直接使用，编译器会直接报错"变量尚未初始化"
    // 当然局部变量没有初始值定义是没有问题的，只要在使用前赋值了就不会报错
    public DoubleNode prev;
    public DoubleNode next;

    @Override
    public String toString() {
        return "DoubleNode{" +
                "id=" + id +
                '}';
    }

    public DoubleNode(int id){
        this.id = id;
        // 这里只给 id 赋值，所以 prev 和 next 均会被赋值为null（默认值）
    }
    public DoubleNode(){

    }
}

class DoubleLinkedList{
    private DoubleNode dummy = new DoubleNode(0);

    public DoubleNode getDummy(){
        return dummy;
    }

    public void show(){
        DoubleNode curr = dummy.next;
        if(curr == null){
            System.out.println("The list is empty!");
            return;
        }
        while (curr != null){
            System.out.println(curr);
            curr = curr.next;
        }
    }

    public void lastAdd(DoubleNode node){
        DoubleNode temp = dummy;

        while (temp.next != null){
            temp = temp.next;
        }

        temp.next = node;
        node.prev = temp;
    }

    public void orderedAdd(DoubleNode node){
        DoubleNode temp = dummy;    // node节点添加在temp节点之后

        while (temp.next != null && temp.next.id <= node.id){
            temp = temp.next;
        }

        node.next = temp.next;
        node.prev = temp;

        if(temp.next != null){
            temp.next.prev = node;
        }
        temp.next = node;

    }

    public void delete(int delID){
        DoubleNode temp = dummy.next;

        if(temp == null){
            System.out.println("The list is empty, failed to delete!");
        }

        while (temp != null && temp.id != delID){
            temp = temp.next;
        }

        if(temp == null){
            System.out.println("There is no id = " + delID + " in the list!");
        }else {
            // 双向链表-自我删除
            temp.prev.next = temp.next;

            // 如果删除的是最后一个节点 temp.next == null,下面这一行代码会报空指针异常
            if(temp.next != null){
                temp.next.prev = temp.prev;
            }
        }
    }

    // update 省略，只有一个属性-Id

}


