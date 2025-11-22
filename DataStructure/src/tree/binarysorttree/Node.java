package tree.binarysorttree;

/**
 * author: Galaxy Violet
 * date: 2025/10/29, 20:10
 */
public class Node {
    public int value;
    public Node left;
    public Node right;

    public Node(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Node{" +
                "value=" + value +
                '}';
    }
}
