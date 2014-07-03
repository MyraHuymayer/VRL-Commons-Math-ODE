/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gcsc.vrl.multi_compartment_model;

import java.util.*;

/**
 *
 * @author myra
 */
public class CModelCreator {
    // der CModelCreator wird später auf jeden Fall in der VRL sichtbar sein!! 
    //brauchen wir denn wirklich eine Arraylist mit den Compartments - reicht es nicht nur die id's der Compartments zu kennen --> dh. eine ArrayList mit den id's der Compartments 
//    private final ArrayList<Compartment> compartmentList = new ArrayList();
    
    
    private final ArrayList<Compartment> allcomps = new ArrayList(); 
    
    int totalNum = allcomps.size();
    ConnectivityMatrix matrix = new ConnectivityMatrix(totalNum); 
    
    //evtl brauchen wir wirklich keine EdgeList - evtl reicht die Konnektivitaetsmatix

    /*-----------------------------------------------------------------------------------------------------------------------------*/
    
    //Constructor still needed - later
    
   


   
    
}
