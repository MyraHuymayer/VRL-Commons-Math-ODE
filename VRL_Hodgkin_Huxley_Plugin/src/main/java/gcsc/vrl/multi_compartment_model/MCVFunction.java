/*
 * NOTE: This function will most propably replace soon the function of MCVFunction2D.java
 * we need to split the solution that would be generated in MCVFunction2D.java into three solution subsets
 * which will later be written into the Matrix 
 * 
 */
package gcsc.vrl.multi_compartment_model;
//import gcsc.vrl.hodgkin_huxley_plugin.*;

/**
 * to solve C_m * dV/dt = -i_m + i_e + sum(g_u,u' * V_u' - v_u)
 * i_m = g_barK * n^4 * (v - e_K) + g_barNa * m^3 * h * (v - e_Na) + g_barL * (v - e_L)  
 */



public class MCVFunction {
    
    /*
     * to solve C_m * dV/dt = -i_m + i_e + sum(g_u,u' * V_u' - v_u)
     * i_m = g_barK * n^4 * (v - e_K) + g_barNa * m^3 * h * (v - e_Na) + g_barL * (v - e_L)   <--MCVFunction also needs to know these values; access is needed to these values 
     * MEMO: n,m and h are calculated as in the Hodgkin Huxley Equations
     * also i_e is known to this function
     * /
    
    /**
     * number of neighboring compartments
     */
    private int num;
    
    /**
     * inter-compartmental conductance [mS/mm^2]
     */
    private double[] g;
    
    /**
     * Maximal sodium conductance [mS/mm^2]
     */ 
    //memo: gNa = gbarNa * m^3*h
    private double gNa;
    /**
     * Maximal potassium conductance [mS/mm^2]
     */ 
    private double gK;
     /**
     * Maximal leak conductance [mS/mm^2]
     */ 
    private double gL;
    
    /**
     * Equilibrium potential of sodium [in mV]
     */
    private double eNa; 
    
    /**
     * Equilibrium potential of potassium [in mV]
     */
    private double eK;
    
    /**
     * Equilibrium potential of leakage current [in mV]
     */
    private double eL;
    
    /**
     * externally injected current [in uA]
     */
    private double ie; 
    
    /**
     * area of compartment [in mm^2]
     */
    private double area_u; 
    
    /**
     * membrane conductance [in uF/mm^2]
     */
    private double cm;

    private double[] voltage;
    private double timestep;
    private double z; 
    /*-----------------------------------------------------------------------------------------------------------------------------------*/
    public MCVFunction() {
        
    }
    //TODO: nochmal drueber nachdenken ob ich wirklich alle getter und setter brauche
    public void setG(double[] g){
        this.g = g;
    }

     public void setTimestep(double timestep) {
        this.timestep = timestep;
    }
     
    public double getTimestep() {
        return timestep;
    }

    public int getNum() {
        return num;
    }

    public double getgNa() {
        return gNa;
    }

    public double getgK() {
        return gK;
    }

    public double getgL() {
        return gL;
    }

    public double geteNa() {
        return eNa;
    }

    public double geteK() {
        return eK;
    }

    public double geteL() {
        return eL;
    }

    public double getIe() {
        return ie;
    }

    public double getArea_u() {
        return area_u;
    }

    public double getZ() {
        return z;
    }

    public double[] getG() {
        return g;
    }

    public double getCm() {
        return cm;
    }

    public double[] getVoltage() {
        return voltage;
    }

    public void setVoltage(double[] voltage) {
        this.voltage = voltage;
    }
    
    public void setNum(int num) {
        this.num = num;
    }

    public void setgNa(double gNa) {
        this.gNa = gNa;
    }

    public void setgK(double gK) {
        this.gK = gK;
    }

    public void setgL(double gL) {
        this.gL = gL;
    }

    public void seteNa(double eNa) {
        this.eNa = eNa;
    }

    public void seteK(double eK) {
        this.eK = eK;
    }

    public void seteL(double eL) {
        this.eL = eL;
    }

    public void setIe(double ie) {
        this.ie = ie;
    }

    public void setArea_u(double area_u) {
        this.area_u = area_u;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public void setCm(double cm) {
        this.cm = cm;
    }
    
    
    
    /*
    * WE need three solutions that will be multiplicated with Vu or Vu' respectively
    * The general solution has the form: del V_u(1-b_u) - sum(a_uu' * del Vu') = d_u 
    * b_u = 1/cm * (g_Na + g_K + g_L + sum(g_uu')) * z Del t  
    * a_uu' = 1/cm * sum(g_uu'* z Del t)
    * d_u = 1/cm * (g_Na*E_Na + g_K*E_K + g_L*E_L + i_e/area_u + sum(g_uu' * V_u'(t)) + (g_Na + g_K + g_L + sum(g_uu'))* V_u(t)) * del t
    */
    
    /**
     * calculate b_u:  b_u = 1/cm * (g_Na + g_K + g_L + sum(g_uu')) * z Del t 
     * @return the value of b_u which is written into the matrix A, when index i == index j
     */
    // 
    public double calculateBi(){
        
        double g_intercomp = 0.0;
        for(int i = 0; i < num; i++){
           g_intercomp = g_intercomp + g[i]; 
        }
        // sollten wir b_u auch ausserhalb definieren oder reicht es auch hier drinnen? 
        double sumconductances = gNa + gK + gL + g_intercomp;
        double b_u = sumconductances * timestep * z / cm;
        return 1-b_u;
    }
    
    //richtige Reihenfolge der Leitfaehigkeiten sichergestellt? Fuer uns minimalbeispiel stimmt die Reihenfolge auf jeden Fall schon mal 
    /**
     * Calculate a_(uu'): a_uu' = 1/cm * sum(g_uu'* z Del t)
     * @param k index value of g[] 
     * @return value a_uu' which is written into matrix A at [i][j]
     */
    public double calculateAij(int k){
        
        double a_ij = z*timestep*g[k]/cm; 
        return a_ij;
    }
    
    /**
     * d_u of the compartment that is currently in the focus - is written to the vector on the right hand-side in the linear system
     * @param currentVoltageNeighbor array that contains the voltages from the neighboring compartments at a given time 
     * @param currentVoltageComp the voltage of the compartment determined for a given timestep
     * @return  
     */
    //d_u = 1/cm * (g_Na*E_Na + g_K*E_K + g_L*E_L + i_e/area_u + sum(g_uu' * V_u'(t)) + (g_Na + g_K + g_L + sum(g_uu'))* V_u(t)) * del t
    public double calculateDi(double[] currentVoltageNeighbor, double currentVoltageComp){ //NOTE/MEMO: die parameter, die hier von calculateDi genutzt werden, werden in Compartment.java definiert
       
        
        //hhge = g_Na*E_Na + g_K*E_K + g_L*E_L + i_e/area_u
        // <==> hhge = D_u 
        // see Dayan and Abott: Theoretical Neuroscience
        double hhge = gNa * eNa + gK * eK + gL*eL + ie/area_u;
        
        //sumNeighborCurrent = sum(g_uu' * V_u'(t))
        // <==> sumNeighborCurrent = sum(A_u * V_u'(t))
        double sumNeighborCurrent = 0.0; 
        for(int i = 0; i < g.length; i++){
            sumNeighborCurrent = sumNeighborCurrent + g[i] * currentVoltageNeighbor[i];
        
        }
        
        //sumCompCurrent = (g_Na + g_K + g_L + sum(g_uu'))* V_u(t) 
        // <==> sumCompCurrent = B_u * V_u(t) 
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
