package ui;


import model.Category;
import model.ToDoList;
import model.UrgentTask;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UrgentTaskTest extends TaskTest {


    @BeforeEach
    public void setup() {
        testList = new ToDoList();
        test = new UrgentTask("Test",  "3 pm", new Category("Test cg"));
    }

    @Test
    public void testGetTaskNameWithAsterisks() {

        assertEquals("Test", test.getTaskName());

    }
    @Test
    public void testGetDeadline() {

        assertEquals("3 pm **You must complete this soon!**", test.getDeadline());

    }

    @Test
    public void testSetDeadline() {

        test.setDeadline("April 20, 2069");

        assertEquals("April 20, 2069 **You must complete this soon!**", test.getDeadline());

    }

    @Override
    public void testGetCategory() {
        assertEquals("Test cg", test.getCategory());
    }
}

