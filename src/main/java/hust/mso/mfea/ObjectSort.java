package hust.mso.mfea;

import java.util.Comparator;

public class ObjectSort {
    int id;
    double fitness0, fitness1;

    public ObjectSort(int id, double fitness0, double fitness1) {
        this.id = id;
        this.fitness0 = fitness0;
        this.fitness1 = fitness1;
    }
}

class SortByFitness0 implements Comparator<ObjectSort> {

    @Override
    public int compare(ObjectSort o1, ObjectSort o2) {
        if (o1.fitness0 < o2.fitness0)
            return -1;
        if (o1.fitness0 > o2.fitness0)
            return 1;
        return 0;
    }

}

class SortByFitness1 implements Comparator<ObjectSort> {

    @Override
    public int compare(ObjectSort o1, ObjectSort o2) {
        if (o1.fitness1 < o2.fitness1)
            return -1;
        if (o1.fitness1 > o2.fitness1)
            return 1;
        return 0;
    }

}