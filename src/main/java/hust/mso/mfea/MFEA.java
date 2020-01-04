package hust.mso.mfea;

import java.util.ArrayList;
import java.util.Random;

public class MFEA {
    Population p = new Population();

    public MFEA() {
        Parameter.rand = new Random(Parameter.seed);
    }
    
    public ArrayList<Individual> run(){
        ArrayList<Individual> best = new ArrayList<>();
        p.init();
        p.calScalarFitness_SkillFactor();
        int generation = 1;
        
        while(generation <Parameter.CONDITION_STOP){
            ArrayList<Individual> child = new ArrayList<>();
            while(child.size()<Parameter.SIZE_POPULATION){
                child.addAll(crossover_mutation());
            }
            p.pop.addAll(child);
            p.calScalarFitness_SkillFactor();
            p.selection();
            generation++;
        }
        for(int i=0; i<p.pop.size(); i++){
            if(p.pop.get(i).skillFactor==0){
                best.add(p.pop.get(i));
                break;
            }
        }
        for(int i=0; i<p.pop.size(); i++){
            if(p.pop.get(i).skillFactor==1){
                best.add(p.pop.get(i));
                break;
            }
        }
        
        return best;
        
    }
    public ArrayList<Individual> crossover_mutation(){
        ArrayList<Individual> off_spring;
        int a = Parameter.rand.nextInt(Parameter.SIZE_POPULATION);
        int b = Parameter.rand.nextInt(Parameter.SIZE_POPULATION);
        while(a==b){
            b = Parameter.rand.nextInt(Parameter.SIZE_POPULATION);
        }
        Individual parent1 = p.pop.get(a);
        Individual parent2 = p.pop.get(b);
        if(parent1.skillFactor == parent2.skillFactor|| Parameter.rand.nextDouble() < Parameter.rmp){
            off_spring = p.crossover(parent1, parent2);
            for(Individual ind:off_spring){
                if(Parameter.rand.nextDouble() >0.5){
                    ind.skillFactor =0;
                    ind.CalFitness0();
                    ind.SetFitness1(Parameter.LARGE_NUMBER);
                }else{
                    ind.skillFactor =1;
                    ind.CalFitness1();
                    ind.SetFitness0(Parameter.LARGE_NUMBER);
                }
            }
        }else{
            off_spring = new ArrayList<>();
            off_spring.add(p.mutation(parent1));
            off_spring.add(p.mutation(parent2));
            off_spring.get(0).skillFactor = parent1.skillFactor;
            off_spring.get(1).skillFactor = parent1.skillFactor;
            for(Individual ind : off_spring){
                if(ind.skillFactor ==0){
                    ind.CalFitness0();
                    ind.SetFitness1(Parameter.LARGE_NUMBER);
                }else{
                    ind.CalFitness1();
                    ind.SetFitness0(Parameter.LARGE_NUMBER);
                }
            }
            
        }
        return off_spring;
    }
    
}
