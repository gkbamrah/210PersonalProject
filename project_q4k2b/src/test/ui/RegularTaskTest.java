package ui;

import model.Category;
import model.RegularTask;
import model.ToDoList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class RegularTaskTest extends TaskTest {


    @BeforeEach
    public void setup() {
        testList = new ToDoList();
        test = new RegularTask("Test", "October 11", new Category("Test category"));
    }

    @Test
    public void testGetDeadline() {

        assertEquals("October 11 (You have a bit of time with this one)", test.getDeadline());

    }

    @Override
    public void testGetTaskName() {
        assertEquals("Test", test.getTaskName());
    }

    @Test
    public void testSetDeadline() {

        test.setDeadline("April 20, 2069");

        assertEquals("April 20, 2069 (You have a bit of time with this one)", test.getDeadline());

    }

    @Override
    public void testGetCategory() {
        assertEquals("Test category", test.getCategory());
    }


}
