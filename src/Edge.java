public class Edge {
    private int weight;
    private String id; //X1-Y1,X2-Y2

    public Edge(String id, int weight) {
        this.id = id;
        this.weight = weight;
    }

    public static String parser(int N1_x, int N1_y, int N2_x,int  N2_y){
        //return the id of the edge, given the coordinates of the source and destination nodes, 
        //they are adjacent to each other in he grid , smaller x+y values node is the first in our convention
        if((N1_x + N1_y)<(N2_x + N2_y)){
                return N1_x + "-" + N1_y + "," + N2_x + "-" + N2_y;
        }else{
                return N2_x + "-" + N2_y + "," + N1_x + "-" + N1_y;
            }
      
    }
    
    //getters
    public String getId() {
        return id;
    }
    public int getWeight() {
        return weight;
    }
    //setters
    public void setId(String id) {
        this.id = id;
    }
    public void setWeight(int weight) {
        this.weight = weight;
    }
    

}
