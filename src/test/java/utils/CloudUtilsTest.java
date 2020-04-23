package utils;

import domain.TimeStamp;
import domain.ToDoItem;
import exceptions.ParameterIsNotJsonStringException;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CloudUtilsTest {

    private final LocalDate dateNow = LocalDate.now();
    private final int dayNow = dateNow.getDayOfMonth();
    private final int monthNow = dateNow.getMonthValue();
    private final int yearNow = dateNow.getYear();
    private final TimeStamp currentTime = new TimeStamp(yearNow,monthNow,dayNow);

    CloudUtils cloudUtils = new CloudUtils();

    ToDoItem todoItem1 = new ToDoItem("Reminder for grilled cheese", "teamone", new TimeStamp(2020,4,4), currentTime, "Snoozed", "Unsorted", 10);
    ToDoItem todoItem2 = new ToDoItem("Don't forget the pana cotta", "teamone", new TimeStamp(2020,4,12));

    List<ToDoItem> list2 = new LinkedList<>();

    CloudUtilsTest() throws SQLException {
    }


    @Test
    void connectedToInternet(){
        assertTrue(cloudUtils.checkConnection());
    }


    @Test
    void uploadItemWithoutID() {
        String response;
        List<ToDoItem> itemList;

        response = cloudUtils.uploadItemToCloud(todoItem2);
        itemList = cloudUtils.readCloud();

        assertEquals("Success", response);
        assertEquals( todoItem2, itemList.get(0));
    }

    @Test
    void uploadItemWithID() {
        String response;
        List<ToDoItem> itemList;

        response = cloudUtils.uploadItemToCloud(todoItem2);
        itemList = cloudUtils.readCloud();

        assertEquals("Success", response);
        assertEquals(todoItem2.about, itemList.get(0).about);
    }

    @Test
    void brokenToDoItemReturnsErrorWhenUploading(){
        String response;

        todoItem1.status = null;
        response = cloudUtils.uploadItemToCloud(todoItem1);

        assertEquals("Failure", response);

    }


    @Test
    void nullToDoItemReturnsErrorWhenUploading() {
        String response;

        response = cloudUtils.uploadItemToCloud(null);

        assertEquals("Failure", response);
    }

    @Test
    void cloudReturnsJsonString(){
        String response = cloudUtils.retrieveCloud();

        assertEquals('[', response.charAt(0));
    }

    @Test
    void parseMethodThrowsExceptionForNotJson(){
        try {
            String json = "Todoitem 1: About: memo other stuff";
            cloudUtils.parseCloudJSONString(json);
        } catch (ParameterIsNotJsonStringException e){
            assert true;
        }
    }

    @Test
    void parseMethodCreatesToDoItem() throws ParameterIsNotJsonStringException {
        List<ToDoItem> list;
        String jsonString = "[\n" +
                "  {\n" +
                "    \"about\": \"test for timestamps\",\n" +
                "    \"owner\": \"teamone\",\n" +
                "    \"due_date\": \"{year='2020', month='33', day='14'}\",\n" +
                "    \"created_date\": \"{year='2020', month='4', day='17'}\",\n" +
                "    \"status\": \"Snoozed\",\n" +
                "    \"category\": \"Unsorted\",\n" +
                "    \"id\": 4\n" +
                "  }\n" +
                "]";

        list = cloudUtils.parseCloudJSONString(jsonString);
        assertEquals(4, list.get(0).id);
    }

    @Test
    void deleteCloudWorks() throws ParameterIsNotJsonStringException {
        cloudUtils.clearTheCloud();

        list2 = cloudUtils.parseCloudJSONString(cloudUtils.retrieveCloud());

        assertNull(list2);

    }

    @Test
    void deleteSpecificCloudEntries(){
        cloudUtils.deleteCloudEntrySpecific("fetjfKU");
    }


    @Test
    void getPieDataNormalTest() {
        //Stuff here
    }
}