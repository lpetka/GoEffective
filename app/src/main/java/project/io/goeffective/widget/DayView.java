package project.io.goeffective.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import project.io.goeffective.models.DayModel;
import project.io.goeffective.models.IDayModel;
import project.io.goeffective.widget.adapters.DayTaskAdapter;

public class DayView extends LinearLayout {
    private Context context;
    ListView taskListView;
    private IDayModel model = new DayModel();

    public DayView(Context context) {
        super(context);
        setupView(context);
    }

    public DayView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupView(context);
    }

    public DayView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setupView(context);
    }

    private void setupView(Context context) {
        this.context = context;
        this.setOrientation(VERTICAL);
        addTasksList();
    }

    private void addTasksList() {
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setLayoutParams(
                new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        );
        this.addView(linearLayout);

        taskListView = new ListView(context);
        DayTaskAdapter taskAdapter = new DayTaskAdapter(context, model);
        taskListView.setAdapter(taskAdapter);
//        prev.setOnClickListener(new ClickListener(this, false)); TODO
        linearLayout.addView(taskListView);
    }

    private void update() {
        DayTaskAdapter taskAdapter = new DayTaskAdapter(context, model);
        taskListView.setAdapter(taskAdapter);
    }
}
