package domain;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable(tableName = "todoitem")
public class ToDoItem {


    @DatabaseField()
    public String about;

    @DatabaseField()
    public String owner;

    @DatabaseField()
    public String dueDate;

    @DatabaseField()
    public String createdDate;

    @DatabaseField()
    public String itemCategory = "Unsorted";

    @DatabaseField()
    public String status;

    public String currentTime = java.time.Clock.systemUTC().instant().toString();

    public ToDoItem(){
    }

    public ToDoItem(String about, String owner, TimeStamp dueDate){
        this.about = about;
        this.owner = owner;
        this.dueDate = dueDate.toString();

        this.createdDate = new TimeStamp(currentTime).toString();
        this.status = "In Progress";
    }

    public ToDoItem(String about, String owner, TimeStamp dueDate, TimeStamp createdDate, String status, String category){
        this.about = about;
        this.owner = owner;
        this.dueDate = dueDate.toString();
        this.createdDate = createdDate.toString();
        this.status = status;
        this.itemCategory = category;
    }

    public String getUniqueItemID(){
        return about+owner+dueDate;
    }

    public String getStatus(){return status; }

    public void changeAbout(String newAbout){
        this.about = newAbout;
    }

    public void changeDueDate(TimeStamp newDueDate){
        this.dueDate = newDueDate.toString();
    }

    public void changeCategory(String newCategory) {this.itemCategory = newCategory;}

    @Override
    public String toString() {
        return "ToDoItem{" +
                "About='" + about + '\'' +
                ", Owner='" + owner + '\'' +
                ", Due Date='" + dueDate + '\'' +
                ", Status='" + status + '\'' +
                ", Category='" + itemCategory + '\'' +
                '}';
    }

    public void setComplete() { this.status = "Complete"; }

    public void setSnoozed(){
        this.status = "Snoozed";
    }

    public void setInProgress(){
        this.status = "In Progress";
    }
}
