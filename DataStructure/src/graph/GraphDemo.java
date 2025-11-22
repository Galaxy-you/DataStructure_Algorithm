package graph;


/**
 * author: Galaxy Violet
 * date: 2025/10/30, 16:52
 */
public class GraphDemo {
    public static void main(String[] args) {
        int numOfVertex = 8;
        String[] vertices = {"a", "b", "c", "d", "e", "f", "g", "h"};

        Graph graph = new Graph(numOfVertex);

        for(String vertex: vertices){
            graph.insertVertex(vertex);
        }

        graph.insertEdge(0,1,1);    // a-b
        graph.insertEdge(0,2,1);
        graph.insertEdge(1,3,1);
        graph.insertEdge(1,4,1);
        graph.insertEdge(3,7,1);
        graph.insertEdge(4,7,1);
        graph.insertEdge(2,5,1);
        graph.insertEdge(2,6,1);
        graph.insertEdge(5,6,1);

        graph.showGraph();

        System.out.println("DFS");
        graph.DFS();

        System.out.println("\nBFS");
        graph.BFS();
    }
}
