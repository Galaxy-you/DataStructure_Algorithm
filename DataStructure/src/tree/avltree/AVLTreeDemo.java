package tree.avltree;

import tree.binarysorttree.BinarySortTree;
import tree.binarysorttree.Node;

import java.util.NoSuchElementException;

/**
 * author: Galaxy Violet
 * date: 2025/10/29, 19:53
 */
public class AVLTreeDemo {
    public static void main(String[] args) {
        // int[] arr = new int[]{4,3,6,5,7,8}; // LL
        // int[] arr = new int[]{10,12,8,9,7,6}; // RR
        int[] arr = new int[]{10,11,7,6,8,9};   // LR

        AVLTree avlTree = new AVLTree();

        for (int i = 0; i < arr.length; i++) {
            avlTree.add(new Node(arr[i]));
        }

        avlTree.inOrderTraversal();

        Node root = avlTree.root;

        System.out.println("height = " + avlTree.getHeight(root));
        System.out.println("leftChildTreeHeight = " + avlTree.getHeight(root.left));
        System.out.println("rightChildTreeHeight = " + avlTree.getHeight(root.right));


    }
}



class AVLTree extends BinarySortTree {

    // 返回以node节点为根节点的树高
    public int getHeight(Node node){
        if(node == null){
            return 0;
        }
        if(node.left == null && node.right == null){
            return  1;
        }
        return Math.max(getHeight(node.left), getHeight(node.right)) + 1;
    }

    // 获取平衡因子：右子树的高度 - 左子树的高度
    public int getBalanceFactor(Node node){
        if(node == null){
            return 0;
        }
        return getHeight(node.right) - getHeight(node.left);
    }

    // 四种旋转函数
    // 1、左旋转，解决RR失衡（右孩子的右子树过高）
    // 返回以 node 为根节点的子树旋转后的新的根节点
    public Node leftRotate(Node node){
        Node right = node.right;    // right 上提
        Node rightLeftChild = right.left;

        right.left =  node;
        node.right = rightLeftChild;

        return right;

    }

    // 2、右旋转，解决LL失衡（左孩子的左子树过高）
    // 返回以 node 为根节点的子树旋转后的新的根节点
    public Node rightRotate(Node node){
        Node left = node.left;  // 上提
        Node leftRightChild = left.right;

        left.right = node;
        node.left = leftRightChild;

        return  left;
    }


    @Override
    public void add(Node addNode){
        if(root == null){
            root = addNode;
        }else {
            root = addRec(root, addNode);
        }
    }

    /**
     * 递归插入，在回溯是执行平衡调整
     * @param currNode  当前子树的根节点
     * @param addNode   要插入的新节点
     * @return          插入或者旋转后的新子树的根节点(每一次插入都可能导致旋转，从而改变根节点)
     */
    private Node addRec(Node currNode, Node addNode) {
        if(currNode == null){   // 找到空位，返回新节点，作为上一层currNode.left/right的值
            return addNode;
        }

        if(addNode.value == currNode.value){    // 节点已存在，阻止重复插入，并返回当前节点。
            return addNode;
        }

        if(addNode.value < currNode.value){
            // 递归到左子树，并将左子树返回的新根赋给 currNode.left
            currNode.left = addRec(currNode.left, addNode);
        }else {
            currNode.right = addRec(currNode.right, addNode);
        }

        int bf = getBalanceFactor(currNode);    // 右子树的高度 - 左子树的高度

        // 右孩子失衡
        if(bf > 1){
            if(getBalanceFactor(currNode.right) > 0){
                // RR --> leftRotate
                return leftRotate(currNode);
            }else {
                // RL --> 先对右孩子rightRotate，然后leftRotate
                currNode.right = rightRotate(currNode.right);
                return leftRotate(currNode);
            }
        }

        // 左孩子失衡
        else if (bf < -1) {
            if(getBalanceFactor(currNode.left) < 0){
                // LL --> rightRotate
                return rightRotate(currNode);
            }else {
                // LR --> 先对左孩子 leftRotate，然后rightRotate
                currNode.left = leftRotate(currNode.left);
                return rightRotate(currNode);   // 这里需要return旋转后子树的根节点
            }
        }

        // 平衡，返回当前节点
        return currNode;
    }
}
