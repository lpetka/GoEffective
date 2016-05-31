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

public class DayTaskAdapter extends BaseAdapter {
    static int MAX_HISTORY_LENGTH = 5;
    List<DayTaskModel> dayTaskModels;
    private Context context;
    private IDayModel model;

    public DayTaskAdapter(Context context, IDayModel model) {
        this.context = context;
        this.tasks = model.getTodayTasks();
        this.model = model;
    }

    @Override
    public int getCount() {
        return dayTaskModels.size();
    }

    @Override
    public Object getItem(int i) {
        return dayTaskModels.get(i);
    }

    @Override
    public long getItemId(int i) {
        DayTaskModel dayTaskModel = dayTaskModels.get(i);
        return dayTaskModel.getName().hashCode();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        DayTaskModel dayTaskModel = dayTaskModels.get(i);
        return createListItem(dayTaskModel);
    }

    private View createListItem(DayTaskModel dayTaskModel) {
        RelativeLayout layout = new RelativeLayout(context);
        layout.setPadding(16, 16, 16, 16);

        View taskNameView = createTaskNameView(dayTaskModel);
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

        View taskProgressView = createTaskProgressView(dayTaskModel);
        linearLayout.addView(taskProgressView);

        View taskDaysInARowView = createTaskDaysInARowView(dayTaskModel);
        linearLayout.addView(taskDaysInARowView);

        layout.addView(linearLayout);
        return layout;
    }

    private View createTaskProgressView(DayTaskModel dayTaskModel) {
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

    private View createTaskNameView(DayTaskModel dayTaskModel) {
        TextView textView = new TextView(context);
        final String taskName = dayTaskModel.getName();
        textView.setText(taskName);
        textView.setTextSize(24);
        return textView;
    }

    private View createTaskDaysInARowView(DayTaskModel dayTaskModel) {
        TextView textView = new TextView(context);

        String historyLengthLabel = getDaysInARowLabel(dayTaskModel);
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
