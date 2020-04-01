package utils;

import domain.ToDoItem;
import com.google.api.client.http.*;
import com.google.api.client.http.javanet.NetHttpTransport;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CloudUtils {

    HttpRequestFactory requestFactory;
    String baseURL = "https://todoserver222.herokuapp.com/";
    String todosURL = baseURL + "todos/";

    public CloudUtils() {
        requestFactory = new NetHttpTransport().createRequestFactory();
    }

    public boolean establishConnection() throws IOException {
        HttpRequest getRequest = requestFactory.buildGetRequest(
                new GenericUrl("https://todoserver222.herokuapp.com/todos/"));
        String rawResponse = getRequest.execute().parseAsString();
        return !rawResponse.isEmpty();
    }

    public void updateCloud(){

    }

    public String retrieveCloud(String user) throws IOException {
        HttpRequest getRequest = requestFactory.buildGetRequest(
                new GenericUrl("https://todoserver222.herokuapp.com/todos/" + user+ "/"));
        String rawResponse = getRequest.execute().parseAsString();
        return rawResponse;
    }


    public int addTodoItem(String note, String owner) throws IOException {
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("title", note);
        data.put("owner", owner);
        HttpContent content = new UrlEncodedContent(data);
        HttpRequest postRequest = requestFactory.buildPostRequest(
                new GenericUrl("https://todoserver222.herokuapp.com/todos"),content);
        String rawResponse = postRequest.execute().parseAsString();
        int idAddress = rawResponse.indexOf("id");
        String resultingID = rawResponse.substring(idAddress+5,idAddress+7);
        return Integer.parseInt(resultingID);
    }

    public boolean deleteTodoItem(int id) {
        try {
            HttpRequest deleteRequest = requestFactory.buildDeleteRequest(
                    new GenericUrl("https://todoserver222.herokuapp.com/todos/" + id));
            String rawResponse = deleteRequest.execute().parseAsString();
            return true;
        } catch (IOException e){
            e.printStackTrace();
            return false;
        }
    }

}
