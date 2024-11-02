import java.io.File;
import java.util.Scanner;


import java.util.ArrayList;
import java.util.HashSet;

public class Project {
    public static Graph dataGraph;
    public static Graph knownGraph;
    public static HashSet<String> nodesInPath = new HashSet<>();
   
    public static void main(String[] args) throws Exception {
        
        String nodeFilePath = "C:\\BOUN\\cmpe250-sa\\src\\node-4-4.txt";
        String edgeFilePath = "C:\\BOUN\\cmpe250-sa\\src\\4-4.txt";

        // starting point
        int currentX = 0;
        int currentY = 0;

        // ending point
        int endX = 3;
        int endY = 3;

        // line of sight radius
        int losRadius = 2;
        
        
        File nodeFile = new File(nodeFilePath);
        Scanner nodeScanner = new Scanner (nodeFile);
    
        String firstline = nodeScanner.nextLine();
        String[] parts = firstline.split(" ");
        int sizeX = Integer.parseInt(parts[0]);
        int sizeY = Integer.parseInt(parts[1]);

        dataGraph = new Graph(sizeX, sizeY);
        knownGraph = new Graph(sizeX, sizeY);

        while(nodeScanner.hasNextLine()){
            String[] line = nodeScanner.nextLine().split(" ");
            String ID = line[0] + "-" +line[1];
            Node newNode = new Node(ID,Integer.parseInt(line[0]), Integer.parseInt(line[1]), Integer.parseInt(line[2]));
            dataGraph.addNode(newNode);

            if (Integer.parseInt(line[2]) == 1){
                knownGraph.addNode(new Node(ID,Integer.parseInt(line[0]), Integer.parseInt(line[1]), 1));
            }
            else {
                // player initially does not know the type of the node if its bigger than 1
                // it knows it as a passable node
                knownGraph.addNode(new Node(ID,Integer.parseInt(line[0]), Integer.parseInt(line[1]), 0));
            }
        }
        nodeScanner.close();
        
        
        
        
        
        
        
        // Edge reading and adding to the dataGraph
        File edgeFile = new File(edgeFilePath);
        Scanner edgeScanner = new Scanner (edgeFile);
        while(edgeScanner.hasNextLine()){

            String[] line = edgeScanner.nextLine().split(" ");
            Edge newEdge = new Edge(line[0], Double.parseDouble(line[1]));
            dataGraph.addEdge(newEdge);

            knownGraph.addEdge(new Edge(line[0], Double.parseDouble(line[1])));

        }
        edgeScanner.close();

        LineOfSight los = new LineOfSight(losRadius, currentX, currentY, sizeX, sizeY);
        
        // add all nodes in the line of sight to the knownGraph
        ArrayList<CoordinateTuple> allInitialyKnownNodes = los.calculateInitialArea();

        for(CoordinateTuple node: allInitialyKnownNodes){
            knownGraph.getNodes().get(node.x + "-" + node.y).setType(dataGraph.getNodes().get(node.x + "-" + node.y).getType());
        }


        // initial path finding
        String startID = currentX + "-" + currentY;
        String endID = endX + "-" + endY;
        ArrayList<String> path = Dijkstra.findPath(knownGraph, startID, endID);


        // create a player object
        Player player = new Player(los, path);
        
        // moving the player
        CoordinateTuple lastPosition = player.move();
        
        // update position
        currentX = lastPosition.x;
        currentY = lastPosition.y;

        // check if the player is not at the end
        while (currentX != endX || currentY != endY){
            
            // path finding again  
            path = Dijkstra.findPath(knownGraph, currentX + "-" + currentY, endX + "-" + endY);
            
            // update the path
            player.currentPath = path;

            // move the player
            lastPosition = player.move();

            // update position
            currentX = lastPosition.x;
            currentY = lastPosition.y;

        }

        

        
    }
}
