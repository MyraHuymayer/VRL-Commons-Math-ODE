package gcsc.vrl.multi_compartment_model;

/**
 *
 * @author myra
 */
public class Edge {

    
    private final Compartment[] edge = new Compartment[2];


    public Edge() {

    }
    
    public void setFirst(Compartment c){
        edge[0] = c;
    }
    
    public void setSecond(Compartment c){
        edge[1] = c; 
    }
    
//    public void createEdge(int i, int j, Compartment c1, Compartment c2){
//       
//       if(i == c1.getId() && j == c2.getId()){
//           edge[0] = c1;
//           edge[1] = c2; 
//       }
//              
//    }
//    
   
    
    public Compartment first(){
      Compartment first = edge[0];
      return first;
    }
    
    public Compartment second(){
      Compartment second = edge[1];
      return second; 
    }
    
}
