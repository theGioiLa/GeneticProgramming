package hust.mso.mfea;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        String datasets[] = { "eli51.tsp", "xqf131.tsp" };
        String set = "set1";
        int seed = 9;
        if (args.length > 0) {
            datasets[0] = args[0];
            datasets[1] = args[1];
            seed = Integer.parseInt(args[2]);
            set = args[3];
        }

        Parameter.init(datasets.length, seed);

        for (int taskID = 0; taskID < datasets.length; taskID++) {
            readFile("../data/" + set + "/" + datasets[taskID], taskID);
        }

        Parameter.setSizeGene();

        MFEA mfea = new MFEA();
        ArrayList<Individual> best = mfea.run();
        for (Individual individual : best) {
           System.out.println(individual); 
        }
    }

    public static void readFile(String filepath, int taskID) {
        ArrayList<Node> nodes = new ArrayList<>();
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(filepath));
            String line = reader.readLine();
            while (!line.equals("NODE_COORD_SECTION")) {
                line = reader.readLine();
            }

            line = reader.readLine();
            while (!line.equals("EOF")) {
                String str[] = line.split(" ");
                double x, y;
                int id = Integer.parseInt(str[0]) - 1;
                x = Double.parseDouble(str[1]);
                y = Double.parseDouble(str[2]);
                Node _node = new Node(id, x, y);
                nodes.add(_node);

                line = reader.readLine();
            }
            reader.close();

            final int SIZE_GENE = nodes.size();
            Parameter.PARTIAL_SIZE_GENE.add(taskID, SIZE_GENE);

            double weight[][] = new double[SIZE_GENE][SIZE_GENE];
            for (int i = 0; i < SIZE_GENE; i++) {
                for (int j = i + 1; j < SIZE_GENE; j++) {
                    double dx = nodes.get(i).x - nodes.get(j).x;
                    double dy = nodes.get(i).y - nodes.get(j).y;
                    double distance = Math.sqrt(dx * dx + dy * dy);
                    weight[i][j] = distance;
                    weight[j][i] = distance;
                }
            }

            Parameter.WEIGHTS.add(taskID, weight);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
