package utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import domain.ToDoItem;
import org.javatuples.Pair;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.util.Rotation;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import javax.swing.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
        for (ToDoItem item : toDoItems){
            String status = item.getStatus().toLowerCase();
            if (pairsResult.contains(status)){
                int index = pairsResult.indexOf(status);
                Pair<String, Integer> toBeUpdated = pairsResult.remove(index);
                toBeUpdated.setAt1(toBeUpdated.getValue1()+1);
                pairsResult.add(toBeUpdated);
            }else{
                pairsResult.add(new Pair<>(status, 1));
            }
        }
        return pairsResult;
    }
}