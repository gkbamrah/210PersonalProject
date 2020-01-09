package ui;

import model.Category;
import model.RegularTask;
import model.Task;
import model.ToDoList;
import model.exceptions.TaskAlreadyInListException;
import model.exceptions.TaskDoesNotExistException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {

    private Category test;
    private Task testTask;
    private ToDoList testList;

    @BeforeEach
    void setup() {
        testList = new ToDoList();
        test = new Category("test");
        testTask = new RegularTask("Test", "Thursday,", test);
    }

    @Test
    void testGetCategory() {

        assertEquals("test", test.getCategoryName());
    }

    @Test
    void testGetTasksEmpty() {

        assertEquals(0, test.getTasks().size());
    }

    @Test
    void testGetTasksNotEmpty() {

        try {
            test.addTasks(testTask);
        } catch (TaskAlreadyInListException e) {
            fail("Should not have been thrown");
        }

        assertEquals(1, test.getTasks().size());
        assertTrue(test.containsTask(testTask));
    }

    @Test
    void testTasksSizeEmpty() {

        assertEquals(0, test.tasksSize());

    }

    @Test
    void testTasksSizeNotEmpty() {

        try {
            test.addTasks(testTask);
        } catch (TaskAlreadyInListException e) {
            fail("Exception should not have been thrown");
        }

        assertEquals(1, test.tasksSize());

    }

    @Test
    void testAddTasksNoExceptionsThrown() {

        try {
            test.addTasks(testTask);
        } catch (TaskAlreadyInListException e) {
            fail("Exception should not have been thrown");
        }
        assertTrue(test.containsTask(testTask));

    }

    @Test
    void testAddTasksTaskAlreadyInListExceptionThrown() {

        try {
            test.addTasks(testTask);
        } catch (TaskAlreadyInListException e) {
            fail("Exception thrown too early");
        }

        try {
            test.addTasks(testTask);
            test.addTasks(testTask);
            fail("Exception should have been thrown");
        } catch (TaskAlreadyInListException e) {
            System.out.println("Exception thrown at correct time");
        }

        assertTrue(test.containsTask(testTask));
    }

    @Test
    void testRemoveTasksNoExceptionsThrown() {

        try {
            test.addTasks(testTask);
        } catch (TaskAlreadyInListException e) {
            fail();
        }
        try {
            test.removeTasks(testTask);
        } catch (TaskDoesNotExistException e) {
            fail();
        }
        assertEquals(0, test.tasksSize());
    }


    @Test
    void testRemoveTaskDoesNotExistExceptionThrown() {
        try {
            test.removeTasks(testTask);
        } catch (TaskDoesNotExistException e) {
            System.out.println("Correct exception thrown");
        }
    }

    @Test
    void testEqualsTrueSameObject() {

        assertTrue(test.equals(test));

    }

    @Test
    void testEqualsNullObject() {

        assertFalse(test.equals(null));

    }

    @Test
    void testEqualsDifferentObjects() {

        assertFalse(test.equals(testTask));

    }

    @Test
    void testEqualsSameNameOnly() {
        Category category = new Category("test");

        assertTrue(test.equals(category));

    }

    @Test
    void testHashcodeSameName() {
        Category category = new Category("test");
        assertEquals(test.hashCode(), category.hashCode());
    }

    @Test
    void testHashcodeDifferentName() {

        Category category = new Category("Test");
        assertFalse(category.equals(new Category("Test Category")));

    }

    @Test
    void testHashcodeDifferentObjects() {

        assertFalse(test.hashCode() == testTask.hashCode());

    }
}
