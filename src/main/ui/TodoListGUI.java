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


/**
 * Represents the Graphical User Interface for the todoList.
 */
public class TodoListGUI  extends JPanel {



    private JTable table;
    private MyTableModel model;

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
    private JFrame frame;
    private JLabel label;
    private JScrollPane scrollPane;
    private JMenuBar menuBar;
    private JPanel bottomPanel;

    /**
     * EFFECTS: Instantiates the GUI and puts everything on the main ContentPane
     */
    public TodoListGUI() {

        initFrame();

        String[] columnNames = {"Task", "Status"};
        Object[][] data = {};
        model = new MyTableModel(data, columnNames);
        table = new JTable();
        table.setModel(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        menuBar = getJMenuBar();
        bottomPanel = new JPanel();
        scrollPane = new JScrollPane(table);

        initSaveLoadComplete();

        addButton = new JButton(addString);
        AddListener addListener = new AddListener(addButton);
        addButton.setActionCommand(addString);
        addButton.addActionListener(addListener);

        removeButton = new JButton(removeString);
        RemoveListener removeListener = new RemoveListener();
        removeButton.setActionCommand(removeString);
        removeButton.addActionListener(removeListener);

        initTextField(addListener);

        addToBottomPanel(bottomPanel);

        initContentPane();

        frame.setVisible(true);
    }

    /**
     * Initializes the save, load, and complete buttons.
     */
    private void initSaveLoadComplete() {
        saveButton.setActionCommand("save");
        saveButton.addActionListener(new SaveListener());

        loadButton.setActionCommand("load");
        loadButton.addActionListener(new LoadListener());

        completeButton = new JButton(completeString);
        completeButton.setActionCommand(completeString);
        completeButton.addActionListener(new CompleteListener());
    }

    /**
     * Initializes the main frame for the program.
     */
    private void initFrame() {
        frame = new JFrame("GET-IT-DONE");
        frame.setSize(575, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    /**
     * MODIFIES: this
     * EFFECTS: Initializes contentPane by adding menuBar, scrollPane and bottomPanel to frame
     */
    private void initContentPane() {

        frame.getContentPane().add(BorderLayout.NORTH, menuBar);
        frame.getContentPane().add(BorderLayout.CENTER, scrollPane);
        frame.getContentPane().add(BorderLayout.SOUTH, bottomPanel);
    }

    /**
     * EFFECTS: Instantiates and returns a MenuBar with Save and Load Buttons.
     */
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

    /**
     * EFFECTS: adds buttons and TextField to the bottom most panel
     */
    private void addToBottomPanel(JPanel bottomPanel) {
        label = new JLabel("Task:");
        bottomPanel.add(label);
        bottomPanel.add(textField);
        bottomPanel.add(addButton);
        bottomPanel.add(completeButton);
        bottomPanel.add(removeButton);
    }

    /**
     * EFFECTS: initializes the textField
     * @param addListener handles what happens to textField after task is added.
     */
    private void initTextField(AddListener addListener) {
        textField = new JTextField(15);
        textField.addActionListener(addListener);
        textField.getDocument().addDocumentListener(addListener);
    }

    /**
     * Represents action to be taken when user wants to add a task to the todoList.
     */
    class AddListener implements ActionListener, DocumentListener {

        private boolean alreadyEnabled = false;
        private JButton button;

        AddListener(JButton button) {
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

        /**
         * EFFECTS: Disable button if textField is empty
         */
        public boolean handleEmptyTextField(DocumentEvent e) {
            if (e.getDocument().getLength() <= 0) {
                button.setEnabled(false);
                alreadyEnabled = false;
                return true;
            }
            return false;
        }

        // EFFECTS: enable addButton
        private void enableButton() {
            if (!alreadyEnabled) {
                button.setEnabled(true);
            }
        }
    }

    /**
     * Represents action to be taken when user wants to complete a todoList.
     */
    class CompleteListener implements ActionListener {
        JPanel panel;
        JLabel iconLabel;
        ImageIcon icon;

        @Override
        public void actionPerformed(ActionEvent e) {
            icon = new ImageIcon("./Media/fireworks-well-done.gif");

            int row = table.getSelectedRow();

            if (model.getRowCount() == 0) {

                JOptionPane.showMessageDialog(frame, "No task to complete.");

            } else if (row == -1) {

                JOptionPane.showMessageDialog(frame, "No task selected.");


            } else if ((table.getValueAt(row, 1)).equals("Complete")) {
                JOptionPane.showMessageDialog(frame, "Task is already completed.");
            } else {
                model.setValueAt("Complete", row, 1);

                JOptionPane.showMessageDialog(frame, centeredIcon(), "Task Completed", JOptionPane.PLAIN_MESSAGE);
            }


        }

        // EFFECTS: returns a JPanel with the icon centered in it.
        private JPanel centeredIcon() {
            iconLabel = new JLabel(icon);
            panel = new JPanel();
            panel.setLayout(new BorderLayout());
            panel.add(iconLabel, BorderLayout.CENTER);
            return panel;
        }


    }

    /**
     * Represents action to be taken when user wants to remove a task from todoList.
     */
    class RemoveListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int row = table.getSelectedRow();
            if (model.getRowCount() == 0) {
                JOptionPane.showMessageDialog(frame, "No tasks to remove.");
            } else if (row == -1) {
                JOptionPane.showMessageDialog(frame, "No task selected.");

            } else {
                model.removeRow(row);
            }

        }
    }

    /**
     * Represents action to be taken when user wants to save todoList.
     */
    class SaveListener implements ActionListener {
        TodoList todoList;
        JsonWriter jsonWriter;


        @Override
        public void actionPerformed(ActionEvent e) {

            todoList = new TodoList();
            jsonWriter = new JsonWriter(JSON_STORE);
            for (int i = 0; i < table.getRowCount(); i++) {
                Task task = new Task(table.getValueAt(i, 0).toString(), i,
                        getBool(table.getValueAt(i, 1).toString()));
                todoList.addTask(task);
            }

            try {
                jsonWriter.open();
                jsonWriter.write(todoList);
                jsonWriter.close();
                JOptionPane.showMessageDialog(frame, "Saved TodoList to " + JSON_STORE);
            } catch (FileNotFoundException f) {
                System.out.println();
                JOptionPane.showMessageDialog(frame, "Unable to write to file " + JSON_STORE);
            }


        }


        //EFFECTS: returns true is string is "Complete", else it returns false.
        private boolean getBool(String status) {
            return status.equals("Complete");
        }


    }

    /**
     * Represents action to be taken when user wants to load a saved todoList.
     */
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
                JOptionPane.showMessageDialog(frame, "Loaded Todolist from " + JSON_STORE);
            } catch (IOException f) {
                JOptionPane.showMessageDialog(frame, "Unable to read from file: " + JSON_STORE);
            }
        }
    }

    /**
     * A Table with un-editable cells.
     */
    class MyTableModel extends DefaultTableModel {

        //EFFECTS: initializes a table using DefaultTableModel
        MyTableModel(Object[][] tableData, Object[] colNames) {
            super(tableData, colNames);
        }


        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    }



}
