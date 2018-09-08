package io.carolynn.todolisttracker;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;

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
        } catch (IOException e){
            System.out.println("file creation error");
        }
    }

    public void startList(String data){
        try (BufferedWriter writer = Files.newBufferedWriter(file, charset)) {
            writer.append(data);
        } catch (IOException e) {
            System.out.println("start list method error");
        }
    }


    public void addData(String data){
        try (BufferedWriter writer = Files.newBufferedWriter(file, charset, APPEND)) {
            writer.newLine();
            writer.append(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> retrieveData(){
        ArrayList<String> dataList = new ArrayList<>();
        try(BufferedReader reader = Files.newBufferedReader(file, charset)){
            String line = "";
            while((line = reader.readLine()) != null){
                dataList.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataList;
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
        StringBuilder builder = new StringBuilder();
        retrieveData().stream()
                .filter(e->!e.equals(data))
                .forEach(e->builder.append(e).append("\n"));
        startList(builder.toString());
    }

    public String printNumberedList(){
        StringBuilder builder = new StringBuilder("To Do List:\n");
        ArrayList<String> numberedList = retrieveData();
        for(int i = 0; i<numberedList.size();i++){
            builder.append(i+1)
                    .append(") ")
                    .append(numberedList.get(i))
                    .append("\n");
        }
        return builder.toString();
    }

    public void clearList(){
        try (BufferedWriter writer = Files.newBufferedWriter(file, charset)) {
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
