package gcsc.vrl.multi_compartment_model;

/**
 *
 * @author myra
 */
public class Main {
    
    public static void main(String [] args){
//         Testing the connectivity matrix
        int n = 4; 
        ConnectivityMatrix a = new ConnectivityMatrix(n); 
        a.addEntry(0,1); // eigentlich waere das ja intuitiv bei (1,2), aber bei dem array faengt man ja bei 0 zu zaehlen an
        a.addEntry(3, 2);
        a.deleteEntry(3,2);
        a.addEntry(1,2);
        a.addEntry(1,3);

        for(int i = 0; i<n; i++){
            for(int j = 0; j < n; j++){
                
//                System.out.print(a+" ");
              System.out.print(a.getEntry(i, j) +" ");
            }
            System.out.print("\n");
        }
//        System.out.println();
         //System.out.print("-----------------------------------------------------------------------------------------\n");
        
       // System.out.print("-----------------------------************************-----------------------------------\n");
        
        CModelCreator model = new CModelCreator(); 
        Compartment[] test; 
        model.createAllCompartments(a);
        model.createAllEdges(a);
       // model.setProperties(0.7, 0.345, 1.23442);
        test = model.getAllCompartments();
        
        for(int i = 0; i<n; i++){
            test[i].init(0.7, 0.345, 1.23442);
            int id = test[i].getId();
            double r_L = test[i].getR_L(); 
            double length = test[i].getLength();
            double radius = test[i].getRadius();
            System.out.print("\n");
            System.out.print("Intracellular resistivity of Compartment["+i+"]:"+r_L+"\n " );
            System.out.print("Length of Compartment["+i+"]:"+length+"\n " );
            System.out.print("Radius of Compartment["+i+"]:"+radius+"\n " );
            System.out.print("-------------------------------------------------------------------\n");
            System.out.print("ID of Compartment["+i+"]:"+id+"\n " );
            System.out.print("\n");
            System.out.print("-------------------------------------------------------------------");
        }
       
        
//        //Compartment TEST
//         int complength = 3;
//        Compartment[] test2 = new Compartment[complength];
//        System.out.print("Length of this Compartment Array= "+complength+"\n");
//        for(int i = 0; i < complength; i++){
//            test2[i] = new Compartment(i);
//            System.out.print("test2= "+test2[i].getId()+"\n");
//        }
////         Compartment comp = new Compartment(3);
////         System.out.print("Id of single compartment is = "+comp.getId()+"\n");
    }    
}
