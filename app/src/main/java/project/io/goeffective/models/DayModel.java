package project.io.goeffective.models;

import android.support.annotation.NonNull;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import project.io.goeffective.App;
import project.io.goeffective.utils.DatabaseHandler;
import project.io.goeffective.utils.dbobjects.Task;

public class DayModel implements IDayModel {
    private Date date;
    private final int MINIMUM_DAYS = 7;

    @Inject
    DatabaseHandler databaseHandler;

    public DayModel(Date date) {
        this.date = date;
        App.getComponent().inject(this);
    }

    public List<Task> getTodayTasks() {
        return databaseHandler.getTasksAtDate(date);
    }

    @NonNull
    public List<Boolean> getHistory(Task task) {
        return databaseHandler.getTaskHistoryUntilFalse(task, date, MINIMUM_DAYS);
    }

    public int countDaysInARow(Task task) {
        List<Boolean> history = databaseHandler.getTaskHistoryUntilFalse(task, date);
        int len = history.size();
        if(len == 0)return 0;
        if(!history.get(len-1)){
            len-=1;
        }
        return len;
    }

    public void toggle(Task task) {
        boolean flag = databaseHandler.checkTaskStatusAtDate(task, date);
        databaseHandler.setTaskStatusAtDate(date, task, !flag);
    }
}
