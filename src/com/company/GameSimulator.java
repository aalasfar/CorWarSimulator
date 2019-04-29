package com.company;
import java.util.*;
import java.util.regex.Pattern;

public class GameSimulator {


    ArrayList<String> Player1 = new ArrayList<String>();
    ArrayList<String> Player2 = new ArrayList<String>();
    OpenFile f = new OpenFile();

    final int MAX = 8000;
    final int MIN = 0;
    boolean noWinner = true;
    int initialPos2 = 0;
    //File fileInput = openFile();
    //printFileContents(fileInput);
    //5 new arrays including to have each input checked
    String[] myArray = new String[MAX];
    /*this can use a different MAX value since it only ever uses
    the amount of lines that are in the txt file  */
    String[] instruction = new String[MAX];
    //this instruction array is takes over myArray with just the instruction as string
    String[] modeA = new String[MAX];
    String[] modeB = new String[MAX];
    //allows us to see if the value has a mode enabled
    int[] Afield = new int[MAX];
    int[] Bfield = new int[MAX];
    //these keep the values as ints


    public void mySim(OpenFile f) {

        Player1 = f.Openfile("Warrior1.txt");
        Player2 = f.Openfile("Warrior2.txt");

        //initialize to DAT 0
        for (int i = 0; i < instruction.length; i++) {
            instruction[i] = "DAT";
            modeA[i] = " ";
            Afield[i] = 0;
            modeB[i] = " ";
            Bfield[i] = 0;
        }

        //generating random number between MAX and MIN for warrior1
        int initialPos = (int) ((Math.random() * (MAX + 1 - MIN)) + MIN); // generate random number
        //int j = initialPos; // used for assigning Player1 into the myArray (memory array)
        System.out.println("\nWarrior 1 ");
        System.out.println("initialPos = " + initialPos);
        int sizeOfWarrior1 = PlacingInput(Player1, myArray, initialPos);
        ParsingInput(sizeOfWarrior1, initialPos, myArray, instruction, modeA, modeB, Afield, Bfield);
        PrintWarriors(instruction, initialPos, modeA, modeB, Afield, Bfield);

        checkPosition(initialPos);
        System.out.println("\nWarrior 2 ");
        System.out.println("initialPos2 = "+ initialPos2);
        int sizeOfWarrior2 = PlacingInput(Player2, myArray, initialPos2);
        ParsingInput(sizeOfWarrior2, initialPos2, myArray, instruction, modeA, modeB, Afield, Bfield);
        PrintWarriors(instruction, initialPos2, modeA, modeB, Afield, Bfield);
        //int count = 0;  //count how many times iterated in Player1 for tracking size

        System.out.println("noWinner " + noWinner);
        RunSimulation(instruction, modeA, modeB, Afield, Bfield, initialPos, initialPos2);



        }

    //method for placing warrior
    public int PlacingInput(ArrayList<String> player, String[] memory, int position) {
        ArrayList<String> p = new ArrayList<String>();
        p = player;
        int k = 0;
        boolean NotDoneInput = true;
        while (NotDoneInput) {
            memory[position] = p.get(k);
            position++;
            k++;
            if (position >= MAX) {
                position = 0;
            }
            if (k >= p.size()) {
                NotDoneInput = false;
            }
        }
        return k;
    }
    //method for executing the instructions in memory
    //inputs the program into the main array so i can then be executed
    public void ParsingInput(int count, int position, String[] array, String[] instruction, String[] mA, String[] mB, int[] fA, int[] fB) {
        for (int i = 0; i < count; i++) {
            /*uses count from previous loop of getting each line to know how many lines to
            change  */
            int x = (position + i + MAX) % MAX; //makes sure each array is using same position
            String s = myArray[x];
            instruction[x] = myArray[x].substring(0, 3); // gets instruction
            // need to check for DAT input since the number is in the B field
            //code is same as else statement but doesn't check mode and only changes B value
            if (instruction[x].equals("DAT")) {
                String str = s.replaceAll("[^-?0-9]+", " ");
                String strnum[] = str.split(" ");
                char[] arr = s.toCharArray();
                int[] numbers = new int[strnum.length];
                for (int u = 0; u < strnum.length - 1; u++) {
                    numbers[u] = Integer.parseInt(strnum[u + 1]);
                }
                Bfield[x] = numbers[0];
            } else {
                String str = s.replaceAll("[^-?0-9]+", " ");
                //gets numbers from the string
                String strnum[] = str.split(" ");
                String[] str2 = new String[3];
                str2 = s.split(" ");
                //splits string into 3 to get the mode
                int[] numbers = new int[strnum.length];
                //array to change the numbers from string to int
                for (int u = 0; u < strnum.length - 1; u++) {
                    numbers[u] = Integer.parseInt(strnum[u + 1]);
                }
                //checks if the string has a mode for A
                if (str2[1].substring(0, 1).equals("#") || str2[1].substring(0, 1).equals("*")
                        || str2[1].substring(0, 1).equals("@")) {
                    modeA[x] = str2[1].substring(0, 1);
                }
                //checks if the string has mode for B
                if (str2.length == 3) {
                    if (str2[2].substring(0, 1).equals("#") || str2[2].substring(0, 1).equals("*")
                            || str2[2].substring(0, 1).equals("@")) {
                        modeB[x] = str2[2].substring(0, 1);
                    }
                }
                //assigns the numbers to their appropriate field
                Afield[x] = numbers[0];
                Bfield[x] = numbers[1];
            }
        }
    }

