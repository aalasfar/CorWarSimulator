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

    String[] myArray = new String[MAX];
    String[] instuction = new String[MAX];
    String[] modeA = new String[MAX];
    String[] modeB = new String[MAX];
    int[] Afield = new int[MAX];
    int[] Bfield = new int[MAX];

    public void mySim(OpenFile f) {

        Player1 = f.Openfile();

        //initialize to DAT 0
        for (int i = 0; i < myArray.length; i++) {
            instuction[i] = "DAT";
            modeA[i]= " ";
            Afield[i]= 0;
            modeB[i]= " ";
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
        for ( int i = 0; i < count ; i++) {
            String s = myArray[initialPos +i];
            instuction[initialPos+i] = myArray[initialPos+i].substring(0,3);
            String str = s.replaceAll("[^0-9]+", " ");
            String strnum[] = str.split(" ");
            char[] arr = s.toCharArray();
            int[] numbers = new int[strnum.length];
            for (int u = 0; u < strnum.length - 1; u++) {
                numbers[u] = Integer.parseInt(strnum[u + 1]);
            }
            Afield[initialPos+i] = numbers[0];
            Bfield[initialPos+i] = numbers[1];
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
