package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static model.TodoList.MAX_SIZE;
import static org.junit.jupiter.api.Assertions.*;

class TodoListTest {

    private TodoList todoList;

    @BeforeEach
    void runBefore() {
        todoList = new TodoList();
    }

    @Test
    void testAddTaskOne() {
        assertEquals(0, todoList.getSize());

        Task task = new Task("hello", 1);
        todoList.addTask(task);

        assertEquals(1, todoList.getSize());
        assertEquals(1, todoList.getAllTasks().size());
    }

    @Test
    void testAddSame() {
        Task task1 = new Task("hello", 1);
        assertTrue(todoList.addTask(task1));

        Task task2 = new Task("hi", 1);
        assertFalse(todoList.addTask(task2));

        assertEquals(1, todoList.getSize());

    }
    @Test
    void testAddMany() {
        for (int i = 1; i <= MAX_SIZE; i++) {
            Task task = new Task("hello", i);
            todoList.addTask(task);
        }

        assertEquals(MAX_SIZE, todoList.getSize());
        assertFalse(todoList.isEmpty());

    }

    @Test
    void testAddTooMany() {
        for (int i = 1; i <= MAX_SIZE; i++) {
            Task task = new Task("hello", i);
            todoList.addTask(task);
        }

        assertEquals(MAX_SIZE, todoList.getSize());
        assertFalse(todoList.isEmpty());

        Task task2 = new Task("hello", MAX_SIZE + 1);

        assertFalse(todoList.addTask(task2));
        assertEquals(MAX_SIZE, todoList.getSize());


    }

    @Test
    void testRemoveAll() {
        Task task1 = new Task("hello", 1);
        Task task2 = new Task("hi", 2);

        todoList.addTask(task1);
        todoList.addTask(task2);

        todoList.removeTask(1);

        assertEquals(1, todoList.getSize());

        todoList.removeTask(2);

        assertEquals(0, todoList.getSize());
    }

    @Test
    void removeSome() {

        for (int i = 1; i <= MAX_SIZE; i++) {
            Task task = new Task("hello", i);
            todoList.addTask(task);
        }

        for (int i = 1; i <= MAX_SIZE/2; i++) {
            todoList.removeTask(i);
        }

        assertEquals(MAX_SIZE/2, todoList.getSize());
        assertEquals(MAX_SIZE/2, todoList.getAllTasks().size());
    }

    @Test
    void testNumberCompletedNone() {
        for (int i = 1; i <= MAX_SIZE;i++) {
            Task task = new Task("hello", i);
            todoList.addTask(task);
        }
        assertEquals(0, todoList.getNumberOfCompletedTasks());
        assertEquals(MAX_SIZE, todoList.getNumberOfInCompleteTasks());

        assertEquals(MAX_SIZE, todoList.getAllTasks().size());
    }

    @Test
    void testCompletedMany() {
        for (int i = 1; i <= MAX_SIZE; i++) {
            Task task = new Task("hello", i);
            todoList.addTask(task);
        }

        for (Task t : todoList.todoList) {
            todoList.completeTask(t.getID());
        }

        assertEquals(MAX_SIZE, todoList.getNumberOfCompletedTasks());
        assertEquals(0, todoList.getNumberOfInCompleteTasks());

    }

    @Test
    void testNumberCompletedAll() {
        for (int i = 1; i <= MAX_SIZE/2; i++) {
            Task task = new Task("hello", i);
            todoList.addTask(task);
        }

        for (Task t : todoList.todoList) {
            todoList.completeTask(t.getID());
        }


        for (int i = MAX_SIZE/2 + 1; i <= MAX_SIZE; i++) {
            Task task = new Task("hello", i);
            todoList.addTask(task);
        }

        assertEquals(MAX_SIZE/2, todoList.getNumberOfCompletedTasks());
        assertEquals(MAX_SIZE/2, todoList.getNumberOfInCompleteTasks());

    }


}