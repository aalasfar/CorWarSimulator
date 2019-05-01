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
        //PrintWarriors(instruction, initialPos, modeA, modeB, Afield, Bfield);

        checkPosition(initialPos);
        System.out.println("\nWarrior 2 ");
        System.out.println("initialPos2 = " + initialPos2);
        int sizeOfWarrior2 = PlacingInput(Player2, myArray, initialPos2);
        ParsingInput(sizeOfWarrior2, initialPos2, myArray, instruction, modeA, modeB, Afield, Bfield);
        PrintWarriors(instruction, initialPos, modeA, modeB, Afield, Bfield,initialPos2);
        //int count = 0;  //count how many times iterated in Player1 for tracking size

        // System.out.println("noWinner " + noWinner);
        System.out.println("\n\nWar Started!");
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
            /*if (instruction[x].equals("DAT")) {
                String str = s.replaceAll("[^-?0-9]+", " ");
                String strnum[] = str.split(" ");
                char[] arr = s.toCharArray();
                int[] numbers = new int[strnum.length];
                for (int u = 0; u < strnum.length - 1; u++) {
                    numbers[u] = Integer.parseInt(strnum[u + 1]);
                }
                Bfield[x] = numbers[0];*/
            //} else {
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
            if (!instruction[x].equals("DAT")) {
                if (str2[1].substring(0, 1).equals("#") || str2[1].substring(0, 1).equals("*")
                        || str2[1].substring(0, 1).equals("@")) {
                    modeA[x] = str2[1].substring(0, 1);
                }
            }
            //checks if the string has mode for B
            if (str2.length == 3) {
                if (str2[2].substring(0, 1).equals("#") || str2[2].substring(0, 1).equals("*")
                        || str2[2].substring(0, 1).equals("@")) {
                    modeB[x] = str2[2].substring(0, 1);
                }
            }
            //assigns the numbers to their appropriate field
            if (!instruction[x].equals("DAT")) {
                Afield[x] = numbers[0];
                Bfield[x] = numbers[1];
            } else {
                Afield[x] = 0;
                Bfield[x] = numbers[0];
                modeB[x] = str2[1].substring(0, 1);
            }
        }
    }
    //  }

    public void PrintWarriors(String[] inst, int position_1, String[] mA, String[] mB, int[] fA, int[] fB, int position_2) {
        for (int i = -5; i < 6; i++) {
            int k = (i + position_1 + MAX) % MAX;
            int j = (i + position_2 + MAX) % MAX;
            //System.out.println("k = "+k);
            System.out.print(String.format("%04d", k) + " :" + inst[k] + "\t" + mA[k] + fA[k] + "\t" + mB[k] + fB[k]);
            System.out.print(String.format("\t\t"+ "%04d", j) + " :" + inst[j] + "\t" + mA[j] + fA[j] + "\t" + mB[j] + fB[j]+ "\n");

        }
        //for (int i = -5; i < 6; i++) {
          //  int k = (i + position_2 + MAX) % MAX;
            //System.out.println("k = "+k);
            //System.out.println(String.format("\t\t"+ "%04d", k) + " :" + inst[k] + "\t" + mA[k] + fA[k] + "\t" + mB[k] + fB[k]);
        //}
        System.out.println("\n");
    }

    //method for checking if the initial position of second warrior is valid
    public void checkPosition(int x1) {
        boolean notValidPos = true;
        while (notValidPos) {
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
    public int KeepIndexWithinLimits(int index) {
        return index % MAX;
    }

    public void RunSimulation(String[] operation, String[] mode_A, String[] mode_B, int[] f_A, int[] f_B, int IndexPlayer1, int IndexPlayer2) {
        int j;
        int GameCounter = 1;    //for alternating between warriors; warrior 1 is odd and 2 is even
        int SplCount1 = 0;
        int draw = 0;
        int SplCount2 = 0;
        int SplIndex1 = 0;
        int SplIndex2 = 0;
        boolean Flag1 = false;
        boolean Flag2 = false;
        Instructions ObjOp = new Instructions(operation, mode_A, mode_B, f_A, f_B);
        while (noWinner) {
            //checking which warrior's turn
            if (GameCounter % 2 != 0) {
                if (Flag1 && SplCount1 % 2 != 0) {
                    System.out.println("********* Turn:   Warrior 1' **********\n");
                    SplIndex1 = KeepIndexWithinLimits(SplIndex1);
                    j = SplIndex1;
                    SplIndex1++;
                } else {
                    System.out.println("********* Turn:   Warrior 1 ************\n");
                    IndexPlayer1 = KeepIndexWithinLimits(IndexPlayer1);
                    j = IndexPlayer1;
                    IndexPlayer1++;
                }
            } else {
                if (Flag2 && SplCount2 % 2 != 0) {
                    System.out.println("******** Turn:   Warrior 2' ************\n");
                    SplIndex2 = KeepIndexWithinLimits(SplIndex2);
                    j = SplIndex2;
                    SplIndex2++;
                } else {
                    System.out.println("******** Turn:   Warrior 2 *************\n");
                    IndexPlayer2 = KeepIndexWithinLimits(IndexPlayer2);
                    j = IndexPlayer2;
                    IndexPlayer2++;
                }
            }

            //checking instructions
            if (operation[j].equals("DAT")) {
                ObjOp.DAT(j); //instantiate DAT object
                break;
            } else if (operation[j].equals("MOV")) {
                ObjOp.MOV(j); //instantiate MOV object
            } else if (operation[j].equals("ADD")) {
                ObjOp.ADD(j);
            } else if (operation[j].equals("SUB")) {
                ObjOp.SUB(j);
            } else if (operation[j].equals("JMP")) {
                System.out.println("JMP is being called");
                if (GameCounter % 2 == 1) {
                    IndexPlayer1 = ObjOp.JMP(j);
                    //       System.out.println("IndexPlayer1 " + IndexPlayer1);
                } else {
                    IndexPlayer2 = ObjOp.JMP(j);
                    //     System.out.println("IndexPlayer2 " + IndexPlayer2);
                }
            } else if (operation[j].equals("JMZ")) {
                System.out.println("in JMZ");

                if (GameCounter % 2 != 0) {
                    int temp = ObjOp.JMZ(j);
                    if (temp != 1000000) {
                        IndexPlayer1 = temp;
                        System.out.println("IndexPlayer1 " + IndexPlayer1);
                    } else {
                        temp = ObjOp.JMZ(j);
                        if (temp != 1000000) {
                            IndexPlayer2 = temp;
                            System.out.println("IndexPlayer2 " + IndexPlayer2);
                        }
                    }
                }

            } else if (operation[j].equals("DJN")) {
                System.out.println("in DJN");
                if (GameCounter % 2 != 0) {
                    System.out.println("I am in if in DJN");
                    int temp = ObjOp.DJN(j);
                    if (temp != 1000000) {
                        IndexPlayer1 = temp;
                    }
                } else {
                    System.out.println("I am in if in DJN");
                    int temp = ObjOp.DJN(j);
                    if (temp != 1000000) {
                        IndexPlayer2 = temp;
                    }
                }

            } else if (operation[j].equals("CMP")) {
                if (GameCounter % 2 == 1) {
                    IndexPlayer1 = ObjOp.CMP(j);
                } else {
                    IndexPlayer2 = ObjOp.CMP(j);
                }
            } else if (operation[j].equals("SPL")) {
                if (GameCounter % 2 == 1) {
                    System.out.println("SPL 1 is called");
                    Flag1 = true;
                    SplIndex1 = ObjOp.JMP(j);
                    SplCount1++;
                } else {
                    System.out.println("SPL 2 is called");
                    Flag2 = true;
                    SplIndex2 = ObjOp.JMP(j);
                    SplCount2++;
                }

            } else {
                System.out.println("breaking from while");
                break;
            }
            // System.out.println("\nWarrior 1");
            // PrintWarriors(operation, IndexPlayer1, mode_A, mode_B, f_A, f_B);
            GameCounter++;

            System.out.println("Warrior 1 \t\t\t\tWarrior 2");
            //PrintWarriors(operation, IndexPlayer1, mode_A, mode_B, f_A, f_B,operation, IndexPlayer2, mode_A, mode_B, f_A, f_B);

            //System.out.println("Warrior 2");
            PrintWarriors(operation, IndexPlayer1, mode_A, mode_B, f_A, f_B, IndexPlayer2);
            if (GameCounter == MAX * 3){
                System.out.println("Game is a Draw");
                draw++;
                break;
            }
        }
        if (draw == 0) {
            if (GameCounter % 2 == 0) {
                System.out.println("\nWarrior 1 is the Winner!");
            } else {
                System.out.println("\nWarrior 2 is the Winner!");
            }
        }
    }
}


