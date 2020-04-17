package utils;

import domain.TimeStamp;
import domain.ToDoItem;
import domain.User;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UIUtilsTest {

    UIUtils uiUtils = new UIUtils(new User("teamone"));

    ToDoItem todoItem2 = new ToDoItem("Don't forget the pana cotta", "teamone", new TimeStamp(2020,4,12));

    UIUtilsTest() throws SQLException {
    }

    @Test
    void syncCloudAndLocalToDatabase() {
        User user = new User("George");
        user.toDoList.add(todoItem2);

        List<String> list = uiUtils.syncCloudAndLocalToDatabase();
        assertEquals("Success", list.get(0));
    }

    @Test
    void removeDuplicateToDoItems() {
        List<ToDoItem> list = new LinkedList<>();
        List<ToDoItem> list2 = new LinkedList<>();
        list.add(todoItem2);
        list2.add(todoItem2);

        list = uiUtils.removeDuplicateToDoItems(list, list2);

        assertEquals(1, list.size());

    }
    @Test
    void makeToDoItemInLocation() {
        String response = uiUtils.makeToDoItemInLocation(todoItem2);

        assertEquals("Item saved to cloud", response);
    }
}