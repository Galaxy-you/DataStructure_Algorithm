package tree.huffmantree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * author: Galaxy Violet
 * date: 2025/10/25, 16:47
 */
public class HuffmanTreeDemo {
    public static void main(String[] args) {
        int[] arr = new int[]{13,7,8,3,29,6,1};

        HuffmanTree huffmanTree = new HuffmanTree(arr);

        huffmanTree.preOrderTraversal();


    }
}

// 为了让Node对象支持Collection对象的排序，实现Comparable接口
// 当然也可以使用内部接口来实现这个功能
/*
nodes.sort(new Comparator<Node>() {
                @Override
                public int compare(Node o1, Node o2) {
                    return o1.weight - o2.weight;
                }
            });
 */
class Node implements Comparable<Node>{
    /*char和Byte(直接)
    char 16位（两个字节），无符号字符类型，表示范围是0~65535，用来表示单个Unicode字符
    Byte 8位（一个字节）， 有符号整数，   表示范围是-128~127，用于处理原始二进制数据
    下面的data域如果使用Byte作为数据类型，需要确保数据在ASCll范围内
     */
    Byte data;     // 数据本身
    int weight; // 权值，即数据出现的次数
    Node left;
    Node right;

    public Node(int weight) {
        this.weight = weight;
        // char c ,没有赋初值，默认为 Unicode空字符，即 \u0000
    }

    public Node(Byte data, int weight) {
        this.data = data;
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Node{" +
                "c=" + data +
                ", weight=" + weight +
                '}';
    }

    @Override
    public int compareTo(Node o) {
        // 对weight进行比较 --> 从小到大进行排序
        return this.weight - o.weight;
    }


}

class HuffmanTree{
    Node root;

    // 创建Huffman树，并返回根节点root
    public HuffmanTree(List<Node> nodes){
        while (nodes.size() > 1){
            nodes.sort(new Comparator<Node>() {
                @Override
                public int compare(Node o1, Node o2) {
                    return o1.weight - o2.weight;
                }
            });
            Node leftChild = nodes.remove(0);
            Node rightChild = nodes.remove(0);

            // 根节点只有weight,没有data
            Node parent = new Node((leftChild.weight + rightChild.weight));
            parent.left = leftChild;
            parent.right = rightChild;

            nodes.add(parent);
        }
        root = nodes.get(0);
    }

    public HuffmanTree(int[] arr) {
        // 将array中的元素转换为Node类型，然后放入到一个ArrayList中去
        ArrayList<Node> nodes = new ArrayList<>();
        for(int weight : arr){
            nodes.add(new Node(weight));
        }

        // 取出根节点最小的两棵二叉树组成一棵新的二叉树
        // 当nodes中只含有一个节点时，即为哈夫曼树的根节点(或者使用for循环进行arr.length - 1 次也可以)
        while(nodes.size() != 1){
            // 升序排序
            // 这里可能会因为不同的排序算法而产生不同的Huffman树，因为weight权值一样的话取出其中的任何一个都可以
            Collections.sort(nodes);

            // 这里直接remove，而不是先get然后再remove（remove也有返回值的）
            Node leftChild = nodes.remove(0);
            // 注意rightChild这里不要写 1，如果使用get这里是写 1，但是 remove不一样，remove会将之后的元素自动前移
            //  Shifts any subsequent elements to the left      // subsequent adj.随后的，后来的
            Node rightChild = nodes.remove(0);

            // 构建一棵新的二叉树
            Node parent = new Node((leftChild.weight + rightChild.weight));
            parent.left = leftChild;
            parent.right = rightChild;

            nodes.add(parent);
        }

        root = nodes.get(0);
    }

    // 前序遍历 - root.left,right
    public void preOrderTraversal(){
        preOrderTraversalRec(root);
    }

    private void preOrderTraversalRec(Node node){
        if(node != null){
            System.out.print(node);
            preOrderTraversalRec(node.left);
            preOrderTraversalRec(node.right);
        }
    }
}
