package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TaskTest {
    Task task;

    @BeforeEach
    void runBefore() {
        task = new Task("hello", 1);
    }

    @Test
    void testComplete() {
        assertFalse(task.isComplete());
        assertEquals("Incomplete", task.getStatus());
        task.complete();
        assertTrue(task.isComplete());
        assertEquals("Complete", task.getStatus());




    }

    @Test
    void testGetID() {
        assertEquals(1, task.getID());
    }

    @Test
    void testGetLabel() {
        task = new Task("hello", 1);

        assertEquals("hello", task.getLabel());
    }


}
