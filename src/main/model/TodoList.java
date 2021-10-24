package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;


//

// represents a To-do list with a max size of 20
public class TodoList implements Writable {
    public static final int MAX_SIZE = 20;
    public final ArrayList<Task> todoList;

    // Initializes an empty TaskList.
    public TodoList() {
        this.todoList = new ArrayList<>();
    }

    /*
     * REQUIRES:
     * MODIFIES: this
     * EFFECTS: is task with given id already exists in todoList or toodoList size == MAXSIZE, return false
     * else, adds the given task into the TaskList and returns true
     */
    public Boolean addTask(Task task) {
        if (!(this.containsTask(task))) {

            if (this.getSize() < MAX_SIZE) {
                todoList.add(task);
                return true;
            }
            return false;
        }

        return false;
    }

    /*
     * REQUIRES: Task with given ID exists in TodoList
     * MODIFIES: this
     * EFFECTS: Removes the given task from todoList
     */
    public void removeTask(int id) {

        todoList.removeIf(task -> task.getID() == id);
    }

    /*
     * EFFECTS: returns number of Tasks in todoList
     */
    public int getSize() {
        return todoList.size();
    }

    /*
     * EFFECTS: return number of incomplete Tasks in todoList
     */
    public int getNumberOfInCompleteTasks() {
        return this.getIncompleteTasks().size();
    }

    /*
     * EFFECTS: return number of completed Tasks in todoList
     */
    public int getNumberOfCompletedTasks() {
        return this.getCompletedTasks().size();
    }

    /*
     * EFFECTS: returns true if TodoList is empty.
     */
    public boolean isEmpty() {
        return todoList.isEmpty();
    }

    /*
     * REQUIRES: The task being marked as complete is in the TodoList and it is not already completed
     * MODIFIES: this
     * EFFECTS: marks the task as complete
     */
    public void completeTask(int id) {
        for (Task task : todoList) {
            if (task.getID() == id) {
                task.complete();
            }
        }
    }

    /*
     * EFFECTS: returns true if todoList already contains task with same ID, else returns False
     */
    public boolean containsTask(Task task) {
        for (Task t : todoList) {
            if (task.getID() == t.getID()) {
                return true;
            }
        }
        return false;
    }

    // EFFECTS: returns a list of all current tasks
    public ArrayList<Task> getAllTasks() {

        return todoList;
    }

    // EFFECTS: returns a list of all current  completed tasks
    public ArrayList<Task> getCompletedTasks() {

        ArrayList<Task> listOfTasks = new ArrayList<>();

        for (Task task : todoList) {
            if (task.completionStatus) {
                listOfTasks.add(task);
            }
        }
        return listOfTasks;
    }

    // EFFECTS: returns a list of all current incomplete tasks
    public ArrayList<Task> getIncompleteTasks() {

        ArrayList<Task> listOfTasks = new ArrayList<>();

        for (Task task : todoList) {
            if (!task.completionStatus) {
                listOfTasks.add(task);
            }
        }
        return listOfTasks;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("tasks", tasksToJson());
        return json;
    }

    // EFFECTS: returns tasks in TodoList as JSON Array.
    private JSONArray tasksToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Task t: todoList) {
            jsonArray.put(t.toJson());
        }

        return jsonArray;
    }

}



