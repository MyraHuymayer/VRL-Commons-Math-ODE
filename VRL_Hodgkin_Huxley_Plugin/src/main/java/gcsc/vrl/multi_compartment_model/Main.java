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
    }
}
