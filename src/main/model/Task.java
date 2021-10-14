package model;

// represents a task with a label, status (complete or not), and an ID number.
public class Task {

    protected boolean isComplete;
    protected int id;
    private String label;

    /*
     * REQUIRES: label must have length > 0
     * EFFECTS: initializes a task with a label, completion status is False by default.
     */
    public Task(String label, int id) {
        this.label = label;
        isComplete = false;
        this.id = id;
    }

    /*
     * MODIFIES: this
     * EFFECTS: sets Task completion status to true.
     */
    public void complete() {
        isComplete = true;
    }

    /*
     * EFFECTS: returns ID number of Task.
     */
    public int getID() {
        return this.id;
    }

    /*
     * EFFECTS: returns completion status of Task.
     */
    public Boolean isComplete() {
        return isComplete;
    }

    public String getLabel() {
        return this.label;
    }

    public String getStatus() {
        if (this.isComplete()) {
            return "Complete";
        }

        return "Incomplete";
    }







}
