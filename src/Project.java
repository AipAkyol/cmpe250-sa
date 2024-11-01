import java.io.File;
import java.util.Scanner;

import javax.sound.sampled.Line;

import java.util.ArrayList;

public class Project {
   
    public static void main(String[] args) throws Exception {
        
        String nodeFilePath = "C:\\BOUN\\cmpe250-sa\\src\\node-4-4.txt";
        String edgeFilePath = "C:\\BOUN\\cmpe250-sa\\src\\4-4.txt";

        // starting point
        int currentX = 0;
        int currentY = 0;
        
        
        File nodeFile = new File(nodeFilePath);
        Scanner nodeScanner = new Scanner (nodeFile);
    
        String firstline = nodeScanner.nextLine();
        String[] parts = firstline.split(" ");
        int sizeX = Integer.parseInt(parts[0]);
        int sizeY = Integer.parseInt(parts[1]);

        Graph dataGraph = new Graph(sizeX, sizeY);
        Graph knownGraph = new Graph(sizeX, sizeY);

        while(nodeScanner.hasNextLine()){
            String[] line = nodeScanner.nextLine().split(" ");
            String ID = line[0] + "-" +line[1];
            Node newNode = new Node(ID,Integer.parseInt(line[0]), Integer.parseInt(line[1]), Integer.parseInt(line[2]));
            dataGraph.addNode(newNode);

            if (Integer.parseInt(line[2]) == 1){
                knownGraph.addNode(new Node(ID,Integer.parseInt(line[0]), Integer.parseInt(line[1]), 0));
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

        LineOfSight los = new LineOfSight(2, 0, 0);
        

        
        String startID = currentX + "-" + currentY;
        ArrayList<String> path = Dijkstra.findPath(knownGraph, startID, "3-3");
    
        for(String nodeID: path){
            System.out.println(nodeID);
        }

        

        
    }
}
