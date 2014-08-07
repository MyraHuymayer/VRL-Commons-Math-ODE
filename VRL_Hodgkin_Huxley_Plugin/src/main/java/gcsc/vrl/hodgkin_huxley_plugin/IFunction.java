package gcsc.vrl.hodgkin_huxley_plugin;

import eu.mihosoft.vrl.annotation.*;
import java.io.Serializable;


/**
 *
 * @author myra
 */
@ComponentInfo(name = "IFunction", category = "Commons/Math/ODE", description = "Function to adjust the current")
public class IFunction implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    /**
     * induced current at given duration in uA/mm^2
     */
    private double i; 
    
    /*
     * initial time of current induction in ms 
     */
    private double t_i0; 
    
    /*
     * end of current induction in ms
     */
    private double t_in;

    
    public IFunction() {
    }
    
    
    
    /**
     * initialization of the current i, the initial time of stimulation t_i0 and the final time of stimulation t_in; an exception is thrown when t_in < t_i0
     * @param i
     * @param t_i0
     * @param t_in
     * @throws InitialExceedsFinalException 
     */
    @MethodInfo(name="init")
    public void init(
            @ParamInfo(name="current i in uA/mm^2", options="value=0.07D") double i, 
            @ParamInfo(name="Initial time of current induction in ms", options="value=1.0D")double t_i0, 
            @ParamInfo(name="end time of current induction", options="value=10.0D") double t_in) throws InitialExceedsFinalException{
        
        this.i = i;
        this.t_i0 = t_i0;
        this.t_in = t_in; 
        
        if(t_i0 > t_in){
            throw new InitialExceedsFinalException();
        }
    }
    
    /**
     * Setting the current-value at different time intervals 
     * @param t
     * @return current i 
     */
    @MethodInfo(name="calculateI", noGUI=true)
    public double calculateI(double t){
   
        
        if(t_i0 <= t && t_in >= t){
            
            return i; 
        }else{
            
            return 0;
        }
         
        
    }
    
    /**
     * Comparison of initial stimulation (t_i0) with the start point of simulation (t0), where t_i0 may not be smaller than t0 (exception is thrown at violation)
     * and comparison of the final stimulation (t_in) with the ending time of simulation (tn), where t_in may not exceed the value of t_n (exception is thrown at violation) 
     * @param t0
     * @param tn
     * @throws InitialCurrentException
     * @throws FinalCurrentException 
     */
    @MethodInfo(name="compareTi", noGUI=true)
    public void compareTi(double t0, double tn ) throws InitialCurrentException, FinalCurrentException{
            //throws InitialCurrentException, FinalCurrentException{
        if(t_i0 < t0){
           //System.out.println("Bla");
            throw new InitialCurrentException();
        }
        if(t_in > tn){
            //System.out.println("Bla !!!! ");
            throw new FinalCurrentException();
        }        
    }
    
}
