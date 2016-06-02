package project.io.goeffective.widgets;

import android.content.Context;
import android.util.AttributeSet;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Calendar;

import project.io.goeffective.R;
import project.io.goeffective.models.CalendarModel;
import project.io.goeffective.models.ICalendarModel;
import project.io.goeffective.widgets.adapters.CalendarAdapter;
import project.io.goeffective.widgets.events.OnMonthChangeListener;

public class CalendarView extends LinearLayout implements ICalendarChanger, ICalendarWidget {
    private Context context;
    private final Integer DAYS_OF_WEEK = 7;
    private Integer dayOffset = 0;
    private final String[] dayName = getResources().getStringArray(R.array.short_week_days);
    private final String[] monthName = getResources().getStringArray(R.array.months);
    private GridView gridView;
    private Calendar cal = Calendar.getInstance();
    private TextView monthTextView;

    private ICalendarModel model = new CalendarModel();
    private OnMonthChangeListener monthChangeListener;

    ///////////Style
    private final int GRIDVIEW_SPACING = 1;

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
        addMonth();
        addDaysOfWeek();
        addGridCalendar();
    }

    @Override
    public void nextMonth() {
        cal.add(Calendar.MONTH, 1);
        if(monthChangeListener != null) {
            monthChangeListener.onMonthChange(this);
        }
        update();
    }

    @Override
    public void prevMonth() {
        cal.add(Calendar.MONTH, -1);
        if(monthChangeListener != null) {
            monthChangeListener.onMonthChange(this);
        }
        update();
    }

    @Override
    public void setModel(ICalendarModel model) {
        this.model = model;
        update();
    }

    @Override
    public void setOnActionListener(OnMonthChangeListener actionListener) {
        this.monthChangeListener = actionListener;
    }

    private class ClickListener implements OnClickListener {
        private final boolean moveForward;
        ICalendarChanger ICalendarChanger;
        public ClickListener(ICalendarChanger ICalendarChanger, boolean next){
            this.ICalendarChanger = ICalendarChanger;
            this.moveForward = next;
        }

        @Override
        public void onClick(View view) {
            if(moveForward){
                ICalendarChanger.nextMonth();
            } else {
                ICalendarChanger.prevMonth();
            }
        }
    }

    private void addMonth(){

        LinearLayout linearLayout = new LinearLayout(context);
        //linearLayout.setOrientation(HORIZONTAL);
        linearLayout.setLayoutParams(
                new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        );
        this.addView(linearLayout);


        Button prev = new Button(context);
        prev.setText("<");
        prev.setTextSize(25);
        prev.setLayoutParams(
                new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0.1f)
        );
        prev.setOnClickListener(new ClickListener(this, false));
        linearLayout.addView(prev);


        monthTextView = new TextView(context);
        monthTextView.setTextSize(25);
        monthTextView.setGravity(Gravity.CENTER);
        monthTextView.setLayoutParams(
                new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0.8f)
        );
        linearLayout.addView(monthTextView);

        Button next = new Button(context);
        next.setText(">");
        next.setTextSize(25);
        next.setLayoutParams(
                new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0.1f)
        );
        next.setOnClickListener(new ClickListener(this, true));
        linearLayout.addView(next);
    }

    private void setMonth(int month){
        monthTextView.setText(monthName[month]);
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
        gridView = new GridView(context);
        gridView.setLayoutParams(
                new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        );
        gridView.setNumColumns(DAYS_OF_WEEK);
        gridView.setHorizontalSpacing(GRIDVIEW_SPACING);
        gridView.setVerticalSpacing(GRIDVIEW_SPACING);
        this.addView(gridView);
        update();
    }

    public void update(){
        setMonth(cal.get(Calendar.MONTH));
        CalendarAdapter calendarAdapter = new CalendarAdapter(context, cal, model);
        gridView.setAdapter(calendarAdapter);
    }

    public GridView getGridView() {
        return gridView;
    }
}
