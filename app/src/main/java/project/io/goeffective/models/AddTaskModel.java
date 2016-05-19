package project.io.goeffective.models;

import android.content.Context;

import java.util.Date;

import javax.inject.Inject;

import project.io.goeffective.utils.DatabaseHandler;
import project.io.goeffective.utils.dbobjects.Task;
import project.io.goeffective.utils.dbobjects.TaskStart;

public class AddTaskModel implements IAddTaskModel {
    @Inject
    DatabaseHandler databaseHandler;

    @Inject
    Context context;


    @Override
    public Task createTask(String name) {
        Task newTask = new Task(name);
        Task task = databaseHandler.addTask(newTask);
        return task;
    }

    @Override
    public void addEveryWeek(Task task, Date date) {
        TaskStart taskStart = new TaskStart(task.getId(), date, 7);
        task.addTaskStart(taskStart);
        databaseHandler.updateTask(task);
    }

    @Override
    public void removeEveryWeek(Task task, Date date) {

    }
}
