package com.company;
import java.util.*;
import java.util.regex.Pattern;

public class GameSimulator {


    ArrayList<String> Player1 = new ArrayList<String>();
    OpenFile f = new OpenFile();

    final int MAX = 100;
    final int MIN = 0;
    boolean noWinner = true;
    boolean NotDoneInput = true;
    //File fileInput = openFile();
    //printFileContents(fileInput);
    //5 new arrays including to have each input checked
    String[] myArray = new String[MAX];
    /*this can use a different MAX value since it only ever uses
    the amount of lines that are in the txt file  */
    String[] instuction = new String[MAX];
    //this instruction array is takes over myArray with just the instruction as string
    String[] modeA = new String[MAX];
    String[] modeB = new String[MAX];
    //allows us to see if the value has a mode enabled
    int[] Afield = new int[MAX];
    int[] Bfield = new int[MAX];
    //these keep the values as ints

    public void mySim(OpenFile f) {

        Player1 = f.Openfile();

        //initialize to DAT 0
        for (int i = 0; i < instuction.length; i++) {
            instuction[i] = "DAT";
            modeA[i] = " ";
            Afield[i]= 0;
            modeB[i] = " ";
            Bfield[i] = 0;
        }

        //generating random number between MAX and MIN
        int initialPos = (int) ((Math.random() * (MAX + 1 - MIN)) + MIN); // generate random number
        int j = initialPos; // used for assigning Player1 into the myArray (memory array)
        System.out.println("initialPos = " + initialPos);
        int count = 0;  //count how many times iterated in Player1 for tracking size
        while (NotDoneInput) {
            myArray[j] = Player1.get(count);
            j++;
            count++;
            if (j >= MAX) {
                j = 0;
            }
            if (count >= Player1.size()) {
                NotDoneInput = false;
            }
        }
        //inputs the program into the main array so i can then be excicuted
        for ( int i = 0; i < count ; i++) {
            /*uses count from previous loop of getting each line to know how many lines to
            change  */
            int x = (initialPos +i+MAX)% MAX; //makes sure each array is using same position
            String s = myArray[x];
            instuction[x] = myArray[x].substring(0,3); // gets instruction
            // need to check for DAT input since the number is in the B field
            //code is same as else statement but doesn't check mode and only changes B value
            if (instuction[x].equals("DAT")){
                String str = s.replaceAll("[^-?0-9]+", " ");
                String strnum[] = str.split(" ");
                char[] arr = s.toCharArray();
                int[] numbers = new int[strnum.length];
                for (int u = 0; u < strnum.length - 1; u++) {
                    numbers[u] = Integer.parseInt(strnum[u + 1]);
                }
                Bfield[x] = numbers[0];
            }
            else {
                String str = s.replaceAll("[^-?0-9]+", " ");
                //gets numbers from the string
                String strnum[] = str.split(" ");
                String[]  str2 =  new String[3];
                str2 = s.split(" ");
                //splits string into 3 to get the mode
                int[] numbers = new int[strnum.length];
                //array to change the numbers from string to int
                for (int u = 0; u < strnum.length - 1; u++) {
                    numbers[u] = Integer.parseInt(strnum[u + 1]);
                }
                //checks if the string has a mode for A
                if (str2[1].substring(0,1).equals("#")|| str2[1].substring(0,1).equals("*")
                        || str2[1].substring(0,1).equals("@")){
                    modeA[x] = str2[1].substring(0,1);
                }
                //checks if the string has mode for B
                if (str2.length == 3) {
                    if (str2[2].substring(0, 1).equals("#") || str2[2].substring(0, 1).equals("*")
                            || str2[2].substring(0, 1).equals("@")) {
                        modeB[x] = str2[2].substring(0, 1);
                    }
                }
                //assigns the numbers to their appropiate field
                Afield[x] = numbers[0];
                Bfield[x] = numbers[1];
            }
        }


        //outputting current location with 5 before and after
        for (int i = -5; i < 6; i++) {
            int k = (i + initialPos + MAX) % MAX;
            //System.out.println("k = "+k);
            if (k == initialPos) {
                System.out.println(k + ":" + instuction[k]+ " " + modeA[k]+Afield[k]+ "," + modeB[k]+Bfield[k]);
            }
            else {  System.out.println(k + ":" + instuction[k]+ " " + modeA[k]+Afield[k]+ "," + modeB[k]+Bfield[k]);    }
        }
    }
}