    public void PrintWarriors(String[] inst, int position, String[] mA, String[] mB, int[] fA, int[] fB) {
        for (int i = -5; i < 6; i++) {
            int k = (i + position + MAX) % MAX;
            //System.out.println("k = "+k);
            if (k == position) {
                System.out.println(k + ":" + inst[k] + " " + mA[k] + fA[k] + "," + mB[k] + fB[k]);
            } else {
                System.out.println(k + ":" + inst[k] + " " + mA[k] + fA[k] + "," + mB[k] + fB[k]);
            }
        }
    }

//method for checking if the initial position of second warrior is valid
    public void checkPosition(int x1) {
        boolean notValidPos = true;
        while(notValidPos) {
            int x2 = (int) ((Math.random() * (MAX + 1 - MIN)) + MIN);
            if (x2 > x1) {
                if (x2 - x1 > Player2.size()) {
                    continue;
                }
            } else {
                initialPos2 = x2;
                break;
            }
        }
    }

    //method for keeping index within limits
    public int KeepIndexWithinLimits(int index){
        return index % MAX;
    }

    public void RunSimulation(String[] operation, String[] mode_A, String[] mode_B, int[] f_A, int[] f_B, int IndexPlayer1, int IndexPlayer2){
        int j;
        int GameCounter = 1;    //for alternating between warriors; warrior 1 is odd and 2 is even
        Instructions ObjOp = new Instructions(operation, mode_A, mode_B, f_A, f_B);
        while(noWinner) {
               //checking which warrior's turn
               if(GameCounter % 2 != 0){
                   IndexPlayer1 = KeepIndexWithinLimits(IndexPlayer1);
                   System.out.println("Index Player player checker " + IndexPlayer1);

            j = IndexPlayer1;
                   IndexPlayer1++;
                   //System.out.println("\nwarrior 1 turn");
                  // System.out.println("j = "+j);
               }
               else{
                   IndexPlayer2 = KeepIndexWithinLimits(IndexPlayer2);
                   j = IndexPlayer2;
                   IndexPlayer2++;
                  // System.out.println("\nwarrior 2 turn");
                   //System.out.println("j = "+j);
               }

               //checking instructions
               if(operation[j].equals("DAT")) {
                   ObjOp.DAT(j); //instantiate DAT object
                    System.out.println("Reach a DAT");
                    break;
                }
                else if(operation[j].equals("MOV")){
                    ObjOp.MOV(j); //instantiate MOV object
                }
                else if(operation[j].equals("ADD")){
                    ObjOp.ADD(j);
               }else if(operation[j].equals("SUB")){
                    ObjOp.SUB(j);
               }else if(operation[j].equals("JMP")){
                    System.out.println("GameCounter " + GameCounter);
                   if(GameCounter % 2 == 1){    IndexPlayer1 = ObjOp.JMP(j); }
                   else{    IndexPlayer2 = ObjOp.JMP(j);}
                }
                else if(operation[j].equals("JMZ")){
                System.out.println("GameCounter " + GameCounter);
                if(GameCounter % 2 == 1){    IndexPlayer1 = ObjOp.JMZ(j); }
                else{    IndexPlayer2 = ObjOp.JMZ(j);}
                }else{
                  System.out.println("breaking from while");
                  break;}
           // System.out.println("\nWarrior 1");
           // PrintWarriors(operation, IndexPlayer1, mode_A, mode_B, f_A, f_B);
                GameCounter++;

             // if(GameCounter == 4){ break;}
           }
        System.out.println("\nWarrior 1");
        PrintWarriors(operation, IndexPlayer1, mode_A, mode_B, f_A, f_B);

       System.out.println("\nWarrior 2");
       PrintWarriors(operation, IndexPlayer2, mode_A, mode_B, f_A, f_B);
       if(GameCounter % 2 == 0){
           System.out.println("Warrior 1 is the Winner!");
       }
       else{
           System.out.println("Warrior 2 is the Winner!");
       }


    }




}