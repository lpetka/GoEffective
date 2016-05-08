package project.io.goeffective.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

import project.io.goeffective.widgets.adapters.WeekDayListAdapter;

public class WeekDayView extends ListView {
    public WeekDayView(Context context) {
        super(context);
        setupView(context);
    }

    public WeekDayView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupView(context);
    }

    public WeekDayView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setupView(context);
    }

    private void setupView(Context context) {
        WeekDayListAdapter weekDayListAdapter = new WeekDayListAdapter(context);
        setAdapter(weekDayListAdapter);
        setOnItemClickListener((adapterView, view, i, l) -> weekDayListAdapter.toggle(i));
    }
}
