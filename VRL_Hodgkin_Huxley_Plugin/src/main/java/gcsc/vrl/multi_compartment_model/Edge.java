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

    
    private Compartment node1; 
    private Compartment node2; 

    public Edge() {
    }
    
   
    //prinzipiell koennte man das auch nur in den Konstruktor schreiben! 
    public void createEdge(int i, int j) {
        node1 = new Compartment(i); 
        node2 = new Compartment(j);
        
    }  
    
}
