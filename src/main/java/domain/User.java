package domain;

import exceptions.ParameterIsEmptyException;
import utils.CloudUtils;

import java.io.IOException;
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

    public void makeToDoItem(String about, String owner, String dueDate){
        userToDoList.add(new ToDoItem(about, owner, dueDate));
    }

    public void viewToDoItems(){

    }

    public void editToDoItem(ToDoItem itemToEdit){

    }

    public void removeToDoItem(ToDoItem itemToRemove){
        userToDoList.remove(itemToRemove);
    }

    public boolean syncItemsToCloud(){
        CloudUtils cloudUtils = new CloudUtils();
        try {
            cloudUtils.uploadItemsToCloud(userToDoList);
        } catch (IOException e){
            e.printStackTrace();
        }
        return cloudUtils.checkConnection();
    }
}
