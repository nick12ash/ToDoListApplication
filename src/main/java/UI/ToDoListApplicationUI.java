package UI;

import domain.TimeStamp;
import domain.ToDoItem;
import domain.User;
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

    public ToDoListApplicationUI() throws SQLException {
        UIManager.put("Label.font", new FontUIResource(new Font("Arial", Font.PLAIN, 20)));
        UIManager.put("Button.font", new FontUIResource(new Font("Dialog", Font.PLAIN, 20)));

        // Gets User of To-Do-Application
        String userName = JOptionPane.showInputDialog(null,
                "ToDoApplication User",
                "Enter your name",
                JOptionPane.QUESTION_MESSAGE);
        User user = new User(userName);

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
        String[] columnNames = {"Memo", "Category", "Status", "Due Date"};
        Object[][] data = {};

        DefaultTableModel tableData = new DefaultTableModel(data, columnNames);


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
            String memo = JOptionPane.showInputDialog(panel,
                    "What is the memo of the To-Do-Item",
                    "New To-Do-Item",
                    JOptionPane.QUESTION_MESSAGE);

            int dueDateYear = Integer.parseInt(JOptionPane.showInputDialog(panel,
                    "What is the Due date year? ex: 2017",
                    "New To-Do-Item Due Date Year",
                    JOptionPane.QUESTION_MESSAGE));

            int dueDateMonth = Integer.parseInt(JOptionPane.showInputDialog(panel,
                    "What is the Due date month? ex: 05",
                    "New To-Do-Item Due Date Month",
                    JOptionPane.QUESTION_MESSAGE));

            int dueDateDay = Integer.parseInt(JOptionPane.showInputDialog(panel,
                    "What is the Due date day? ex: 14",
                    "New To-Do-Item Due Date Day",
                    JOptionPane.QUESTION_MESSAGE));

            int submit = JOptionPane.showConfirmDialog(panel, "Is the info " +
                            "in this To-Do-Item correct?\n"+ " [" + memo + " " +dueDateMonth + "/" + dueDateDay + "/" + dueDateYear + "]",
                    "To-Do-Item Confirmation", JOptionPane.YES_NO_OPTION);
            if (submit == 0) {
                var newToDo = new ToDoItem(memo, user.name, new TimeStamp(dueDateYear, dueDateMonth, dueDateDay));
                user.toDoList.add(newToDo);
                tableData.addRow(new Object[]{newToDo.about, newToDo.itemCategory, newToDo.status, newToDo.dueDate});
                tableData.fireTableStructureChanged();
                JOptionPane.showMessageDialog(null, "You are one step closer to being productive");
            } else {
                JOptionPane.showMessageDialog(null, "That's unfortunate...");
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
                Object selectedToDoItem = tableData.getValueAt(selectedRow, 0);
                List<ToDoItem> list = user.getToDoItemList();
                for (ToDoItem item : list) {
                    if (selectedToDoItem == item.about) {
                        selectedToDoItem = item.toString();
                        JOptionPane.showMessageDialog(panel,"To-Do-Item Details: \n" + selectedToDoItem);
                    }
                }
            }
        });

        ///EDIT TO-DO-ITEM BUTTON
        JButton editToDoButton = new JButton("Edit To-Do-Item");
        var editToDoButtonConstraints = new GridBagConstraints(2, 3, 1, 1, 1, 1, GridBagConstraints.SOUTH, GridBagConstraints.CENTER, new Insets(20, 1, 20, 1), 0, 0);
        panel.add(editToDoButton,editToDoButtonConstraints);
        editToDoButton.addActionListener(e -> {
            if (toDoTable.getSelectionModel().isSelectionEmpty()) {
                JOptionPane.showMessageDialog(panel, "Select the To-Do-Item you would like to edit.");
            } else {
                int selectedRow = toDoTable.getSelectedRow();
                Object selectedToDoItem = tableData.getValueAt(selectedRow, 0);
                List<ToDoItem> list = user.getToDoItemList();
                for (ToDoItem item : list) {
                    if (selectedToDoItem == item.about) {
                        selectedToDoItem = item;
                        String memo = (String) JOptionPane.showInputDialog(panel,
                                "Would you like to update the memo of the To-Do-Item",
                                "Update To-Do-Item Memo",
                                JOptionPane.QUESTION_MESSAGE, null, null, item.about);
                        item.changeAbout(memo);
                        tableData.setValueAt(memo, selectedRow, 0);

                        String category = (String) JOptionPane.showInputDialog(panel,
                                "Would you like to update the category of the To-Do-Item",
                                "Update To-Do-Item Category",
                                JOptionPane.QUESTION_MESSAGE, null, null, item.itemCategory);
                        item.changeCategory(category);
                        tableData.setValueAt(memo, selectedRow, 1);

                        Object[] statuses = {"In-Progress", "Snoozed", "Completed"};
                        String status = (String)JOptionPane.showInputDialog(panel,
                                "Would you like to update the status of the To-Do-Item.",
                                "Update To-Do-Item Status",
                                JOptionPane.PLAIN_MESSAGE, null, statuses, item.status);
                        tableData.setValueAt(memo, selectedRow, 2);
                        //Still need to set status of the To-Do-Item via IF statement

                        int updateDeadline = JOptionPane.showConfirmDialog(panel,
                                "Would you like to update the deadline of the To-Do-Item",
                                "Update To-Do-Item Deadline",
                                JOptionPane.YES_NO_OPTION);

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
                            item.changeDueDate(new TimeStamp(dueDateYear, dueDateMonth, dueDateDay));
                            tableData.setValueAt(item.dueDate,selectedRow, 3);
                        } else  {
                            JOptionPane.showMessageDialog(panel,"Your " + item.about + " has been updated.");
                        }
                    }
                }
                tableData.fireTableCellUpdated(selectedRow,0);
                tableData.fireTableCellUpdated(selectedRow,1);
                tableData.fireTableCellUpdated(selectedRow,2);
                tableData.fireTableCellUpdated(selectedRow,3);
                tableData.fireTableStructureChanged();
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
                Object selectedToDoItem = tableData.getValueAt(selectedRow, 0);
                List<ToDoItem> list = user.getToDoItemList();
                for (ToDoItem item : list) {
                    if (selectedToDoItem == item.about) {
                        selectedToDoItem = item;
                        user.toDoList.remove(item);
                        JOptionPane.showMessageDialog(panel,"Your " + item.about + " has been deleted.");
                    }
                }
                tableData.removeRow(selectedRow);
                tableData.fireTableStructureChanged();
            }
        });

        ///Application Window
        setPreferredSize(new Dimension(800, 600));
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
