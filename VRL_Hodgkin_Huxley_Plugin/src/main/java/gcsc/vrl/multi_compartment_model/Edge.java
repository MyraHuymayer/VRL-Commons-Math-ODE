/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gcsc.vrl.multi_compartment_model;

/**
 *
 * @author myra
 */
public class Edge {

    
    private Compartment[] edge = new Compartment[2];
//    private Compartment node1; 
//    private Compartment node2; 

    public Edge(int i, int j) {
       edge[0] = new Compartment(i);
       edge[1] = new Compartment(j);
    }
    
   
//prinzipiell koennte man das auch nur in den Konstruktor schreiben! 
//    public void createEdge(int i, int j) {
//        
//       edge[0] = new Compartment(i);
//       edge[1] = new Compartment(j);
//
//       
//    }  
    
    public Compartment first(){
      Compartment first = edge[0];
      return first;
    }
    
    public Compartment second(){
      Compartment second = edge[1];
      return second; 
    }
    
}
