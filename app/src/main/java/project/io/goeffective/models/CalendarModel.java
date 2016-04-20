package project.io.goeffective.models;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Random;

import project.io.goeffective.R;

public class CalendarModel implements ICalendarModel{
    private TaskStatus[] t = new TaskStatus[]{TaskStatus.DONE, TaskStatus.PARTLY_DONE, TaskStatus.NOT_DONE};
    private Random random = new Random();

    public CalendarModel(){}

    public TaskStatus getTaskStatus(Calendar calendar){
        int r = random.nextInt(3);
        return t[r];
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
