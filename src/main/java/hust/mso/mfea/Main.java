package hust.mso.mfea;

public class Main {

    public static void main(String[] args) {
        String filename_0 = "test_10_10_1000_7_6.idpc";
        String filename_1 = "test_15_15_3375_10_8.idpc";
        String set = "set1";
        int seed = 9;
        if (args.length > 0) {
            filename_0 = args[0];
            filename_1 = args[1];
            seed = Integer.parseInt(args[2]);
            set = args[3];
        }
        readFile("DATA\\" + set + "\\" + filename_0, 0);
        readFile("DATA\\" + set + "\\" + filename_1, 1);
        System.out.println("MFEA ");
    }
    public static void readFile(String filepath, int task){
        
    }
}
