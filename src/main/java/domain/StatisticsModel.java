package domain;

public class StatisticsModel {

    private int numTasks;
    private int numComplete;
    private int numPending;
    private int numOverdue;


    public int getNumComplete() {
        return numComplete;
    }

    public int getNumOverdue() {
        return numOverdue;
    }

    public int getNumPending() {
        return numPending;
    }

    public int getNumTasks() {
        return numTasks;
    }

    public void setNumComplete(int numComplete) {
        this.numComplete = numComplete;
    }

    public void setNumOverdue(int numOverdue) {
        this.numOverdue = numOverdue;
    }

    public void setNumPending(int numPending) {
        this.numPending = numPending;
    }

    public void setNumTasks(int numTasks) {
        this.numTasks = numTasks;
    }
}
