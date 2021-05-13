package csc248.smirn42.NotebookScheduler;

public class ListModel {

    private int TaskId, TaskStatus;
    private String task;

    public int getTaskId() {
        return TaskId;
    }

    public void setTaskId(int TaskId) {
        this.TaskId = TaskId;
    }

    public int getTaskStatus() {
        return TaskStatus;
    }

    public void setTaskStatus(int TaskStatus) {
        this.TaskStatus = TaskStatus;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }
}

