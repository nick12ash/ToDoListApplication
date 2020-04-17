package domain;

import java.time.*;
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
    public String itemCategory;

    @DatabaseField()
    public String status;

    @DatabaseField(id = true)
    public int id;

    private LocalDate dateNow = LocalDate.now();
    private int dayNow = dateNow.getDayOfMonth();
    private int monthNow = dateNow.getMonthValue();
    private int yearNow = dateNow.getYear();


    public ToDoItem(){
    }

    public ToDoItem(String about, String owner, TimeStamp dueDate, TimeStamp createdDate, String status, String category, int id){
        this.about = about;
        this.owner = owner;
        this.dueDate = dueDate.toString();
        this.createdDate = createdDate.toString();
        this.status = status;
        this.itemCategory = category;
        this.id = id;
    }

    public ToDoItem(String about, String owner, TimeStamp dueDate){
        this.about = about;
        this.owner = owner;
        this.dueDate = dueDate.toString();
        this.createdDate = new TimeStamp(yearNow,monthNow,dayNow).toString();
        this.status = "Snoozed";
        this.itemCategory = "Unsorted";
        this.id = -1;
    }


    public String getStatus(){return status; }

    @Override
    public String toString() {
        return "ToDoItem{" +
                "Id='" + id + '\'' +
                "About='" + about + '\'' +
                ", Owner='" + owner + '\'' +
                ", Due Date='" + dueDate + '\'' +
                ", Status='" + status + '\'' +
                ", Category='" + itemCategory + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o){
        if(o == null)                return false;
        if(!(o instanceof ToDoItem)) return false;

        ToDoItem other = (ToDoItem) o;
        if(! (this.id ==other.id))      return false;

        return true;
    }

}
