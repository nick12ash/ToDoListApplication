package utils;

import domain.ToDoItem;
import org.javatuples.Pair;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import java.util.LinkedList;
import java.util.List;

public class UIUtils {

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

}