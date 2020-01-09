package model;

public class UrgentTask extends Task {

    public UrgentTask(String taskName, String deadline, Category category) {
        super(taskName, deadline, category);
    }


    //EFFECTS: returns the deadline of the given task
    public String getDeadline() {
        return deadline + " **You must complete this soon!**";
    }

}
