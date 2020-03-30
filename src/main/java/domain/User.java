package domain;

import java.util.List;

public class User {

    private String name;
    private List<ToDoItem> userToDoList;
    private StatisticsModel statisticsModel;

    public User(String name) {
        this.name = name;
    }

    public List<ToDoItem> organizeItems(){
        return null;
    }

    public boolean syncItems(){
        return false;
    }
}
