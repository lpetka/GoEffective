package project.io.goeffective.widget.adapters;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

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

    private TextView createListItem(Task task) {
        TextView textView = new TextView(context);
        textView.setText(task.getName());
        textView.setGravity(Gravity.START);
        textView.setHeight(70);
        textView.setTextSize(20);
//        final int color = context.getResources().getColor(R.color.taskDone);
//        textView.setBackgroundColor(color);
        return textView;
    }
}
