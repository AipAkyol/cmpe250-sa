import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;



public class Project {
    public static Graph dataGraph;
    public static Graph knownGraph;
    public static HashSet<String> nodesInPath = new HashSet<>();
    public static ArrayList<Integer> closedSwitches = new ArrayList<>();

    public static Double dijkstraWeight = 0.0;
   
    public static void main(String[] args) throws Exception {
        // Save the original System.out
        PrintStream originalOut = System.out;

        try {
            PrintStream fileOut = new PrintStream(new File("output.txt"));
            System.setOut(fileOut);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // time
        long startTime = System.currentTimeMillis();

        
        
        String nodeFilePath = args[0];
        String edgeFilePath = args[1];
        String objectivesFilePath = args[2];

        
        
        File nodeFile = new File(nodeFilePath);
        Scanner nodeScanner = new Scanner (nodeFile);
    
        String firstNodeLine = nodeScanner.nextLine();
        String[] parts = firstNodeLine.split(" ");
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


        // Objective reading
        File objectivesFile = new File(objectivesFilePath);
        Scanner objectivesScanner = new Scanner (objectivesFile);
        int firstObjLine = Integer.parseInt(objectivesScanner.nextLine());
        String[] secondObjLine = objectivesScanner.nextLine().split(" ");
        ArrayList<CoordinateTuple> objectives = new ArrayList<>();
        ArrayList<LinkedList<Integer>> switches = new ArrayList<>();
        while (objectivesScanner.hasNextLine()){
            String[] line = objectivesScanner.nextLine().split(" ");
            CoordinateTuple newObjective = new CoordinateTuple(Integer.parseInt(line[0]), Integer.parseInt(line[1]));
            objectives.add(newObjective);
            LinkedList<Integer> newSwitch = new LinkedList<>();
            if (line.length > 2){
                for (int i = 2; i < line.length; i++){
                    newSwitch.add(Integer.parseInt(line[i]));
                }
            }
            else {
                newSwitch.add(-1); // if there is no switch, add -1 for index parallelism
            }
            switches.add(newSwitch);
        }
        objectivesScanner.close();

        // line of sight radius
        int losRadius = firstObjLine;

        // starting point
        int currentX = Integer.parseInt(secondObjLine[0]);
        int currentY = Integer.parseInt(secondObjLine[1]);

        LineOfSight los = new LineOfSight(losRadius, currentX, currentY, sizeX, sizeY);
        
        // add all nodes in the line of sight to the knownGraph
        ArrayList<CoordinateTuple> allInitialyKnownNodes = los.calculateInitialArea();

        for(CoordinateTuple node: allInitialyKnownNodes){
            knownGraph.getNodes().get(node.x + "-" + node.y).setType(dataGraph.getNodes().get(node.x + "-" + node.y).getType());
        }


        // create a player object
        Player player = new Player(los);

        int endX;
        int endY;

        for (int i=0; i<objectives.size(); i++){

            // set the end point
            endX = objectives.get(i).x;
            endY = objectives.get(i).y;
            
            // check if the player is not at the end
            while (currentX != endX || currentY != endY){
            
                // path finding again  
                ArrayList<String> path = Dijkstra.findPath(knownGraph, currentX + "-" + currentY, endX + "-" + endY);
                

                // update the path
                player.currentPath = path;

                // move the player
                CoordinateTuple lastPosition = player.move();

                // update position
                currentX = lastPosition.x;
                currentY = lastPosition.y;
            
            }
            int objNumber = i+1;
            System.out.println("Objective " + objNumber + " reached!");

            

            
            LinkedList<Integer> switchList = switches.get(i);

            // if there is a switch
            // TODO: in case of equal weights, the smaller switch number is chosen, add this to description
            if (switchList.get(0) != -1){
                Integer minSwitch = null;
                Double minWeight = Double.MAX_VALUE;
                Double secondMinWeight = Double.MAX_VALUE;
                for (int j = 0; j < switchList.size(); j++){
                    if (closedSwitches.contains(switchList.get(j))){ // if the switch is already closed, skip it
                        continue;
                    }
                    int switchType = switchList.get(j);
                    String nextObjId = objectives.get(i+1).x + "-" + objectives.get(i+1).y;
                    Double weight = calculateWeightWhenSwitched(currentX + "-" + currentY, nextObjId, switchType);
                    if (weight <= minWeight){
                        secondMinWeight = minWeight;
                        minWeight = weight;
                        minSwitch = switchType;
                    }
                }
                final double TOLERANCE = 1e-9; // or any small value appropriate for your use case
                if (Math.abs(minWeight - secondMinWeight) < TOLERANCE) {
                    System.out.println("Two minimum weights are equal, solution is not unique!");
                    // close the program
                    System.exit(-1);
                }
                if (minSwitch != null){ // if there is a switch to close

                    // close the switch here in dataGraph and knownGraph
                    closedSwitches.add(minSwitch);

                    for (Node node: dataGraph.getNodes().values()){
                        if (node.getType() == minSwitch){
                            node.setType(0); // node is passable now
                        }
                    }
                    for (Node node: knownGraph.getNodes().values()){
                        if (node.getType() == minSwitch){
                            node.setType(0); // node is passable now
                        }
                    }
                    System.out.println("Number " + minSwitch + " is chosen!");
                }
            }


        

        }
        // time
        long endTime = System.currentTimeMillis();
        // ptint time to console so setout should go to console
        System.setOut(originalOut);
        
        // SECONDS
        System.out.println("Execution time: " + (endTime - startTime) / 1000 + " seconds");
    }

    public static Double calculateWeightWhenSwitched(String startId, String endId, int switchType) {
        
        ArrayList<String> nodesClosed = new ArrayList<>(); // to reopen them after the calculation
        
        // close all nodes in the known graph that have the switchType
        for (Node node: knownGraph.getNodes().values()){
            if (node.getType() == switchType){
                nodesClosed.add(node.getId());
                node.setType(0); // node is passable now
            }
        }
        
        // run the dijkstra for weight calculation
        Dijkstra.findPath(knownGraph, startId, endId);
        // get the weight of end node
        Double weight = dijkstraWeight;
        
        // reopen the nodes
        for (String nodeId: nodesClosed){
            knownGraph.getNodes().get(nodeId).setType(switchType);
        }
        
        return weight;
    }
}
