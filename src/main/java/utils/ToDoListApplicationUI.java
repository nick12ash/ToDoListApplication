package utils;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ToDoListApplicationUI extends JFrame implements ActionListener{
    JLabel displayLabel;
    private Object JButton;

    public ToDoListApplicationUI() {
        UIManager.put("Label.font", new FontUIResource(new Font("Arial", Font.PLAIN, 20)));
        UIManager.put("Button.font", new FontUIResource(new Font("Dialog", Font.PLAIN, 20)));

        // Gets User of To-Do-Application
        String userName = JOptionPane.showInputDialog(null,
                "ToDoApplication User",
                "Enter your name",
                JOptionPane.QUESTION_MESSAGE);

        JPanel panel = new JPanel();
        GridBagLayout gridBagLayout = new GridBagLayout();
        panel.setLayout(gridBagLayout);
        setContentPane(panel);

        ///Title Label
        JLabel title = new JLabel("To Do Application");
        var titleLabelConstraints = new GridBagConstraints(0, 0, 4, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.CENTER, new Insets(20, 1, 20, 1), 0, 0);
        panel.add(title, titleLabelConstraints);


        ///Table of To-Do-Items
        String[] columnNames = {"Memo", "Category", "Status", "Due Date"};
        Object[][] data = {{"This is", "where the", "to-do-items go", "Like Here!"}};
        JTable toDoTable = new JTable(data, columnNames);
        var toDoTableConstraints = new GridBagConstraints(0, 2, 10, 2, 2, 2, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(20, 20, 20, 20), 0, 0);
        panel.add(toDoTable,toDoTableConstraints);


        ///Buttons
        JButton makeToDoButton = new JButton("Make To-Do-Item");
        var makeToDoButtonConstraints = new GridBagConstraints(0, 3, 1, 1, 1, 1, GridBagConstraints.SOUTH, GridBagConstraints.CENTER, new Insets(20, 1, 20, 1), 0, 0);
        panel.add(makeToDoButton,makeToDoButtonConstraints);

        JButton viewToDoButton = new JButton("View To-Do-Item");
        var viewToDoButtonConstraints = new GridBagConstraints(1, 3, 1, 1, 1, 1, GridBagConstraints.SOUTH, GridBagConstraints.CENTER, new Insets(20, 1, 20, 1), 0, 0);
        panel.add(viewToDoButton,viewToDoButtonConstraints);

        JButton editToDoButton = new JButton("Edit To-Do-Item");
        var editToDoButtonConstraints = new GridBagConstraints(2, 3, 1, 1, 1, 1, GridBagConstraints.SOUTH, GridBagConstraints.CENTER, new Insets(20, 1, 20, 1), 0, 0);
        panel.add(editToDoButton,editToDoButtonConstraints);

        JButton deleteToDoButton = new JButton("Edit To-Do-Item");
        var deleteToDoButtonConstraints = new GridBagConstraints(3, 3, 1, 1, 1, 1, GridBagConstraints.SOUTH, GridBagConstraints.CENTER, new Insets(20, 1, 20, 1), 0, 0);
        panel.add(deleteToDoButton,deleteToDoButtonConstraints);


        ///Application Window
        setPreferredSize(new Dimension(800, 600));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    public static void main(String[] args) {
        new ToDoListApplicationUI();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
