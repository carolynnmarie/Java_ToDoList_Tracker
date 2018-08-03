package io.carolynn.todolisttracker;

import java.io.*;
import java.util.*;


public class ToDoList {


    private BufferedWriter writer;
    private Scanner scanner;
    private String file;


    public ToDoList(String file){
        try {
            this.file = file;
//            this.writer = new BufferedWriter(new FileWriter(file));
            this.scanner = new Scanner(new FileReader(file));
        } catch (FileNotFoundException e) {
            System.out.println(file + " Does Not Exist");
            System.exit(-1);
        }

    }


    public String printList(){
        StringBuilder builder = new StringBuilder();
        String x = null;
        while (scanner.hasNextLine()){
            x = scanner.nextLine();
            builder.append(x)
                    .append("\n");
        }
        return builder.toString();
    }



}
