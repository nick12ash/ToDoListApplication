package domain;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;

class UserTest {

    ToDoItem todoItem1 = new ToDoItem("Reminder for grilled cheese", "Klemm", new TimeStamp(2020,4,4));
    ToDoItem todoItem2 = new ToDoItem("Don't forget the pana cotta", "Klemm", new TimeStamp(2020,4,12));
    User user = new User("john");

    UserTest() throws SQLException {
    }

    @Test
    void organizeItems() {
    }

    @Test
    void makeToDoItem() {

    }

    @Test
    void changeToDoItemStatusToCompleted() {
        user.addToDoItemtoList(todoItem1);


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