package gcsc.vrl.multi_compartment_model;

/**
 *
 * @author myra
 */
public class ConnectivityMatrix {
    
    double connectivityMatrix[][];
    int nodes; 

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
    
    
    public int getNodes() {
        return nodes;
    }
    
    /**
     * An entry of 1.0 is added to the connectivity matrix.
     * @param i line
     * @param j column
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
     * @param i line 
     * @param j column
     */
    public void deleteEntry(int i, int j){
        if(i >= 0 && i < nodes && j >= 0 && j< nodes){
            connectivityMatrix[i][j] = 0.0; 
            connectivityMatrix[j][i] = 0.0; 
        }
    }
    /**
     * returns value at any position of the ConnectivityMatrix
     * @param i row number 
     * @param j column number 
     * @return entry at position a_(ij)
     */
    public double getEntry(int i, int j){
        
        return connectivityMatrix[i][j];
    }

    
}
