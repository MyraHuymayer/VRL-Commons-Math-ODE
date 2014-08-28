package gcsc.vrl.multi_compartment_model;

import gcsc.vrl.hodgkin_huxley_plugin.*;
/**
 * Creates the Linear System that needs to be solved
 * @author myra
 */
public class LSCreator {
    
    private double[][] matrixA; 
    double [] rhs;
    private double n; 
    private double m;
    private double h; 
    private VFunction2D vf = new VFunction2D();
    private MCVFunction mcvf = new MCVFunction();

    public VFunction2D getVf() {
        return vf;
    }

    public MCVFunction getMcvf() {
        return mcvf;
    }

    
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

    public void setVf(VFunction2D vf) {
        this.vf = vf;
    }

    public void setMcvf(MCVFunction mcvf) {
        this.mcvf = mcvf;
    }
    
    public double[] getRhs(){
        return rhs; 
    }
    
    //TODO: eventuell kann man die beiden Funktionen auch mergen
    /**
     * make a copy (matrixA) of the connectivity matrix 
     * @param cmat connectivity matrix
     */
    public double[][] copyMatrix(ConnectivityMatrix cmat){
        
        matrixA = new double[cmat.getNodes()][cmat.getNodes()];
       
        for(int i = 0; i < cmat.getNodes(); i++){
            for(int j = 0; j< cmat.getNodes(); j++){
                matrixA[i][j] = cmat.getEntry(i, j);
            }
        }
        return matrixA; 

    } 
    
    //NOTE: Die Matrix wird uebergeben, die rechte Seite nicht -- die muss explizit im LinearSystemSolver nochmal aufgerufen werden --> die bekommt also auch keine GUI
    /**
     * The entries of the matrix are calculated using the methods calculateAij and calculateBi of the MCVFunction 
     * @param allcomp List of all compartments 
     * @return the matrix A for the linear system  
     */
    public double[][] determineMatrix(Compartment[] allcomp){
     
       // NFunction2D nf = new NFunction2D();
       // MFunction2D mf = new MFunction2D();
       // HFunction2D hf = new HFunction2D();
       mcvf.setgNa(vf.getgBarNa()*Math.pow(m, 3)*h);
              
       mcvf.setgK(vf.getgBarK()*Math.pow(n, 4));
       
       //gL = gBarL
       //NOTE: MAN SOLLTE AUCH BEDENKEN: EVTL UNTERSCHEIDEN SICH DIE VOLTAGES FUER DIE UNTERSCHIEDLICHEN COMPARTMENTS VONEINANDER 
       mcvf.setgL(vf.getgBarL()); //genau die gleichen Variablen brauchen wir auch in rightHandSide() - 
      
        
        for(int i = 0; i < matrixA.length; i++){
            for(int j = 0; j < matrixA.length; j++ ){
                 
                mcvf.setG(allcomp[i].getG());
                
                if(i==j){
                     
                    matrixA[i][j] =  mcvf.calculateBi();
                }//TODO: nochmal anschauen, was bei der berechnung von aij hier passiert - nicht sicher ob das so stimmt.
                if(matrixA[i][j] == 1 && i != j){
                    for(int k = 0; k < mcvf.getG().length; k ++){
                        
                        Compartment tmp = allcomp[i].getDependencies().get(k);
                        
                        if(tmp.getId() == j){
                           matrixA[i][j] = mcvf.calculateAij(k);
                       }
                    }
                }
            }
        }
        
        return matrixA;
    }
    
    /**
     * Calculates the vector on the right hand side of the linear system 
     * @param t the actual time
     * @param voltageNeighbor the voltages of the neighboring compartments
     * @param voltageComp the voltage of the compartment currently analysed
     * @param area the area upon which the external current acts in [mm^2]
     */
    public void rightHandSide(/*double t,*/ double[] voltageNeighbor, double voltageComp, double area, int i){ 
        rhs = new double[matrixA.length]; 
        System.out.print("Matrix length = "+matrixA.length+" \n");
      
//        IFunction ifun = new IFunction(); 
//        vf.setI(ifun.calculateI(t));
//        mcvf.setIe(vf.getI()); //Im Prinzip geht es nur um EIN compartment, da wohl nur ein Compartment dem externen Strom ausgesetzt sein wird -- TODO: Es ist noch noetig das Ausweahlen einzelner Compartments zu implementieren. 
//        mcvf.setArea_u(area);
        
        mcvf.setgNa(vf.getgBarNa()*Math.pow(m, 3)*h);
        System.out.print("Sodium conductance = "+mcvf.getgNa()+" \n");
        
        mcvf.setgK(vf.getgBarK()*Math.pow(n, 4));
        System.out.print("Potassium conductance = "+mcvf.getgK()+" \n");
        
        mcvf.setgL(vf.getgBarL());
        System.out.print("Leakage conductance = "+mcvf.getgL()+" \n");
        
        System.out.println("length of the neighbor-voltages-array: "+voltageNeighbor.length+" ");
//        for(int i = 0; i < rhs.length; i++ ){
            
        rhs[i] = mcvf.calculateDi(voltageNeighbor, voltageComp); //macht das so sinn??
//        }

    }
    
}
