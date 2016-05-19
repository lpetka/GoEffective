package project.io.goeffective.models;

import java.util.Date;
import project.io.goeffective.utils.dbobjects.Task;

public interface IAddTaskModel {
    Task createTask(String name);
    void addEveryWeek(Task task, Date date);
    void removeEveryWeek(Task task, Date date);
}
