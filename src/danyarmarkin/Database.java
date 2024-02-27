package danyarmarkin;

import java.io.*;
import java.util.Scanner;

public class Database {
    public static void saveResult(int[] result) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("db/score.txt"));
            for (int r : result) {
                writer.write(String.valueOf(r));
                writer.write("\n");
            }
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static int[] getResult() {
        try {
            Scanner scanner = new Scanner(new File("db/score.txt"));
            int[] r = new int[2];
            for (int i = 0; i < 2; i++) {
                r[i] = scanner.nextInt();
            }
            return r;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
