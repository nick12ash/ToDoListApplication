package utils;

import domain.ToDoItem;
import domain.User;
import org.javatuples.Pair;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class UIUtils {

    CloudUtils cloudUtils = new CloudUtils();
    DatabaseUtils databaseUtils;

    public UIUtils() throws SQLException {
        databaseUtils = new DatabaseUtils();
    }

    public static PieDataset convertPairsToPieDataset(List<Pair<String, Integer>> pairs) {
        DefaultPieDataset result = new DefaultPieDataset();
        for (Pair<String, Integer> p : pairs) {
            result.insertValue(result.getItemCount(), p.getValue0(), p.getValue1());
        }
        return result;
    }

    public static List<Pair<String, Integer>> convertListOfToDosToListOfPairs(List<ToDoItem> toDoItems){
        List<Pair<String, Integer>> pairsResult = new LinkedList<>();
        int numCompleted = 0;
        int numInProgress = 0;
        int numSnoozed = 0;

        for (ToDoItem item : toDoItems){
            String status = item.getStatus().toLowerCase();
            if (status.equals("completed")){
                numCompleted+=1;
            }else if(status.equals("in-progress")){
                numInProgress+=1;
            }else if(status.equals("snoozed")){
                numSnoozed+=1;
            }else{
            }
        }
        //Adding the tuples based on values made above.
        pairsResult.add(0, new Pair<>("completed", numCompleted));
        pairsResult.add(1, new Pair<>("in-progress", numInProgress));
        pairsResult.add(2, new Pair<>("snoozed", numSnoozed));

        return pairsResult;
    }

    public List<String> syncCloudAndLocalToDatabase(User user) {
        List<ToDoItem> itemList = new LinkedList<>();
        List<String> responseList = new LinkedList<>();
        if(cloudUtils.checkConnection()){
            itemList = cloudUtils.readCloud();
        }
        if(!user.getToDoItemList().isEmpty()){
            itemList.addAll(user.getToDoItemList());
        }
        if (itemList == null){
            responseList.add("No items to sync");
            return responseList;
        }
        for (ToDoItem item : itemList){
            responseList.add(databaseUtils.addItemToDatabase(item));
        }
        return responseList;
    }

    public void updateTableDataFromSources(DefaultTableModel tableData, User user) {
        List<ToDoItem> list;
        List<ToDoItem> cloudList = cloudUtils.readCloud();
        list = removeDuplicateToDoItems(cloudList,user.toDoList);
        list = removeDuplicateToDoItems(list,databaseUtils.readDatabase());
        if (list == null){
            return;
        }
        if (tableData.getRowCount() == 0){
            for (ToDoItem item : list){
                tableData.addRow(new Object[]{item.id, item.about, item.itemCategory, item.status, item.dueDate});
            }
        }
        else{
            clearTable(tableData);
            for (ToDoItem item : list){
                tableData.addRow(new Object[]{item.id, item.about, item.itemCategory, item.status, item.dueDate});
            }
        }
        tableData.fireTableStructureChanged();
    }

    private List<ToDoItem> removeDuplicateToDoItems(List<ToDoItem> list1, List<ToDoItem> list2) {
        ArrayList<ToDoItem> itemList = new ArrayList<>();
        if(list1 == null || list2 == null){
            if(list1 == null && list2 == null){ return null; }
            if(list1 == null){ return list2; }
            else{ return list1; }
        }
        itemList.addAll(list1);
        itemList.addAll(list2);
        ArrayList<ToDoItem> noDuplicateList = new ArrayList<>();
        for (ToDoItem element : itemList){
            if(!noDuplicateList.contains(element)){
                noDuplicateList.add(element);
            }
        }
        return noDuplicateList;
    }

    private void clearTable(DefaultTableModel tableData) {
        int rows = tableData.getRowCount();
        for (int i = 0; i<rows; i++){
            tableData.removeRow(0);
        }
    }

    public String removeSelectedToDoItemFromSource(DefaultTableModel tableData, User user, int selectedRow) {
        int toDoItemID = (Integer) tableData.getValueAt(selectedRow,0);
        String cloudResponse = cloudUtils.deleteSingleItem(toDoItemID);
        String localResponse = user.deleteToDoItem(toDoItemID);
        String databaseReponse = databaseUtils.deleteSingleItem(toDoItemID);
        return String.format("%s\n%s\n%s\n",cloudResponse, localResponse, databaseReponse);
    }

    public ToDoItem getSelectedToDoItemFromSource(DefaultTableModel tableData, User user, int selectedRow) {
        int toDoItemID = (Integer)tableData.getValueAt(selectedRow,0);
        ToDoItem chosenToDo = null;
        if (cloudUtils.checkConnection()){
            chosenToDo = getFromList(cloudUtils.readCloud(), toDoItemID);
        }
        if (chosenToDo == null){
            chosenToDo = getFromList(user.getToDoItemList(), toDoItemID);
        }
        if (chosenToDo == null){
            chosenToDo = getFromList(databaseUtils.readDatabase(), toDoItemID);
        }
        return chosenToDo;
    }

    private ToDoItem getFromList(List<ToDoItem> list, int identifier) {
        ToDoItem itemToReturn = null;
        for (ToDoItem item : list) {
            if (item.id == identifier) {
                itemToReturn = item;
            }
        }
        return itemToReturn;
    }

    public String makeToDoItemInLocation(User user, ToDoItem newToDo) {
        if(cloudUtils.checkConnection()){
            cloudUtils.uploadItemToCloud(newToDo);
            return "Item saved to cloud";
        } else {
            user.addToDoItemtoList(newToDo);
            return "Item saved to local";
        }
    }

}