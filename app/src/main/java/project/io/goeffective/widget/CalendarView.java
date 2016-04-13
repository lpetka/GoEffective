package project.io.goeffective.widget;

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
import project.io.goeffective.widget.adapters.CalendarAdapter;

public class CalendarView extends LinearLayout implements CalendarChanger{
    private Context context;
    private final Integer DAYS_OF_WEEK = 7;
    private Integer dayOffset = 0;
    private final String[] dayName = new String[]{"Pon", "Wt", "Śr", "Czw", "Pt", "Sob", "Nie"};
    private int[] monthId = new int[]{
            R.string.jan, R.string.feb, R.string.mar, R.string.apr, R.string.may, R.string.jun,
            R.string.jul, R.string.aug, R.string.sep, R.string.oct, R.string.nov, R.string.dec
    };
    private GridView gridView;
    private Calendar cal = Calendar.getInstance();
    private TextView monthTextView;

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
        update();
    }

    @Override
    public void prevMonth() {
        cal.add(Calendar.MONTH, -1);
        update();
    }

    private class ClickListener implements OnClickListener {
        private final boolean moveForward;
        CalendarChanger calendarChanger;
        public ClickListener(CalendarChanger calendarChanger, boolean next){
            this.calendarChanger = calendarChanger;
            this.moveForward = next;
        }

        @Override
        public void onClick(View view) {
            if(moveForward){
                calendarChanger.nextMonth();
            } else {
                calendarChanger.prevMonth();
            }
        }
    }

    private void addMonth(){
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(HORIZONTAL);
        this.addView(linearLayout);

        Button prev = new Button(context);
        prev.setText("<");
        prev.setTextSize(25);
        prev.setOnClickListener(new ClickListener(this, false));
        linearLayout.addView(prev);


        monthTextView = new TextView(context);
        monthTextView.setTextSize(25);
        monthTextView.setLayoutParams(
                new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        );
        linearLayout.addView(monthTextView);

        Button next = new Button(context);
        next.setText(">");
        next.setTextSize(25);
        next.setOnClickListener(new ClickListener(this, true));
        linearLayout.addView(next);
    }

    private void setMonth(int month){
        String monthName = getResources().getString(monthId[month]);
        monthTextView.setText(monthName);
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
        this.addView(gridView);
        update();
    }


    private void update(){
        setMonth(cal.get(Calendar.MONTH));
        CalendarAdapter calendarAdapter = new CalendarAdapter(context, cal);
        gridView.setAdapter(calendarAdapter);
    }
}
