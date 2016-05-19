package project.io.goeffective.widgets.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.OnClick;
import project.io.goeffective.R;
import project.io.goeffective.models.ITaskListModel;
import project.io.goeffective.utils.dbobjects.Task;

public class TaskListAdapter extends BaseAdapter {
    private final List<Task> taskList;
    private final Context context;
    private LayoutInflater inflater;


    public TaskListAdapter(Context context, ITaskListModel taskListModel) {
        this.context = context;
        this.taskList = taskListModel.getDetailedTaskList();
    }

    @Override
    public int getCount() {
        return taskList.size();
    }

    @Override
    public Object getItem(int position) {
        return taskList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            if(inflater == null)
                inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.fragment_task_list_row, null);
        }
        TextView name = (TextView) convertView.findViewById(R.id.taskName);
        Button editButton = (Button) convertView.findViewById(R.id.editTaskButton);
        Task task = taskList.get(position);
        name.setText(task.getName());

        editButton.setOnClickListener(
                view -> Toast.makeText(context,"edit: "+task.getName(),Toast.LENGTH_SHORT).show()
        );

        return null;
    }
}
