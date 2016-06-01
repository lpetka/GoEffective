package project.io.goeffective.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

import butterknife.InjectView;
import project.io.goeffective.App;
import project.io.goeffective.R;
import project.io.goeffective.common.BaseActivity;
import project.io.goeffective.presenters.IPresenter;
import project.io.goeffective.presenters.TaskEditPresenter;
import project.io.goeffective.utils.dbobjects.Task;
import project.io.goeffective.utils.dbobjects.TaskStart;
import project.io.goeffective.views.ITaskEditView;
import project.io.goeffective.widgets.WeekDayView;
import project.io.goeffective.widgets.adapters.WeekDayListAdapter;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.android.view.ViewObservable;

public class TaskEditActivity extends BaseActivity implements ITaskEditView {
    private Task task;

    @InjectView(R.id.task_edit_task_name)
    EditText taskName;

    @InjectView(R.id.remove_task_button)
    Button removeTaskButton;

    @InjectView(R.id.save_task_button)
    Button saveTaskButton;

    @InjectView(R.id.task_edit_week_day_list_selector)
    WeekDayView weekDayListSelector;

    @InjectView(R.id.task_edit_note_input)
    EditText noteInput;


    public TaskEditActivity() {
        super(R.layout.activity_edit_task);
        App.getComponent().inject(this);
    }

    @Override
    protected IPresenter createPresenter(BaseActivity baseActivity, Bundle savedInstanceState) {
        return new TaskEditPresenter(this, AndroidSchedulers.mainThread(), this);
    }

    @Override
    protected void onViewReady() {
        this.task = (Task) getIntent().getSerializableExtra("task");
        updateView();
    }

    private void updateView() {
        taskName.setText(task.getName(), TextView.BufferType.EDITABLE);
        noteInput.setText(task.getNote(), TextView.BufferType.EDITABLE);
        WeekDayListAdapter weekDayListAdapter = (WeekDayListAdapter) weekDayListSelector.getAdapter();
        int checkedDay;
        Calendar calendar;
        for(TaskStart taskStart: task.getTaskStartList()) {
            calendar = Calendar.getInstance();
            calendar.setTime(taskStart.getStart());
            checkedDay = (calendar.get(Calendar.DAY_OF_WEEK) - 2) % 7;
            if(checkedDay < 0)
                checkedDay += 7;
            weekDayListAdapter.setChecked(checkedDay);
        }
        weekDayListAdapter.notifyDataSetChanged();
    }

    @Override
    public Observable saveTaskButtonClick() {
        return ViewObservable.clicks(saveTaskButton);
    }

    @Override
    public Observable removeTaskButtonClick() {
        return ViewObservable.clicks(removeTaskButton);
    }

    @Override
    public void close() {
        this.finish();
    }

    @Override
    public Task getTask() {
        return task;
    }

    @Override
    public WeekDayView getWeekDayListSelector() {
        return weekDayListSelector;
    }

    @Override
    public EditText getTaskName() {
        return taskName;
    }

    @Override
    public EditText getNoteInput() {
        return noteInput;
    }
}
