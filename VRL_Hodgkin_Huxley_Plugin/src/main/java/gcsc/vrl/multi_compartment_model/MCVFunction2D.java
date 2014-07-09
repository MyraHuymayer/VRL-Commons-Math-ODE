package gcsc.vrl.multi_compartment_model;

import eu.mihosoft.vrl.math.*;

/**
 *
 * @author myra
 */
public class MCVFunction2D implements Function2D{
   
    /**
     * number of neighboring compartments
     */
    private int num;
    
    /**
     * inter-compartmental conductance
     */
    final private double[] g = new double[num];
   
    /**
     * voltage of neighbor
     */
    final private double[] v_neighbor = new double[num];
    
    /**
     * externally injected current 
     */
    private double ie; 
    
    /**
     * area of compartment 
     */
    private double a_u; 
    
    /**
     * membrane current of compartment
     */
    private double im; 
    
    private double cm = 1.0;
     

/*------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
    /**
     * Constructor
     */
    public MCVFunction2D() {
    }

    public int getNum() {
        return num;
    }

    public double[] getG() {
        return g;
    }

    public double[] getV_neighbor() {
        return v_neighbor;
    }

    public double getIe() {
        return ie;
    }

    public double getA_u() {
        return a_u;
    }

    public double getIm() {
        return im;
    }

    public double getCm() {
        return cm;
    }

    public void setCm(double cm) {
        this.cm = cm;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setIe(double ie) {
        this.ie = ie;
    }

    public void setA_u(double a_u) {
        this.a_u = a_u;
    }

    public void setIm(double im) {
        this.im = im;
    }
    
    
    
    /**
     * running the function
     * @param x
     * @param y
     * @return solution
     */
    @Override
    public Double run(Double x, Double y){
        
        //C_m * dV/dt = -i_m + I_e + sum(g_u,u' * V_u' - v_u)
        Double mcvfct;
        double tmp = 0;
        for(int i = 0; i < num; i++){
            //y is the voltage of the compartment, that is currently investigated
            tmp = g[i] * (v_neighbor[i] - y);
        }
        
        ie = ie/a_u;
        
        mcvfct = (-im + ie + tmp)/cm;
        return mcvfct; 
    }
    
    
}
