package linearstructure;

import java.util.LinkedList;

/**
 * author: Galaxy Violet
 * date: 2025/11/21, 11:40
 */
public class CircleQueue {
    /**
     * 用LinkedList实现循环队列
     * @param n 总人数
     * @param m 从1开始报数，报道m的人出局
     * @return  最后剩下的人
     */
    public static int josephus(int n, int m){
        LinkedList<Integer> list = new LinkedList<>();
        for (int i = 1; i <= n; i++) {
            list.add(i);
        }

        int index = 0;  // 当前位置
        System.out.print("出局：");
        while (list.size() > 1){
            // 报数到m,计算要删除的位置
            index = (index + m - 1) % list.size();
            System.out.print(" " + list.remove(index));

            // 下面这个if可以不用，因为如果删除的是最后一个元素，相当于index = list.size() + 1
            // 但是取模list.size()后是1，是正确的
            if(index == list.size()){
                // 刚刚删除的为链表的最后一个元素，此时这个index是非法的，需要重置一下
                // 如果删除的不是最后一个元素，然后remove之后相当于当前index自动指向下一个开始报数的人
                index = 0;
            }
        }
        return list.get(0);
    }

    public static void main(String[] args){
        int n = 5;
        int m = 3;
        int survivor = josephus(n, m);
        System.out.println("最后剩下：" + survivor);
    }
}
