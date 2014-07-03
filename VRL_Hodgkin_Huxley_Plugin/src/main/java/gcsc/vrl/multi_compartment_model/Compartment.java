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
    private int id; 
    
    /**
     * number of neighbors
     */
    private int number;
    
    /**
     * all compartments that are coupled to instantiated compartment are listed here
     */
    private ArrayList<Compartment> dependencies;
    
    private ArrayList<Compartment> all; 
    
    private ConnectivityMatrix edgematrix;
 
    
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
     * Constructor
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

    public ArrayList<Compartment> getAll() {
        return all;
    }

    public ConnectivityMatrix getEdgematrix() {
        return edgematrix;
    }

    public void setDependencies(ArrayList<Compartment> dependencies) {
        this.dependencies = dependencies;
    }

    public void setAll(ArrayList<Compartment> all) {
        this.all = all;
    }

    public void setEdgematrix(ConnectivityMatrix edgematrix) {
        this.edgematrix = edgematrix;
    }
      
    /*-----------------------------*/
    
    /**
     * Compartments coupled to the instantiated compartment are added to a list of compartments in order to establish relationship between coupled compartments.
     * @param c 
     */
    public void link(Compartment c){
        
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
