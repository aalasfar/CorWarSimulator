package com.company;

import java.util.*;
//import javax.swing.*;
import java.io.*;
import java.util.ArrayList;

public class Main {

	/*import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
*/

        public static void main(String[] args) {
            //String tempForBuff;
            OpenFile P1 = new OpenFile();
            // ArrayList<String> Player2 = new ArrayList<>();

            ArrayList<String> Player1 = P1.Openfile();

            final int MAX = 100;
            final int MIN = 0;
            boolean noWinner = true;
            boolean NotDoneInput = true;
            //File fileInput = openFile();
            //printFileContents(fileInput);

            String[] myArray = new String[MAX];

            for(int i= 0; i<myArray.length; i++){
                myArray[i]= "DAT 0";        //initialize to 0 or DAT 0
            }

            int initialPos = (int)((Math.random() * (MAX+1-MIN)) + MIN); // generate random number
            //for(int j = initialPos; j < (initialPos+Player1.size()); j++){

            int j = initialPos;
            System.out.println("initialPos = " + initialPos);
            int count = 0;
            while (NotDoneInput){
                myArray[j] = Player1.get(count);
                j++;
                count++;
                if(j >= MAX){   j = 0;  }
                if(count >= Player1.size()){    NotDoneInput = false;   }
            }
            j = 0;
            for(int i=-5; i<6; i++){
                int k = (i+MAX)%MAX;
                //if(k == initialPos){    System.out.println(myArray[k]);   }
                //System.out.println(myArray[k]);
            }
            /*if(i+initialPos < 0){
                System.out.println(MAX+i+initialPos +"      " +myArray[MAX+i+initialPos]);    }
            else if(i+initialPos > MAX){
                System.out.println(j+ "     " +myArray[j]);
                j++;
            }
            else{
                System.out.println(i+initialPos + "        " + myArray[i+initialPos]);
            }
        }*/


            // while (noWinner){



            //}

        }
    }

