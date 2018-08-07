package io.carolynn.todolisttracker;

import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

public class TestListTracker {

    @Test
    public void testConstructor1(){
        ListTracker tracker = new ListTracker(Paths.get("/Users/carolynn/dev/testSaveOnDesktop.txt"));
        tracker.startList("new");
        tracker.addData("id");
        System.out.println(tracker.printData());
    }

    @Test
    public void testAddData(){
        ListTracker tracker = new ListTracker(Paths.get("/Users/carolynn/dev/Work-in-process-Projects/Java_ToDoList_Tracker" +
                "/src/main/resources/test.txt"));
       tracker.addData("id");
       tracker.addData("and");
        System.out.println(tracker.printData());
    }

    @Test
    public void testStartList(){
        ListTracker tracker = new ListTracker(Paths.get("/Users/carolynn/dev/Work-in-process-Projects/Java_ToDoList_Tracker" +
                "/src/main/resources/test.txt"));
        tracker.startList("new data");
        System.out.println(tracker.printData());
    }

    @Test
    public void testDeleteFile(){
        Path path = Paths.get("/Users/carolynn/dev/Work-in-process-Projects/Java_ToDoList_Tracker" +
                "/src/main/resources/test");
        ListTracker tracker = new ListTracker(path);
        tracker.deleteFile(path);
    }

    @Test
    public void testDeleteListItem(){
        ListTracker tracker = new ListTracker(Paths.get("/Users/carolynn/dev/Work-in-process-Projects/Java_ToDoList_Tracker" +
                "/src/main/resources/test.txt"));
        tracker.addData("and");
        System.out.println(tracker.printData());
        tracker.deleteListItem("and");
        System.out.println(tracker.printData());
    }

    @Test
    public void testClearList(){
        ListTracker tracker = new ListTracker(Paths.get("/Users/carolynn/dev/Work-in-process-Projects/Java_ToDoList_Tracker" +
                "/src/main/resources/test.txt"));
        tracker.clearList();
        System.out.println(tracker.printData());
    }

}
