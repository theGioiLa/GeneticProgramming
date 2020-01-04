/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hust.mso.gaframework;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author thang.tb153544
 */
public class Main {
    public static void main(String[] args) {
        // READ file
        String path = "../data/eli51.tsp";
        readFile(path);

        GA al = new GA();
        Individual best = al.run();
        System.err.println(best);
    }

    public static void readFile(String path) {
        GA.cities = new ArrayList<>();
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(path));
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
                City city = new City(id, x, y);
                GA.cities.add(city);

                line = reader.readLine();
            }
            reader.close();

            GA.SIZE_GENE = GA.cities.size();
            GA.weight = new double[GA.SIZE_GENE][GA.SIZE_GENE];
            for (int i = 0; i < GA.SIZE_GENE; i++) {
                for (int j = i+1; j < GA.SIZE_GENE; j++) {
                    double dx = GA.cities.get(i).x - GA.cities.get(j).x;
                    double dy = GA.cities.get(i).y - GA.cities.get(j).y;
                    double distance = Math.sqrt(dx*dx + dy*dy);
                    GA.weight[i][j] = distance;
                    GA.weight[j][i] = distance;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
