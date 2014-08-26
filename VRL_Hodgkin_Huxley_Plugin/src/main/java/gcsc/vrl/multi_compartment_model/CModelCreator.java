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
//    private int totalNumber;  
    //private ArrayList<Compartment> allCompartments = new ArrayList<Compartment>(); 
    private Compartment[] allCompartments;
    private ArrayList<Edge> allEdges = new ArrayList<Edge>(); 
    private ConnectivityMatrix cmat; 
    private int totalNumber; 
    
    /*---------------------------------------------------------------------------------------------------------------------------------------------------------------*/

    public void setCmat(ConnectivityMatrix cmat) {
        this.cmat = cmat;
        totalNumber = cmat.getNodes();
        
    }

    public ConnectivityMatrix getCmat() {
        return cmat;
    }
    
    
    public void createAllCompartments(){ 
//        int totalNumber = cmat.getNodes();
        allCompartments = new Compartment[totalNumber]; 
        
        for(int i = 0; i<totalNumber; i++){
            for(int j = 0; j< totalNumber; j++){
                if(i == j){
                    allCompartments[i] = new Compartment(i);                     
                }
            }
        }
    }
    
 
     
    public Compartment[] getAllCompartments(){
        return allCompartments; 
    }
    
    /**
     * creates all edges subject to all compartments  
     * @param cmat connectivity matrix
     */
    //TODO: throw Exeption if AllCompartments is empty??
    public void createAllEdges(){
//        int totalNumber = cmat.getNodes();
        
        for(int i = 0; i< totalNumber; i++){
            for(int j = 0; j<totalNumber; j++){
                if(cmat.getEntry(i, j) == 1 && i != j){
                    Edge tmp = new Edge();
                    tmp.setFirst(allCompartments[i]);
                    tmp.setSecond(allCompartments[j]); 
                    allEdges.add(tmp);
                }
            }
        }
        
    }
    
    public ArrayList<Edge> getAllEdges() {
        return allEdges;
    }
    
    /**
     * sets the dependencies between all compartments and determines the conductances 
     */
    public void compartmentalParameters(){
//        int totalNumber = cmat.getNodes();
        for(int i = 0; i< totalNumber; i++){
            for(int j = 0; j < allEdges.size(); j++){
                allCompartments[i].link(allEdges.get(j));
            }
        }
       
        
        for(int i = 0; i < totalNumber; i++){
            allCompartments[i].calculateConductance(); // damit wir das so ausfuehren koennen, brauchen wir den Radius, die Laenge und auch die intracellular resistivity, sollte fuer jedes einzelne Comparment gesetzt werden - eventuell auch hier in dieser Klasse 
        }
        
    }
    
//    TODO: hier muessen die Werte der Compartments gesetzt werden  -- das sollte ich mittels ueberladen von Methoden realisieren: z.B. 
//    1.  area, length und radius sind fuer alle Compartments gleich: public void setCompartmentParameters(double length, double r_l, double radius)
//    2. unterschiedliche Compartments haben unterschiedliche Parameter: public void setCompartmentParameters(double ... /*Wie das hier drin realisiert wird, weiss ich noch nicht */)
    
    
//    Ziemlich Nutzlos, da wir in COmpartment.java die Methode init() haben
    public void setProperties(double length, double rl, double radius){
//        int totalNumber = cmat.getNodes(); // evtl macht es doch sinn totalNumber als festen Wert zu haben
        for(int i =0; i<totalNumber; i++){
            allCompartments[i].init(length, radius, rl);
        }
    }
   

}