package persistence;

import model.TodoList;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest {
    TodoList td;
    //JsonReader reader;

    @Test
    void testReaderNonExistentFile() {

        JsonReader reader = new JsonReader("./data/noFile.json");
        try {
            td = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            //pass
        }
    }

    @Test
    void testReaderEmptyTodoList() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyTodoList.json");
        try {
            td = reader.read();
            assertEquals(0, td.getSize());
        } catch (IOException e) {
            fail("Couldn't read file");
        }
    }

    @Test
    void testReaderNotEmptyTodoList() {
        JsonReader reader = new JsonReader("./data/testReaderNotEmptyTodoList.json");
        try {
            td = reader.read();
            assertEquals(5, td.getSize());
            assertEquals(0, td.getNumberOfCompletedTasks());
            assertEquals(5, td.getNumberOfInCompleteTasks());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

}
