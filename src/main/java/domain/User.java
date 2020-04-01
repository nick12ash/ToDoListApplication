package domain;

import java.util.List;

public class User {

    public String name;
    private List<ToDoItem> userToDoList;
    private StatisticsModel statisticsModel;

    public User(String name) {
        this.name = name;
    }

    public List<ToDoItem> organizeItems(){
        return null;
    }

    public void addToDoItem(String about, String owner, TimeStamp dueDate){
        userToDoList.add(new ToDoItem(about, owner, dueDate));
    }

    public void viewToDoItems(){

    }
    public void editToDoItem(ToDoItem itemToEdit){

    }

    public void removeToDoItem(ToDoItem itemToRemove){
        userToDoList.remove(itemToRemove);
    }

    public boolean syncItems(){
        return false;
    }
}
