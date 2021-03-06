package project.io.goeffective.utils;

import android.util.Pair;

import java.util.Date;
import java.util.List;

import project.io.goeffective.utils.dbobjects.Task;

public interface IDatabase {
    void addTask(Task task);
    void removeTask(Task task);
    void updateTask(Task task);
    List<Pair<Task, Boolean>> getTasksStatusAtDate(Date date);
    List<Task> getTasksAtDate(Date date);
    Pair<Date, List<Task>> getNextNonemptyDayTasks(Date date);
    void setTaskStatusAtDate(Date date, Task task, Boolean flag);
    List<Task> getTasksList();
    List<Boolean> getTaskHistory(Task task, Date date, int days);
    List<Boolean> getTaskHistoryUntilFalse(Task task, Date date);
    List<Boolean> getTaskHistoryUntilFalse(Task task, Date date, int minDays);
    void clearDatabase();
}
