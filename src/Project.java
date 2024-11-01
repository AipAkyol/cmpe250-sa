import java.io.File;
import java.util.Scanner;
import java.util.ArrayList;

public class Project {
   
    public static void main(String[] args) throws Exception {
        
        String nodeFilePath = "C:\\BOUN\\cmpe250-sa\\src\\node-4-4.txt";
        String edgeFilePath = "C:\\BOUN\\cmpe250-sa\\src\\4-4.txt";

        
        
        File nodeFile = new File(nodeFilePath);
        Scanner nodeScanner = new Scanner (nodeFile);
    
        String firstline = nodeScanner.nextLine();
        String[] parts = firstline.split(" ");
        int sizeX = Integer.parseInt(parts[0]);
        int sizeY = Integer.parseInt(parts[1]);

        Graph graph = new Graph(sizeX, sizeY);

        while(nodeScanner.hasNextLine()){
            String[] line = nodeScanner.nextLine().split(" ");
            String ID = line[0] + "-" +line[1];
            Node newNode = new Node(ID,Integer.parseInt(line[0]), Integer.parseInt(line[1]), Integer.parseInt(line[2]));
            graph.addNode(newNode);
        }
        nodeScanner.close();
        
        
        
        
        
        
        
        // Edge reading and adding to the graph
        File edgeFile = new File(edgeFilePath);
        Scanner edgeScanner = new Scanner (edgeFile);
        while(edgeScanner.hasNextLine()){

            String[] line = edgeScanner.nextLine().split(" ");
            Edge newEdge = new Edge(line[0], Double.parseDouble(line[1]));
            graph.addEdge(newEdge);

        }
        edgeScanner.close();

        

        ArrayList<String> path = Dijkstra.findPath(graph, "0-0", "3-3");
    
        for(String nodeID: path){
            System.out.println(nodeID);
        }
        
    }
}
