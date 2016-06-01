package project.io.goeffective.widgets.adapters;

import android.content.Context;
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
    private final String[] shortMonthNames;

    public CalendarAdapter(Context context, Calendar month){
        this.context = context;
        this.currentMonth = month;
        this.shortMonthNames = context.getResources().getStringArray(R.array.short_months);

        month.set(Calendar.DAY_OF_MONTH, 1);
        day_of_week = month.get(Calendar.DAY_OF_WEEK) - Calendar.MONDAY - 1;
        Calendar last_month = (Calendar) month.clone();
        last_month.add(Calendar.MONTH, -1);
        days_last_month = last_month.getActualMaximum(Calendar.DAY_OF_MONTH);
        days_current_month = month.getActualMaximum(Calendar.DAY_OF_MONTH);
        currentMonth.set(Calendar.DAY_OF_MONTH, 1);
    }

    public CalendarAdapter(Context context, Calendar month, ICalendarModel model){
        this(context, month);
        this.model = model;
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

    private Calendar getDate(int itemNumber){
        Calendar tmpMonth = (Calendar) currentMonth.clone();
        int currentDay = -DAYS_PER_WEEK - day_of_week + itemNumber - 1; //-1 because current day represented as 1
        tmpMonth.add(Calendar.DATE, currentDay);
        return tmpMonth;
    }

    private View.OnClickListener getOnClickListener(int itemNumber){
        if(model != null) {
            return model.getOnClickListener(context, getDate(itemNumber));
        }
        return null;
    }

    private TaskStatus getTaskStatus(int itemNumber){
        if(model != null) {
            return model.getTaskStatus(getDate(itemNumber));
        }
        return TaskStatus.DOES_NOT_MATTER;
    }

    private int getColor(int itemNumer){
        TaskStatus status = getTaskStatus(itemNumer);
        if(status == TaskStatus.DONE){
            return context.getResources().getColor(R.color.taskDone);
        } else if (status == TaskStatus.PARTLY_DONE){
            return context.getResources().getColor(R.color.taskPartlyDone);
        } else  if (status == TaskStatus.NOT_DONE){
            return context.getResources().getColor(R.color.taskNotDone);
        }
        return context.getResources().getColor(R.color.taskFuture);
    }

    private TextView createGridItem(String text, int color, View.OnClickListener listener){
        TextView textView = new TextView(context);
        textView.setText(text);
        textView.setGravity(Gravity.CENTER);
        textView.setHeight(70);
        textView.setTextSize(20);
        textView.setBackgroundColor(color);
        textView.setOnClickListener(listener);
        return textView;
    }

    private String getText(int itemNumber){
        Integer day = getDayNumber(itemNumber);
        if(day == 1){
            Calendar calendar = getDate(itemNumber);
            int month = calendar.get(Calendar.MONTH);
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(this.shortMonthNames[month]).append(" ").append(day);
            return stringBuffer.toString();
        }
        return day.toString();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return createGridItem(getText(i), getColor(i), getOnClickListener(i));
    }
}
