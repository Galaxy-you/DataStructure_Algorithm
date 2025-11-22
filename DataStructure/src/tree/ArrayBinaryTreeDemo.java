package tree;

public class ArrayBinaryTreeDemo {
    public static void main(String[] args) {
        int[] arr = new int[]{1,2,3,4,5,6,7};

        ArrayBinaryTree arrayBinaryTree = new ArrayBinaryTree(arr);
        arrayBinaryTree.preOrderTraversal();
    }
}

// 实现顺序存储二叉树
class ArrayBinaryTree{
    private int[] arr;  // 原始数组数据

    public ArrayBinaryTree(int[] arr) {
        this.arr = arr;
    }

    public void preOrderTraversal(int index){
        if(arr == null || arr.length == 0){
            // traversal n.遍历，横过，障碍物
            System.out.println("The arr is empty,failed traversal");
        }

        System.out.println(arr[index]);

        if((index * 2 + 1) < arr.length){
            preOrderTraversal(index * 2 + 1);
        }

        if((index * 2 + 1) < arr.length){
            preOrderTraversal(index * 2 + 2);
        }
    }

    public void preOrderTraversal(){
        preOrderTraversal(0);
    }
}
