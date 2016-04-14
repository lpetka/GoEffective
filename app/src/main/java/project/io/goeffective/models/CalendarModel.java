package project.io.goeffective.models;

import java.util.Calendar;
import java.util.Random;

public class CalendarModel implements ICalendarModel{
    private TaskStatus[] t = new TaskStatus[]{TaskStatus.DONE, TaskStatus.PARTLY_DONE, TaskStatus.NOT_DONE};
    private Random random = new Random();

    public CalendarModel(){}

    public TaskStatus getTaskStatus(Calendar calendar){
        int r = random.nextInt(3);
        return t[r];
    }
}
