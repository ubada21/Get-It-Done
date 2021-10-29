package persistence;

import model.Task;
import model.TodoList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// Tests based on JsonWriterTest form jsonDemo
// Tests to test JsonWriterClass
public class JsonWriterTest {

    private TodoList td;
    private JsonWriter writer;
    private JsonReader reader;

    @BeforeEach
    void runBefore() {
        td = new TodoList();
    }

    @Test
    void testWriterInvalidFile() {
        try {
            TodoList td = new TodoList();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            //pass
        }
    }

    @Test
    void testWriterEmptyTodoList() {
        try {
            TodoList td = new TodoList();
            writer = new JsonWriter("./data/testWriterEmptyTodoList.json");
            writer.open();
            writer.write(td);
            writer.close();

            reader = new JsonReader("./data/testWriterEmptyWorkroom.json");
            td = reader.read();
            assertEquals(td.getSize(), 0);


        } catch (IOException e) {
            //pass
        }
    }

    @Test
    void testWriterFullTodoList() {
        try {
            td = new TodoList();

            for (int i = 0; i < 5; i++) {
                Task task = new Task("hello", i);
                td.addTask(task);
            }

            writer = new JsonWriter("./data/testWriterFullTodoList");
            writer.open();
            writer.write(td);
            writer.close();

            reader = new JsonReader("./data/testWriterFullTodoList");
            td = reader.read();
            assertEquals(5, td.getSize());
            assertEquals(0, td.getNumberOfCompletedTasks());
            assertEquals(5, td.getNumberOfInCompleteTasks());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }


}
