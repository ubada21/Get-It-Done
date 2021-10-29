package persistence;

import model.TodoList;
import org.json.JSONObject;

import java.io.*;

// represents a writer that writes JSON representation of TodoList to file.
//Based on JsonWriter class from jsonDemo
public class JsonWriter {

    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    //EFFECTS: constructs writer to write to destination file
    //Based on JsonWriter class from jsonDemo
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    // Based on open() method from jsonDemo
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of TodoList to file
    // Based on write() method from jsonDemo
    public void write(TodoList td) {
        JSONObject json = td.toJson();
        saveToFile(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    // based on close() method from jsonDemo
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    // based on saveToFile() from jsonDemo
    private void saveToFile(String json) {
        writer.print(json);
    }
}

