package hust.mso.mfea;
import java.util.ArrayList;

public class Individual {
    int id;
    double fitness[] = new double[2];
    double saclarFitness =0;
    int skillFactor =0;
    ArrayList<Double> genes = new ArrayList<>();
    ArrayList<Integer> genes_in_task_0 = new ArrayList<Integer>();
    ArrayList<Integer> genes_in_task_1 = new ArrayList<Integer>();
    public void Init(){
        
    }
    public void CalFitness0(){
        decode(0);
        //
        
        
        
        
        
        
        fitness[0] = 0;
    }
    public void CalFitness1(){
        decode(1);
        
        
        
        
        fitness[1] = 0;
    }
    
    public void decode(int task){
        if(task ==0){
            
        }else if(task==1){
            
        }
    }
    public void SetFitness0(double b){
        fitness[0] =b;
    }
    public void SetFitness1(double b){
        fitness[1] =b;
    }
    
    
    
}
