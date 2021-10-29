package persistence;

import org.json.JSONObject;

public interface Writable {

    //EFFECTS: returns this as JSON object
    // Based on toJson from jsonDemo
    JSONObject toJson();
}
