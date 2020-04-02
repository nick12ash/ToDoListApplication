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

    public void uploadItemsToCloud(List<ToDoItem> toDoItemList) throws IOException, ParameterIsEmptyException {
        Map<String, Object> data = new LinkedHashMap<>();
        if (toDoItemList.isEmpty()){
            throw new ParameterIsEmptyException();
        }
        for (ToDoItem tdi : toDoItemList){
            data.put("memo", tdi.about);
            data.put("owner", tdi.owner);
            data.put("due_date", tdi.dueDate.getFormattedTimeStamp());
            data.put("created_date", tdi.createdDate.getFormattedTimeStamp());
            data.put("status", tdi.status.getStatus());
            data.put("category", tdi.itemCategory);
            HttpContent content = new UrlEncodedContent(data);
            HttpRequest postRequest = requestFactory.buildPostRequest(
                    new GenericUrl(todosURL),content);
            postRequest.execute();
        }
    }

    public String retrieveCloud() throws IOException {
        HttpRequest getRequest = requestFactory.buildGetRequest(
                new GenericUrl(todosURL));
        String rawResponse = getRequest.execute().parseAsString();
        return rawResponse;
    }


    public List<ToDoItem> parseJSONString(String jsonString) throws ParameterIsNotJsonStringException {
        if (thisIsNotAJSONString(jsonString)) {
            throw new ParameterIsNotJsonStringException();
        }
        List<ToDoItem> list = new LinkedList<>();
        JsonParser jsonParser = new JsonParser();
        JsonElement rootElement = jsonParser.parse(jsonString);
        JsonArray rootObjects = rootElement.getAsJsonArray();
        for (JsonElement rootObject : rootObjects){
            var about = rootObject.getAsJsonObject().getAsJsonPrimitive("memo").getAsString();
            var owner = rootObject.getAsJsonObject().getAsJsonPrimitive("owner").getAsString();
            var dueDate = rootObject.getAsJsonObject().getAsJsonPrimitive("due_date").getAsString();
            list.add(new ToDoItem(about,owner,dueDate));
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

}
