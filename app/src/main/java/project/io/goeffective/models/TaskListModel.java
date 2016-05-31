package project.io.goeffective.models;

import java.util.List;

import javax.inject.Inject;

import project.io.goeffective.App;
import project.io.goeffective.utils.DatabaseHandler;
import project.io.goeffective.utils.dbobjects.Task;

public class TaskListModel implements ITaskListModel {
    @Inject
    DatabaseHandler databaseHandler;

    public TaskListModel() {
        App.getComponent().inject(this);
    }

    @Override
    public List<Task> getDetailedTaskList() {
        return databaseHandler.getTasksList();
    }
}
