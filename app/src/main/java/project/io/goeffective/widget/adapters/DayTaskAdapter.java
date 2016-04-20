package project.io.goeffective.widget.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import project.io.goeffective.R;
import project.io.goeffective.models.IDayModel;
import project.io.goeffective.models.Task;

public class DayTaskAdapter extends BaseAdapter {
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

        final String taskName = task.getName();
        View taskNameView = createTaskNameView(taskName);
        layout.addView(taskNameView);

        final List<Boolean> taskHistory = task.getHistory();
        View taskProgressView = createTaskProgressView(taskHistory);
        layout.addView(taskProgressView);

        return layout;
    }

    private View createTaskNameView(String taskName) {
        TextView textView = new TextView(context);
        textView.setText(taskName);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        textView.setLayoutParams(params);
        return textView;
    }

    private View createTaskProgressView(List<Boolean> taskHistory) {
        TextView textView = new TextView(context);
        final String historyLengthLabel = Integer.toString(taskHistory.size());
        textView.setText(historyLengthLabel);

        final int color = context.getResources().getColor(R.color.taskDone);
        textView.setHeight(70);
        textView.setTextSize(20);
        textView.setBackgroundColor(color);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
        textView.setLayoutParams(params);
        return textView;
    }
}
