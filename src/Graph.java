import java.util.*;

public class Graph {
    private HashMap<String, Node> nodes;
    private HashMap<String, Edge> edges;
    int sizeX;
    int sizeY;
    public Graph(int sizeX, int sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        nodes = new HashMap<>();
        edges = new HashMap<>();
    }
    //getters
    public HashMap<String, Node> getNodes() {
        return nodes;
    }
    public HashMap<String, Edge> getEdges() {
        return edges;
    }
    //adders
    public void addNode(Node node) {
        nodes.put(node.getId(), node);
    }
    public void addEdge(Edge edge) {
        edges.put(edge.getId(), edge);
    }
    
}
