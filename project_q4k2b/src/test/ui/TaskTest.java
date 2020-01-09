package ui;

import model.*;
import model.exceptions.TaskAlreadyInListException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public abstract class TaskTest {

    protected Task test;
    protected ToDoList testList;

    //todo write tests that ensure that the observers have been added

    @BeforeEach
    public void setup() {
        testList = new ToDoList();
        test = new RegularTask("Test", "March 1, 2000", new Category("Test category"));
    }


    @Test
    public abstract void testGetDeadline();

//    @Test
//    public void testGetCompletionStatus() {
//
//        assertFalse(test.getCompletionStatus());
//
//    }

    @Test
    void testGetTaskName() {

        assertEquals("Test", test.getTaskName());

    }

    @Test
    public abstract void testSetDeadline();

    @Test
    public abstract void testGetCategory();

    @Test
    void testEqualsNullObject() {

        assertFalse(test.equals(null));

    }

    @Test
    void testEqualsSameNameSameTaskTypes() {

        Task regularTask = new RegularTask("Test", "5 pm", new Category("Test Category"));

        assertTrue(test.equals(regularTask));


    }

    @Test
    void testEqualsSameNameDifferentTaskTypes() {

        Task urgentTask = new UrgentTask("Test", "5 pm", new Category("Test Category"));

        assertTrue(test.equals(urgentTask));


    }

    @Test
    void testEqualsDifferentApparentTypesUrgentTaskSameName() {

        UrgentTask urgentTask = new UrgentTask("Test", "5 pm", new Category("Test Category"));

        assertTrue(test.equals(urgentTask));

    }

    @Test
    void testEqualsDifferentApparentTypesRegularTaskSameName() {

        RegularTask regularTask = new RegularTask("Test", "5 pm", new Category("Test Category"));

        assertTrue(test.equals(regularTask));

    }

    @Test
    void testEqualsDifferentObjects() {

        Category category = new Category("Category");

        assertFalse(test.equals(category));

    }

    @Test
    void testEqualsSameObject() {

        assertTrue(test.equals(test));

    }

    @Test
    void testHashcodeSameObject() {

        assertEquals(test.hashCode(), test.hashCode());

    }

    @Test
    void testHashcodeSameNameDifferentTaskTypes() {

        Task urgentTask = new UrgentTask("Test", "5 pm", new Category("Test Category"));

        assertEquals(test.hashCode(), urgentTask.hashCode());

    }

    @Test
    void testHashCodeSameNameSameTaskTypes() {
        Task regularTask = new RegularTask("Test", "5 pm", new Category("Test Category"));
        assertEquals(test.hashCode(), regularTask.hashCode());
    }

    @Test
    void testHashcodeDifferentObjects() {
        Category category = new Category("Category");

        assertFalse(test.hashCode() == category.hashCode());

    }

}
