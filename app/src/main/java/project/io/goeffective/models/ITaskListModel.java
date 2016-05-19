package project.io.goeffective.models;

import java.util.List;

import project.io.goeffective.utils.dbobjects.Task;

public interface ITaskListModel {
    List<Task> getDetailedTaskList();
}
