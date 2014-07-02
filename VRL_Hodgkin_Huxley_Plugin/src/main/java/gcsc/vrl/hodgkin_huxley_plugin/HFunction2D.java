package gcsc.vrl.hodgkin_huxley_plugin;

import eu.mihosoft.vrl.annotation.*;
import eu.mihosoft.vrl.math.Function2D;
/**
 *
 * @author myra
 */
public class HFunction2D implements Function2D{
    
    private double v;

    public double getV() {
        return v;
    }

    public void setV(double v) {
        this.v = v;
    }

    public HFunction2D() {
        
    }
    
    
    /**
     * implementation of the steady state (in)activation function in the HH model
     * @return Steady state inactivation function h_infinity
     */
    @MethodInfo(name="h infinity", noGUI=true)
    public double hinf(){
        double alpha_h;
        double beta_h;
        
        alpha_h = 0.07 * Math.exp(-0.05 * (v+65));
        double denom = 1 + Math.exp(-0.1 * (v+35)); 
        beta_h = 1/denom;
        
        double tmp = alpha_h + beta_h;
        double h_inf = alpha_h/tmp;
        
        return h_inf;
    }
    
     /**
     * Implementation of the voltage-dependent time constant in the HH model 
     * @return voltage dependent time constant tau_h
     */
    @MethodInfo(name="time constant Tau h", noGUI=true)
    public double tauh(){
        double alpha_h;
        double beta_h;
        
        alpha_h = 0.07 * Math.exp(-0.05*(v+65));
        beta_h = 1/(1+ Math.exp(-0.1*(v+35)));
        
        double tmp = alpha_h + beta_h;
        double tau_h = 1/tmp;
        
        return tau_h;
    }
    
    /**
     * Running the function
     * @param x
     * @param y
     * @return 
     */
    @Override 
   public Double run(Double x, Double y){
               
        Double hfct = (hinf()-y)/tauh(); 
        return hfct;
     
   }    
   
   
}
