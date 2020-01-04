/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hust.mso.gaframework;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

/**
 *
 * @author thang.tb153544
 */
public class Population {
    ArrayList<Individual> pop = new ArrayList<>();
    int size;

    public Population() {
        this.size = GA.SIZE_POPULATION;
    }

    public void Init() {
        for (int i = 0; i < size; i++) {
            Individual ind = new Individual();
            ind.Init();
            ind.setFitness();
            pop.add(ind);
        }
    }

    public ArrayList<Individual> crossover(Individual parent1, Individual parent2) {
        System.out.println(correlate(parent1, parent2));

        ArrayList<Individual> children;

        children = crossOX(parent1, parent2);
        // children = crossPMX(parent1, parent2);

        for (Individual child : children) {
            child.setFitness();
            child.growth = 1;
        }

        // System.out.println("--------------------------------");

        return children;
    }

    public Individual mutation(Individual parent) {
        // dot bien
        Individual child = new Individual();
        int a = GA.rand.nextInt(GA.SIZE_GENE);
        int b = GA.rand.nextInt(GA.SIZE_GENE);
        while (b == a)
            b = GA.rand.nextInt(GA.SIZE_GENE);

        ArrayList<Integer> genes = parent.getGenes();
        // Collections.swap(genes, a, b);
        int tmp = genes.get(a);
        genes.set(a, genes.get(b));
        genes.set(b, tmp);

        child.growth = 2;
        child.setGenes(genes);
        child.setFitness();
        return child;
    }

    public void selection() {
        Collections.sort(pop);
        ArrayList<Individual> _pop = new ArrayList<>();
        for (int i = 0; i < GA.SIZE_POPULATION; i++) {
            _pop.add(pop.get(i));
        }

        pop = _pop;
    }

    public Individual getBest() {
        Individual best = pop.get(0); // vi du
        return best;
    }

    private ArrayList<Individual> crossOX(Individual parent1, Individual parent2) {
        ArrayList<Individual> children = new ArrayList<>();

        int left = GA.rand.nextInt(GA.SIZE_GENE);
        int right = GA.rand.nextInt(GA.SIZE_GENE);
        while (right == left)
            right = GA.rand.nextInt(GA.SIZE_GENE);
        if (left > right) {
            left = right + left;
            right = left - right;
            left = left - right;
        }

        // System.out.println(left + " " + right);

        boolean fixedGenes1[], fixedGenes2[];
        fixedGenes1 = new boolean[GA.SIZE_GENE];
        fixedGenes2 = new boolean[GA.SIZE_GENE];
        Arrays.fill(fixedGenes1, false);
        Arrays.fill(fixedGenes2, false);

        for (int i = left; i < right + 1; i++) {
            fixedGenes1[parent1.genes.get(i)] = true;
            fixedGenes2[parent2.genes.get(i)] = true;
        }

        ArrayList<Integer> chromo1, chromo2;
        chromo1 = parent1.getGenes();
        chromo2 = parent2.getGenes();

        makeChromoOX(chromo1, parent2, fixedGenes1, left, right);
        makeChromoOX(chromo2, parent1, fixedGenes2, left, right);

        Individual o1, o2;
        o1 = new Individual();
        o2 = new Individual();
        o1.setGenes(chromo1);
        o2.setGenes(chromo2);

        children.add(o1);
        children.add(o2);

        return children;
    }

    private void makeChromoOX(ArrayList<Integer> chromo, Individual parent, boolean fixedGenes[], final int left, final int right) {
        int it1 = 1, it2 = 0;
        int currId = 0;

        while (it2 != GA.SIZE_GENE - right + left - 1) {
            int index = (right + it1) % GA.SIZE_GENE;
            int value = parent.genes.get(index);
            it1++;
            if (!fixedGenes[value]) {
                it2++;
                currId = (right + it2) % GA.SIZE_GENE;
                chromo.set(currId, value);
            }
        }
    }

    private ArrayList<Individual> crossPMX(Individual parent1, Individual parent2) {
        ArrayList<Individual> children = new ArrayList<>();

        int left = GA.rand.nextInt(GA.SIZE_GENE);
        int right = GA.rand.nextInt(GA.SIZE_GENE);
        while (right == left)
            right = GA.rand.nextInt(GA.SIZE_GENE);
        if (left > right) {
            left = right + left;
            right = left - right;
            left = left - right;
        }

        // System.out.println(left + " " + right);

        boolean fixedGenes1[], fixedGenes2[];
        fixedGenes1 = new boolean[GA.SIZE_GENE];
        fixedGenes2 = new boolean[GA.SIZE_GENE];
        Arrays.fill(fixedGenes1, false);
        Arrays.fill(fixedGenes1, false);

        HashMap<Integer, Integer> map1, map2;
        map1 = new HashMap<>();
        map2 = new HashMap<>();

        for (int i = left; i < right + 1; i++) {
            int p1, p2;
            p1 = parent1.genes.get(i);
            p2 = parent2.genes.get(i);

            fixedGenes1[p1] = true;
            fixedGenes2[p2] = true;
            map1.put(p1, p2);
            map2.put(p2, p1);
        }

        ArrayList<Integer> chromo1 = new ArrayList<>();
        ArrayList<Integer> chromo2 = new ArrayList<>();

        for (int i = 0; i < GA.SIZE_GENE; i++) {
            if (i < left || i > right) {
                chromo1.add(-1);
                chromo2.add(-1);
            } else {
                chromo1.add(parent1.genes.get(i));
                chromo2.add(parent2.genes.get(i));
            }
        }

        makeChromoPMX(chromo1, parent2, fixedGenes1, map1);
        makeChromoPMX(chromo2, parent1, fixedGenes2, map2);

        Individual o1, o2;
        o1 = new Individual();
        o2 = new Individual();
        o1.setGenes(chromo1);
        o2.setGenes(chromo2);

        children.add(o1);
        children.add(o2);

        return children;
    }

    private void makeChromoPMX(ArrayList<Integer> chromo, Individual parent, boolean fixedGenes[],
            HashMap<Integer, Integer> map) {
        for (int i = 0; i < GA.SIZE_GENE; i++) {
            if (chromo.get(i) == -1) {
                int value = parent.genes.get(i);
                while (fixedGenes[value]) {
                    value = map.get(value);
                }

                chromo.set(i, value);
            }
        }
    }

    private double correlate(Individual indv1, Individual indv2) {
        double norm1 = 0, norm2 = 0, dot = 0; 

        for (int i = 0; i < GA.SIZE_GENE; i++) {
            norm1 += indv1.genes.get(i) * indv1.genes.get(i);
            norm2 += indv2.genes.get(i) * indv2.genes.get(i);

            dot += indv1.genes.get(i) * indv2.genes.get(i);
        }

        return dot / (Math.sqrt(norm1) * Math.sqrt(norm2));
    }

    @Override
    public String toString() {
        Individual best = getBest();
        return String.format("Best: %f", best.getFitness());
    }

}
