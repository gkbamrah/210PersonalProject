package model;



import java.io.Serializable;
import java.util.Objects;

public abstract class Task implements Serializable {

    String deadline;
    private String taskName;
    private Category category;


    //EFFECTS: constructs a new task with a deadline
    public Task(String taskName, String deadline, Category category) {
        this.deadline = deadline;
        this.taskName = taskName;
        this.category = category;
    }

    //EFFECTS: returns the due date of a task
    public String getDeadline() {
        return deadline;
    }


    //EFFECTS: returns the task name
    public String getTaskName() {
        return taskName;
    }

    //EFFECTS: returns the category that is associated with the task
    public Category getCategory() {
        return category;
    }


    //MODIFIES: this
    //EFFECTS: sets a new deadline for a task
    public void setDeadline(String deadline) {

        this.deadline = deadline;
    }

    @Override
    // REQUIRES: a valid task
    // EFFECTS: overrides the given equals method to make tasks comparable by their name
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Task)) {
            return false;
        }
        Task task = (Task) o;
        return taskName.equals(task.taskName);
    }

    @Override
    //EFFECTS: makes the hashcode the same if two tasks have the same name
    public int hashCode() {
        return Objects.hash(taskName);
    }
}


