package project.io.goeffective.widgets.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import project.io.goeffective.R;

public class WeekDayListAdapter extends BaseAdapter {
    final String[] weekDayNames;
    private final Context context;

    public WeekDayListAdapter(Context context) {
        this.context = context;
        this.weekDayNames = context.getResources().getStringArray(R.array.week_days);
    }

    @Override
    public int getCount() {
        return weekDayNames.length;
    }

    @Override
    public Object getItem(int i) {
        return weekDayNames[i];
    }

    @Override
    public long getItemId(int i) {
        return getItem(i).hashCode();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        RelativeLayout layout = new RelativeLayout(context);
        layout.setPadding(16, 16, 16, 16);

        TextView weekDayTextView = new TextView(context);
        weekDayTextView.setText(weekDayNames[i]);
        RelativeLayout.LayoutParams leftParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        leftParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        weekDayTextView.setLayoutParams(leftParams);
        layout.addView(weekDayTextView);

        CheckBox checkBox = new CheckBox(context);
        RelativeLayout.LayoutParams rightParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        rightParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
        checkBox.setLayoutParams(rightParams);
        layout.addView(checkBox);

        layout.setOnClickListener(view1 -> checkBox.toggle());

        return layout;
    }
}
