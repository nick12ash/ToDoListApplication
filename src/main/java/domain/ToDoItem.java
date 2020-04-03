package domain;


import com.google.gson.annotations.SerializedName;

public class ToDoItem {

    public int id;

    @SerializedName(value = "about", alternate = "memo")
    public String about;

    @SerializedName(value = "owner", alternate = "user")
    public String owner;

    @SerializedName(value = "due_date", alternate = "due-date")
    public TimeStamp dueDate;

    public ToDoItemStatus status;

    public String itemCategory = "Unsorted";
    public TimeStamp reminder;



    public TimeStamp createdDate;


    public TimeStamp completedDate;

    public ToDoItem(String about, String owner, String dueDate){
        this.about = about;
        this.owner = owner;
        this.dueDate = new TimeStamp(dueDate);
        this.createdDate = new TimeStamp(java.time.Clock.systemUTC().instant().toString());
        this.status = new ToDoItemStatus();
    }

    public void setItemCategory(String category){
        this.itemCategory = category;
    }

    public String getUniqueItemID(){
        return about+owner+dueDate.getFormattedTimeStamp();
    }

    public boolean isEmpty(){
        if (this.about == "" && this.owner == "") {
            return true;
        } else {
            return false;
        }
    }

}
