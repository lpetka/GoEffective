package project.io.goeffective.utils;

import android.util.Pair;

import java.sql.Date;
import java.util.List;

import project.io.goeffective.utils.dbobjects.Task;

public interface IDatabase {
    void addTask(Task task);
    void removeTask(Task task);
    void changeTask(Task task);
    List<Pair<Task, Boolean>> getTasksStatusAtDate(Date date);
    List<Task> getTasksAtDate(Date date);
    void setTaskStatusAtDate(Date date, Task task, Boolean flag);
}
