package hust.mso.mfea;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Parameter {
    public static int CONDITION_STOP = 100;
    public static ArrayList<Integer> PARTIAL_SIZE_GENE;
    public static int SIZE_GENE;
    public static int SIZE_POPULATION = 100;
    public static double rmp = 0.5;
    public static Random rand;
    public static int LARGE_NUMBER = 1000000;
    public static int seed = 0;
    public static int ID = 0;
    public static ArrayList<double[][]> WEIGHTS;


    public static void init(int nOfTask, int _seed) {
        seed = _seed;
        PARTIAL_SIZE_GENE = new ArrayList<>(nOfTask);
        WEIGHTS = new ArrayList<>(nOfTask);
        rand = new Random(seed);
    }

    public static void setSizeGene() {
        SIZE_GENE = Collections.max(PARTIAL_SIZE_GENE);
    }
}
