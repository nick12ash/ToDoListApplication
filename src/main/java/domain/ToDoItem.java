package domain;

import java.sql.Timestamp;

public class ToDoItem {

    private String name;
    private Integer itemID;
    private ToDoItemStatus status;
    private String itemCategory;
    private TimeStamp reminder;
    private TimeStamp dueDate;
    private TimeStamp createdDate;
    private TimeStamp completedDate;
}
