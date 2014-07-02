/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gcsc.vrl.multi_compartment_model;

import java.util.ArrayList;
//import gcsc.vrl.hodgkin_huxley_plugin.*;

/**
 *
 * @author myra
 */
public class Compartment {
    
    /**
     * total number of compartments created 
     */
    private static int totalNumber = 0; 
   
    /**
     * number to identify the compartment
     */
    private int id; 
    
    /**
     * number of neighbors
     */
    private int number;
    
    /**
     * all compartments that are coupled to instantiated compartment are listed here
     */
    private ArrayList<Compartment> dependencies; 

    
    /**
     * length of the compartment  
     */
    private double length; 
    
    /**
     * radius of the compartment 
     */
    private double radius; 
    
    /**
     * intracellular resistivity 
     */
    private double r_L; 
    
//these two variables are most probably not necessary:
//###############################################################################################################################
    /**
     * current injected into the compartment 
     */
    private double ie; 
    
    /**
     * membrane current of the compartment, which is calculated using the Hodgkin Huxley Plug-in (or in another way!)
     */
    private double im;
//###############################################################################################################################
    
    /**
     * membrane voltage of this compartment  
     */
    // each compartment only knows its own voltage, but it can access the voltage from its neighbor
    //BRAUCHEN WIR UEBERHAUPT V?
    private double v; 
    
    /**
     * inter-compartmental conductances
     */
    //number of inter-compartmental conductances depends on number of neighbors
    private double[] g; 

    
    /*--------------------------------------------------------------------------------------------------------------------------------------*/
    /** 
     * Constructor 
     */
    public Compartment() {
        totalNumber++;
        id = totalNumber;
        
    }    
    
    public void setID(int id){
        this.id = id; 
    }
    
    public int getID(){
        return id;
    }
    
    /**
     * Compartments coupled to the instantiated compartment are added to a list of compartments in order to establish relationship between coupled compartments.
     * @param c 
     */
    public void link(Compartment c){
        
    }
    
    public void calculateConductance(){
        // g_(u,u') = (a_u * a_u')/(r_L * L_u * (L_u*(a_u')^2 + L_u'*(a_u)^2 ))
        
    }

    

    
    
}
