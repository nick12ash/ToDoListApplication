package domain;

import utils.CloudUtils;
import utils.DatabaseUtils;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class User {

    public String name;
    public List<ToDoItem> toDoList = new LinkedList<>();

    public User(String name){
        this.name = name;

    }

    public void addToDoItemtoList(ToDoItem item){
        toDoList.add(item);
    }

    public List<ToDoItem> getToDoItemList(){return toDoList;}

    public String deleteToDoItem(int identifier){
        for (ToDoItem item : toDoList){
            if (item.id == identifier){
                toDoList.remove(item);
                return "Local Delete: Success";
            }
        }
        return "Not in local storage";
    }


}
