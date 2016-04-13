package project.io.goeffective.widget.adapters;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.Calendar;

public class CalendarAdapter extends BaseAdapter {
    private Integer day_of_week;
    private Integer days_last_month;
    private Integer days_current_month;
    private final Integer DAYS_PER_WEEK = 7;
    private Context context;

    public CalendarAdapter(Context context, Calendar month){
        this.context = context;
        month.set(Calendar.DAY_OF_MONTH, 1);
        day_of_week = month.get(Calendar.DAY_OF_WEEK) - Calendar.MONDAY - 1;
        Calendar last_month = (Calendar) month.clone();
        last_month.add(Calendar.MONTH, -1);
        days_last_month = last_month.getActualMaximum(Calendar.DAY_OF_MONTH);
        days_current_month = month.getActualMaximum(Calendar.DAY_OF_MONTH);
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

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        TextView textView = new TextView(context);
        textView.setText(getDayNumber(i).toString());
        textView.setGravity(Gravity.CENTER);
        textView.setHeight(70);
        textView.setTextSize(20);
        return textView;
    }
}
