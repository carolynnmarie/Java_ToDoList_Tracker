package io.carolynn.todolisttracker;

import org.junit.Test;

public class TestToDoList {

    @Test
    public void testPrintList(){

        ToDoList list = new ToDoList(ToDoList.class.getResource("/todo.txt").getFile());
//        String y = list.printList();
//        System.out.println(y);
 //       list.writeToList("adding");
        String x = list.printList();
        System.out.println(x);

    }
}
