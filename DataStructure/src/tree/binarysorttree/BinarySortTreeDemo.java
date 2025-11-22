package tree.binarysorttree;

/**
 * author: Galaxy Violet
 * date: 2025/10/27, 21:54
 */
public class BinarySortTreeDemo {
    public static void main(String[] args) {
        int[] arr = {7,3,10,12,5,1,9};
        BinarySortTree binarySortTree = new BinarySortTree();
        for (int i = 0; i < arr.length; i++) {
            Node addNode = new Node(arr[i]);
            binarySortTree.add(addNode);
        }
        binarySortTree.inOrderTraversal();

        System.out.println("after delete");
        binarySortTree.delete(7);
        binarySortTree.delete(5);
        binarySortTree.inOrderTraversal();

    }
}



