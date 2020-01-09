package model;

public class RegularTask extends Task {

    public RegularTask(String taskName, String deadline, Category category) {
        super(taskName, deadline, category);
    }

    //EFFECTS: returns the deadline with "You have a bit of time with this one" written beside it in parentheses
    public String getDeadline() {
        return deadline + " (You have a bit of time with this one)";
    }

}
