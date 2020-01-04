/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hust.mso.gaframework;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author thang.tb153544
 */
public class GA {
    public static int SIZE_POPULATION = 100;
    public static int CONDITION_STOP = 500;
    public static int SEED = 5;
    public static Random rand;
    public static int SIZE_GENE;
    Population pop = new Population();
    public static double pc = 0.5; // cross-over prob
    public static double pm = 0.2; // mutation prob

    public static ArrayList<City> cities;
    public static double weight[][];

    public GA() {
        rand = new Random(SEED);
    }

    public Individual run() {

        pop.Init();
        int generation = 1;

        while (generation < CONDITION_STOP) {
            ArrayList<Individual> child = new ArrayList<Individual>();
            while (child.size() < SIZE_POPULATION) {
                ArrayList<Individual> off_spring = crossover_mutation();
                for (Individual ind : off_spring) {
                    child.add(ind);
                }
            }
            // chon loc
            pop.pop.addAll(child);
            pop.selection();
            generation++;
        }

        return pop.getBest();
    }

    public ArrayList<Individual> crossover_mutation() {
        ArrayList<Individual> off_spring = new ArrayList<Individual>();
        int a = rand.nextInt(SIZE_POPULATION);
        int b = rand.nextInt(SIZE_POPULATION);
        while (b == a) {
            b = rand.nextInt(SIZE_POPULATION);
        }

        Individual ind1 = pop.pop.get(a);
        Individual ind2 = pop.pop.get(b);
        double r = rand.nextDouble();
        if (r < pm) {
            off_spring.add(pop.mutation(ind1));
            off_spring.add(pop.mutation(ind2));
        } else if (r < pc)
            off_spring = pop.crossover(ind1, ind2);

        return off_spring;
    }

}
