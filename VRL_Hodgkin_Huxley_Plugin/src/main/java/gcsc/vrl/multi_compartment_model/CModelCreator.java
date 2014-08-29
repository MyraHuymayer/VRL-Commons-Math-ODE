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
    
 //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
 //   Gruppieren der einzelnen Compartments 
    
 /* Welche Faelle gibt es?  
  * 1. Kleine Konnektivitaetsmatrix -- Benutzer kann die einzelnen id's der Compartments angeben
  * 2. SWC-Dateien werden eingelesen -- Kodierung in Compartment.java orientiert sich an der Kodierung in den SWC-Dateien - beim auslesen automatisch gesetzt
  * 3. sehr große Konnektivitaetsmatrix, die der Benutzer (wie auch immer ... z.B. durch Einzeichnen) -- wie kann man das in solch einem Fall gewaerleisten?
  * 
  * NOTE: Evtl. sollte man das in drei unterschiedliche Klassen stecken, die alle vom CModelCreator abgeleitet werden 
  */
    
    public void setSomaCompartments(int ... compartmentIDs){//ArrayList<Integer>
        //Gehe alle Compartments durch -- wenn die id's mit denen der obigen arraylist uebereinstimmen, dann setze den typ auf 1 
        // allerdings gibt es da noch ein kleines Problem: die Anzahl der compartmentIDs ist in der Regel viel kleiner als die von allCompartments 
        for(int i = 0; i < compartmentIDs.length; i++){
            for(int j = 0; j < allCompartments.length; j++){
                if(compartmentIDs[i] == allCompartments[j].getId()){
                    allCompartments[j].setType(1);
                }
            }
        }
        
    }
    //im Prinzip haben die alle denselben algorithmus
    public void setAxonalCompartments(int ... compartmentIDs){
        for(int i = 0; i < compartmentIDs.length; i++){
            for(int j = 0; j < allCompartments.length; j++){
                if(compartmentIDs[i] == allCompartments[j].getId()){
                    allCompartments[j].setType(2);
                }
            }
        }
    }
 
    public void setBasalDendrites(int ... compartmentIDs){

        for(int i = 0; i < compartmentIDs.length; i++){
            for(int j = 0; j < allCompartments.length; j++){
                if(compartmentIDs[i] == allCompartments[j].getId()){
                    allCompartments[j].setType(3);
                }
            }
        }
    }
    
    public void setApicalDendrites(int ... compartmentIDs){
        
       for(int i = 0; i < compartmentIDs.length; i++){
          for(int j = 0; j < allCompartments.length; j++){
               if(compartmentIDs[i] == allCompartments[j].getId()){
                   allCompartments[j].setType(4);
               }
           }
       }
    }
    
 
    
//    Ziemlich Nutzlos, da wir in COmpartment.java die Methode init() haben
    public void setProperties(double length, double rl, double radius){
//        int totalNumber = cmat.getNodes(); // evtl macht es doch sinn totalNumber als festen Wert zu haben
        for(int i =0; i<totalNumber; i++){
            allCompartments[i].init(length, radius, rl);
        }
    }
   

}