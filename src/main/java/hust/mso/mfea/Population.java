package hust.mso.mfea;
import java.util.ArrayList;

public class Population {
    ArrayList<Individual> pop = new ArrayList<>();
    public void init(){
        
    }
    public void selection(){
        
    }
    public void calScalarFitness_SkillFactor(){
        
    }
    public Individual mutation(Individual parent){
        Individual ind = new Individual();
        
        return ind;
    }
     public ArrayList<Individual> crossover(Individual parent1,Individual parent2 ){
         ArrayList<Individual> off_spring = new ArrayList<Individual>();
        Individual child1 = new Individual();
        Individual child2 = new Individual();
        
        
        
        
        
        
       off_spring.add(child1);
       off_spring.add(child2);
       return off_spring;
    }
    
    
}
