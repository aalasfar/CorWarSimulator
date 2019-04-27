package com.company;

import java.io.*;
import java.util.*;

public class OpenFile {

    //returns an array list

    public static ArrayList<String> Openfile(String nameOfFile){
        ArrayList<String> warrior = new ArrayList<String>();

        try {
            File file = new File(nameOfFile);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            StringBuffer stringBuffer = new StringBuffer();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line);
                stringBuffer.append("\n");
                warrior.add(line);
            }
            fileReader.close();

            System.out.println("Contents of file:");
            System.out.println(stringBuffer.toString());
        } catch (IOException except) {
            except.printStackTrace();
        }
        return warrior;

    }
}
