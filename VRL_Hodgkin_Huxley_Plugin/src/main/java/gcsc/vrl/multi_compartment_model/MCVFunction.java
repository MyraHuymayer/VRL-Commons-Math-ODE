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
    private double[] g;
    
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
    private final double cm = 1.0;

    private double timestep;
    private double z; 
    /*-----------------------------------------------------------------------------------------------------------------------------------*/
    public MCVFunction() {
        
    }
    
    public void setG(double[] g){
        this.g = g;
    }

     public void setTimestep(double timestep) {
        this.timestep = timestep;
    }
     
    public double getTimestep() {
        return timestep;
    }

   
    
    
    /*
    * WE need three solutions that will be multiplicated with Vu or Vu' respectively
    * The general solution has the form: del V_u(1-b_u) - sum(a_uu' * del Vu') = d_u 
    * b_u = 1/cm * (g_Na + g_K + g_L + sum(g_uu')) * z Del t  
    * a_uu' = 1/cm * sum(g_uu'* z Del t)
    * d_u = 1/cm * (g_Na*E_Na + g_K*E_K + g_L*E_L + i_e/a_u + sum(g_uu' * V_u'(t)) + (g_Na + g_K + g_L + sum(g_uu'))* V_u(t)) * del t
    */
    
    // b_u = 1/cm * (g_Na + g_K + g_L + sum(g_uu')) * z Del t 
    public double calculateBi(){
        
        double g_intercomp = 0.0;
        for(int i = 0; i < num; i++){
            //y is the voltage of the compartment, that is currently investigated
           g_intercomp = g_intercomp + g[i]; 
        }
        // sollten wir b_u auch ausserhalb definieren oder reicht es auch hier drinnen? 
        double sumconductances = gNa + gK + gL + g_intercomp;
        double b_u = sumconductances * timestep * z / cm;
        return 1-b_u;
    }
    
    //TODO: die richtige Reihenfolge der Leitfaehigkeiten muss sichergestellt werden!!! 
    /**
     * 
     * @param j the column or respectively the id of the neighboring compartment
     * @return value of the neighboring conductance
     */
    public double calculateAij(int j){
        //what is a_ij? 
        double a_ij = z*g[j]; //das bringt nicht besonders viel, da die Nachbarn vorraussichtlich nicht geordnet sind
        return a_ij;
    }
    
    /**
     * 
     * @param currentVoltageNeighbor
     * @param currentVoltageComp
     * @return 
     */
    //d_u = 1/cm * (g_Na*E_Na + g_K*E_K + g_L*E_L + i_e/a_u + sum(g_uu' * V_u'(t)) + (g_Na + g_K + g_L + sum(g_uu'))* V_u(t)) * del t
    public double calculateDi(double[] currentVoltageNeighbor, double currentVoltageComp){
        //g_Na*E_Na + g_K*E_K + g_L*E_L + i_e/a_u
        double hhge = gNa * eNa + gK * eK + gL*eL + ie/a_u;
        //Die Logik dahinter stimmt so noch nicht !!
        //sum(g_uu' * V_u'(t))
        double sumNeighborCurrent = 0.0; 
        for(int i = 0; i < currentVoltageNeighbor.length; i++){
            sumNeighborCurrent = sumNeighborCurrent + g[i] * currentVoltageNeighbor[i];
        
        }
        //Die Logik dahinter stimmt so noch nicht !!
        //(g_Na + g_K + g_L + sum(g_uu'))* V_u(t)
        double sumCompCurrent = gNa + gK + gL; 
        for(int i = 0; i < num; i++){
            sumCompCurrent = sumCompCurrent + g[i];
        }
        
        sumCompCurrent = sumCompCurrent * currentVoltageComp; 
       
        double sum = hhge + sumNeighborCurrent + sumCompCurrent;
       
        double d_u = sum * timestep/cm  ;
        return d_u;
    }
}
