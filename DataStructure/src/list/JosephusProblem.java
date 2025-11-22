package list;

import java.util.ArrayList;
import java.util.List;

/**
 * author: galaxy-violet
 * date: 2025/10/7,17:35
 */

/*
Josephus 和他的 40 个同伴被罗马军队围困，他们决定以一种致命的方式（围成一圈并按固定间隔杀掉身边的人）集体自杀，
而不是投降。Josephus 和另一人不想死，就通过计算确定了他们应该站立的位置，
从而成为了最后剩下的人，最终投降了罗马人。这个传说构成了这个著名问题的基础。
 */

/*
设编号为1，2，.n的n个人围坐一圈，
约定编号为k（1<=k<=n）的人从1开始报数，数到m的那个人出列，
它的下一位又从1开始报数，数到m的那个人又出列，
依次类推，直到所有人出列为止，
请给出出队编号的序列。
*/
public class JosephusProblem {
    public static void main(String[] args) {
        CircleSingleLinkedList circleSingleLinkedList1 = new CircleSingleLinkedList();
        circleSingleLinkedList1.addBoy(5);
        circleSingleLinkedList1.Josephus(1,2,5);

        System.out.println("with the helper:");
        CircleSingleLinkedList circleSingleLinkedList2 = new CircleSingleLinkedList();
        circleSingleLinkedList2.addBoy(5);
        circleSingleLinkedList2.JosephusWithHelper(1,1,5);

        // simulate: v.模仿，假装，冒充，装作;侧重于功能和过程的模仿，旨在预测或测试。
        // imitate: v.模仿，仿效;侧重于外表和行为的模仿，旨在复制或学习。
        System.out.println("array imitate：");
        josephus(5,1,2);

    }


    /*
    维护当前索引 idx，删除位置 idx = (idx + m - 1) % size；ArrayList.remove(i) 会移动后续元素，时间复杂度为 O(n)／次，整体 O(n^2)。
    优点：代码短、直接；缺点：当 n 很大时性能受影响。
     */
    /**
     * @param n  总人数
     * @param startNo   开始报数的编号
     * @param m 数几下
     */
    public static void josephus(int n, int startNo, int m) {
        if (n <= 0 || startNo < 1 || startNo > n || m < 1) throw new IllegalArgumentException("参数错误");
        List<Integer> list = new ArrayList<>();
        // ArrayList的尾添加方法为 list.add(value)
        for (int i = 1; i <= n; i++) list.add(i);

        int idx = startNo - 1; // 从这个下标开始报数
        while (!list.isEmpty()) {
            // index = (index + m -1) % list.size();
            idx = (idx + m - 1) % list.size();

            // list.remove(index) 方法会出列相应下标对应的元素，并且更新列表的大小
            System.out.println("出列: " + list.remove(idx));
            // idx 已指向下一个元素（因为 remove 后此处就是新的当前位置）
        }
    }

}

class Child{
    private int id;
    public Child next;     // 指向下一个节点，默认为null

    public Child(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Child{" +
                "id=" + id +
                '}';
    }
}

class CircleSingleLinkedList{
    private Child first = null;

    // 添加节点
    public void addBoy(int nums){
        if(nums < 1){
            System.out.println("invalid number!");
            return;
        }

        // curr 指向最近添加到环形链表中的节点
        Child curr = null;
        for (int i = 1; i <= nums; i++) {
            Child child = new Child(i);

            if(i == 1){
                first = child;
                child.next = first;
                curr = child;
            }
            else {
                curr.next = child;
                child.next = first;
                curr = child;
            }

        }
    }

    // 遍历环形链表
    public void show(){
        if(first == null){
            System.out.println("The list is empty!");
            return;
        }

        Child curr = first;
        do{
            System.out.println(curr);
            curr = curr.next;
        }while (curr != first);
    }

    public Child getFirst() {
        return first;
    }

    /**
     * @param startNo   开始报数的编号
     * @param countNum  数几下
     * @param childNums 总人数
     */
    public void Josephus(int startNo,int countNum,int childNums){
        if(first == null || startNo < 1){
            System.out.println("error");
        }

        Child curr = first;     // 此时编号为1
        // 确定开始报数的位置
        for(int i = 1; i <= startNo - 1; i++){
            curr = curr.next;
        }

        int removed = 0;    // 出队人数
        while(removed < childNums){
            // curr.next出列 (countNum == 1 时出列的应该是curr自己，所以这是一个特殊情况，这里没解决)
            for(int i = 1; i <= countNum-2; i++){
                curr = curr.next;
            }

            System.out.println(curr.next);
            curr.next = curr.next.next;     // 出列，被垃圾回收机制回收
            curr = curr.next;   // 从下一个人重新开始报数

            removed++;
        }
    }

     /**
     * @param startNo   开始报数的编号
     * @param countNum  数几下
     * @param childNums 总人数
     */
     // 使用helper帮助理解，出列的即为helper.next，也就是first
    public void JosephusWithHelper(int startNo,int countNum,int childNums){

        // 其实countNum == 1是一种很特殊的情况，不过使用helper可以解决
        if(startNo < 1 || startNo > childNums || countNum < 1 || first ==null){
            System.out.println("error");
            return;
        }

        Child helper = first;

        // 移动helper使其位于first之后
        while (helper.next != first){
            helper = helper.next;
        }

        // 移动first和helper至startNo,需要移动helper - 1 次
        for(int i = 1; i <= startNo - 1; i++){
            first = first.next;
            helper = helper.next;
        }

        // 循环到只剩下一个节点，这时 helper == first
        while(helper != first){
            // 这里可以应对 countNum = 1 的情况，first 和 helper移动的次数为 countNum - 1
            for(int i = 1; i <= countNum - 1; i++){
                first = first.next;
                helper = helper.next;
            }
            // fist 出列
            System.out.println(first);
            helper.next = first.next;
            first = first.next;
        }

        System.out.println("最后一个 ：" + first);

    }
}