package graph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * author: Galaxy Violet
 * date: 2025/11/21, 19:38
 */
/*
    图的标识方式有两种，邻接矩阵 和 领接表
    邻接矩阵使用于稠密图，领接表适用于稀疏图
 */
public class Graph {
    // 用领接表表示图
    // adj.get(u)表示与顶点u相连的顶点的ArrayList集合
    // 这里是Edge不带权值的领接表
    private List<List<Integer>> adj;

    // Edge带权值的领接表
    // private List<List<Edge>> adj;
    private int n;

    public Graph(int n){
        this.n = n;
        adj = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            adj.add(new ArrayList<Integer>());
        }
    }

    // 添加边（无向图）
    public void addEdge(int u, int v){
        adj.get(u).add(v);
        // 带权值的图的添加
        // adj.get(u).add(new Edge(v,weight));
        adj.get(v).add(u);
    }

    // 添加边（无向图）
    public void addDirectEdge(int u, int v){
        adj.get(u).add(v);
    }

    // 获取顶点v的所有邻接点
    public List<Integer> getNeighbors(int v){
        return adj.get(v);
    }

    public boolean hasEdge(int u, int v){
        return adj.get(u).contains(v);
    }


    // 这是从某个顶点开始DFS，也就是遍历一个连通分量
    private void DFS(int start, boolean[] visited){
        visited[start] = true;
        System.out.print(start + " ");

        for(int adjVertex: getNeighbors(start)){
            if(!visited[adjVertex]){
                DFS(adjVertex, visited);
            }
        }
    }

    public void DFS(){
        boolean[] visited = new boolean[n]; // 默认为false
        // 遍历所有连通分量
        for (int i = 0; i < n; i++) {
            if(!visited[i]){
                DFS(i, visited);
            }
        }
    }

    // 广度优先遍历----借助队列实现
    public void BFS(int start, boolean[] visited){
        Queue<Integer> queue = new ArrayDeque<>();

        System.out.print(start + " ");
        visited[start] = true;
        queue.offer(start);

        while (!queue.isEmpty()){
            int vertex = queue.poll();

            for (int adjVertex: getNeighbors(vertex)){
                if(!visited[adjVertex]){
                    System.out.print(adjVertex + " ");
                    visited[adjVertex] = true;
                    queue.offer(adjVertex);
                }
            }
        }
    }

    public void BFS(){
        boolean[] visited = new boolean[n]; // default: false

        for (int i = 0; i < n; i++) {
            if(!visited[i]){
                BFS(i, visited);
            }
        }
    }

    // 判断无向图是否有环
    /*
    使用DFS遍历，如果在访问过程中访问到一个已访问的节点，
    且这个节点不是当前节点的父节点，则说明存在环。
    父节点：DFS(A) --> 访问到了B，则B的父节点为A
     */
    public boolean hasCycle(){
        boolean[] visited = new boolean[n];

        // 遍历所有连通分量
        for (int i = 0; i < n; i++) {
            if(!visited[i]){
                if(dfs(i, -1, visited)){    // -1 表示没有父节点
                    // 发现了环
                    return true;
                }
            }
        }

        // 没有发现环
        return false;
    }

    private boolean dfs(int vertex, int parent, boolean[] visited){
        visited[vertex] = true;

        for(int neighbor: getNeighbors(vertex)){
            if(!visited[neighbor]){
                // 没有访问过，继续递归
                if(dfs(neighbor, vertex, visited)){
                    // 继续递归的过程中发现了环，返回true
                    return true;
                }
            } else if (neighbor != parent) {
                // visited[neighbor]  == true,即已经访问过该节点
                // 且该节点不是父节点，可以推断发现了环
                return true;
            }
            // neighbor == parent , 这是回头边，刚刚从parentDFS到vertex
        }

        // 遍历了一个连通分量，没有发现环
        return false;
    }

    // 有向图判断环--先跳过吧


}
