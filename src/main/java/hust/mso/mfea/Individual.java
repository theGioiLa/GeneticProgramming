package hust.mso.mfea;

import java.util.ArrayList;
import java.util.Collections;

public class Individual implements Comparable<Individual> {
    int id;
    double fitness[] = new double[2];
    public int rank[] = new int[2];
    double saclarFitness = 0;
    int skillFactor = 0;
    ArrayList<Integer> genes;
    ArrayList<Integer> genes_in_task_0;
    ArrayList<Integer> genes_in_task_1;

    public Individual() {
        id = Parameter.counterID++;
    }

    public void init() {
        genes = new ArrayList<>();
        for (int i = 0; i < Parameter.SIZE_GENE; i++) {
            genes.add(i);
        }

        Collections.shuffle(genes, Parameter.rand);
        calFitness0();
        calFitness1();
    }

    public ArrayList<Integer> getGenes() {
        ArrayList<Integer> _genes = new ArrayList<>();
        for (int i = 0; i < Parameter.SIZE_GENE; i++) {
            _genes.add(this.genes.get(i));
        }
        return _genes;
    }

    public void calFitness0() {
        decode(0);
        _fitness(0);
    }

    public void calFitness1() {
        decode(1);
        _fitness(1);
    }

    private void _fitness(int taskID) {
        ArrayList<Integer> _genes;
        if (taskID == 0)
            _genes = this.genes_in_task_0;
        else
            _genes = this.genes_in_task_1;

        double[][] weight = Parameter.WEIGHTS.get(taskID);
        final int _SIZE_GENE = Parameter.PARTIAL_SIZE_GENE.get(taskID);

        this.fitness[taskID] = 0;
        for (int i = 0; i < _SIZE_GENE; i++) {
            int cityId = _genes.get(i);
            int nextCityId = _genes.get((i + 1) % _SIZE_GENE);
            this.fitness[taskID] += weight[cityId][nextCityId];
        }
    }

    public void decode(int task) {
        // String str = "Gen " + task + ": ";
        // String org = "GEN: ";
        if (task == 0) {
            genes_in_task_0 = new ArrayList<Integer>();
            for (int gene : genes) {
                // org += gene + " ";
                if (gene < Parameter.PARTIAL_SIZE_GENE.get(0)) {
                    genes_in_task_0.add(gene);
                    // str += gene + " ";
                }
            }
        } else if (task == 1) {
            genes_in_task_1 = new ArrayList<Integer>();
            for (int gene : genes) {
                // org += gene + " ";
                if (gene < Parameter.PARTIAL_SIZE_GENE.get(1)) {
                    genes_in_task_1.add(gene);
                    // str += gene + " ";
                }
            }
        }

        // System.out.println(org);
        // System.out.println(str);
    }

    public void setFitness(int taskID, double b) {
        fitness[taskID] = b;
    }

    @Override
    public String toString() {
        ArrayList<Integer> _genes;
        if (skillFactor == 0)
            _genes = genes_in_task_0;
        else
            _genes = genes_in_task_1;

        String str = "NST: ";
        for (Integer cityId : _genes) {
            str += cityId + " ";
        }

        str += "\nFitness: " + fitness[skillFactor];
        str += "\nScalar Fitness: " + saclarFitness;
        str += "\nSkill Factor: " + skillFactor;
        // str += "\nMAX: " + Collections.max(genes);

        return str;
    }

    @Override
    public int compareTo(Individual ind) {
        if (saclarFitness < ind.saclarFitness)
            return 1;
        if (saclarFitness > ind.saclarFitness)
            return -1;
        return 0;
    }
}