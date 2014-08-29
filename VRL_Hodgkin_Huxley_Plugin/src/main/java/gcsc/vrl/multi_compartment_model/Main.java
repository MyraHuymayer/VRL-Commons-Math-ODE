package gcsc.vrl.multi_compartment_model;
import gcsc.vrl.hodgkin_huxley_plugin.*;
import java.util.*;
/**
 *
 * @author myra
 */
public class Main {
    
    public static void main(String [] args){
//------------------------------------------------------------------------------------------------------------------------------------------------------------
//         Create the connectivity-Matrix
//------------------------------------------------------------------------------------------------------------------------------------------------------------
        int n = 4; 
        ConnectivityMatrix a = new ConnectivityMatrix(n); 
        a.addEntry(0,1); 
        a.addEntry(3, 2);
     
        a.addEntry(1,2);
        a.addEntry(1,3);

//        for(int i = 0; i<n; i++){
//            for(int j = 0; j < n; j++){
//                
////                System.out.print(a+" ");
//              System.out.print(a.getEntry(i, j) +" ");
//            }
//            System.out.print("\n");
//        }
//        System.out.println();
        
         a.deleteEntry(3,2);
        
         for(int i = 0; i<n; i++){
            for(int j = 0; j < n; j++){
                
//                System.out.print(a+" ");
              System.out.print(a.getEntry(i, j) +" ");
            }
            System.out.print("\n");
        }
         
//------------------------------------------------------------------------------------------------------------------------------------------------------------
//Create Model using the connectivity matrix (USING the CModelCreator)
//------------------------------------------------------------------------------------------------------------------------------------------------------------
         
        CModelCreator model = new CModelCreator();
        model.setCmat(a);
        model.createAllCompartments();
     
        Compartment[] c = model.getAllCompartments();
        
        c[0].setPresent_Voltage(0.334);
        c[1].setPresent_Voltage(2.334);
        c[2].setPresent_Voltage(0.123);
        c[3].setPresent_Voltage(9.00001);
//        double volt; 
        double[] neighborVoltages; 
        
        model.setSomaCompartments(0);
        model.setBasalDendrites(1);
        model.setApicalDendrites(2,3);
        model.createAllEdges();
        
        
//        for(int i = 0; i< c.length; i++){
//            c[i].init(4, 2, 3);
//        }
//        
//         for(int i = 0; i< c.length; i++){
//            double rl = c[i].getR_L();
//            double len = c[i].getLength(); 
//            double rad = c[i].getRadius(); 
////            System.out.println();
////            System.out.print("resistivity ="+rl+" \n");
////            System.out.print("length ="+len+" \n");
////            System.out.print("radius ="+rad+" \n");
////            System.out.println();
//        }
         
        
        for(int i = 0; i< c.length; i++){
            if(c[i].getType() == 1){
                c[i].init(1, 1, 1);
            }else if(c[i].getType() == 2){
                c[i].init(2, 2, 2);
            }else if(c[i].getType() == 3){
                c[i].init(3, 3, 3);
            }else if(c[i].getType() == 4){
                c[i].init(4, 4, 4);
            }else{
                c[i].init(0, 0, 0);
            }
        }
        
        c[3].init(5000, 899, 8884);
         for(int i = 0; i< c.length; i++){
            double rl = c[i].getR_L();
            double len = c[i].getLength(); 
            double rad = c[i].getRadius(); 
            System.out.println();
            System.out.print("Comp["+i+"] resistivity ="+rl+" \n");
            System.out.print("Comp["+i+"] length ="+len+" \n");
            System.out.print("Comp["+i+"] radius ="+rad+" \n");
            System.out.println();
        }
   
               

        
        ArrayList<Edge> ed = model.getAllEdges();
        System.out.println("----------------------------------"); 
        System.out.println("Number of edges: "+ed.size());
        for (int i = 0; i<ed.size(); i++){
            
            Edge tmp = ed.get(i);
            Compartment one = tmp.first(); 
            Compartment two = tmp.second(); 
            int id1 = one.getId(); 
            int id2 = two.getId(); 
            System.out.println("Edge[1]: "+id1+" ---- Edge[2]: "+id2+"  " );
            
        }
        
        System.out.println();
        
        for(int i = 0; i < c.length; i++){

            int id = c[i].getId(); 
            System.out.println("Compartment["+i+"] = "+id);
        }
        //NOTE: the Function compartmentalParameters() can only be called after createAllCompartments() AND createAllEdges()
       model.compartmentalParameters();
//        for(int i = 0; i < c.length; i++){
//            volt = c[i].getPresent_Voltage();
//            System.out.println("Voltage of Compartment ["+i+"] = "+volt+"   ");
////            System.out.println("----------------   ***************************   ------------------"); 
//        }
        

//        System.out.println("----------------------------------------------------------------"); 
//        //NOTE: all obtainNeighborVoltages() can only be called, when dependencies was initialized
//        for(int i = 0; i < c.length; i++){
//            
//            c[i].obtainNeighborVoltages();
//            neighborVoltages = c[i].getNeighborVoltages();
//            int lentest = neighborVoltages.length;
//            System.out.println("length of voltage-array of compartment ["+i+"] = "+lentest+" "); 
//            for(int j = 0; j < neighborVoltages.length; j++){
//                //System.out.println("----------------TEST!TEST!TEST!TEST!TEST!TEST!TEST!------------------"); 
//                System.out.print("Comp["+i+"] --> V["+j+"] = "+neighborVoltages[j]+"   \n");
//            }
//            System.out.println("----------------------------------------------------------------"); 
//        }
        
        for(int i = 0; i<c.length; i++){
            double[] g = c[i].getG();
            
            for(int k = 0; k< g.length; k++){

                System.out.print("Conductance of Compartment["+i+"]: "+g[k]+" \n");
            }
            System.out.println();
        }
      

        
        
        
        
        for(int i = 0; i<c.length; i++){
            System.out.print("Compartment["+i+"]: Type number: "+c[i].getType()+", Type Description: "+c[i].getTypeDescription()+"\n");
            System.out.println("-------------------------------------------------------------------------------------------------------"); 
        } 
        System.out.println();
 
        
//------------------------------------------------------------------------------------------------------------------------------------------------------------
// Create the linear system and calculate matrix A of the LS 
//------------------------------------------------------------------------------------------------------------------------------------------------------------
        LSCreator linsys =  new LSCreator(); 
        VFunction2D vfun = new VFunction2D(); 
//        double[ ] neighbVoltages = new double[n];
//        neighbVoltages[0] = 0.334; 
//        neighbVoltages[1] = 2.334;
//        neighbVoltages[2] = 0.123; 
//        neighbVoltages[3] = 90; 
    
//        IFunction ifun = new IFunction(); //wird erst mal noch nicht gebraucht! 
 
        
        MCVFunction mcfun = new MCVFunction(); 
        double [][] mata = linsys.copyMatrix(a);
       
        //TODO: right_hand_side sollte eine ArrayList sein und kein double[], da immer nur das i geschrieben wird, welches gerade in der for SChleife aufgerufen wird und die anderen Werte werden gleich 0.0 gesetzt!!
        double [] right_hand_side = new double[n];
        
        for(int i = 0; i<c.length; i++){
            double[] g = c[i].getG();
//            volt = c[i].getPresent_Voltage(); 
//            System.out.print("volt "+volt+"\n");
//            System.out.print("compartment number =  "+i+"\n");
//            System.out.print("compartment id =  "+c[i].getId()+"\n");
            
            c[i].obtainNeighborVoltages();
            neighborVoltages = c[i].getNeighborVoltages();
            
//            System.out.println("_________________________________________");
            for(int j = 0; j < neighborVoltages.length; j ++){
                System.out.println("v["+j+"] = "+neighborVoltages[j]);
            }
//            System.out.println("_________________________________________");
//            double volt = c[i].getPresent_Voltage();
//            double[] neighborVoltages = c[i].obtainNeighborVoltages();
            
            
            vfun.init(0.36, -77.00, 1.2, 50.00, 0.003, -54.387, 0.01);
            vfun.setI(0.5); //eher unschoen, dass es sowohl hier eine setI-Function, als auch ...
            linsys.setVf(vfun); 
            mcfun.setIe(vfun.getI()); // ... hier eine setI(e)-Function gibt! 
            mcfun.seteK(vfun.geteK());
            mcfun.seteL(vfun.geteL());
            mcfun.seteNa(vfun.geteNa());
            mcfun.setgK(vfun.getgBarK());
            mcfun.setgL(vfun.getgBarL());
            mcfun.setgNa(vfun.getgBarNa());
            mcfun.setArea_u(2);
            mcfun.setCm(vfun.getCm());
            mcfun.setZ(1.0001);
            mcfun.setTimestep(0.0001);
            linsys.setMcvf(mcfun);
            linsys.setH(12);
            linsys.setM(2); 
            linsys.setN(4); 
            mata = linsys.determineMatrix(c); 
//            linsys.rightHandSide(neighborVoltages, volt, mcfun.getArea_u(),i);
            
            System.out.println();
            
            
        }
   
       linsys.rightHandSide(c, mcfun.getArea_u());
       right_hand_side = linsys.getRhs(); 
       
       System.out.println();
       
       System.out.println("This is the matrix with the new values: ");
       
       System.out.println();
       for(int i = 0; i < n; i++){
           for(int j = 0; j < n; j++){
                

             System.out.print(mata[i][j] +" ");
           }
           System.out.print("\n");
       }
        
       System.out.println();
       for(int i = 0; i < n; i++){
           System.out.print("d_["+i+"] =  "+right_hand_side[i] +" \n");
       }
       System.out.println();
       System.out.println("_________________________________________ END of TEST _________________________________________");
 //NOTE: bis hier hin scheinen die Tests erfolgreich zu sein!! 
        
        
//         System.out.println("##################################"); 
        /* TODO: For further testing: 
         *      1. Teste, ob die Edges so funktionieren wie sie sollen
         *      3. erstelle die Dependencies Liste!  
         *      2. berechne die Conductances mit den Compartments/mit einem einzelnen Compartment 
         *      
         */

    }    
}
