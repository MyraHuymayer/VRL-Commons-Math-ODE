package gcsc.vrl.multi_compartment_model;

/**
 *
 * @author myra
 */
public class ConnectivityMatrix {
    
    double connectivityMatrix[][];
    int nodes; 
    Edge e = new Edge();

    /*-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
    /**
     * Constructor: creates the initial matrix, whose elements on the main diagonal are equal to 1.0 and all other elements are equal to zero 
     * @param nodes number of compartments 
     */
    public ConnectivityMatrix(int nodes) {
        this.nodes = nodes; 
        connectivityMatrix = new double[nodes][nodes];
        
        //the initial connectivity matrix is a nodes X nodes matrix, filled with 0.0 
        for(int i = 0; i< nodes; i++){
            for(int j = 0; j< nodes; j++){
                if(i==j ){
                    connectivityMatrix[i][j] = 1.0; 
                }else{
                    connectivityMatrix[i][j] = 0.0;
                }
            }
        }
    }
    
    /**
     * An entry of 1.0 is added to the connectivity matrix.
     * @param i node 1
     * @param j node 2
     */
    public void addEntry(int i, int j){
        if(i >= 0 && i < nodes && j >= 0 && j< nodes){
            connectivityMatrix[i][j] = 1.0; 
            connectivityMatrix[j][i] = 1.0;
  
        }
    }

//    public double[][] getConnectivityMatrix() {
//        return connectivityMatrix;
//    }
    
    /**
     * If a wrong entry was added, it can be deleted with this method. 
     * @param i node 1
     * @param j node 2
     */
    //eventuell brauchen wir diese Klasse gar nicht 
    public void deleteEntry(int i, int j){
        if(i >= 0 && i < nodes && j >= 0 && j< nodes){
            connectivityMatrix[i][j] = 0.0; 
            connectivityMatrix[j][i] = 0.0; 
        }
    }
    
    public double getEntry(int i, int j){
        
        return connectivityMatrix[i][j];
    }
    
    
//    public double isEntry(int i, int j){
//        if(i>=0 && i< nodes && j >= 0 && j < nodes){
//            return connectivityMatrix[i][j];
//        }else{
//            return 0.0; 
//        }
//    }
    
}
