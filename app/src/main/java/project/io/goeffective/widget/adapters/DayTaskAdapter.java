package project.io.goeffective.widget.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
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
import project.io.goeffective.models.Task;

public class DayTaskAdapter extends BaseAdapter {
    static int MAX_HISTORY_LENGTH = 5;
    List<Task> tasks;
    private Context context;

    public DayTaskAdapter(Context context, IDayModel model) {
        this.context = context;
        this.tasks = model.getTodayTasks();
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
        return task.getName().hashCode();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Task task = tasks.get(i);
        return createListItem(task);
    }

    private View createListItem(Task task) {
        RelativeLayout layout = new RelativeLayout(context);

        View taskNameView = createTaskNameView(task);
        RelativeLayout.LayoutParams leftParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        leftParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        taskNameView.setLayoutParams(leftParams);
        layout.addView(taskNameView);

        LinearLayout linearLayout = new LinearLayout(context);
        RelativeLayout.LayoutParams rightParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        rightParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
        linearLayout.setLayoutParams(rightParams);

        View taskDaysInARowView = createTaskDaysInARowView(task);
        linearLayout.addView(taskDaysInARowView);

        View taskProgressView = createTaskProgressView(task);
        linearLayout.addView(taskProgressView);

        layout.addView(linearLayout);
        return layout;
    }

    private View createTaskProgressView(Task task) {
        LinearLayout linearLayout = new LinearLayout(context);

        List<Boolean> taskHistory = task.getHistory();
        final int size = taskHistory.size();
        if (size > MAX_HISTORY_LENGTH){
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
        return textView;
    }

    private View createTaskDaysInARowView(Task task) {
        TextView textView = new TextView(context);

        String historyLengthLabel = getDaysInARowLabel(task);
        textView.setText(historyLengthLabel);
        return textView;
    }

    private String getDaysInARowLabel(Task task) {
        final int daysInARow = task.countDaysInARow();
        String historyLengthLabel = "";
        if (daysInARow > 0) {
            historyLengthLabel = Integer.toString(daysInARow);
        }
        return historyLengthLabel;
    }

    public void toggle(int i) {
        Task task = (Task) getItem(i);
        task.toggle();
        notifyDataSetChanged();
    }
}
