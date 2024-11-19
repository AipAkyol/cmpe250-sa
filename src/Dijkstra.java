import java.util.*;

public class Dijkstra {

    
    public static ArrayList<String> findPath(Graph graph, String startID, String endID ) {

        boolean isFound = false;

        Comparator<Node> nodeComparator = Comparator.comparingDouble(node -> node.getCost());
        PriorityQueue<Node> queue = new PriorityQueue<>(nodeComparator);

        /* 
        Set <Node> visited = new HashSet<>();
        */
        Set <String> visited = new HashSet<>();

        HashMap <String, Node> nodesMap = graph.getNodes();

        
        /*for(Map.Entry<String,Node> entry: nodesMap.entrySet() ) { // Ensure getNodes() returns an Iterable
            Node node = entry.getValue();
            node.setCost(Double.MAX_VALUE);
            node.setParent(null);
        }
        Node start = nodesMap.get(startID);
        start.setCost(0);
        queue.add(start);*/

        Node startInfo = nodesMap.get(startID);
        Node start = new Node(startInfo.getId(), startInfo.getX(), startInfo.getY(), startInfo.getType());
        start.setCost(0);
        start.setParent(null);
        queue.add(start);

        Node finishNode = null;

        while(!queue.isEmpty()) {
            Node current = queue.poll();

            if (visited.contains(current.getId())) {
                continue;
            }

            // check if there are two possible greedy solutions from the current node//
            // TODO: dont run this while calculating the time
            Node next = queue.peek();
            if (next != null && current.getCost() == next.getCost()) {
                System.out.println("Two possible greedy solutions from the current node");
                // close the program
                System.exit(-1);
            }
            //TODO: if visited atla??? 

            
            if (current.getId().equals(endID)) {
                isFound = true;
                finishNode = current;
                break;
            }
            

            visited.add(current.getId());
            for (String neighbourID: current.getNeighbourIDs(graph.sizeX, graph.sizeY)) {
                Node neighbourInfo = graph.getNodes().get(neighbourID);
                Node neighbour = new Node(neighbourInfo.getId(), neighbourInfo.getX(), neighbourInfo.getY(), neighbourInfo.getType());
                // if the neighbour is a obstacle, skip it
                if (neighbour.getType() != 0) {
                    continue;
                }
                if (visited.contains(neighbour.getId())) {
                    continue;
                }
                String edge_String = Edge.parser(current.getX(), current.getY(), neighbour.getX(), neighbour.getY());
                Edge edge = graph.getEdges().get(edge_String);
                double newCost = current.getCost() + edge.getWeight();
                neighbour.setCost(newCost);
                neighbour.setParent(current);
                queue.add(neighbour);
                /* 
                if (newCost < neighbour.getCost()) {
                    
                    // TODO:removed the neighbour from the queue because more than one greedy solution checker cannot handle more than one instance of one node in queue
                    queue.remove(neighbour);

                    neighbour.setCost(newCost);
                    neighbour.setParent(current);
                    queue.add(neighbour);
                }*/

        
            }
        }
        ArrayList<String> path = new ArrayList<>();
        if (!isFound) {
            System.out.println("No path found");
            System.exit(-1);
            return path;
        }
        Project.dijkstraWeight = finishNode.getCost();
        while (finishNode != null) {
            path.add(finishNode.getId());
            Project.nodesInPath.add(finishNode.getId());
            finishNode = finishNode.getParent();
        }
        Collections.reverse(path);
        return path;




    }
    
}
