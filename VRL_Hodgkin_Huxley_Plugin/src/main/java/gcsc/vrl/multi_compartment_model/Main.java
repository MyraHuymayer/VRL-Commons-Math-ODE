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

        for(int i = 0; i<n; i++){
            for(int j = 0; j < n; j++){
                
//                System.out.print(a+" ");
              System.out.print(a.getEntry(i, j) +" ");
            }
            System.out.print("\n");
        }
        System.out.println();
        
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
        
        
        for(int i = 0; i< c.length; i++){
            c[i].init(4, 2, 3);
        }
        
         for(int i = 0; i< c.length; i++){
            double rl = c[i].getR_L();
            double len = c[i].getLength(); 
            double rad = c[i].getRadius(); 
            System.out.println();
            System.out.print("resistivity ="+rl+" \n");
            System.out.print("length ="+len+" \n");
            System.out.print("radius ="+rad+" \n");
            System.out.println();
        }
   
        
        model.createAllEdges();
        
        ArrayList<Edge> ed = model.getAllEdges();
        System.out.println("----------------------------------"); 
        System.out.println("Number of edges: "+ed.size());
        for (int i = 0; i<ed.size(); i++){
            
            Edge tmp = ed.get(i);
            Compartment one = tmp.first(); 
            Compartment two = tmp.second(); 
            int id1 = one.getId(); 
            int id2 = two.getId(); 
            System.out.println("Comp[1]: "+id1+" ---- Comp[2]: "+id2+"  " );
            
        }
        
        for(int i = 0; i < c.length; i++){

            int id = c[i].getId(); 
            System.out.println("Compartment["+i+"] = "+id);
        }
        //NOTE: the Function compartmentalParameters() can only be called after createAllCompartments() AND createAllEdges()
        model.compartmentalParameters();
        

        
        for(int i = 0; i<c.length; i++){
            double[] g = c[i].getG();
            
            for(int k = 0; k< g.length; k++){

                System.out.print("Conductance: "+g[k]+" \n");
            }
            System.out.println();
        }
        
         
 
        
//------------------------------------------------------------------------------------------------------------------------------------------------------------
// Create the linear system and calculate matrix A of the LS 
//------------------------------------------------------------------------------------------------------------------------------------------------------------
        LSCreator linsys =  new LSCreator(); 
        VFunction2D vfun = new VFunction2D(); 
   
        MCVFunction mcfun = new MCVFunction(); 
        double [][] mata = linsys.copyMatrix(a);
         
        for(int i = 0; i<c.length; i++){
            double[] g = c[i].getG();
            vfun.init(0.36, -77.00, 1.2, 50.00, 0.003, -54.387, 0.01);
            linsys.setVf(vfun); 
            mcfun.seteK(vfun.geteK());
            mcfun.seteL(vfun.geteL());
            mcfun.seteNa(vfun.geteNa());
            mcfun.setgK(vfun.getgBarK());
            mcfun.setgL(vfun.getgBarL());
            mcfun.setgNa(vfun.getgBarNa());
            mcfun.setA_u(2);
            mcfun.setCm(vfun.getCm());
            mcfun.setIe(0.89);
            mcfun.setZ(1.0001);
            mcfun.setTimestep(1.0001);
            linsys.setMcvf(mcfun);
            linsys.setH(12);
            linsys.setM(2); 
            linsys.setN(4); 
            mata = linsys.determineMatrix(c); 
            System.out.println();
        }
   
       
        
         System.out.println();
        System.out.println("This is the matrix with the new values: ");
        System.out.println();
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                

              System.out.print(mata[i][j] +" ");
            }
            System.out.print("\n");
        }

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
