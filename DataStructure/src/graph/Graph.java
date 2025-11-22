package graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * author: Galaxy Violet
 * date: 2025/10/30, 16:34
 */
public class Graph {
    private ArrayList<String> vertexList;   // 存储顶点的集合
    private int [][] edges; // 邻接矩阵
    private int numOfEdges; // 表示边的数目
    private boolean[] isVisited;

    public Graph(int n){
        vertexList = new ArrayList<>(n);
        edges = new int[n][n];
        numOfEdges = 0; // 默认为0
        isVisited = new boolean[n];
    }

    public void insertVertex(String vertex){
        vertexList.add(vertex);
    }

    /**
     * 添加边
     * @param v1   第一个顶点对应的下标
     * @param v2    第一个顶点对应的下标
     * @param weight    weight
     */
    public void insertEdge(int v1, int v2, int weight){
        edges[v1][v2] = weight;
        edges[v2][v1] = weight;
        numOfEdges++;
    }

    public int getNumOfVertex(){
        return vertexList.size();
    }

    public int getNumOfEdges(){
        return numOfEdges;
    }

    public String getValueByIndex(int index){
        return vertexList.get(index);
    }

    public int getWeight(int v1, int v2){
        return edges[v1][v2];
    }

    public void showGraph(){
        // System.out.println(Arrays.toString(edges));
        for(int[] link: edges){
            System.out.println(Arrays.toString(link));
        }
    }

    // 返回某个顶点的全部邻接点
    public List<Integer> getAdjVertices(int index){
        ArrayList<Integer>adjVertices = new ArrayList<>();

        int vertexNum = getNumOfVertex();
        for(int i = 0; i < vertexNum; i++){
            if(edges[index][i] != 0){   // weight != 0 => 连通
                adjVertices.add(i);
            }
        }
        return adjVertices;
    }

    // 初始化isVisited[]
    public void initIsVisited(){
        for(int i = 0; i < isVisited.length; i++){
            isVisited[i] = false;
        }
    }

    // 从index开始DFS（depth first search）
    private void DFS(int index){
        System.out.print(getValueByIndex(index) + " -> ");
        isVisited[index] = true;

        List<Integer> adjVertices = getAdjVertices(index);

        // for-each循环在列表为空时，循环提升是不会执行的，因此不需要显示判断是否为空
        for(int adjIndex: adjVertices){ // 通过遍历
            if(!isVisited[adjIndex]){
                DFS(adjIndex);
            }
        }
    }

    // 遍历所有节点,处理非连通的图的多个连通分量
    public void DFS(){
        int vertexNum = getNumOfVertex();
        initIsVisited();

        for (int i = 0; i < vertexNum; i++){
            if(!isVisited[i]){
                DFS(i);
            }
        }
    }

//    // BFS(Broad First Search)
//    private void BFS(int index){
//        if(!isVisited[index]){
//            System.out.print(getValueByIndex(index) + " -> ");
//        }
//        isVisited[index] = true;
//
//        List<Integer> adjVertices = getAdjVertices(index);
//
//        for (int adjIndex: adjVertices){
//            if(!isVisited[adjIndex]){
//                System.out.print(getValueByIndex(adjIndex) + " -> ");
//                isVisited[adjIndex] = true;
//            }
//        }
//
//        // 递归报错： java.lang.StackOverflowError
//        // 这里没有判断，而是直接递归，A->B,B->A，...导致无限递归
//        for(int adjVertex: adjVertices){
//            BFS(adjVertex);
//        }
//    }
//
//    public void BFS(){
//        int vertexNum = getNumOfVertex();
//        initIsVisited();
//
//        for(int i = 0; i < vertexNum; i++){
//            if(!isVisited[i]){
//                BFS(i);
//            }
//        }
//    }

    // BFS(Broad First Search)
    // BFS一个连通分量。
    private void BFS(int index){
        LinkedList<Integer> queue = new LinkedList<>();

        System.out.print(getValueByIndex(index) + " -> ");
        isVisited[index] = true;
        queue.addLast(index);

        while (!queue.isEmpty()){
            int currentIndex = queue.removeFirst();

            List<Integer> adjVertices = getAdjVertices(currentIndex);

            for(int adjIndex: adjVertices){
                if(!isVisited[adjIndex]){
                    System.out.print(getValueByIndex(adjIndex) + " -> ");
                    isVisited[adjIndex] = true;
                    queue.addLast(adjIndex);    // 把已经访问的节点入队，为之后遍历做准备
                }
            }
        }
    }

    // private void BFS(int index){
    //     LinkedList<Integer> queue = new LinkedList<>();
        
    //     isVisited[index] = true;
    //     queue.addLast(index);

    //     while(!queue.isEmpty()){
    //         int curr = queue.removeFirst();
    //         System.out.print(getValueByIndex(curr) + " -> ");

    //         List<Integer> adjVertices = getAdjVertices(currentIndex);

    //         for(int adjIndex: adjVertices){
    //             if(!isVisited[adjIndex]){
    //                 isVisited[adjIndex] = true;
    //                 queue.addLast(adjIndex);    // 把已经访问的节点入队，为之后遍历做准备
    //             }
    //         }
    //     }
    // }

    public void BFS(){
        int vertexNum = getNumOfVertex();
        initIsVisited();

        for (int i = 0; i < vertexNum; i++) {
            if(!isVisited[i]){
                BFS(i);
            }
        }
    }
}


