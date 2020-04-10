package domain;


import java.util.LinkedList;
import java.util.List;

public class User {

    public String name;
    public List<ToDoItem> toDoList = new LinkedList<>();
    private StatisticsModel statisticsModel;

    public User(String name){
        this.name = name;
    }

    public void makeToDoItem(String about, String owner, String dueDate){
        toDoList.add(new ToDoItem(about, owner, new TimeStamp(dueDate)));
    }

    public void makeToDoItem(ToDoItem item){
        toDoList.add(item);
    }

    public ToDoItem getToDoItem(int itemID){ return toDoList.get(itemID); }

    public List<ToDoItem> getToDoItemList(){return toDoList;}

    public void removeToDoItem(int itemID){ toDoList.remove(itemID); }


}
