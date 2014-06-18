package gcsc.vrl.hodgkin_huxley_plugin;

import eu.mihosoft.vrl.annotation.ComponentInfo;
import eu.mihosoft.vrl.annotation.OutputInfo;
import eu.mihosoft.vrl.annotation.ParamInfo;
import java.io.Serializable;
import org.apache.commons.math3.ode.FirstOrderDifferentialEquations;



/**
 *
 * @author myra
 */
@ComponentInfo(name = "ODE Creator", category = "Commons/Math/ODE", description = "Creates RHS for ODESolver")
public class ODECreator implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    @OutputInfo(name="RHS")
    public FirstOrderDifferentialEquations createHHequs(
            @ParamInfo(name="VFunction2D") VFunction2D vf,
            @ParamInfo(name="IFunction") IFunction ifct){
        
        HHequs result = new HHequs();
        result.setIFct(ifct);
        result.setVF(vf);
        return result;
    }

    
    
}
