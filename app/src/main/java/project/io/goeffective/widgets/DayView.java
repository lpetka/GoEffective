package project.io.goeffective.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import project.io.goeffective.models.DayModel;
import project.io.goeffective.models.IDayModel;
import project.io.goeffective.widgets.adapters.DayTaskAdapter;

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
        taskListView.setOnItemClickListener((adapterView, view, i, l) -> taskAdapter.toggle(i));
        linearLayout.addView(taskListView);
    }

    private void update() {
        DayTaskAdapter taskAdapter = new DayTaskAdapter(context, model);
        taskListView.setAdapter(taskAdapter);
    }
}
