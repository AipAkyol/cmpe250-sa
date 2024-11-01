import java.util.ArrayList;

public class LineOfSight {
    //line of sight is calculated according to the farthest node in the circle having radius of n in all directions in a grid
    int radius;
    int currentX;
    int currentY;

    int sizeX; //size of the grid for boundary checks
    int sizeY; //size of the grid for boundary checks
    
    ArrayList<CoordinateTuple> rightPerimeterNodes = new ArrayList<>(); //nodes on the right perimeter of the circle
    ArrayList<CoordinateTuple> leftPerimeterNodes = new ArrayList<>(); //nodes on the left perimeter of the circle
    ArrayList<CoordinateTuple> topPerimeterNodes = new ArrayList<>(); //nodes on the top perimeter of the circle
    ArrayList<CoordinateTuple> bottomPerimeterNodes = new ArrayList<>(); //nodes on the bottom perimeter of the circle

    public LineOfSight(int radius, int currentX, int currentY) {
        this.radius = radius;
        this.currentX = currentX;
        this.currentY = currentY;

        //find the perimeter nodes
        
        //top perimeter
        for (int i = - radius; i <= radius; i++) { // itarate from left to right
            int y = radius;
            double distance = Math.pow(Math.abs(i),2) + Math.pow(Math.abs(y),2);
            while(distance > Math.pow(radius,2)){
                y--;
                distance = Math.pow(Math.abs(i),2) + Math.pow(Math.abs(y),2);
            }
            CoordinateTuple node = new CoordinateTuple(currentX + i, currentY + y);
            topPerimeterNodes.add(node);

        //bottom perimeter
            y = -radius;
            distance = Math.pow(Math.abs(i),2) + Math.pow(Math.abs(y),2);
            while(distance > Math.pow(radius,2)){
                y++;
                distance = Math.pow(Math.abs(i),2) + Math.pow(Math.abs(y),2);
            }
            node = new CoordinateTuple(currentX + i, currentY + y);
            bottomPerimeterNodes.add(node);
        }

        //right perimeter
        for (int i = - radius; i <= radius; i++) { // itarate from top to bottom
            int x = radius;
            double distance = Math.pow(Math.abs(i),2) + Math.pow(Math.abs(x),2);
            while(distance > Math.pow(radius,2)){
                x--;
                distance = Math.pow(Math.abs(i),2) + Math.pow(Math.abs(x),2);
            }
            CoordinateTuple node = new CoordinateTuple(currentX + x, currentY + i);
            rightPerimeterNodes.add(node);

        //left perimeter
            x = -radius;
            distance = Math.pow(Math.abs(i),2) + Math.pow(Math.abs(x),2);
            while(distance > Math.pow(radius,2)){
                x++;
                distance = Math.pow(Math.abs(i),2) + Math.pow(Math.abs(x),2);
            }
            node = new CoordinateTuple(currentX + x, currentY + i);
            leftPerimeterNodes.add(node);
        }

    }

    public ArrayList<CoordinateTuple> moveRight() {
        //move the current position to the right
        currentX++;
        //update all the perimeter nodes
        for (CoordinateTuple node: rightPerimeterNodes){
            node.x++;
        }
        for (CoordinateTuple node: leftPerimeterNodes){
            node.x++;
        }
        for (CoordinateTuple node: topPerimeterNodes){
            node.x++;
        }
        for (CoordinateTuple node: bottomPerimeterNodes){
            node.x++;
        }

        ArrayList<CoordinateTuple> output = new ArrayList<>();
        //boundary check for right perimeter nodes
        for (CoordinateTuple node: rightPerimeterNodes){
            if (node.x < sizeX){
                output.add(node);
            }
        }

        return output;
    }
    
    public ArrayList<CoordinateTuple> moveLeft() {
        //move the current position to the left
        currentX--;
        //update all the perimeter nodes
        for (CoordinateTuple node: rightPerimeterNodes){
            node.x--;
        }
        for (CoordinateTuple node: leftPerimeterNodes){
            node.x--;
        }
        for (CoordinateTuple node: topPerimeterNodes){
            node.x--;
        }
        for (CoordinateTuple node: bottomPerimeterNodes){
            node.x--;
        }
        
        ArrayList<CoordinateTuple> output = new ArrayList<>();
        //boundary check for left perimeter nodes
        for (CoordinateTuple node: leftPerimeterNodes){
            if (node.x >= 0){
                output.add(node);
            }
        }

        return output;
    }

    public ArrayList<CoordinateTuple> moveUp() {
        //move the current position up
        currentY++;
        //update all the perimeter nodes
        for (CoordinateTuple node: rightPerimeterNodes){
            node.y++;
        }
        for (CoordinateTuple node: leftPerimeterNodes){
            node.y++;
        }
        for (CoordinateTuple node: topPerimeterNodes){
            node.y++;
        }
        for (CoordinateTuple node: bottomPerimeterNodes){
            node.y++;
        }
        
        ArrayList<CoordinateTuple> output = new ArrayList<>();
        //boundary check for top perimeter nodes
        for (CoordinateTuple node: topPerimeterNodes){
            if (node.y < sizeY){
                output.add(node);
            }
        }

        return output;
    }

    public ArrayList<CoordinateTuple> moveDown() {
        //move the current position down
        currentY--;
        //update all the perimeter nodes
        for (CoordinateTuple node: rightPerimeterNodes){
            node.y--;
        }
        for (CoordinateTuple node: leftPerimeterNodes){
            node.y--;
        }
        for (CoordinateTuple node: topPerimeterNodes){
            node.y--;
        }
        for (CoordinateTuple node: bottomPerimeterNodes){
            node.y--;
        }

        ArrayList<CoordinateTuple> output = new ArrayList<>();
        //boundary check for bottom perimeter nodes
        for (CoordinateTuple node: bottomPerimeterNodes){
            if (node.y >= 0){
                output.add(node);
            }
        }

        return output;
    }
}
