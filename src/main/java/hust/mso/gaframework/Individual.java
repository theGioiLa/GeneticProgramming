/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hust.mso.gaframework;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author thang.tb153544
 */
public class Individual implements Comparable<Individual> {
    int size_gene = 0;
    ArrayList<Integer> genes;
    double fitness;
    int growth;

    public Individual() {
        size_gene = GA.SIZE_GENE;
    }

    public void Init() {
        // init
        growth = 0;
        genes = new ArrayList<>(size_gene);
        for (int i = 0; i < this.size_gene; i++) {
            genes.add(i);
        }

        Collections.shuffle(this.genes, GA.rand);
    }

    public ArrayList<Integer> getGenes() {
        ArrayList<Integer> _genes = new ArrayList<>(size_gene);
        // Collections.copy(genes, this.genes);
        for (int i = 0; i < size_gene; i++) {
            _genes.add(this.genes.get(i));
        }
        return _genes;
    }

    public void setGenes(ArrayList<Integer> genes) {
        this.genes = genes;
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness() {
        // Tinh fitness
        this.fitness = 0;
        for (int i = 0; i < this.size_gene - 1; i++) {
            int cityId = this.genes.get(i);
            int nextCityId = this.genes.get(i + 1);
            this.fitness += GA.weight[cityId][nextCityId];
        }

        int lastCityId = this.genes.get(this.size_gene - 1);
        int startCityId = this.genes.get(0);
        this.fitness += GA.weight[lastCityId][startCityId];
    }

    @Override
    public String toString() {
        String str = "NST: ";
        for (Integer cityId : genes) {
            str += cityId + " ";
        }

        str += "\nFitness: " + fitness;
        str += "\nGrowth: " + growth;
        // str += "\nMAX: " + Collections.max(genes);

        return str;
    }

    @Override
    public int compareTo(Individual ind) {
        if (fitness < ind.getFitness()) return -1;
        if (fitness > ind.getFitness()) return 1;
        return 0;
    }

}
