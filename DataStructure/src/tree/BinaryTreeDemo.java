package tree;

public class BinaryTreeDemo {
    public static void main(String[] args) {
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);
        Node node5 = new Node(5);

        node1.left = node2;
        node1.right = node3;
        node3.left = node4;
        node3.right = node5;

        BinaryTree binaryTree = new BinaryTree(node1);

        binaryTree.preOrderTraversal();

        binaryTree.inOrderTraversal();

        binaryTree.postOrderTraversal();

        System.out.println(binaryTree.preOrderSearch(9));

        System.out.println(binaryTree.inOrderSearch(9));

        binaryTree.deleteNode(1);
        binaryTree.deleteNode(3);
        binaryTree.preOrderTraversal();

    }
}


// 节点
class Node{
    private int id;
    public Node left;
    public Node right;

    public Node(int id) {
        this.id = id;
    }

    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Node{" +
                "id=" + id +
                '}';
    }
}

class BinaryTree{
    Node root;

    public BinaryTree(Node root){
        this.root = root;
    }

    // 先序遍历-根左右
    public void preOrderTraversal() {
        System.out.println("preOrderTraversal");
        // 启动递归函数
        preOrderTraversalRec(root);
    }

    // rec-recursive,adj.递归的，循环的
    private void preOrderTraversalRec(Node node){
        if(node != null){
            System.out.println(node);
            preOrderTraversalRec(node.left);
            preOrderTraversalRec(node.right);
        }
    }

    // 中序遍历-左右根
    public void inOrderTraversal(){
        System.out.println("inOrderTraversal");
        inOrderTraversalRec(root);
    }

    private void inOrderTraversalRec(Node node){
        if(node != null){
            inOrderTraversalRec(node.left);
            System.out.println(node);
            inOrderTraversalRec(node.right);
        }
    }

    // 后续遍历-左右根
    public void postOrderTraversal(){
        System.out.println("postOrderTraversal");
        postOrderTraversalRec(root);
    }

    private void postOrderTraversalRec(Node node){
        if(node != null){
            postOrderTraversalRec(node.left);
            postOrderTraversalRec(node.right);
            System.out.println(node);
        }
    }

    // 前序遍历查找
    public Node preOrderSearch(int id){
        System.out.println("preOrderSearch");
        return preOrderSearchRec(id,root);
    }

    private Node preOrderSearchRec(int id, Node node){
        if(node != null){
            if(node.getID() == id){
                return node;
            }
            // 这里需要一个值来接受结果来判断是否为null
            Node resNode;
            resNode = preOrderSearchRec(id, node.left);
            if(resNode != null){
                return resNode;
            }

            resNode = preOrderSearchRec(id,node.right);
            if(resNode != null){
                return resNode;
            }
            // 下面这句return需不需要?
            // 必须需要，表示当前节点不为空，但是以当前节点为根节点的子树中没有所要查找的节点
            return null;
        }
        return null;
    }

    // 中序遍历查找
    public Node inOrderSearch(int id){
        System.out.println("inOrderSearch");
        return inOrderSearchRec(id,root);
    }

    private Node inOrderSearchRec(int id, Node node){
        if(node != null){
            Node resNode;
            resNode = inOrderSearchRec(id,node.left);
            if(resNode != null){
                return resNode;
            }
            if(node.getID() == id){
                return node;
            }
            resNode = inOrderSearchRec(id, node.right);
            if(resNode != null){
                return resNode;
            }
            return null;
        }
        return null;
    }

    // 删除节点（以前序遍历为例）
    // 二叉树为单向的，所以需要删除的节点为node.left或者node.right
    public void deleteNode(int id){
        if(root != null && root.getID() == id){
            root.left = null;
            root.right = null;
            root = null;
            System.out.println("successfully deleted");
            return;
        }
        if(deleteNodeRec(id,root)){
            System.out.println("successfully deleted");
        }else{
            System.out.println("deletion failed");
        }

    }

    private boolean deleteNodeRec(int id, Node node){
        if(node != null){
            if(node.left != null && node.left.getID() == id){
                node.left = null;
                return true;
            }
            if(node.right != null && node.right.getID() == id){
                node.right = null;
                return true;
            }
            if(deleteNodeRec(id, node.left)){
                return true;
            }
            if(deleteNodeRec(id, node.right)){
                return true;
            }
            return false;
        }
        return false;
    }
}
