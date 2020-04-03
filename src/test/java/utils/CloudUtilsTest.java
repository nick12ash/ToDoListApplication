package utils;

import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.javanet.NetHttpTransport;
import domain.TimeStamp;
import domain.ToDoItem;
import exceptions.ParameterIsEmptyException;
import exceptions.ParameterIsNotJsonStringException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CloudUtilsTest {

    CloudUtils cloudUtils = new CloudUtils();

    ToDoItem todoItem1 = new ToDoItem("Reminder for grilled cheese", "Klemm", "2020-04-04T18:35:23.669Z");
    ToDoItem todoItem2 = new ToDoItem("Don't forget the pana cotta", "Klemm", "2020-04-12T14:43:54.669Z");

    List<ToDoItem> list = new LinkedList<>();
    List<ToDoItem> list2 = new LinkedList<>();

    @Test
    void checkConnection(){
        assertEquals(true, cloudUtils.checkConnection());
    }


    @Test
    void cloudUploadsAndDownloadsOneItem() throws IOException, ParameterIsEmptyException, ParameterIsNotJsonStringException {
        list.add(todoItem1);


        cloudUtils.uploadItemsToCloud(list);
        list2 = cloudUtils.parseJSONString(cloudUtils.retrieveCloud());


        assertEquals(list.get(0).getUniqueItemID(), list2.get(0).getUniqueItemID());

        cloudUtils.deleteTodoItem(1);
    }

    @Test
    void cloudUploadsNoItems() throws IOException, ParameterIsEmptyException, ParameterIsNotJsonStringException {
        list.add(null);

        assertEquals(0, cloudUtils.uploadItemsToCloud(list));
        list2 = cloudUtils.parseJSONString(cloudUtils.retrieveCloud());

        assertEquals("Cloud is emptyYou big dummy0000-00-00T00:00:00.0000", list2.get(0).getUniqueItemID());
    }

    @Test
    void cloudUploadsMultipleItems() throws IOException, ParameterIsEmptyException, ParameterIsNotJsonStringException {
        list.add(todoItem1);
        list.add(todoItem2);


        cloudUtils.uploadItemsToCloud(list);
        list2 = cloudUtils.parseJSONString(cloudUtils.retrieveCloud());


        assertEquals(list.get(0).getUniqueItemID(), list2.get(0).getUniqueItemID());
        assertEquals(list.get(1).getUniqueItemID(), list2.get(1).getUniqueItemID());


        cloudUtils.deleteTodoItem(1);
        cloudUtils.deleteTodoItem(2);
    }

    @Test
    void deleteCloudWorks() throws ParameterIsNotJsonStringException {
        cloudUtils.clearTheCloud();

        list2 = cloudUtils.parseJSONString(cloudUtils.retrieveCloud());

        assertEquals("Cloud is emptyYou big dummy0000-00-00T00:00:00.0000", list2.get(0).getUniqueItemID());

    }

}