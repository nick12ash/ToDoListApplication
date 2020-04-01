package domain;


public class ToDoItem {

    private String about;
    private String owner;
    private ToDoItemStatus status;
    private String itemCategory;
    private TimeStamp reminder;
    private TimeStamp dueDate;
    private TimeStamp createdDate;
    private TimeStamp completedDate;

    public ToDoItem(String about, String owner, TimeStamp dueDate){
        this.about = about;
        this.owner = owner;
        this.dueDate = dueDate;
        this.createdDate = new TimeStamp(java.time.Clock.systemUTC().instant().toString());
        this.status = new ToDoItemStatus();
    }

    public void setItemCategory(String category)


}
