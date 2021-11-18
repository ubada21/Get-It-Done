package ui;

import model.Task;
import model.TodoList;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;


// TODO: make popup for IOExceptions, sound and image when completing task.
public class TodoListGUI  extends JPanel {



    private JTable table;
    private DefaultTableModel model;

    private static final String JSON_STORE = "./data/todolist.json";
    private static final String addString = "Add Task";
    private static final String completeString = "Complete Task";
    private static final String removeString = "Remove Task";
    private JButton completeButton;
    private JButton addButton;
    private JButton removeButton;
    private JMenuItem saveButton;
    private JMenuItem loadButton;
    private JTextField textField;
    private TodoList todoList;

    public TodoListGUI() {

        JFrame frame = new JFrame("GET-IT-DONE");
        frame.setSize(575, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel bottomPanel = new JPanel();

        JMenuBar menuBar = getJMenuBar();

        JLabel label = new JLabel("Task:");
        String[] columnNames = {"Task", "Status"};
        Object[][] data = {};

        model = new DefaultTableModel(data, columnNames);
        table = new JTable();
        table.setModel(model);

        AddListener addListener = initButtons();

        initTextField(addListener);

        addToBottomPanel(bottomPanel, label);

        JPanel topPanel = new JPanel();

        JButton completedTasksButton = new JButton("Completed Tasks");
        JButton incompleteTasksButton = new JButton("Incomplete Tasks");

        addToTopPanel(topPanel, completedTasksButton, incompleteTasksButton);

        JScrollPane scrollPane = new JScrollPane(table);

        addToContentPane(frame, bottomPanel, menuBar, scrollPane);

        frame.setVisible(true);
    }

    private void addToTopPanel(JPanel topPanel, JButton completedTasksButton, JButton incompleteTasksButton) {
        topPanel.add(completedTasksButton);
        topPanel.add(incompleteTasksButton);
    }

    private void addToContentPane(JFrame frame, JPanel bottomPanel, JMenuBar menuBar, JScrollPane scrollPane) {
        frame.getContentPane().add(BorderLayout.NORTH, menuBar);
        frame.getContentPane().add(BorderLayout.CENTER, scrollPane);
        frame.getContentPane().add(BorderLayout.SOUTH, bottomPanel);
    }

    private JMenuBar getJMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menuFile = new JMenu("FILE");
        menuBar.add(menuFile);
        saveButton = new JMenuItem("Save");
        loadButton = new JMenuItem("Load");
        menuFile.add(saveButton);
        menuFile.add(loadButton);
        return menuBar;
    }

    private void addToBottomPanel(JPanel bottomPanel, JLabel label) {
        bottomPanel.add(label);
        bottomPanel.add(textField);
        bottomPanel.add(addButton);
        bottomPanel.add(completeButton);
        bottomPanel.add(removeButton);
    }

    private void initTextField(AddListener addListener) {
        textField = new JTextField(15);
        textField.addActionListener(addListener);
        textField.getDocument().addDocumentListener(addListener);
    }

    private AddListener initButtons() {
        saveButton.setActionCommand("save");
        saveButton.addActionListener(new SaveListener());

        loadButton.setActionCommand("load");
        loadButton.addActionListener(new LoadListener());

        completeButton = new JButton(completeString);
        completeButton.setActionCommand(completeString);
        completeButton.addActionListener(new CompleteListener());

        addButton = new JButton(addString);
        AddListener addListener = new AddListener(addButton);
        addButton.setActionCommand(addString);
        addButton.addActionListener(addListener);

        removeButton = new JButton(removeString);
        RemoveListener removeListener = new RemoveListener();
        removeButton.setActionCommand(removeString);
        removeButton.addActionListener(removeListener);
        return addListener;
    }


    class AddListener implements ActionListener, DocumentListener {

        private boolean alreadyEnabled = false;
        private JButton button;

        public AddListener(JButton button) {
            this.button = button;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String data = textField.getText();

            if (data.equals("")) {
                textField.requestFocusInWindow();
                textField.selectAll();
                return;
            }

            model.addRow(new Object[]{data, "Incomplete"});


            textField.requestFocusInWindow();
            textField.setText("");
        }

        @Override
        public void insertUpdate(DocumentEvent e) {
            enableButton();

        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            handleEmptyTextField(e);

        }

        @Override
        public void changedUpdate(DocumentEvent e) {

            if (!(handleEmptyTextField(e))) {
                enableButton();
            }

        }

        public boolean handleEmptyTextField(DocumentEvent e) {
            if (e.getDocument().getLength() <= 0) {
                button.setEnabled(false);
                alreadyEnabled = false;
                return true;
            }
            return false;
        }

        private void enableButton() {
            if (!alreadyEnabled) {
                button.setEnabled(true);
            }
        }
    }


    class CompleteListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            int row = table.getSelectedRow();

            model.setValueAt("Complete", row, 1);

        }
    }

    class RemoveListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            model.removeRow(table.getSelectedRow());
        }
    }

    class SaveListener implements ActionListener {
        TodoList todoList;
        JsonWriter jsonWriter;


        @Override
        public void actionPerformed(ActionEvent e) {

            todoList = new TodoList();
            jsonWriter = new JsonWriter(JSON_STORE);
            for (int i = 0; i < table.getRowCount(); i++) {
                Task task = new Task(table.getValueAt(i, 0).toString(), i, getBool(table.getValueAt(i, 1).toString()));
                todoList.addTask(task);
            }

            try {
                jsonWriter.open();
                jsonWriter.write(todoList);
                jsonWriter.close();
                System.out.println("Saved TodoList to " + JSON_STORE);
            } catch (FileNotFoundException f) {
                System.out.println("Unable to write to file " + JSON_STORE);
            }
        }


    }

    class LoadListener implements ActionListener {
        TodoList todoList;


        @Override
        public void actionPerformed(ActionEvent e) {

            model.setRowCount(0);

            JsonReader jsonReader = new JsonReader(JSON_STORE);
            try {
                todoList = jsonReader.read();
                for (Task t : todoList.getAllTasks()) {
                    model.addRow(new Object[]{t.getLabel(), t.getStatus()});
                }
                System.out.println("Loaded Todolist from " + JSON_STORE);
            } catch (IOException f) {
                System.out.println("Unable to read from file: " + JSON_STORE);
            }
        }
    }

    private boolean getBool(String status) {
        return status.equals("Complete");
    }

}
