package model;


import model.exceptions.TaskAlreadyInListException;
import model.exceptions.TaskDoesNotExistException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class Category implements Serializable {

    private String categoryName;
    private List<Task> tasks;


    public Category(String categoryName) {

        this.categoryName = categoryName;
        tasks = new ArrayList<>();

    }

    // EFFECTS: returns the list of tasks associated with that category
    public List<Task> getTasks() {
        return tasks;
    }

    // EFFECTS: returns the size of the tasks list
    public int tasksSize() {
        return tasks.size();
    }

    // EFFECTS: return the name of the category
    public String getCategoryName() {
        return  this.categoryName;
    }

    // MODIFIES: this
    // EFFECTS: adds the given task into the list of tasks associated with this
    //          If it is already in the list, throws TaskAlreadyInListException
    public void addTasks(Task task) throws TaskAlreadyInListException {
        if (!containsTask(task)) {
            tasks.add(task);
        } else {
            throw new TaskAlreadyInListException();
        }
    }

    //EFFECTS: returns true if the given false is associated with the category, false otherwise
    public boolean containsTask(Task task) {
        return tasks.contains(task);
    }


    //REQUIRES: a valid task
    //MODIFIES: this
    //EFFECTS: removes the given task from the list, throws TaskNotCompleteException if the task isn't complete,
    //         throws TaskDoesNotExistException if the task isn't in the list
    public void removeTasks(Task task) throws TaskDoesNotExistException {
        if (!tasks.contains(task)) {
            throw new TaskDoesNotExistException();
        } else {
            tasks.remove(task);
        }
    }


    @Override
    // REQUIRES: a valid category
    // EFFECTS: overrides the given equals method to make categories comparable by their name
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Category category = (Category) o;
        return categoryName.equals(category.categoryName);
    }

    @Override
    //EFFECTS: makes the hashcode the same if two categories have the same name
    public int hashCode() {
        return Objects.hash(categoryName);
    }
}
