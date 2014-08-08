package gcsc.vrl.multi_compartment_model;

import java.util.*;

/** NOTE: Dieser CModelCreator bekommt definitiv keine GUI in der VRL, aufgrund der vielen Methoden/Functionen -- es ist noetig, dass die ConnectivityMatrix an den ModelCreator, der eine Repraesentation in der VRL 
 * erhaelt, uebergeben wird!!!!
 * 
 * 
 * 
 * The CModelCreator creates the morphology of the investigated neuron 
 * TODO: will receive a GUI in VRL - so we need to import eu.mihosoft.vrl.annotation.*; 
 * @author myra
 */
public class CModelCreator {
    
    /**
     * the total number of compartments in the model 
     */
    private int totalNumber;  
    //private ArrayList<Compartment> allCompartments = new ArrayList<Compartment>(); 
    private Compartment[] allCompartments;
    private ArrayList<Edge> allEdges = new ArrayList<Edge>(); 
    ConnectivityMatrix cmat; 
    
    /*---------------------------------------------------------------------------------------------------------------------------------------------------------------*/

    public void setCmat(ConnectivityMatrix cmat) {
        this.cmat = cmat;
    }

    public ConnectivityMatrix getCmat() {
        return cmat;
    }
    
        
    public void setTotalNumber(){
        totalNumber = cmat.getNodes();
    }
    
    public int getTotalNumber(){
        return totalNumber; 
    }
    
    public void createAllCompartments(){ 
        //totalNumber = cmat.getNodes();
        allCompartments = new Compartment[totalNumber]; 
        
        for(int i = 0; i<totalNumber; i++){
            for(int j = 0; j< totalNumber; j++){
                if(i == j){
                    allCompartments[i] = new Compartment(i);                     
                }
            }
        }
    }
    
//     public void createAllCompartments(ConnectivityMatrix cmat){ 
//        totalNumber = cmat.getNodes();
////        allCompartments = new Compartment[totalNumber]; 
//        Compartment tmp; 
//        for(int i = 0; i<totalNumber; i++){
//            for(int j = 0; j< totalNumber; j++){
//                if(i == j){
//                    tmp = new Compartment(i); 
//                    allCompartments.add(tmp);                     
//                }
//            }
//        }
//     }
//     
     
//    public ArrayList<Compartment> getAllCompartments(){
//        return allCompartments; 
//    }
    public Compartment[] getAllCompartments(){
        return allCompartments; 
    }
    
    /**
     * creates all edges subject to all compartments  
     * @param cmat connectivity matrix
     */
    //TODO: throw Exeption if AllCompartments is empty??
    public void createAllEdges(){
        //totalNumber = cmat.getNodes();
        Edge tmp = new Edge();
        for(int i = 0; i< totalNumber; i++){
            for(int j = 0; j<totalNumber; j++){
                if(cmat.getEntry(i, j) == 1 && i != j){
                    tmp.setFirst(allCompartments[i]);
                    tmp.setSecond(allCompartments[j]); 
                }
            }
        }
        allEdges.add(tmp);
    }
    
    public ArrayList<Edge> getAllEdges() {
        return allEdges;
    }
    
    /**
     * sets the dependencies between all compartments 
     */
    public void compartmentalParameters(){
        for(int i = 0; i< totalNumber; i++){
            for(int j = 0; j < allEdges.size(); j++){
                allCompartments[i].link(allEdges.get(j));
            }
        }
        
        for(int i = 0; i < totalNumber; i++){
            allCompartments[i].calculateConductance();
        }
        
    }
//    Ziemlich Nutzlos, da wir in COmpartment.java die Methode init() haben
//    public void setProperties(double length, double rl, double radius){
//        for(int i =0; i<totalNumber; i++){
//            allCompartments[i].init(length, radius, rl);
//        }
//    }
   

}