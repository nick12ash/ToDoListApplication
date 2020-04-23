package UI;

import domain.TimeStamp;
import domain.ToDoItem;
import domain.User;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.util.Rotation;
import utils.CloudUtils;
import utils.UIUtils;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class ToDoListApplicationUI extends JFrame implements ActionListener{

    private JScrollPane JScrollPane;
    private CloudUtils cloud = new CloudUtils();
    private UIUtils uiUtils;
    private User user;
    private DefaultTableModel tableData;

    public ToDoListApplicationUI() throws SQLException {
        UIManager.put("Label.font", new FontUIResource(new Font("Arial", Font.BOLD, 20)));
        UIManager.put("Button.font", new FontUIResource(new Font("Dialog", Font.PLAIN, 20)));

        // Gets User of To-Do-Application
        String userName = JOptionPane.showInputDialog(null,
                "ToDoApplication User",
                "Enter your name",
                JOptionPane.QUESTION_MESSAGE);
        user = new User(userName);
        uiUtils = new UIUtils(user);

        JPanel panel = new JPanel();
        GridBagLayout gridBagLayout = new GridBagLayout();
        panel.setLayout(gridBagLayout);
        setContentPane(panel);

        ///Title Label
        JLabel title = new JLabel("To Do Application");
        var titleLabelConstraints = new GridBagConstraints(0, 0, 4, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.CENTER, new Insets(20, 1, 20, 1), 0, 0);
        panel.add(title, titleLabelConstraints);

        ///Table of To-Do-Items
        ///TableData tableData = new TableData();
        String[] columnNames = {"ID","Memo", "Category", "Status", "Due Date"};
        Object[][] data = {};
        tableData = new DefaultTableModel(data, columnNames);
        JTable toDoTable = new JTable(tableData);


        var toDoTableConstraints = new GridBagConstraints(0, 2, 4, 2, 2, 2, GridBagConstraints.BASELINE, GridBagConstraints.HORIZONTAL, new Insets(20, 20, 20, 20), 0, 200);
        JScrollPane = new JScrollPane(toDoTable);
        JScrollPane.setPreferredSize(new Dimension(600,400));
        panel.add(JScrollPane,toDoTableConstraints);


        ///Buttons
        ///MAKE TO-DO-ITEM BUTTON
        JButton makeToDoButton = new JButton("Make To-Do-Item");
        var makeToDoButtonConstraints = new GridBagConstraints(0, 3, 1, 1, 1, 1, GridBagConstraints.SOUTH, GridBagConstraints.CENTER, new Insets(20, 1, 20, 1), 0, 0);
        panel.add(makeToDoButton,makeToDoButtonConstraints);
        makeToDoButton.addActionListener(e -> {
            boolean creating = true;
            while (creating) {
                String memo = JOptionPane.showInputDialog(panel,
                        "What is the memo of the To-Do-Item",
                        "New To-Do-Item",
                        JOptionPane.QUESTION_MESSAGE);
                if (memo == null) {
                    break;
                }

                String dueDateYear = JOptionPane.showInputDialog(panel,
                        "What is the Due date year? ex: 2017",
                        "New To-Do-Item Due Date Year",
                        JOptionPane.QUESTION_MESSAGE);
                if (dueDateYear == null) {
                    break;
                }

                String dueDateMonth = JOptionPane.showInputDialog(panel,
                        "What is the Due date month? ex: 05",
                        "New To-Do-Item Due Date Month",
                        JOptionPane.QUESTION_MESSAGE);
                if (dueDateMonth == null) {
                    break;
                }

                String dueDateDay = JOptionPane.showInputDialog(panel,
                        "What is the Due date day? ex: 14",
                        "New To-Do-Item Due Date Day",
                        JOptionPane.QUESTION_MESSAGE);
                if (dueDateDay == null) {
                    break;
                }

                int submit = JOptionPane.showConfirmDialog(panel, "Is the info " +
                                "in this To-Do-Item correct?\n" + " [" + memo + " " + dueDateMonth + "/" + dueDateDay + "/" + dueDateYear + "]",
                        "To-Do-Item Confirmation", JOptionPane.YES_NO_OPTION);
                if (submit == 0) {
                    var newToDo = new ToDoItem(memo, user.name, new TimeStamp(Integer.parseInt(dueDateYear), Integer.parseInt(dueDateMonth), Integer.parseInt(dueDateDay)));
                    JOptionPane.showMessageDialog(null, uiUtils.makeToDoItemInLocation(newToDo));
                    uiUtils.updateTableDataFromSources(tableData);
                    JOptionPane.showMessageDialog(null, "You are one step closer to being productive");
                } else {
                    JOptionPane.showMessageDialog(null, "That's unfortunate...");
                }
                creating = false;
            }
        });
        ///VIEW TO-DO-ITEM BUTTON
        JButton viewToDoButton = new JButton("View To-Do-Item");
        var viewToDoButtonConstraints = new GridBagConstraints(1, 3, 1, 1, 1, 1, GridBagConstraints.SOUTH, GridBagConstraints.CENTER, new Insets(20, 1, 20, 1), 0, 0);
        panel.add(viewToDoButton,viewToDoButtonConstraints);
        viewToDoButton.addActionListener(e -> {
            if (toDoTable.getSelectionModel().isSelectionEmpty()) {
                JOptionPane.showMessageDialog(panel, "Select the To-Do-Item you would like to view in more detail.");
            } else {
                int selectedRow = toDoTable.getSelectedRow();
                ToDoItem selectedToDoItem = uiUtils.getSelectedToDoItemFromSource(tableData, selectedRow);
                JOptionPane.showMessageDialog(panel,"To-Do-Item Details: \n" + selectedToDoItem.toString());
                uiUtils.updateTableDataFromSources(tableData);
            }
        });

        ///EDIT TO-DO-ITEM BUTTON
        JButton editToDoButton = new JButton("Edit To-Do-Item");
        var editToDoButtonConstraints = new GridBagConstraints(2, 3, 1, 1, 1, 1, GridBagConstraints.SOUTH, GridBagConstraints.CENTER, new Insets(20, 1, 20, 1), 0, 0);
        panel.add(editToDoButton,editToDoButtonConstraints);
        editToDoButton.addActionListener(e -> {
            if (toDoTable.getSelectionModel().isSelectionEmpty()) {
                JOptionPane.showMessageDialog(panel, "Select the To-Do-Item you would like to edit.");
            }
            else {
                boolean editing = true;
                while (editing) {
                    int selectedRow = toDoTable.getSelectedRow();
                    ToDoItem item = uiUtils.getSelectedToDoItemFromSource(tableData, selectedRow);
                    String memo = (String) JOptionPane.showInputDialog(panel,
                            "Would you like to update the memo of the To-Do-Item",
                            "Update To-Do-Item Memo",
                            JOptionPane.QUESTION_MESSAGE, null, null, item.about);
                    if (memo == null) {
                        break;
                    }

                    String category = (String) JOptionPane.showInputDialog(panel,
                            "Would you like to update the category of the To-Do-Item",
                            "Update To-Do-Item Category",
                            JOptionPane.QUESTION_MESSAGE, null, null, item.itemCategory);
                    if (category == null) {
                        break;
                    }

                    Object[] statuses = {"In-Progress", "Snoozed", "Completed"};
                    String status = (String) JOptionPane.showInputDialog(panel,
                            "Would you like to update the status of the To-Do-Item.",
                            "Update To-Do-Item Status",
                            JOptionPane.QUESTION_MESSAGE, null, statuses, item.status);
                    if (status == null) {
                        break;
                    } else {
                        if (status == "In-Progress") {
                            item.status = "In-Progress";
                        }
                        if (status == "Snoozed") {
                            item.status = "Snoozed";
                        }
                        if (status == "Completed") {
                            item.status = "Completed";
                        }
                    }

                    int updateDeadline = JOptionPane.showConfirmDialog(panel,
                            "Would you like to update the deadline of the To-Do-Item",
                            "Update To-Do-Item Deadline",
                            JOptionPane.YES_NO_OPTION);

                    TimeStamp newDueDate;
                    if (updateDeadline == 0) {
                        int dueDateYear = Integer.parseInt(JOptionPane.showInputDialog(panel,
                                "What is the Due date year? ex: 2017",
                                "Update To-Do-Item Due Date Year",
                                JOptionPane.QUESTION_MESSAGE));
                        int dueDateMonth = Integer.parseInt(JOptionPane.showInputDialog(panel,
                                "What is the Due date month? ex: 05",
                                "Update To-Do-Item Due Date Month",
                                JOptionPane.QUESTION_MESSAGE));
                        int dueDateDay = Integer.parseInt(JOptionPane.showInputDialog(panel,
                                "What is the Due date day? ex: 14",
                                "Update To-Do-Item Due Date Day",
                                JOptionPane.QUESTION_MESSAGE));
                        newDueDate = new TimeStamp(dueDateYear, dueDateMonth, dueDateDay);
                    } else {
                        newDueDate = new TimeStamp(item.dueDate);
                        JOptionPane.showMessageDialog(panel, "Your " + "[ " + item.about + " ]" + " To-Do-Item has been updated.");
                    }
                    ToDoItem newToDoItem = new ToDoItem(memo, item.owner, newDueDate, new TimeStamp(item.createdDate), status, category, item.id);
                    uiUtils.removeSelectedToDoItemFromAll(tableData, selectedRow);
                    uiUtils.makeToDoItemInLocation(newToDoItem);
                    uiUtils.updateTableDataFromSources(tableData);
                    editing = false;
                }
            }
        });
        ///DELETE TO-DO-ITEM BUTTON
        JButton deleteToDoButton = new JButton("Delete To-Do-Item");
        var deleteToDoButtonConstraints = new GridBagConstraints(3, 3, 1, 1, 1, 1, GridBagConstraints.SOUTH, GridBagConstraints.CENTER, new Insets(20, 1, 20, 1), 0, 0);
        panel.add(deleteToDoButton,deleteToDoButtonConstraints);
        deleteToDoButton.addActionListener(e -> {
            if (toDoTable.getSelectionModel().isSelectionEmpty()) {
                JOptionPane.showMessageDialog(panel, "Select the To-Do-Item you would like to delete.");
            } else {
                int selectedRow = toDoTable.getSelectedRow();
                String removeMessage;
                JCheckBox cloud = new JCheckBox("Cloud");
                JCheckBox local = new JCheckBox("Local");
                JCheckBox database = new JCheckBox("Database");
                JCheckBox all = new JCheckBox("All");
                Object[] options = {cloud,local,database,all,"OK"};
                JOptionPane.showOptionDialog(panel, "Delete item from which location?", "Choose location", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
                if(all.isSelected()){
                    removeMessage = uiUtils.removeSelectedToDoItemFromAll(tableData, selectedRow);
                }
                else if(cloud.isSelected()){
                    removeMessage = uiUtils.removeSelectedToDoItemFromCloud(tableData, selectedRow);
                }
                else if(database.isSelected()){
                    removeMessage = uiUtils.removeSelectedToDoItemFromDatabase(tableData, selectedRow);
                }
                else if(local.isSelected()){
                    removeMessage = uiUtils.removeSelectedToDoItemFromLocal(tableData,selectedRow);
                }
                else{
                    removeMessage = "Select a place to delete from";
                }
                JOptionPane.showMessageDialog(panel,removeMessage);
                uiUtils.updateTableDataFromSources(tableData);
            }
        });

        //SYNC BUTTON
        JButton syncButton = new JButton("Sync");
        var syncButtonConstrains = new GridBagConstraints(0,4,1,1,1,1,GridBagConstraints.SOUTH,GridBagConstraints.CENTER,new Insets(20,1,20,1),0,0);
        panel.add(syncButton,syncButtonConstrains);
        syncButton.addActionListener(e ->{
            List<String> syncItemResponses = uiUtils.syncCloudAndLocalToDatabase();
            JOptionPane.showMessageDialog(panel,syncItemResponses);
            uiUtils.updateTableDataFromSources(tableData);
        });

        //REMINDER BUTTON
        JButton reminderButton = new JButton("Remind me!");
        var reminderButtonConstrains = new GridBagConstraints(1,4,1,1,1,1,GridBagConstraints.SOUTH,GridBagConstraints.CENTER,new Insets(20,1,20,1),0,0);
        panel.add(reminderButton,reminderButtonConstrains);
        reminderButton.addActionListener(e ->{
            String reminderMessage = null;
            JCheckBox mostUrgent = new JCheckBox("Most urgent reminder");
            JCheckBox mostOld = new JCheckBox("Oldest reminder");
            JCheckBox both = new JCheckBox("Both");
            Object[] options = {mostUrgent,mostOld,both,"OK"};
            JOptionPane.showOptionDialog(panel, "What kind of reminder do you want?", "Choose reminder type", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
            if((mostUrgent.isSelected() && mostOld.isSelected()) || both.isSelected()){
                reminderMessage = uiUtils.getBothRemindersMessage();
            }
            else if(mostUrgent.isSelected()){
                reminderMessage = uiUtils.getMostUrgentReminderMessage();
            }
            else if(mostOld.isSelected()){
                reminderMessage = uiUtils.getOldestReminderMessage();
            }

            JOptionPane.showMessageDialog(panel, reminderMessage);

        });

        //SHOW PIE CHART BUTTON
        JButton showPieChartButton = new JButton("Show Pie Chart");
        var showPieChartButtonConstraints = new GridBagConstraints(2, 4, 1, 1, 1, 1, GridBagConstraints.SOUTH, GridBagConstraints.CENTER, new Insets(20, 1, 20, 1), 0, 0);
        panel.add(showPieChartButton,showPieChartButtonConstraints);
        showPieChartButton.addActionListener(e -> {
            if(toDoTable.size().equals(0)){
                JOptionPane.showMessageDialog(panel, "There are no To-Do-Items to show data for. Try adding one first!");
            }else{
                JFreeChart chart = null;
                try {
                    chart = ChartFactory.createPieChart3D(
                            "Data by Status",                  // chart title
                            cloud.getPieData(user),                // data
                            true,                   // include legend
                            true,
                            false
                    );
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(panel, "Something went wrong with converting the data to create the pie chart.");
                }
                PiePlot3D plot = (PiePlot3D) chart.getPlot();
                plot.setStartAngle(290);
                plot.setDirection(Rotation.CLOCKWISE);
                plot.setForegroundAlpha(0.5f);
                ChartPanel chartPanel = new ChartPanel(chart);
                // default size
                chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
                // add it to our application
                setContentPane(chartPanel);
                setDefaultCloseOperation(EXIT_ON_CLOSE);
                pack();
                setVisible(true);
                //Displays message containing details and numbers on chart.
                Object[] options = {"OK"};
                JOptionPane.showOptionDialog(null, cloud.calculateTotalCategoriesAndTotalItems() + "\nClick OK to go back."
                        , "Pie Chart Details", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
                //Now that we're finished, we back to the main UI.
                panel.setLayout(gridBagLayout);
                setContentPane(panel);
            }
        });
      
        //Read data from cloud for table before window open
        uiUtils.updateTableDataFromSources(tableData);


        ///Application Window
        setPreferredSize(new Dimension(1000, 600));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    public static void main(String[] args) throws SQLException {
        new ToDoListApplicationUI();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
