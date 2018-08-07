package io.carolynn.todolisttracker;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;

import static java.nio.file.StandardOpenOption.APPEND;

public class ListTracker {

    Charset charset = Charset.forName("US-ASCII");
    Path file;


    public ListTracker(Path file){
        this.file = file;
        try{
            if(Files.notExists(file)){
                Files.createFile(file);
            }
        } catch (FileAlreadyExistsException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void startList(String data){
        try (BufferedWriter writer = Files.newBufferedWriter(file, charset)) {
            writer.append(data);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addData(String data){
        try (BufferedWriter writer = Files.newBufferedWriter(file, charset, APPEND)) {
            writer.append(data);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String printData(){
        StringBuilder builder = new StringBuilder();
        try(BufferedReader reader = Files.newBufferedReader(file, charset)){
            String line = "";
            while((line = reader.readLine()) != null){
                builder.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

    public void deleteFile(Path path){
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteListItem(String data){
        String [] array = printData().split("\n");
        StringBuilder builder = new StringBuilder();
        for(String item: array){
            if(!item.equals(data)){
                builder.append(item);
            }
        }
        startList(builder.toString());
    }

    public void clearList(){
        try (BufferedWriter writer = Files.newBufferedWriter(file, charset)) {
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
