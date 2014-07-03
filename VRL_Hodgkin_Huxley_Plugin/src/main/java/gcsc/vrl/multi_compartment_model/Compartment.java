/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gcsc.vrl.multi_compartment_model;

//import java.util.ArrayList;
import java.util.*;
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
    private final int id; 
    
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
    
    
    /**
     * inter-compartmental conductances
     */
    private double[] g; 

    
    /*--------------------------------------------------------------------------------------------------------------------------------------*/
    /** 
     * Constructor, creates a compartment with an identity number and contains a counter for created compartments
     * @param i id for the compartment
     */
    public Compartment(int i) {
        totalNumber++;
        id = i; 
    }    
    
//    public void setId(int id){
//        this.id = id; 
//    }
    
    public int getId(){
        return id;
    }

    
    public static int getTotalNumber() {
        return totalNumber;
    }

    public double getLength() {
        return length;
    }

    public double getRadius() {
        return radius;
    }

    public double getR_L() {
        return r_L;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public void setR_L(double r_L) {
        this.r_L = r_L;
    }
    
    /*-----------------------------*/

    public ArrayList<Compartment> getDependencies() {
        return dependencies;
    }

    public void setDependencies(ArrayList<Compartment> dependencies) {
        this.dependencies = dependencies;
    }
 
    /**
     * Compartments coupled to the instantiated compartment are added to a list of compartments in order to establish relationship between coupled compartments.
     * @param e 
     */
    //mal schauen ob das so klappt! und wie ich das dann anwende! evtl reicht halt doch die konnektivitaetsmatrix
    public void link(Edge e){
        Compartment target = e.first(); //target never used?? look if that is so.. 
        
        if(e.first() == this){
            target = e.second();
            dependencies.add(target);
            
        }        
    }
    
    /**
     * 
     */
    public void calculateConductance(){
        
        number = dependencies.size(); //determine the number of neighbors of this compartment
        g = new double[number]; //the number of neighbours determines the array length
        
        //calculate intercompartmental conductance for each neighboring compartment 
        // g_(u,u') = (a_u * a_u')/(r_L * L_u * (L_u*(a_u')^2 + L_u'*(a_u)^2 ))
        for(int i=0; i < number; i++){
            double numerator = radius * Math.pow(dependencies.get(i).getRadius(), 2);
            double denominator = r_L * length * (length * Math.pow(dependencies.get(i).getRadius(), 2) + dependencies.get(i).getLength() * Math.pow(radius, 2) );
            g[i] = numerator/denominator; 
        }
        //TODO: eventuell nochmal Gedanken drueber machen, ob hier was schief gehen kann (was eventuell in einer Exception muenden wuerde?)
    }

    

    
    
}
