package project.io.goeffective.models;

import android.content.Context;

import java.util.List;

import javax.inject.Inject;

import project.io.goeffective.App;
import project.io.goeffective.utils.DatabaseHandler;
import project.io.goeffective.utils.dbobjects.Task;

public class TaskListModel implements ITaskListModel {
    @Inject
    DatabaseHandler databaseHandler;

    @Inject
    Context context;

    public TaskListModel() {
        App.component(context).inject(this);
    }

    @Override
    public List<Task> getDetailedTaskList() {
        return databaseHandler.getTasksList();
    }
}
