package domain;

import utils.CloudUtils;
import utils.DatabaseUtils;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class User {

    public String name;
    public List<ToDoItem> toDoList = new LinkedList<>();
    public CloudUtils cloudUtils;
    public DatabaseUtils databaseUtils;

    public User(String name) throws SQLException {
        this.name = name;
        this.cloudUtils = new CloudUtils();
        this.databaseUtils = new DatabaseUtils();

    }

    public void addToDoItemtoList(ToDoItem item){
        toDoList.add(item);
    }

    public List<ToDoItem> getToDoItemList(){return toDoList;}

    public String deleteToDoItem(String identifier){
        for (ToDoItem item : toDoList){
            if (item.id.equals(identifier)){
                toDoList.remove(item);
                return "Local Delete: Success";
            }
        }
        return "Not in local storage";
    }


}
