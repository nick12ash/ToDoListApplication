package domain;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.sql.Time;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    ToDoItem todoItem1 = new ToDoItem("Reminder for grilled cheese", "Klemm", new TimeStamp("2020-04-04T18:35:23.669Z"));
    ToDoItem todoItem2 = new ToDoItem("Don't forget the pana cotta", "Klemm", new TimeStamp("2020-04-12T14:43:54.669Z"));
    User user = new User("john");

    UserTest() throws SQLException {
    }

    @Test
    void organizeItems() {
    }

    @Test
    void makeToDoItem() {
        user.makeToDoItem(todoItem1.about, todoItem1.owner, todoItem1.dueDate);

        assertEquals(todoItem1.getUniqueItemID(), user.toDoList.get(0).getUniqueItemID());
    }

    @Test
    void changeToDoItemStatusToCompleted() {
        user.makeToDoItem(todoItem1);


    }

    @Test
    void changeToDoItemStatusToSnoozed() {
    }

    @Test
    void changeToDoItemCategory() {
    }

    @Test
    void changeToDoItemDueDate() {
    }

    @Test
    void syncItemsToCloud() {

    }
}