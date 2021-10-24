package persistence;


import model.Task;
import model.TodoList;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;


// represents a reader that reads TodoList from JSON data stored in file
public class JsonReader {
    private String source;

    //EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads TodoList from file and returns it
    // throws IOException if an error occurs reading the file
    public TodoList read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseTodoList(jsonObject);
    }

    // EFFECTS: reads source file and returns it as a string
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses the TodoList from JSONObject and returns it
    private TodoList parseTodoList(JSONObject jsonObject) {
        TodoList td = new TodoList();
        addTasks(td, jsonObject);
        return td;
    }

    // MODIFIES: td
    // EFFECTS: parses things from JSONObject and adds them to TodoList
    private void addTasks(TodoList td, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("tasks");
        for (Object json: jsonArray) {
            JSONObject nextTask = (JSONObject) json;
            addTask(td, nextTask);
        }
    }

    // MODIFIES: td
    // EFFECTS: parses tasks from JSONObject and adds them to TodoList
    private void addTask(TodoList td, JSONObject jsonObject) {
        int id = Integer.parseInt(jsonObject.getString("id"));
        String label = jsonObject.getString("label");
        Boolean status = Boolean.valueOf(jsonObject.getString("completionStatus"));
        Task task = new Task(label, id, status);
        td.addTask(task);
    }


}
