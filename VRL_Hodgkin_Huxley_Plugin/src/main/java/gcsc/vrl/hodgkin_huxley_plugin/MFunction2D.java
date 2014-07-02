package gcsc.vrl.hodgkin_huxley_plugin;

import eu.mihosoft.vrl.annotation.*;
import eu.mihosoft.vrl.math.Function2D;

/**
 *
 * @author myra
 */
public class MFunction2D implements Function2D{
    
    private double v;

    public double getV() {
        return v;
    }

    public void setV(double v) {
        this.v = v;
    }

    public MFunction2D() {
    }
    
    
    /**
     * implementation of the steady state (in)activation function in the HH model
     * @return Steady state inactivation function m_infinity
     */
    @MethodInfo(name="m infinity", noGUI=true)
    public double minf(){
        double alpha_m;
        double beta_m;
//        if(v==-40){
//            alpha_m = 1; 
//        }else{
        double num = 0.1*(v+40);
        double denom = 1 - Math.exp(-0.1*(v+40));
        alpha_m = num/denom;
//        }
        
        beta_m = 4 * Math.exp(-0.0556 * (v+65));
        
        double tmp = alpha_m + beta_m;
        double m_inf = alpha_m/tmp;
        
        return m_inf;
    }
    
    
    /**
     * Implementation of the voltage-dependent time constant in the HH model 
     * @return voltage dependent time constant tau_m
     */
    
    @MethodInfo(name="time constant Tau m", noGUI=true)
    public double taum(){
        double alpha_m;
        double beta_m;
        //if(v==-40){
          //  alpha_m = 1; 
        //}else{
        double num = 0.1*(v+40);
        double denom = 1 - Math.exp(-0.1*(v+40));
        alpha_m = num/denom;
        //}
        
        beta_m = 4 * Math.exp(-0.0556 * (v+65));
        
        double tmp = (alpha_m + beta_m);
        double tau_m = 1/tmp;
        
        return tau_m;
    }
    
    /**
     * Running the function
     * @param x
     * @param y
     * @return 
     */
    @Override 
   public Double run(Double x, Double y){
                
        Double mfct = (minf()-y)/taum();
        return mfct;
    
       
   }   
   
 
    
}
