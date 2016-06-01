package project.io.goeffective.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import butterknife.InjectView;
import project.io.goeffective.R;
import project.io.goeffective.common.BaseActivity;
import project.io.goeffective.presenters.AddTaskPresenter;
import project.io.goeffective.presenters.IPresenter;
import project.io.goeffective.services.Navigator;
import project.io.goeffective.views.IAddTaskView;
import project.io.goeffective.widgets.WeekDayView;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.android.view.ViewObservable;

public class TaskAddActivity extends BaseActivity implements IAddTaskView {

    @InjectView(R.id.task_add_note_input)
    EditText noteInput;

    @InjectView(R.id.task_add_task_name)
    EditText taskName;

    @InjectView(R.id.task_add_week_day_list_selector)
    WeekDayView weekDayListSelector;

    @InjectView(R.id.create_task_button)
    Button createTaskButton;

    public TaskAddActivity() {
        super(R.layout.activity_add_task);
    }

    @Override
    protected IPresenter createPresenter(BaseActivity baseActivity, Bundle savedInstanceState) {
        return new AddTaskPresenter(this, new Navigator(this), AndroidSchedulers.mainThread(), this);
    }

    @Override
    public Observable createTaskButtonClick() {
        return ViewObservable.clicks(createTaskButton);
    }

    public EditText getNoteInput() {
        return noteInput;
    }

    public EditText getTaskName() {
        return taskName;
    }

    public WeekDayView getWeekDayListSelector() {
        return weekDayListSelector;
    }

    @Override
    public void close() {
        this.finish();
    }
}
