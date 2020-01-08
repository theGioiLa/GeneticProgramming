package hust.mso.mfea;

import java.util.ArrayList;

public class MFEA {
    private Population _pop = new Population();

    public ArrayList<Individual> run() {
        ArrayList<Individual> best = new ArrayList<>();
        _pop.init();
        _pop.calScalarFitness_SkillFactor();
        int generation = 1;

        while (generation < Parameter.CONDITION_STOP) {
            ArrayList<Individual> child = new ArrayList<>();
            while (child.size() < Parameter.SIZE_POPULATION) {
                child.addAll(crossover_mutation());
            }
            _pop.pop.addAll(child);
            _pop.calScalarFitness_SkillFactor();
            _pop.selection();
            generation++;
        }

        for (Individual ind : _pop.pop) {
            System.out.println(ind.saclarFitness + " " + ind.fitness[0] + " " + ind.fitness[1] + " " + ind.skillFactor);
        }

        // for (int i = 0; i < _pop.pop.size(); i++) {
        // if (_pop.pop.get(i).skillFactor == 0) {
        // best.add(_pop.pop.get(i));
        // break;
        // }
        // }
        // for (int i = 0; i < _pop.pop.size(); i++) {
        // if (_pop.pop.get(i).skillFactor == 1) {
        // best.add(_pop.pop.get(i));
        // break;
        // }
        // }

        return best;
    }

    public ArrayList<Individual> crossover_mutation() {
        ArrayList<Individual> off_spring;
        int a = Parameter.rand.nextInt(Parameter.SIZE_POPULATION);
        int b = Parameter.rand.nextInt(Parameter.SIZE_POPULATION);
        while (a == b) {
            b = Parameter.rand.nextInt(Parameter.SIZE_POPULATION);
        }
        Individual parent1 = _pop.pop.get(a);
        Individual parent2 = _pop.pop.get(b);
        if (parent1.skillFactor == parent2.skillFactor || Parameter.rand.nextDouble() < Parameter.rmp) {
            off_spring = _pop.crossover(parent1, parent2);
            for (Individual ind : off_spring) {
                if (Parameter.rand.nextDouble() > 0.5) {
                    ind.skillFactor = 0;
                    ind.calFitness0();
                    ind.setFitness(1, Parameter.LARGE_NUMBER);
                } else {
                    ind.skillFactor = 1;
                    ind.calFitness1();
                    ind.setFitness(0, Parameter.LARGE_NUMBER);
                }
            }
        } else {
            off_spring = new ArrayList<>();
            off_spring.add(_pop.mutation(parent1));
            off_spring.add(_pop.mutation(parent2));
            off_spring.get(0).skillFactor = parent1.skillFactor;
            off_spring.get(1).skillFactor = parent2.skillFactor;
            for (Individual ind : off_spring) {
                if (ind.skillFactor == 0) {
                    ind.calFitness0();
                    ind.setFitness(1, Parameter.LARGE_NUMBER);
                } else {
                    ind.calFitness1();
                    ind.setFitness(0, Parameter.LARGE_NUMBER);
                }
            }

        }
        return off_spring;
    }

}
