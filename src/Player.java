import java.util.ArrayList;

public class Player {

    LineOfSight los;
    ArrayList<String> currentPath;
    int currentX;
    int currentY;

    public Player(LineOfSight los, ArrayList<String> currentPath) {
        this.los = los;
        this.currentPath = currentPath;
    }

    public CoordinateTuple move() {
        // itarate over the path
        for (int i = 1; i < currentPath.size(); i++) {
            currentX = los.currentX;
            currentY = los.currentY;
            String[] nextNode = currentPath.get(i).split("-");
            int nextX = Integer.parseInt(nextNode[0]);
            int nextY = Integer.parseInt(nextNode[1]);
            System.out.println("Moving to " + nextX + "-" + nextY);
            ArrayList<CoordinateTuple> nextPerimeter;
            if (nextX == currentX) { //y direction
                if (nextY > currentY) {
                    currentY++;
                    nextPerimeter = los.moveUp();
                } else {
                    currentY--;
                    nextPerimeter = los.moveDown();
                }
            } else { //x direction
                if (nextX > currentX) {
                    currentX++;
                    nextPerimeter = los.moveRight();
                } else {
                    currentX--;
                    nextPerimeter = los.moveLeft();
                }
            }
            // update the knownGraph
            for (CoordinateTuple node : nextPerimeter) {
                Project.knownGraph.getNodes().get(node.x + "-" + node.y).setType(Project.dataGraph.getNodes().get(node.x + "-" + node.y).getType());
            }
            
            //check for collisions with path
            for (CoordinateTuple node : nextPerimeter) {
                String id = node.x + "-" + node.y;
                if (Project.dataGraph.getNodes().get(id).getType() < 2) {
                    continue;
                }   
                if (Project.nodesInPath.contains(id)) {
                    System.out.println("Collision with path");
                    CoordinateTuple output = new CoordinateTuple(currentX, currentY);
                    return output;
                }

            }
        }
        CoordinateTuple output = new CoordinateTuple(currentX, currentY);
        return output;
    }


}
