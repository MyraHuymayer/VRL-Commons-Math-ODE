package gcsc.vrl.multi_compartment_model;

import gcsc.vrl.hodgkin_huxley_plugin.*;
/**
 * Creates the Linear System that needs to be solved
 * @author myra
 */
public class LSCreator {
    
    double[][] matrixA; 
    double n; 
    double m;
    double h; 

    public double getN() {
        return n;
    }

    public double getM() {
        return m;
    }

    public double getH() {
        return h;
    }

    public void setN(double n) {
        this.n = n;
    }

    public void setM(double m) {
        this.m = m;
    }

    public void setH(double h) {
        this.h = h;
    }
    
    
    
    
    //TODO: eventuell kann man die beiden Funktionen auch mergen
    /**
     * make a copy (matrixA) of the connectivity matrix 
     * @param cmat connectivity matrix
     */
    public void copyMatrix(ConnectivityMatrix cmat){
        
        matrixA = new double[cmat.getNodes()][cmat.getNodes()];
       
        for(int i = 0; i < cmat.getNodes(); i++){
            for(int j = 0; j< cmat.getNodes(); j++){
                matrixA[i][j] = cmat.getEntry(i, j);
            }
        }
    } 
    
    
    /**
     * The entries of the matrix are calculated using the methods calculateAij and calculateBi of the MCVFunction 
     * @return the matrix A for the linear system  
     */
    public double[][] calculateEntriesOfA(Compartment[] allcomp){
        MCVFunction mcvf = new MCVFunction();
        VFunction2D vf = new VFunction2D();
       
       // NFunction2D nf = new NFunction2D();
       // MFunction2D mf = new MFunction2D();
       // HFunction2D hf = new HFunction2D();
       mcvf.setgNa(vf.getgBarNa()*Math.pow(m, 3)*h);
              
       mcvf.setgK(vf.getgBarK()*Math.pow(n, 4));
       
       //gL = gBarL
       mcvf.setgL(vf.getgBarL());
       
      
       //mcvf.
        
        for(int i = 0; i < matrixA.length; i++){
            for(int j = 0; j < matrixA.length; j++ ){
                //TODO:so ganz stimmt das mit g[] noch nicht -- als naechstes waere es sinnvoll sich das ganz genau klar zu machen 
                mcvf.setG(allcomp[i].getG());
                if(i==j){
                    mcvf.calculateBi(); // nicht sicher ob das stimmt 
                }
                if(matrixA[i][j] == 1 && i != j){
                    mcvf.calculateAij(j); //nicht sicher ob das stimmt 
                }
            }
        }
        
        return matrixA;
    }
    
}
