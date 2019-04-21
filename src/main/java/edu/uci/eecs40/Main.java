package edu.uci.eecs40;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        File warriorOne = new File(args[0]);
        File warriorTwo = new File(args[1]);

        // Call your functions here to play the game
        // Player.playGame(warriorOne, warriorTwo)

        // I am invoking dummy simulator that
        // will print the files sequentially
        // delete this invocation
        dummySimulator(warriorOne, warriorTwo);

    }

    private static void dummySimulator(File warriorX, File warriorY) {
        File[] warriors = {warriorX, warriorY};

        for (File warrior : warriors) {
            BufferedReader reader;
            try {
                reader = new BufferedReader(new FileReader(warrior));
                String line = reader.readLine();
                while (line != null) {
                    System.out.println(line);
                    // read next line
                    line = reader.readLine();
                    System.out.println(line);
                }
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
