package ui;


import model.Category;
import model.RegularTask;
import model.Task;
import model.ToDoList;

import java.io.*;

import model.exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class ToDoListTest {
    private ToDoList test;
    private int numTasks = 10;
    private Task testTask;
    private Category testCategory;


    @BeforeEach
    public void setup() {

        test = new ToDoList();
        testCategory = new Category("Test category");
        testTask = new RegularTask("Test", "March 1, 2000", testCategory);

    }


    @Test
    void testSave() throws IOException, ClassNotFoundException {


        try {
            test.addInList(testTask, testTask.getCategory());
        } catch (TaskAlreadyInListException e) {
            fail();
        }

        test.save("data/testSaveFile.sav");

        //emptying out the todolist so we'll only end up with the loaded data
        try {
            test.removeTask("Test");
        } catch (TaskDoesNotExistException e) {
            fail();
        }


        test.load("data/testSaveFile.sav");

        assertTrue(test.getListMap().containsKey(testTask));
    }


    @Test
    void testGetCategorySchool() {
            Category category = test.getCategory("school");
            assertEquals("School", category.getCategoryName());

    }

    @Test
    void testGetCategoryChores() {

            Category category = test.getCategory("chores");
            assertEquals("Chores", category.getCategoryName());


    }

    @Test
    void testGetCategoryErrands() {

            Category category = test.getCategory("errands");
            assertEquals("Errands", category.getCategoryName());

    }

    @Test
    void testGetCategoryPersonal() {


            Category category = test.getCategory("personal");
            assertEquals("Personal", category.getCategoryName());


    }

    @Test
    void testGetCategoryMiscellaneous() {

            Category category = test.getCategory("miscellaneous");
            assertEquals("Miscellaneous", category.getCategoryName());

    }


    @Test
    void testGetNumTasksZero() {
        assertEquals(0, test.getNumTasks());
    }


    @Test
    public void testAddInListNothingThrown() {
        try {
            test.addInList(testTask, testTask.getCategory());
            System.out.println("No exceptions thrown.");
        } catch (TaskAlreadyInListException e) {
            fail("No exceptions should be thrown");
        }
        assertTrue(test.getListMap().containsKey(testTask));
        assertTrue(testCategory.containsTask(testTask));
    }

    @Test
    public void testAddInListAlreadyInListExceptionThrown() {
        try {
            test.addInList(testTask, testTask.getCategory());
            System.out.println("No exceptions thrown.");
        } catch (TaskAlreadyInListException e) {
            fail("No exceptions should be thrown at this point");
        }
        try {
            test.addInList(testTask, testTask.getCategory());
            fail("Exception should be thrown");
        } catch (TaskAlreadyInListException e) {
            System.out.println("Correct exception thrown.");
        }
        assertEquals(1, test.getListMap().size());
        assertTrue(testCategory.containsTask(testTask));

    }




    @Test
    public void testListSize() throws TaskAlreadyInListException {
        assertEquals(0, test.listSize());

        for (int i = 0; i < numTasks; i++) {
            test.addInList(new RegularTask("Task " + i, "Tomorrow", testCategory), testCategory);

        }

        assertEquals(numTasks, test.listSize());
        assertEquals(10, testCategory.tasksSize());
    }



    @Test
    public void testRemoveTaskNothingThrown() {
        // setup
        Category errands = new Category("Errands");
        try {
            test.addInList(testTask, testCategory);
            test.addInList(new RegularTask("Shopping", "Thursday", errands), errands);
        } catch (TaskAlreadyInListException e) {
            fail("Should not have been thrown");
        }
        assertEquals(2, test.listSize());
        assertEquals(1, testCategory.tasksSize());
        assertEquals(1, errands.tasksSize());


        //call method
        try {
            test.removeTask("Shopping");
            System.out.println(":)");
        } catch (CannotDeleteTaskException e) {
            fail("Shouldn't throw any exceptions");
        }

        //expected result
        assertEquals(1, test.listSize());
        assertEquals(0, errands.tasksSize());
    }

    @Test
    public void testRemoveTaskThrowsTaskDoesNotExistException() {

        Category homework = new Category("Homework");

        try {
            test.addInList(new RegularTask("Do homework", "Tomorrow", homework), homework);
        } catch (TaskAlreadyInListException e) {
            fail("Should not have been thrown");
        }
        try {
            test.removeTask(testTask.getTaskName());
            fail("Exception should be thrown");
        } catch (TaskDoesNotExistException e) {
            System.out.println("Correct exception thrown");
        }

    }



    @Test
    void testContainsTaskTrue() {
        try {
            test.addInList(testTask, testCategory);
        } catch (TaskAlreadyInListException e) {
            fail("Should not have been thrown");
        }
        assertTrue(test.containsTask(testTask));
    }

    @Test
    void testContainsTaskFalse() {

        assertFalse(test.containsTask(testTask));

    }


    @Test
    public void testIsEmptyTrue() {

        assertTrue(test.isEmpty());
    }

    @Test
    void testIsEmptyFalse() {

        try {
            test.addInList(testTask, testTask.getCategory());
        } catch (TaskAlreadyInListException e) {
            fail("Should not have been thrown");
        }

        assertFalse(test.isEmpty());

    }

    @Test
    void testGetTask()  {

        try {
            test.addInList(testTask, testCategory);
        } catch (TaskAlreadyInListException e) {
            fail("Should not have been thrown");
        }

        assertEquals(testTask, test.getTask("Test"));

    }

    @Test
    void testGetTaskEmptyList() {

        assertNull(test.getTask("Test"));

    }

    @Test
    void testGetTaskNotThere() {
        Category category1 = new Category("Category 1");
        Category category2 = new Category("Category 2");
        try {
            test.addInList(new RegularTask("Test 1", "Today", category1), category1);
            test.addInList(new RegularTask("Test 2", "Tomorrow", category2), category2);
        } catch (TaskAlreadyInListException e) {
            fail("Should not have been thrown");
        }
        assertNull(test.getTask("Test"));
    }

    @Test
    void testContainsCategoryEmptyHashSet() {

        assertFalse(test.containsCategory(new Category("Test category")));

    }


    @Test
    public void testContainsCategoryNotThere() {

        try {
            test.addInList(testTask, testCategory);
        } catch (TaskAlreadyInListException e) {
            fail();
        }
         assertFalse(test.containsCategory(new Category("Test")));


    }

    @Test
    void testGetCategoryFromListEmptySet() {

        assertNull(test.getCategoryFromList(testCategory));

    }

    @Test
    public void testGetCategoryFromList() {

        try {
            test.addInList(testTask, testCategory);
        } catch (TaskAlreadyInListException e) {
         fail();
        }

        Category category3 = new Category("Category 3");

        try {

            test.addInList(new RegularTask("Test 2", "Thursday", category3), category3);
        } catch (TaskAlreadyInListException e) {
            fail();
        }

        assertEquals(testCategory, test.getCategoryFromList(new Category("Test category")));

    }

    @Test
    void testGetCategoryFromListNotThere() {

        try {
            test.addInList(testTask, testCategory);
        } catch (TaskAlreadyInListException e) {
            fail();
        }

        Category category3 = new Category("Category 3");

        try {

            test.addInList(new RegularTask("Test 2", "Thursday", category3), category3);
        } catch (TaskAlreadyInListException e) {
            fail();
        }

        assertNull(test.getCategoryFromList(new Category("Test")));
    }

}



