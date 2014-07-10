package gcsc.vrl.multi_compartment_model;

/**
 *
 * @author myra
 */
public class Edge {

    
    private final Compartment[] edge = new Compartment[2];


    public Edge(int i, int j) {
       edge[0] = new Compartment(i);
       edge[1] = new Compartment(j);
    }
    
   
    
    public Compartment first(){
      Compartment first = edge[0];
      return first;
    }
    
    public Compartment second(){
      Compartment second = edge[1];
      return second; 
    }
    
}
