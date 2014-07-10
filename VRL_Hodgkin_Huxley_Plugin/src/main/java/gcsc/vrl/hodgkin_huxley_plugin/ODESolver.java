package gcsc.vrl.hodgkin_huxley_plugin;

import eu.mihosoft.vrl.annotation.ComponentInfo;
import eu.mihosoft.vrl.annotation.OutputInfo;
import eu.mihosoft.vrl.annotation.ParamInfo;
import eu.mihosoft.vrl.math.Trajectory;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.apache.commons.math3.ode.FirstOrderDifferentialEquations;
import org.apache.commons.math3.ode.FirstOrderIntegrator;
import org.apache.commons.math3.ode.nonstiff.DormandPrince54Integrator;
//import org.apache.commons.math3.ode.nonstiff.DormandPrince853Integrator;
//import org.apache.commons.math3.ode.nonstiff.EulerIntegrator;
import org.apache.commons.math3.ode.sampling.StepHandler;
import org.apache.commons.math3.ode.sampling.StepInterpolator;

/**
 *
 * @author myra
 */
@ComponentInfo(name = "ODE Solver", category = "Commons/Math/ODE", description = "ODE solver based on Commons-Math FirstOrderIntegrator")
public class ODESolver implements Serializable {

    private static final long serialVersionUID = 1L;

    public ODESolver() {
    }



    /**
     * Method to plot the following functions: Voltage, n, m and h
     *
     * @param t0 initial time
     * @param tn last time step
     * @param y0 initial value of the voltage
     * @param minStep minimal step
     * @param maxStep maximal step
     * @param absTol absolute tolerance
     * @param relTol relative tolerance
     * @param rhs differential equations that need to be solved in an array
     * [V,n,m,h]
     * @return array of Trajectories containing the time points and the
     * interpolated states of n,m and h
     */
    @OutputInfo(style = "multi-out",
    elemTypes = {Trajectory.class, Trajectory.class, Trajectory.class, Trajectory.class},
    elemNames = {"V", "N", "M", "H"},
    elemStyles = {"default", "default", "default", "default"})
    public Object[] solveNMH(
            //            String label,
            @ParamInfo(name = "t0", options = "value=0.0D") double t0,
            @ParamInfo(name = "tn", options = "value=200.0D") double tn,
            @ParamInfo(name = "v0", options = "value=-65.0") double y0,
            @ParamInfo(name = "Min Step", options = "value=1.0E-4D") double minStep,
            @ParamInfo(name = "Max Step", options = "value=0.01D") double maxStep,
            @ParamInfo(name = "Abs.Tol.", options = "value=1.0E-4D") double absTol,
            @ParamInfo(name = "Rel.Tol.", options = "value=1.0E-4D") double relTol,
            @ParamInfo(name = "RHS") FirstOrderDifferentialEquations rhs,
            @ParamInfo(name = "CurrentFunction")IFunction ifct) throws InitialCurrentException, FinalCurrentException{

//        String label;
        FirstOrderIntegrator integrator = new DormandPrince54Integrator(minStep, maxStep, absTol, relTol);

//       FirstOrderIntegrator integrator = new EulerIntegrator(0.01);

        
//        final Trajectory result = new Trajectory(label);
        final Trajectory[] result = new Trajectory[4];
        
        ifct.compareTi(t0, tn);
     
        result[0]= new Trajectory("V"); 
        result[1]= new Trajectory("N"); 
        result[2]= new Trajectory("M"); 
        result[3]= new Trajectory("H"); 
     
        
      
        for (int i = 0; i < result.length; i++) {
        
            result[i].setTitle("Hodgkin Huxley model");  
            result[i].setxAxisLabel("time (ms)");
            if(i > 0){
                result[i].setyAxisLabel("gating variables");
            }else{
                result[i].setyAxisLabel("Potential (mV)");
            }
        }

        StepHandler stepHandler = new StepHandler() {
            @Override
            public void init(double t0, double[] y0, double t) {
                for (int i = 0; i < result.length; i++) {
                             
                    result[i].add(t0, y0[i]);
        

                }
            }

            @Override
            public void handleStep(StepInterpolator interpolator, boolean isLast) {
                double t = interpolator.getCurrentTime();
                double[] y = interpolator.getInterpolatedState();

                for (int i = 0; i < result.length; i++) {

                  result[i].add(t, y[i]);



                }
            }
        };

        integrator.addStepHandler(stepHandler);

        
        NFunction2D n0 = new NFunction2D();
        MFunction2D m0 = new MFunction2D();
        HFunction2D h0 = new HFunction2D();

        n0.setV(y0);
        m0.setV(y0);
        h0.setV(y0);

        double[] y = new double[]{y0, n0.ninf(), m0.minf(), h0.hinf()}; // initial state
        
        integrator.integrate(rhs, t0, y, tn, y);

        
        Object[] objResult = new Object[result.length];
        
        System.arraycopy(result, 0, objResult, 0, result.length);

        return objResult;
    }
    
    


}
