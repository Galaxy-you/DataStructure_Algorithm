package tree;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * author: Galaxy Violet
 * date: 2025/11/21, 13:50
 */
public class BinaryTree {
    private Node root;

    // 从数组构建完全二叉树
    public BinaryTree(Integer[] arr){
        if(arr == null || arr.length == 0 || arr[0] == null){
            return;
        }
        Queue<Node> queue = new ArrayDeque<>();
        root = new Node(arr[0]);
        queue.offer(root);

        int i = 1;
        while (!queue.isEmpty() && i < arr.length){
            Node node = queue.poll();

            // 配置node的左孩子
            if(i < arr.length && arr[i] != null){
                node.left = new Node(arr[i]);
                queue.offer(node.left);
            }
            i++;

            // 配置node的右孩子
            if(i < arr.length && arr[i] != null){
                node.right = new Node(arr[i]);
                queue.offer(node.right);
            }
            i++;
        }

    }

    // 根左右
    private static void preOrderRecursive(Node node){
        if(node == null){
            return;
        }
        System.out.println(node.val);
        preOrderRecursive(node.left);
        preOrderRecursive(node.right);
    }

    public void preOrder(){
        preOrderRecursive(root);
    }

    // 层次遍历
    // 返回一个List<List<Integer>>， list.get(i)即为第i层的所有节点
    // 比如说 [[1], [2, 3], [4, 5]]
    public List<List<Integer>> levelOrder(){
        List<List<Integer>> result = new ArrayList<>();
        if(root == null){
            return null;
        }

        // 层次遍历使用队列存储相关节点
        Queue<Node> queue = new ArrayDeque<>();
        queue.offer(root);

        while (!queue.isEmpty()){
            int levelSize = queue.size();
            ArrayList<Integer> level = new ArrayList<>();   // 一层的节点

            for (int i = 0; i < levelSize; i++) {
                Node node = queue.poll();
                level.add(node.val);

                queue.offer(node.left);
                queue.offer(node.right);
            }
            result.add(level);
        }
        return result;
    }


    // 求树的最大深度
    private int getDepthRecursive(Node node){
        if(node == null){
            return 0;
        }
        return Math.max(getDepthRecursive(node.left), getDepthRecursive(node.right)) + 1;
    }

    public int getDepth(){
        return getDepthRecursive(root);
    }


    // 判断二叉树树是否对称(symmetric adj.对称的)
    public boolean isSymmetric(){
        return isMirror(root.left, root.right);
    }

    private boolean isMirror(Node left, Node right){

        if(left == null && right == null){
            return true;
        }
        // 运行到这里代表左右至少有一个不为空
        // 有且仅有一个为null
        if(left == null || right == null){
            return false;
        }

        // 两边都不为null
        return isMirror(left.left, right.right)
                && isMirror(left.right, right.left);
    }

    // invert v.倒置，反转，倒转，翻转
    // inverse adj.反向的  n.反面
    public void invertTreeRecursive(Node node){
        if(node == null){
            return;
        }

        Node temp =  node.left;
        node.left = node.right;
        node.right = temp;

        invertTreeRecursive(node.left);
        invertTreeRecursive(node.right);
    }

    public void inverseTree(){
        invertTreeRecursive(root);
    }


    // 路径和问题
    // 给定一棵二叉树和一个目标值 targetSum，判断是否存在一条从根节点到叶子节点的路径，
    // 使得路径上所有节点值的和等于 targetSum。
    public boolean hasPathSumRecursive(Node node,int remaining){
        if(node == null){
            return false;
        }

        // 到达叶子节点，判断val == remaining
        if(node.left == null && node.right == null){
            return node.val == remaining;
        }

        return hasPathSumRecursive(node.left, remaining - node.val) ||
                hasPathSumRecursive(node.right, remaining - node.val);

    }

    public boolean hasPathSum(int targetSum){
        return hasPathSumRecursive(root, targetSum);
    }


    // 路径和问题返回所有路径
    public List<List<Integer>> pathSum(int targetSum){
        List<List<Integer>> paths = new ArrayList<>();
        List<Integer> path = new ArrayList<>();
        dfs(root, targetSum, paths, path);
        return paths;
    }

    // 需要参数 List<Integer>path, 每次递归只能获得一个路径中的节点
    public void dfs(Node node, int target, List<List<Integer>> paths, List<Integer> path){
        if(node == null){
            return;
        }

        // 添加当前节点到path中
        path.add(node.val);

        if(node.left == null && node.right == null){// 到达叶子节点进行验证
            if(node.val == target){// 路径符合条件
                // 注意这里需要重新new一个path:List<Integer>，而不是使用进行的path
                paths.add(new ArrayList<Integer>(path));
            }
        }else { // 非叶子节点：继续向下验证
            dfs(node.left, target - node.val, paths, path);
            dfs(node.right, target - node.val, paths, path);
        }
        // 删除刚刚添加的元素，回退到上一次的调用
        path.remove(path.size() - 1);
    }

    // 路径和问题：路径可以从让一节点开始，到任意节点结束（向下）
    // 返回符合条件的路径总数量
    // 双函数递归
    public int countAllPaths(Node node, int targetNum){
        if(root == null){
            return 0;
        }
        // 查找以 root 为起点的路径
        int count =  countPaths(root, targetNum);

        // 注意下面递归调用的是countAllPaths()而不是下面的子函数 countPaths
        // 并且这里的第二个参数仍然为targetNum，而不是targetNum - root.val
        // countAllPaths()函数的作用是遍历所有节点作为起点，然后调用countPaths()计算出以该节点为起点的所有路径数

        // 左子树中从某个节点开始的所有路径，这个“某个节点”还不止一个
        count += countAllPaths(root.left, targetNum);

        count += countAllPaths(root.right, targetNum);

        return count;

    }

    // 查找以node节点为路径起点且和为target的路径总数量
    private int countPaths(Node node, int target){
        if(node == null){
            return 0;
        }

        int count = 0;
        // 这里有两种情况：路径中只有一个节点 + 该节点为某条路径的最后一个节点
        if(root.val == target){
            count++;
        }
        count = count
                + countPaths(node.left, target - node.val)
                + countPaths(node.right, target - node.val);
        return count;
    }

}

class Node{
    int val;
    Node left;
    Node right;

    Node(int val){
        this.val = val;
    }
}


