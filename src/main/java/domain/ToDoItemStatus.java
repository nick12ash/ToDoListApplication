package domain;

public class ToDoItemStatus {

    private String status;

    public ToDoItemStatus(){
        this.status = "In Progress";
    }

    public void setComplete(){
        this.status = "Complete";
    }

    public void setSnoozed(){
        this.status = "Snoozed";
    }

    public void setInProgress(){
        this.status = "In Progress";
    }

    public String getStatus(){
        return status;
    }
}
