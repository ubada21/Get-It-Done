package ui;

import model.Task;
import model.TodoList;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

// To-do List application
public class TodoListApp {
    private static final String JSON_STORE = "./data/todolist.json";
    private TodoList todoList;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: runs the to-do list application
    public TodoListApp() throws FileNotFoundException {
        input = new Scanner(System.in);
        todoList = new TodoList();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runTodoList();
    }

    // based on runTeller method from AccountNotRobust
    // MODIFIES: this
    // EFFECTS: processes user input
    private void runTodoList() {
        boolean keepGoing = true;
        String command;

        init();

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nGoodBye");
    }

    // based on init method from AccountNotRobust
    // MODIFIES: this
    // EFFECTS: initializes new Todo list.
    private void init() {
        todoList = new TodoList();
        input = new Scanner(System.in);
        input.useDelimiter(System.lineSeparator());
    }

    // processCommand method based on processMethod from AccountNotRobust
    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("a")) {
            addToTodoList();
        } else if (command.equals("c")) {
            markTaskComplete();
        } else if (command.equals("r")) {
            removeTaskFromTodoList();
        } else if (command.equals("nc")) {
            showNumberComplete();
        } else if (command.equals("nic")) {
            showNumberIncomplete();
        } else if (command.equals("s")) {
            showAllTasks();
        } else if (command.equals("sc")) {
            showCompletedTasks();
        } else if (command.equals("sic")) {
            showInCompleteTasks();
        } else if (command.equals("save")) {
            saveTodolist();
        } else if (command.equals("load")) {
            loadTodolist();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // EFFECTS: saves TodoList to file.
    private void saveTodolist() {
        try {
            jsonWriter.open();
            jsonWriter.write(todoList);
            jsonWriter.close();
            System.out.println("Saved TodoList to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file " + JSON_STORE);
        }
    }

    // EFFECTS: loads TodoList from file.
    private void loadTodolist() {
        try {
            todoList = jsonReader.read();
            System.out.println("Loaded Todolist from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // displayMenu method based on displayMenu from AccountNotRobust
    // EFFECTS: displays menu to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\ta -> add task");
        System.out.println("\tc -> complete task");
        System.out.println("\tr -> remove task");
        System.out.println("\tnc -> number of completed tasks");
        System.out.println("\tnic -> number of incomplete tasks");
        System.out.println("\ts -> show all tasks");
        System.out.println("\tsc -> show completed tasks");
        System.out.println("\tsic -> show incomplete tasks");
        System.out.println("\tsave -> save todo list");
        System.out.println("\tload -> load todo list");
        System.out.println("\tq -> quit");
    }

    // EFFECTS: shows all tasks to user
    private void showAllTasks() {

        for (Task task : todoList.getAllTasks()) {
            System.out.println(task.getID() + ": " + task.getLabel() + " (" + task.getStatus() + ")");
        }
    }

    // EFFECTS: shows all completed tasks to user
    private void showCompletedTasks() {

        for (Task task : todoList.getCompletedTasks()) {
            System.out.println(task.getID() + ": " + task.getLabel() + " (" + task.getStatus() + ")");
        }
    }

    // EFFECTS: shows all Incomplete tasks to user
    private void showInCompleteTasks() {

        for (Task task : todoList.getIncompleteTasks()) {
            System.out.println(task.getID() + ": " + task.getLabel() + " (" + task.getStatus() + ")");
        }
    }

    // EFFECTS: shows user number of incomplete tasks
    private void showNumberIncomplete() {
        System.out.println(todoList.getNumberOfInCompleteTasks());
    }


    // EFFECTS: shows user number of complete tasks
    private void showNumberComplete() {
        System.out.println(todoList.getNumberOfCompletedTasks());
    }

    // MODIFIES: this
    // EFFECTS: removes task with given ID from todoList.
    private void removeTaskFromTodoList() {
        System.out.println("Enter Task ID to remove: ");
        int id = input.nextInt();

        todoList.removeTask(id);

        System.out.println("Task successfully removed.");
    }


    // MODIFIES: this
    // EFFECTS: marks task with given id to be Complete
    private void markTaskComplete() {
        System.out.println("Enter Task ID to mark as complete: ");
        int id = input.nextInt();

        todoList.completeTask(id);

        System.out.println("Task successfully completed.");
    }

    // MODIFIES: this
    // EFFECTS: adds a task to the TodoList.
    private void addToTodoList() {
        System.out.println("Enter Task ID:");
        int id = input.nextInt();

        System.out.println("Enter Task Label:");
        String label = input.next();

        Task task = new Task(label, id);

        todoList.addTask(task);

        System.out.println("Task Successfully added.");
    }

}


