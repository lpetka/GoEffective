package project.io.goeffective.services;

import java.util.Date;

import project.io.goeffective.utils.dbobjects.Task;

public interface INavigator {
    void openPreferencesActivity();
    void openTaskAddActivity();
    void openTaskEditActivity(Task task);
    void openDayActivity(Date date);
}
