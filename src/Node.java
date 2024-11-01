
import java.util.*;

public class Node {
   private int type;
    private int x;
    private int y;
    private String id;//X-Y
    private Node parent ;
    private int cost;



    
    public Node(String id, int x, int y, int type) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.type = type;
        this.parent = null;
    }

    public ArrayList<String> getNeighbourIDs(int sizeX, int sizeY) {
        //return the ids of the neighbours of the node
        //neighbours are the nodes that are adjacent to the node in the grid
        //the node is not on the border of the grid

        ArrayList<String> neighbours = new ArrayList<>();
        if (x > 0) {
            neighbours.add((x - 1) + "-" + y);
        }
        if (x < sizeX - 1) {
            neighbours.add((x + 1) + "-" + y);
        }
        if (y > 0) {
            neighbours.add(x + "-" + (y - 1));
        }
        if (y < sizeY - 1) {
            neighbours.add(x + "-" + (y + 1));
        }
        return neighbours;


    }
    

        









































    //getters
    public String getId() {
        return id;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public int getType() {
        return type;
    }
    public Node getParent() {
        return parent;
    }
    public int getCost() {
        return cost;
    }



    //setters
    public void setId(String id) {
        this.id = id;
    }
    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }
    public void setType(int type) {
        this.type = type;
    }
    public void setParent(Node parent) {
        this.parent = parent;
    }
    public void setCost(int cost) {
        this.cost = cost;
    }

}

