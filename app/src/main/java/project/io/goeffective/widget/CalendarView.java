package project.io.goeffective.widget;

import android.content.Context;
import android.util.AttributeSet;

import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Calendar;

import project.io.goeffective.widget.adapters.CalendarAdapter;

public class CalendarView extends LinearLayout{
    private Context context;
    private final Integer DAYS_OF_WEEK = 7;
    private Integer dayOffset = 0;
    private final String[] dayName = new String[]{"Pon", "Wt", "Śr", "Czw", "Pt", "Sob", "Nie"};

    public CalendarView(Context context) {
        super(context);
        setupView(context);
    }

    public CalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupView(context);
    }

    public CalendarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setupView(context);
    }

    private void setupView(Context context){
        this.context = context;
        this.setOrientation(VERTICAL);
        addDaysOfWeek();
        addGridCalendar();
    }

    private void addDaysOfWeek(){
        TableLayout tableLayout = new TableLayout(context);
        tableLayout.setStretchAllColumns(true);
        tableLayout.setLayoutParams(
                new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        );
        TableRow tableRow = new TableRow(context);
        for(int i = 0; i<DAYS_OF_WEEK; i++){
            TextView textView = new TextView(context);
            textView.setText(dayName[i + dayOffset]);
            textView.setTextSize(15);
            textView.setGravity(Gravity.CENTER);
            tableRow.addView(textView);
        }
        tableLayout.addView(tableRow);
        this.addView(tableLayout);
    }

    private void addGridCalendar(){
        GridView gridView = new GridView(context);
        gridView.setLayoutParams(
                new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        );
        gridView.setNumColumns(DAYS_OF_WEEK);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        CalendarAdapter calendarAdapter = new CalendarAdapter(context, cal);
        gridView.setAdapter(calendarAdapter);
        this.addView(gridView);
    }
}
