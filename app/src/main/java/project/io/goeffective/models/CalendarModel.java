package project.io.goeffective.models;

import android.content.Context;
import android.util.Pair;
import android.view.View;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import project.io.goeffective.utils.DatabaseHandler;
import project.io.goeffective.utils.dbobjects.Task;

public class CalendarModel implements ICalendarModel{
    @Inject
    DatabaseHandler databaseHandler;

    @Inject
    Context context;

    public CalendarModel(){}

    public TaskStatus getTaskStatus(Calendar calendar){
        Calendar current = Calendar.getInstance();
        if(current.compareTo(calendar) < 0){
            return TaskStatus.FUTURE;
        }
        List<Pair<Task, Boolean>> taskStatuses = databaseHandler.getTasksStatusAtDate(calendar.getTime());
        boolean partlyDone = false;
        boolean allDone = true;
        for (Pair<Task, Boolean> pair: taskStatuses) {
            partlyDone = partlyDone  || pair.second;
            allDone = allDone || pair.second;
        }
        if(allDone){
            return TaskStatus.DONE;
        } else if (partlyDone){
            return TaskStatus.PARTLY_DONE;
        }
        return TaskStatus.NOT_DONE;
    }

    @Override
    public View.OnClickListener getOnClickListener(Context context, Calendar date) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringBuffer dataString = new StringBuffer();
                dataString.append(date.get(Calendar.DAY_OF_MONTH));
                dataString.append(".");
                dataString.append(date.get(Calendar.MONTH));
                dataString.append(".");
                dataString.append(date.get(Calendar.YEAR));
                Toast.makeText(context, dataString, Toast.LENGTH_SHORT).show();
            }
        };
    }
}
