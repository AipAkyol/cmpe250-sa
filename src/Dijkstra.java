import java.util.*;

public class Dijkstra {

    
    public static ArrayList<String> findPath(Graph graph, String startID, String endID ) {

        Comparator<Node> nodeComparator = Comparator.comparingInt(node -> node.getCost());
        PriorityQueue<Node> queue = new PriorityQueue<>(nodeComparator);


        Set <Node> visited = new HashSet<>();
        HashMap <String, Node> nodesMap = graph.getNodes();

        
        for(Map.Entry<String,Node> entry: nodesMap.entrySet() ) { // Ensure getNodes() returns an Iterable
            Node node = entry.getValue();
            node.setCost(Integer.MAX_VALUE);
            node.setParent(null);
        }
        Node start = nodesMap.get(startID);
        start.setCost(0);
        queue.add(start);
        while(!queue.isEmpty()) {
            Node current = queue.poll();
            visited.add(current);
            if (current.getId().equals(endID)) {
                break;
            }
            for (String neighbourID: current.getNeighbourIDs(graph.sizeX, graph.sizeY)) {
                Node neighbour = graph.getNodes().get(neighbourID);
                if (visited.contains(neighbour)) {
                    continue;
                }
                String edge_String = Edge.parser(current.getX(), current.getY(), neighbour.getX(), neighbour.getY());
                Edge edge = graph.getEdges().get(edge_String);
                int newCost = current.getCost() + edge.getWeight();
                if (newCost < neighbour.getCost()) {
                    neighbour.setCost(newCost);
                    neighbour.setParent(current);
                    queue.add(neighbour);
                }

        
            }
        }
        ArrayList<String> path = new ArrayList<>();
        Node current = nodesMap.get(endID);
        while (current != null) {
            path.add(current.getId());
            current = current.getParent();
        }
        Collections.reverse(path);
        return path;




    }
    
}
