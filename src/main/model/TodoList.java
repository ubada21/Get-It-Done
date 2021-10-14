package model;

import java.util.ArrayList;

// represents a To-do list with a max size of 20
public class TodoList {
    public static final int MAX_SIZE = 20;
    protected final ArrayList<Task> todoList;

    // Initializes an empty TaskList.
    public TodoList() {
        this.todoList = new ArrayList<>();
    }

    /*
     * REQUIRES: task with ID number is not already in the todoList.
     * MODIFIES: this
     * EFFECTS: is task with given id already exists in todoList, return false
     * else, adds the given task into the TaskList and returns true
     */
    public Boolean addTask(Task task) {
        if (!this.containsTask(task)) {
            todoList.add(task);
            return true;
        }

        return false;
    }

    /*
     *
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
     * EFFECTS: return all current Tasks in todoList
     */
    public int getUnCompletedTasks() {
        int numberUnCompleted = 0;
        for (Task task : todoList) {

            if (!task.isComplete) {
                numberUnCompleted++;
            }
        }
        return numberUnCompleted;
    }

    /*
     * EFFECTS: return all completed Tasks in todoList
     */
    public int getCompletedTasks() {
        int numberCompleted = 0;
        for (Task task : todoList) {

            if (task.isComplete) {
                numberCompleted++;
            }
        }
        return numberCompleted;
    }

    /*
     * EFFECTS: returns true if TodoList is empty.
     */
    public boolean isEmpty() {
        return todoList.isEmpty();
    }

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
}
