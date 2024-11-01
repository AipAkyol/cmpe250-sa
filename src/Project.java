import java.io.File;
import java.util.Scanner;
import java.util.ArrayList;

public class Project {
   
    public static void main(String[] args) throws Exception {
        //read file one line by line with bufferedReader
        //parse the first line to get the size of the grid
        
        String filePath = "/Users/simalguven/VSCodeProjects/CMPE250-Graph/src/4-4.txt";
        
        File file = new File(filePath);
        Scanner scanner = new Scanner (file);
    
        String firstline = scanner.nextLine();
        String[] parts = firstline.split(" ");
        int sizeX = Integer.parseInt(parts[0]);
        int sizeY = Integer.parseInt(parts[1]);
        Graph graph = new Graph(sizeX, sizeY);
        int edgeCount = 0;
        while(scanner.hasNextLine()){
            String[] line = scanner.nextLine().split(" ");
            Edge newEdge = new Edge(line[0], Integer.parseInt(line[1]));
            graph.addEdge(newEdge);
            System.out.println("Edge added: " + newEdge.getId() + " with weight: " + newEdge.getWeight());
            edgeCount++;


        }
        scanner.close();
        for(int i = 0; i < sizeX; i++){
            for(int j = 0; j < sizeY; j++){
                Node newNode = new Node(i + "-" + j, i, j, 0);
                graph.addNode(newNode);

            }
        }

        System.out.println(edgeCount );
        ArrayList<String> path = Dijkstra.findPath(graph, "0-0", "3-3");
    
        for(String nodeID: path){
            System.out.println(nodeID);
        }
        
    }
}
