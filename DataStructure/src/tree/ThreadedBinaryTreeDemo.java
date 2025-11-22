package tree;

public class ThreadedBinaryTreeDemo {
    public static void main(String[] args) {
        // 测试中序线索二叉树

        ThreadedNode node1 = new ThreadedNode(1);
        ThreadedNode node2 = new ThreadedNode(3);
        ThreadedNode node3 = new ThreadedNode(6);
        ThreadedNode node4 = new ThreadedNode(8);
        ThreadedNode node5 = new ThreadedNode(10);
        ThreadedNode node6 = new ThreadedNode(14);
        node1.left = node2;
        node1.right = node3;
        node2.left = node4;
        node2.right = node5;
        node3.left = node6;

        ThreadedBinaryTree threadedBinaryTree = new ThreadedBinaryTree();
        threadedBinaryTree.root = node1;

        threadedBinaryTree.threadNodes();

        // 中序序列为 8,3,10,1,14,6
        threadedBinaryTree.inOrderTraversal();
    }
}

class ThreadedBinaryTree{
    public ThreadedNode root;

    // 指向当前节点的前驱节点
    ThreadedNode prev;

    public void threadNodes(){
        threadNodes(root);
    }

    // 中序线索化(逐个线索化二叉树的各个节点)
    public void threadNodes(ThreadedNode node){
        if(node == null){
            return;
        }

        // 线索化左子树
        threadNodes(node.left);

        // 线索化当前节点的左指针
        if(node.left == null){
            node.left = prev;
            node.leftTag = 1;   // 线索
        }else {
            node.leftTag = 0;   // 左子树,可以不写，int 默认初始化为 0,不过建议写
        }

        // 滞后线索化previous节点的右指针
        // 访问到当前节点的时候设置前一个节点的后继
        // 而不是访问到当前节点的时候设置当前节点的前驱（）
       if(prev != null){
           if(prev.right == null){
               prev.right = node;
               prev.rightTag = 1;
           }
           else {
               prev.rightTag = 0;
           }
       }
        // 当前节点为下一个节点的前驱节点
        prev = node;

        // node.right 的 previous 是 node
        // 线索化右子树
        threadNodes(node.right);

    }

    // 中序遍历线索二叉树
    public void inOrderTraversal(){
        ThreadedNode node = root;

        while (node != null){
            // 循环找到输出序列中最靠前的节点
            // 当当前节点存在左子树时向左移动
            while (node.leftTag == 0){
                node = node.left;
            }

            System.out.println(node);

            // 沿着后续线索打印所以节点
            while(node.rightTag == 1){
                node = node.right;
                System.out.println(node);
            }

            // 此时node.rightTag == 0，移动到右子树（如果有的话，最后一个节点的right为null）
            // 开启新一轮的寻找最左节点
            node = node.right;
        }
    }
}

class ThreadedNode{
    public int id;
    public ThreadedNode left;
    public ThreadedNode right;

    public int leftTag;    // 1 --> 前驱节点   0 --> 左子树
    public int rightTag;   // 1 --> 后继节点   0 --> 右子树

    public ThreadedNode(int id) {
        this.id = id;
//        this.leftTag = 0;
//        this.rightTag = 0;
    }

    @Override
    public String toString() {
        return "ThreadedNode{" +
                "id=" + id +
                '}';
    }
}

