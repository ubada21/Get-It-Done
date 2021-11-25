package model;



import org.json.JSONObject;
import persistence.Writable;

// represents a task with a label, status (complete or not), and an ID number.
public class Task implements Writable {

    protected boolean completionStatus;
    protected int id;
    private final String label;

    /*
     * REQUIRES: label must be string with length > 0
     * EFFECTS: initializes a task with a label, and ID. completion status is False by default.
     */
    public Task(String label, int id) {
        this.label = label;
        completionStatus = false;
        this.id = id;
    }

    /*
     * REQUIRES: label must have length > 0
     * EFFECTS: initializes a task with a label, ID and Completion Status. used with JsonReader
     */
    public Task(String label, int id, Boolean status) {
        this.label = label;
        this.completionStatus = status;
        this.id = id;
    }

    /*
     * MODIFIES: this
     * EFFECTS: sets Task completion status to true.
     */
    public void complete() {
        completionStatus = true;
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
        return completionStatus;
    }

    /*
     * EFFECTS: returns label for task.
     */
    public String getLabel() {
        return this.label;
    }

    /*
     * EFFECTS: returns "Complete" is task is complete, else returns "Incomplete".
     */
    public String getStatus() {
        if (this.isComplete()) {
            return "Complete";
        }

        return "Incomplete";
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", String.valueOf(id));
        jsonObject.put("label", label);
        jsonObject.put("completionStatus", String.valueOf(completionStatus));

        return jsonObject;
    }

}
