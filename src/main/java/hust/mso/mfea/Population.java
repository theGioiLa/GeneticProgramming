package hust.mso.mfea;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Population {
    ArrayList<Individual> pop = new ArrayList<>();

    public void init() {
        for (int i = 0; i < Parameter.SIZE_POPULATION; i++) {
            Individual ind = new Individual();
            ind.init();
            pop.add(ind);
        }
    }

    public void selection() {
        Collections.sort(pop);
        ArrayList<Individual> _pop = new ArrayList<>();
        boolean bk[] = new boolean[Parameter.counterID];
        Arrays.fill(bk, false);
        System.out.println(Parameter.counterID + " " + pop.size());
        for (int i = 0; i < pop.size(); i++) {
            if (!bk[pop.get(i).id]) {
                _pop.add(pop.get(i));
                bk[pop.get(i).id] = true;
            }
            if (_pop.size() >= Parameter.SIZE_POPULATION)
                break;
        }

        pop = _pop;
    }

    public void calScalarFitness_SkillFactor() {
        ArrayList<ObjectSort> pop_by_task = new ArrayList<>();
        for (Individual ind : pop) {
            pop_by_task.add(new ObjectSort(ind.id, ind.fitness[0], ind.fitness[1]));
        }

        SortByFitness0 sortByFitness0 = new SortByFitness0();
        Collections.sort(pop_by_task, sortByFitness0);
        for (int i = 0; i < pop.size(); i++) {
            for (int j = 0; j < pop_by_task.size(); j++) {
                if (pop_by_task.get(j).id == pop.get(i).id) {
                    pop.get(i).rank[0] = j + 1;
                    break;
                }
            }
        }

        SortByFitness1 sortByFitness1 = new SortByFitness1();
        Collections.sort(pop_by_task, sortByFitness1);
        for (int i = 0; i < pop.size(); i++) {
            for (int j = 0; j < pop_by_task.size(); j++) {
                if (pop_by_task.get(j).id == pop.get(i).id) {
                    pop.get(i).rank[1] = j + 1;
                    break;
                }
            }
        }

        for (Individual ind : pop) {
            if (ind.rank[0] < ind.rank[1])
                ind.skillFactor = 0;
            else if (ind.rank[0] > ind.rank[1])
                ind.skillFactor = 1;
            else {
                double prob = Parameter.rand.nextDouble();
                if (prob > 0.5)
                    ind.skillFactor = 0;
                else
                    ind.skillFactor = 1;
            }
            ind.saclarFitness = 1.0 / ind.rank[ind.skillFactor];
        }
    }

    public Individual mutation(Individual parent) {
        Individual ind = new Individual();
        final int _SIZE_GENE = Parameter.SIZE_GENE;

        int a = Parameter.rand.nextInt(_SIZE_GENE);
        int b = Parameter.rand.nextInt(_SIZE_GENE);
        while (b == a)
            b = Parameter.rand.nextInt(_SIZE_GENE);

        ind.genes = parent.getGenes();
        int tmp = ind.genes.get(a);
        ind.genes.set(a, ind.genes.get(b));
        ind.genes.set(b, tmp);

        return ind;
    }

    public ArrayList<Individual> crossover(Individual parent1, Individual parent2) {
        ArrayList<Individual> off_spring = new ArrayList<Individual>();
        Individual child1 = new Individual();
        Individual child2 = new Individual();

        final int _SIZE_GENE = Parameter.SIZE_GENE;
        int left = Parameter.rand.nextInt(_SIZE_GENE);
        int right = Parameter.rand.nextInt(_SIZE_GENE);
        while (right == left)
            right = Parameter.rand.nextInt(_SIZE_GENE);
        if (left > right) {
            left = right + left;
            right = left - right;
            left = left - right;
        }

        // System.out.println(left + " " + right);

        boolean fixedGenes1[], fixedGenes2[];
        fixedGenes1 = new boolean[_SIZE_GENE];
        fixedGenes2 = new boolean[_SIZE_GENE];
        Arrays.fill(fixedGenes1, false);
        Arrays.fill(fixedGenes2, false);

        for (int i = left; i < right + 1; i++) {
            fixedGenes1[parent1.genes.get(i)] = true;
            fixedGenes2[parent2.genes.get(i)] = true;
        }

        child1.genes = parent1.getGenes();
        child2.genes = parent2.getGenes();

        makeChromoOX(child1.genes, parent2, fixedGenes1, left, right);
        makeChromoOX(child2.genes, parent1, fixedGenes2, left, right);

        off_spring.add(child1);
        off_spring.add(child2);
        return off_spring;
    }

    private void makeChromoOX(ArrayList<Integer> chromo, Individual parent, boolean fixedGenes[], final int left,
            final int right) {
        int it1 = 1, it2 = 0;
        int currId = 0;
        final int _SIZE_GENE = Parameter.SIZE_GENE;

        while (it2 != _SIZE_GENE - right + left - 1) {
            int index = (right + it1) % _SIZE_GENE;
            int value = parent.genes.get(index);
            it1++;
            if (!fixedGenes[value]) {
                it2++;
                currId = (right + it2) % _SIZE_GENE;
                chromo.set(currId, value);
            }
        }
    }
}
