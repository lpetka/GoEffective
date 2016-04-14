package project.io.goeffective.widget.adapters;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.Calendar;

import project.io.goeffective.R;
import project.io.goeffective.models.ICalendarModel;
import project.io.goeffective.models.TaskStatus;

public class CalendarAdapter extends BaseAdapter {
    private Integer day_of_week;
    private Integer days_last_month;
    private Integer days_current_month;
    private final Integer DAYS_PER_WEEK = 7;
    private Context context;
    private ICalendarModel model;
    private Calendar currentMonth;

    public CalendarAdapter(Context context, Calendar month, ICalendarModel model){
        this.context = context;
        this.model = model;
        this.currentMonth = month;

        month.set(Calendar.DAY_OF_MONTH, 1);
        day_of_week = month.get(Calendar.DAY_OF_WEEK) - Calendar.MONDAY - 1;
        Calendar last_month = (Calendar) month.clone();
        last_month.add(Calendar.MONTH, -1);
        days_last_month = last_month.getActualMaximum(Calendar.DAY_OF_MONTH);
        days_current_month = month.getActualMaximum(Calendar.DAY_OF_MONTH);
        currentMonth.set(Calendar.DAY_OF_MONTH, 1);
    }

    @Override
    public int getCount() {
        return 49;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private Integer getDayNumber(int day_number){
        if(day_number <= DAYS_PER_WEEK + day_of_week){
            return days_last_month - DAYS_PER_WEEK - day_of_week + day_number;
        } else if (day_number > DAYS_PER_WEEK + day_of_week + days_current_month) {
            return day_number - DAYS_PER_WEEK - day_of_week - days_current_month;
        } else {
            return day_number - DAYS_PER_WEEK - day_of_week;
        }
    }

    private TaskStatus getTaskStatus(int itemNumber){
        Calendar tmpMonth = (Calendar) currentMonth.clone();
        int currentDay = -DAYS_PER_WEEK - day_of_week + itemNumber;
        tmpMonth.add(Calendar.DATE, currentDay);

        return model.getTaskStatus(tmpMonth);
    }

    private int getColor(int itemNumer){
        TaskStatus status = getTaskStatus(itemNumer);
        if(status == TaskStatus.DONE){
            return context.getResources().getColor(R.color.taskDone);
        } else if (status == TaskStatus.PARTLY_DONE){
            return context.getResources().getColor(R.color.taskPartlyDone);
        } else  {
            return context.getResources().getColor(R.color.taskNotDone);
        }
    }

    private TextView createGridItem(String text, int color){
        TextView textView = new TextView(context);
        textView.setText(text);
        textView.setGravity(Gravity.CENTER);
        textView.setHeight(70);
        textView.setTextSize(20);
        textView.setBackgroundColor(color);
        return textView;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return createGridItem(getDayNumber(i).toString(), getColor(i));
    }
}
