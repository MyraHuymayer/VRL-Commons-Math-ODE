//TODO: nochmal anschauen, ob das so stimmt
package gcsc.vrl.multi_compartment_model;

/**
 *
 * @author myra
 */
public class LS_solver {
    
    public void solve_ls(double[][] matrixA, double[] x, double[] b){
       //    void solve_ls(data* A, data* x, data* b)
//        {
//            assert(A->get_n_columns() == A->get_n_rows()); --NOTE hier kann man eine Exception generieren, wenn dieser Fall eintritt
//            assert(b->get_n() == x->get_n());
//            assert(A->get_n_columns() == b->get_n());
//            
        //wirklich long? und nicht int? Das muesste dann natuerlich auf das gesamt programm angepasst werden 
        for (int column = 0; column < matrixA.length; column ++){
            scale_add_following_rows(matrixA,b,column);
        }
//            for(unsigned long column=0; column < A->get_n_columns(); column++)
//            {
//                //std::cout<< "COLUMN: " << column << "\n";
//                scale_add_following_rows(A,b,column);
//            }
//            
        for(int rows = matrixA.length-1; rows > -1; rows --){
            if(matrixA[rows][rows] != 0 ){
                x[rows] = b[rows] - back_substitution(matrixA,x,rows)/matrixA[rows][rows]; //nochmal genau anschauen, was das hier tun soll 
            }else if(b[rows]==0){
                x[rows] = 0;
            }
        }
        //            for(int rows=A->get_n_rows()-1 ; rows > -1 ; rows--)
//            {
//                //std::cout << "BACKSUB: " << back_substitution(A,x,rows) << " ROW: " << rows << "\n";
//                if(A->get_value(rows,rows)!=0)
//                    x->set_value(rows, (b->get_value(rows) - back_substitution(A,x,rows))/A->get_value(rows,rows));
//                else
//                    if (b->get_value(rows)==0)
//                        x->set_value(rows, 0);
//
//            }
//            
//        }

       
    }
    
    private double back_substitution(double[][] matrixA, double[] x, int row){ //NOTE : das war vorher long und nicht int 
        double sum = 0; 
        for(int i = matrixA.length -1; i>row; i--){
            
            sum += x[i]* matrixA[i][row];
        }
        //        private:
//        double back_substitution(data*A, data*x, unsigned long row)
//        {
//            double sum = 0;
//            for (unsigned long i=A->get_n_rows()-1; i>row; i--)
//            {
//                sum += x->get_value(i)*A->get_value(row,i);
//            }
//            return sum;
//        }
        
        return sum; 
    }
    
    public void scale_add_following_rows(double[][] matrixA, double[] b, int column){
        
        for(int rows = column +1; rows < matrixA.length; rows++){
            double factor = -matrixA[rows][column]/matrixA[column][column];
            
            if(factor != 0){
                for(int columns = column; columns < matrixA.length; columns ++){
                    double value = factor*matrixA[column][columns] + matrixA[rows][columns];
                    matrixA[rows][columns] = value;
                }
                b[rows] = factor * b[column] + b[rows];
            }
        }
//        void scale_add_following_rows(data* A, data*b, unsigned long column)
//        {
//            assert(A->get_n_columns() == b->get_n());
//            for(unsigned long rows=column+1; rows < A->get_n_rows(); rows++)
//            {
//                double factor = -A->get_value(rows,column)/A->get_value(column,column);
//                if(factor!=0)
//                {
//                //std::cout << "factor: " << factor << "\n";
//                for(unsigned long columns=column; columns < A->get_n_columns(); columns++)
//                {
//                    double value = factor*A->get_value(column,columns)+A->get_value(rows,columns);
//                    A->set_value(rows,columns,value);
//                }
//                b->set_value(rows,factor*b->get_value(column) + b->get_value(rows));
//                }
//               
//            }
//  
    }

    
// double get_value(unsigned long row)
//        {
//            return A[get_index(row)];
//        }
//        void set_value(unsigned long row, double val)
//        {
//            A[get_index(row)] = val;
//        }
      
//        private:
//        unsigned long get_index(unsigned long row, unsigned long column)
//        {
//            return row*n_columns + column;
//        }
//        unsigned long get_index(unsigned long row)
//        {
//            return row;
//        }
    
}
