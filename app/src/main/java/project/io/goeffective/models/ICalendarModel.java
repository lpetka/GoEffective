package project.io.goeffective.models;

import android.content.Context;
import android.view.View;

import java.util.Calendar;

public interface ICalendarModel {
    TaskStatus getTaskStatus(Calendar calendar);
    View.OnClickListener getOnClickListener(Context context, Calendar date);
}
