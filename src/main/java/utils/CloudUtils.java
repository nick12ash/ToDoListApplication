package utils;

import com.google.gson.*;
import domain.ToDoItem;
import com.google.api.client.http.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import exceptions.ParameterIsEmptyException;
import exceptions.ParameterIsNotJsonStringException;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class CloudUtils {

    private HttpRequestFactory requestFactory;
    private String baseURL = "https://todoserver222.herokuapp.com/";
    public String todosURL = baseURL + "todos/";

    public CloudUtils() {
        requestFactory = new NetHttpTransport().createRequestFactory();
    }

    public boolean checkConnection(){
        try {
            HttpRequest getRequest = requestFactory.buildGetRequest(
                    new GenericUrl(todosURL));
            String rawResponse = getRequest.execute().parseAsString();
            return !rawResponse.isEmpty();
        } catch(IOException e){
            return false;
        }
    }

    public int uploadItemsToCloud(List<ToDoItem> toDoItemList) throws IOException{
        Map<String, Object> data = new LinkedHashMap<>();
        try {
            for (ToDoItem tdi : toDoItemList) {
                data.put("memo", tdi.about);
                data.put("owner", tdi.owner);
                data.put("due_date", tdi.dueDate.getFormattedTimeStamp());
                data.put("created_date", tdi.createdDate.getFormattedTimeStamp());
                data.put("status", tdi.status.getStatus());
                data.put("category", tdi.itemCategory);
                HttpContent content = new UrlEncodedContent(data);
                HttpRequest postRequest = requestFactory.buildPostRequest(
                        new GenericUrl(todosURL), content);
                postRequest.execute();
            }
            return 1;
        } catch (NullPointerException e){
            return 0;
        }
    }

    public String retrieveCloud(){
        try{
            HttpRequest getRequest = requestFactory.buildGetRequest(new GenericUrl(todosURL));
            String rawResponse = getRequest.execute().parseAsString();
            return rawResponse;
        } catch (IOException e){
            e.printStackTrace();
        }
        return "";
    }


    public List<ToDoItem> parseJSONString(String jsonString) throws ParameterIsNotJsonStringException {
        if (thisIsNotAJSONString(jsonString)) {
            throw new ParameterIsNotJsonStringException();
        }
        List<ToDoItem> list = new LinkedList<>();
        JsonParser jsonParser = new JsonParser();
        JsonElement rootElement = jsonParser.parse(jsonString);
        JsonArray rootObjects = rootElement.getAsJsonArray();
        if(rootObjects.size() > 0) {
            for (JsonElement rootObject : rootObjects) {
                var about = rootObject.getAsJsonObject().getAsJsonPrimitive("memo").getAsString();
                var owner = rootObject.getAsJsonObject().getAsJsonPrimitive("owner").getAsString();
                var dueDate = rootObject.getAsJsonObject().getAsJsonPrimitive("due_date").getAsString();
                list.add(new ToDoItem(about, owner, dueDate));
            }
        } else {
            list.add(new ToDoItem("Cloud is empty", "You big dummy", "0000-00-00T00:00:00.0000"));
        }
        return list;
    }


    public boolean deleteTodoItem(int id) {
        try {
            HttpRequest deleteRequest = requestFactory.buildDeleteRequest(
                    new GenericUrl(todosURL + id));
            String rawResponse = deleteRequest.execute().parseAsString();
            return true;
        } catch (IOException e){
            e.printStackTrace();
            return false;
        }
    }

    private boolean thisIsNotAJSONString(String json){
        if (json.charAt(0) != '{' || json.charAt(0) != '['){
            return false;
        } else{
            return true;
        }
    }

    public void clearTheCloud(){
        List<Integer> list = new LinkedList<>();
        JsonParser jsonParser = new JsonParser();
        JsonElement rootElement = jsonParser.parse(retrieveCloud());
        JsonArray rootObjects = rootElement.getAsJsonArray();
        for (JsonElement rootObject : rootObjects){
            var number = rootObject.getAsJsonObject().getAsJsonPrimitive("id").getAsInt();
            deleteTodoItem(number);
        }
    }
}
