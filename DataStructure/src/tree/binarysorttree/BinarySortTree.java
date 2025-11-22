package tree.binarysorttree;

/**
 * author: Galaxy Violet
 * date: 2025/10/29, 20:06
 */
public class BinarySortTree {
    public Node root;

    public void add(Node addNode) {
        // 需要判断是否为空树
        if (root == null) {
            root = addNode;
        } else {
            addRec(root, addNode);
        }
    }

    // recursive
    // 私有方法不能被继承，所有子类也不能重写该私有方法
    // 改成protected访问属性
    private void addRec(Node currNode, Node addNode) {
        if (addNode == null) {
            return;
        }
        // 比较add节点和当前子树的根节点 => 左小右大（二叉排序树）
        if (addNode.value <= currNode.value) {
            if (currNode.left == null) {
                currNode.left = addNode;
            } else {
                // 递归向左子树添加
                addRec(currNode.left, addNode);
            }
        } else {
            if (currNode.right == null) {
                currNode.right = addNode;
            } else {
                addRec(currNode.right, addNode);
            }
        }
    }

    // 中序遍历二叉排序树 -- 输出序列为升序排列的=
    public void inOrderTraversal() {
        inOrderTraversalRec(root);
    }

    private void inOrderTraversalRec(Node node) {
        if (node != null) {
            inOrderTraversalRec(node.left);
            System.out.println(node);
            inOrderTraversalRec(node.right);
        }
    }

    // 根据value查找节点
    public Node search(int value) {
        return searchRec(root, value);
    }

    public Node searchRec(Node node, int value) {
        if (node == null) {
            return null;
        }

        if (node.value == value) {
            return node;
        }

        if (node.value < value) {
            return searchRec(node.right, value);
        } else {
            return searchRec(node.left, value);
        }
    }

    // 根据value查找父节点
    public Node findParent(int value) {
        return findParentRec(root, value);

    }

    // node 是 父节点，然后value是子节点的值
    public Node findParentRec(Node node, int value) {
        if (node == null) {
            return null;
        }

        if ((node.left != null && node.left.value == value) ||
                (node.right != null && node.right.value == value)) {
            return node;
        }

        if (node.value < value && node.right != null) {   // 这里判断node.right != null 可以不加：因为递归函数的开头会检查是否为null，加了的话可以减少一次递归
            return findParentRec(node.right, value);
        } else if (node.value > value && node.left != null) {
            return findParentRec(node.left, value); //
        } else {
            // 查到叶子节点还没有找到符合条件的父节点
            return null;
        }
    }

    // 返回以Node为根节点的二叉排序树的最小值（最左边的那个节点的value）
    public int minValue(Node node) {
        Node minNode = node;
        while (minNode.left != null) {
            minNode = minNode.left;
        }
        return minNode.value;
    }

    // 删除节点，分三种情况
    public void delete(int value) {
        Node targetNode = search(value);
        if (targetNode == null) {
            return;
        }

        Node parentNode = findParent(value);

        // 删除的节点为叶子节点
        if (targetNode.left == null && targetNode.right == null) {
            if (parentNode == null) { // 此时该树只有一个节点，根节点，直接root置空即可
                root = null;
            } else {
                if (parentNode.left == targetNode) {
                    parentNode.left = null;
                } else if (parentNode.right == targetNode) {
                    parentNode.right = null;
                } else {
                    System.out.println("error");    // 异常情况，一般不会出现，除非函数编写有问题
                }
            }
        }

        // 删除的节点只有一棵子树
        else if (targetNode.left != null && targetNode.right == null) { // 只有左子树
            if (parentNode == null) { // 需要删除的节点为根节点
                root = targetNode.left;
            } else {
                if (parentNode.left == targetNode) {
                    parentNode.left = targetNode.left;
                } else {
                    parentNode.right = targetNode.left;
                }
            }

        } else if (targetNode.right != null && targetNode.left == null) {  // 只有右子树
            if (parentNode == null) {
                root = targetNode.right;
            } else {
                if (parentNode.left == targetNode) {
                    parentNode.left = targetNode.right;
                } else {
                    parentNode.right = targetNode.right;
                }
            }
        }

        // 删除的节点有两棵子树
        // 将右子树的最小值节点minNode 替换 targetNode
        // 或者将左子树的最大值maxNode 替换 targetNode
        else {
            int minValue = minValue(targetNode.right);  // 右子树的最小值（最左端的叶子节点）

            // 先把这个最小的节点删掉
            delete(minValue);

            // 将node的value换为右子树的最小值
            targetNode.value = minValue;
        }
    }

}
