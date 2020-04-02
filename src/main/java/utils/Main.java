package utils;

import domain.TimeStamp;
import domain.ToDoItem;
import domain.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Main {

    public static void main(String args[]){
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            while (true) {
                System.out.println("What is your name?: ");
                String name = br.readLine();
                User user = new User(name);

                System.out.println("What would you like to do?\n[M]ake a To Do item\n[E]dit a To Do item\n[S]ync to the cloud?");
                String action = br.readLine();
                if (action.contentEquals("M")){
                    System.out.println("What is the memo?: ");
                    String memo = br.readLine();
                    System.out.println("What is the Due date?: ");
                    String dueDate = br.readLine();
                    ToDoItem toDoItem = new ToDoItem(memo, user.name, dueDate);
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
