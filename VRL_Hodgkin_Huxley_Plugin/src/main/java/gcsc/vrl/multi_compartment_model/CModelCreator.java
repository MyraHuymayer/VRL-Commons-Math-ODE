/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gcsc.vrl.multi_compartment_model;

import java.util.*;

/**
 * The CModelCreator creates the morphology of the investigated neuron 
 * TODO: will receive a GUI in VRL - so we need to import eu.mihosoft.vrl.annotation.*; 
 * @author myra
 */
public class CModelCreator {
    
    /**
     * the total number of compartments in the model 
     */
    private int totalNumber;  
    private ArrayList<Compartment> allCompartments = new ArrayList<Compartment>(); 
    //private Compartment[] allCompartments;
    private ArrayList<Edge> allEdges = new ArrayList<Edge>(); 
    
    /*---------------------------------------------------------------------------------------------------------------------------------------------------------------*/
    
//    public void createAllCompartments(ConnectivityMatrix cmat){ 
//        totalNumber = cmat.getNodes();
////        allCompartments = new Compartment[totalNumber]; 
//        
//        for(int k = 0; k < totalNumber; k++){
//            for(int i = 0; i<totalNumber; i++){
//                for(int j = 0; j< totalNumber; j++){
//                    if(i == j){
////                        allCompartments[k] = new Compartment(i); 
//                        
//                    }
//                }
//            }
//        }
//    }
    
     public void createAllCompartments(ConnectivityMatrix cmat){ 
        totalNumber = cmat.getNodes();
//        allCompartments = new Compartment[totalNumber]; 
        Compartment tmp; 
        for(int i = 0; i<totalNumber; i++){
            for(int j = 0; j< totalNumber; j++){
                if(i == j){
                    tmp = new Compartment(i); 
                    allCompartments.add(tmp);                     
                }
            }
        }
     }
     
     
    public ArrayList<Compartment> getAllCompartments(){
        return allCompartments; 
    }
//    public Compartment[] getAllCompartments(){
//        return allCompartments; 
//    }
//    
    //TODO: throw Exeption if AllCompartments is empty??
    public void createAllEdges(ConnectivityMatrix cmat){
        Edge tmp = new Edge();
        for(int i = 0; i< totalNumber; i++){
            for(int j = 0; j<totalNumber; j++){
                if(cmat.getEntry(i, j) == 1 && i != j){
                   // tmp.setFirst(allCompartments[i]);
                   // tmp.setSecond(allCompartments[j]); 
                }
            }
        }
        allEdges.add(tmp);
    }

    public ArrayList<Edge> getAllEdges() {
        return allEdges;
    }
        
//    Ziemlich Nutzlos, da wir in COmpartment.java die Methode init() haben
//    public void setProperties(double length, double rl, double radius){
//        for(int i =0; i<totalNumber; i++){
//            allCompartments[i].init(length, radius, rl);
//        }
//    }
   

}