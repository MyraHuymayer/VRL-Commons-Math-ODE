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
//    private int totalNumber; 
   
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
    private ArrayList<Compartment> dependencies = new ArrayList<Compartment>();
     
    
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
     * @param id id for the compartment
     */
    public Compartment(int id) {
   
        this.id = id; 
    }    
    
    public int getId(){
        return id;
    }
    
    //this is equal to the number of rows in the ConnectivityMatrix 
//    public void setTotalNumber(int totalNumber){
//        this.totalNumber = totalNumber; 
//    }
    
//    public int getTotalNumber() {
//        return totalNumber;
//    }

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
    
    
    public void init(double length, double radius, double r_L){
        this.length = length;
        this.radius = radius;
        this.r_L = r_L;
    }
    
    /*-----------------------------*/

    public ArrayList<Compartment> getDependencies() {
        return dependencies;
    }

    public void setDependencies(ArrayList<Compartment> dependencies) {
        this.dependencies = dependencies;
    }

    public double[] getG() {
        return g;
    }
 
    
    /**
     * Compartments coupled to the instantiated compartment are added to a list of compartments in order to establish relationship between coupled compartments.
     * @param e Edge consisting of two Compartments
     */
    //ein Compartment ueberprueft alle Edges -- fuer eine sehr grosse Konnektivitaetsmatrix braucht das aber voraussichtlich zu viel Rechenzeit 
    public void link(Edge e){
        Compartment target = e.first();
        
        if(e.first() == this){
            target = e.second();
            dependencies.add(target);
            
        }        
    }
    
    /**
     * Method to calculate the intercompartmental conductances for every neighboring compartment 
     * @return array of intercompartmental conductances
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
    }


    
}
