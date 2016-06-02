package project.io.goeffective.widgets.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import project.io.goeffective.R;
import project.io.goeffective.models.IDayModel;
import project.io.goeffective.utils.dbobjects.Task;
import project.io.goeffective.models.DayTaskModel;
import project.io.goeffective.utils.dbobjects.Task;

public class DayTaskAdapter extends BaseAdapter {
    static int MAX_HISTORY_LENGTH = 5;
    private Context context;
    private IDayModel model;
    private List<Task> tasks;

    public DayTaskAdapter(Context context, IDayModel model) {
        this.context = context;
        this.tasks = model.getTodayTasks();
        this.model = model;
    }

    @Override
    public int getCount() {
        return tasks.size();
    }

    @Override
    public Object getItem(int i) {
        return tasks.get(i);
    }

    @Override
    public long getItemId(int i) {
        Task task = tasks.get(i);
        return task.getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Task task = tasks.get(i);
        return createListItem(task);
    }

    private View createListItem(Task task) {
        RelativeLayout layout = new RelativeLayout(context);
        layout.setPadding(16, 16, 16, 16);

        View taskNameView = createTaskNameView(task);
        RelativeLayout.LayoutParams leftParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        leftParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        taskNameView.setLayoutParams(leftParams);
        layout.addView(taskNameView);

        LinearLayout linearLayout = new LinearLayout(context);
        RelativeLayout.LayoutParams rightParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        rightParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
        linearLayout.setLayoutParams(rightParams);
        linearLayout.setGravity(Gravity.CENTER);

        View taskProgressView = createTaskProgressView(task);
        linearLayout.addView(taskProgressView);

        View taskDaysInARowView = createTaskDaysInARowView(task);
        linearLayout.addView(taskDaysInARowView);

        layout.addView(linearLayout);
        return layout;
    }

    private View createTaskProgressView(Task task) {
        LinearLayout linearLayout = new LinearLayout(context);
        List<Boolean> taskHistory = model.getHistory(task);
        final int size = taskHistory.size();
        if (size > MAX_HISTORY_LENGTH) {
            taskHistory = taskHistory.subList(size - MAX_HISTORY_LENGTH, size);
        }
        Drawable drawable;
        for (Boolean isTaskDone : taskHistory) {
            if (isTaskDone) {
                drawable = context.getResources().getDrawable(R.drawable.day_task_done);
            } else {
                drawable = context.getResources().getDrawable(R.drawable.day_task_not_done);
            }
            ImageView imageView = new ImageView(context);
            imageView.setImageDrawable(drawable);
            linearLayout.addView(imageView);
        }

        return linearLayout;
    }

    private View createTaskNameView(Task task) {
        TextView textView = new TextView(context);
        final String taskName = task.getName();
        textView.setText(taskName);
        textView.setTextSize(24);
        return textView;
    }

    private View createTaskDaysInARowView(Task task) {
        TextView textView = new TextView(context);

        String historyLengthLabel = getDaysInARowLabel(task);
        textView.setText(historyLengthLabel);
        textView.setTextSize(24);
        textView.setPadding(16, 0, 16, 0);
        textView.setWidth(70);
        return textView;
    }

    private String getDaysInARowLabel(Task task) {
        final int daysInARow = model.countDaysInARow(task);
        String historyLengthLabel = "";
        if (daysInARow > 0) {
            historyLengthLabel = Integer.toString(daysInARow);
        }
        return historyLengthLabel;
    }

    public void toggle(int i) {
        Task task = (Task) getItem(i);
        model.toggle(task);
        notifyDataSetChanged();
    }
}
