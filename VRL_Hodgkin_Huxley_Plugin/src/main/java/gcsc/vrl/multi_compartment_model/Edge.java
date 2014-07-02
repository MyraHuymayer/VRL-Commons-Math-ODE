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

    /*
        Der Sinn dieser Klasse ist die Darstellung eines Tupels (das sind dann die beiden 
        Nodes die verbunden eine Edge ergeben)
    */
    
    /**
     * Tuple with two integers which represent the compartment id's 
     */
    //geht das wirklich so? 
    final private int[] edge = new int[2]; //MACHT SO WAHRSCHEINLICH NICHT SINN! 
    
    public Edge() {
        
    }  
    
}
