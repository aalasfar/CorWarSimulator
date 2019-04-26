package com.company;

public class Main {

	/*import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
*/

	/*Maybe consider doing another array or two (a and b field) to keep track of values
	and to be make it easier to manipulate said values */
        public static void main(String[] args) {
            //defining objects
            OpenFile objP1 = new OpenFile();
            GameSimulator objSim = new GameSimulator();
            // ArrayList<String> Player2 = new ArrayList<>();

            //calling methods in objects
            objSim.mySim(objP1);
        }
}
