package ui;

import model.Task;

import javax.swing.*;
import java.awt.*;


public class TodoListGUI  extends JPanel {


    private DefaultListModel<Task> list;
    private JList completeList;


    private static final String addString = "Add Task";
    private static final String completeString = "Complete Task";
    private JButton completeButton;
    private JButton addButton;
    private JTextField taskField;

    public TodoListGUI() {

        JFrame frame = new JFrame("GET-IT-DONE");
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        JMenuBar menuBar = new JMenuBar();
        JMenu menuFile = new JMenu("FILE");
        menuBar.add(menuFile);
        JMenuItem saveButton = new JMenuItem("Save");
        JMenuItem loadButton = new JMenuItem("Load");
        menuFile.add(saveButton);
        menuFile.add(loadButton);

        JPanel bottomPanel = new JPanel();
        JLabel label = new JLabel("Task:");
        JTextField textField = new JTextField(15);

        completeButton = new JButton(completeString);
        addButton = new JButton(addString);


        bottomPanel.add(label);
        bottomPanel.add(textField);
        bottomPanel.add(addButton);
        bottomPanel.add(completeButton);

        JPanel topPanel = new JPanel();

        JButton allTasksButton = new JButton("All Tasks");
        JButton completedTasksButton = new JButton("Completed Tasks");
        JButton incompleteTasksButton = new JButton("Incomplete Tasks");

        topPanel.add(allTasksButton);
        topPanel.add(completedTasksButton);
        topPanel.add(incompleteTasksButton);


        frame.getContentPane().add(BorderLayout.NORTH, topPanel);

        frame.getContentPane().add(BorderLayout.SOUTH, bottomPanel);

        frame.setVisible(true);







    }

    public void makeMenuBar() {


    }






}
