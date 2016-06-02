package project.io.goeffective.models;

import java.util.List;

import project.io.goeffective.utils.dbobjects.Task;

public interface IDayModel {
    List<Boolean> getHistory(Task task);
    List<Task> getTodayTasks();
    int countDaysInARow(Task task);
    void toggle(Task task);
}
