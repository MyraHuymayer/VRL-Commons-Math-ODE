/*
 * NOTE: This function will most propably replace soon the function of MCVFunction2D.java
 * we need to split the solution that would be generated in MCVFunction2D.java into three solution subsets
 * which will later be written into the Matrix 
 * 
 */
package gcsc.vrl.multi_compartment_model;
//import gcsc.vrl.hodgkin_huxley_plugin.*;

public class MCVFunction {
    /*to solve C_m * dV/dt = -i_m + i_e + sum(g_u,u' * V_u' - v_u)
     * i_m = g_barK * n^4 * (v - e_K) + g_barNa * m^3 * h * (v - e_Na) + g_barL * (v - e_L)   <--MCVFunction also needs to know these values; access is needed to these values 
     * MEMO: n,m and h are calculated as in the Hodgkin Huxley Equations
     * also i_e is known to this function
     * /
    
    
    /**
     * number of neighboring compartments
     */
    private int num;
    
    /**
     * inter-compartmental conductance
     */
    final private double[] g = new double[num];
    
    /**
     * Maximal membrane conductance of sodium 
     */
    //maybe it would also be better to keep this more general - if there are for example more than only the following conductances - perhaps also an array of conductances would be required here -- think about that later 
    //memo: gNa = gbarNa * m^3*h
    private double gNa;
    private double gK;
    private double gL;
    
    private double eNa; 
    private double eK;
    private double eL;
    
//    not known to this function or is it? 
//    /**
//     * voltage of neighbor [in mV]
//     */
//    final private double[] v_neighbor = new double[num];
    
    /**
     * externally injected current [in uA]
     */
    private double ie; 
    
    /**
     * area of compartment [in mm^2]
     */
    private double a_u; 
    
//    /**
//     * membrane current of compartment
//     */
//    private double im; 
    
    /**
     * membrane conductance [in uF/mm^2]
     */
    private double cm = 1.0;

    /*-----------------------------------------------------------------------------------------------------------------------------------*/
    public MCVFunction() {
        
    }
    /*
    * WE need three solutions that will be multiplicated with Vu or Vu' respectively
    * The general solution has the form: del V_u(1-b_u) - sum(a_uu' * del Vu') = d_u 
    * b_u = 1/cm * (g_Na + g_K + g_L + sum(g_uu')) * z Del t  
    * a_uu' = 1/cm * sum(g_uu'* z Del t)
    * d_u = 1/cm * (g_Na*E_Na + g_K*E_K + g_L*E_L + i_e/a_u + sum(g_uu' * V_u'(t)) + (g_Na + g_K + g_L + sum(g_uu'))* V_u(t)) * del t
    */
    
    // b_u = 1/cm * (g_Na + g_K + g_L + sum(g_uu')) * z Del t 
    public double calculateBi(double timestep){
        
        double g_intercomp = 0.0;
        for(int i = 0; i < num; i++){
            //y is the voltage of the compartment, that is currently investigated
           g_intercomp = g_intercomp + g[i]; 
        }
        // sollten wir b_u auch ausserhalb definieren oder reicht es auch hier drinnen? 
        double sumconductances = gNa + gK + gL + g_intercomp;
        double b_u = sumconductances * timestep / cm;
        return 1-b_u;
    }
    
    public double calculateAij(int i, int j, double timestep){
        //what is a_ij? 
        double a_ij = 0;
        return a_ij;
    }
    
    public double calculateDi(){
        double d_u = 0 ;
        return d_u;
    }
}
