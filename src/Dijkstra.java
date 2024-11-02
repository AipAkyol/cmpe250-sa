import java.util.*;

public class Dijkstra {

    
    public static ArrayList<String> findPath(Graph graph, String startID, String endID ) {

        boolean isFound = false;

        Comparator<Node> nodeComparator = Comparator.comparingDouble(node -> node.getCost());
        PriorityQueue<Node> queue = new PriorityQueue<>(nodeComparator);


        Set <Node> visited = new HashSet<>();
        HashMap <String, Node> nodesMap = graph.getNodes();

        
        for(Map.Entry<String,Node> entry: nodesMap.entrySet() ) { // Ensure getNodes() returns an Iterable
            Node node = entry.getValue();
            node.setCost(Double.MAX_VALUE);
            node.setParent(null);
        }
        Node start = nodesMap.get(startID);
        start.setCost(0);
        queue.add(start);
        while(!queue.isEmpty()) {
            Node current = queue.poll();

            // check if there are two possible greedy solutions from the current node//
            // TODO: dont run this while calculating the time
            Node next = queue.peek();
            if (next != null && current.getCost() == next.getCost()) {
                System.out.println("Two possible greedy solutions from the current node");
                // close the program
                System.exit(-1);
            }

            visited.add(current);
            if (current.getId().equals(endID)) {
                isFound = true;
                break;
            }
            for (String neighbourID: current.getNeighbourIDs(graph.sizeX, graph.sizeY)) {
                Node neighbour = graph.getNodes().get(neighbourID);
                // if the neighbour is a obstacle, skip it
                if (neighbour.getType() != 0) {
                    continue;
                }
                if (visited.contains(neighbour)) {
                    continue;
                }
                String edge_String = Edge.parser(current.getX(), current.getY(), neighbour.getX(), neighbour.getY());
                Edge edge = graph.getEdges().get(edge_String);
                double newCost = current.getCost() + edge.getWeight();
                if (newCost < neighbour.getCost()) {
                    // TODO:removed the neighbour from the queue because more than one greedy solution checker cannot handle more than one instance of one node in queue
                    queue.remove(neighbour);

                    neighbour.setCost(newCost);
                    neighbour.setParent(current);
                    queue.add(neighbour);
                }

        
            }
        }
        ArrayList<String> path = new ArrayList<>();
        if (!isFound) {
            System.out.println("No path found");
            System.exit(-1);
            return path;
        }
        Node current = nodesMap.get(endID);
        while (current != null) {
            path.add(current.getId());
            Project.nodesInPath.add(current.getId());
            current = current.getParent();
        }
        Collections.reverse(path);
        return path;




    }
    
}
