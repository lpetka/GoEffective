package project.io.goeffective.activities;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.InjectView;
import project.io.goeffective.App;
import project.io.goeffective.R;
import project.io.goeffective.common.BaseActivity;
import project.io.goeffective.presenters.IPresenter;
import project.io.goeffective.presenters.TaskEditPresenter;
import project.io.goeffective.utils.DatabaseHandler;
import project.io.goeffective.utils.dbobjects.Task;
import project.io.goeffective.views.ITaskEditView;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.android.view.ViewObservable;

public class TaskEditActivity extends BaseActivity implements ITaskEditView {
    private Task task;

    @InjectView(R.id.task_name_input)
    TextView taskNameInput;

    @InjectView(R.id.remove_task_button)
    Button removeTaskButton;

    @Inject
    DatabaseHandler databaseHandler;

    public TaskEditActivity() {
        super(R.layout.activity_edit_task);
        App.getComponent().inject(this);
    }

    @Override
    protected IPresenter createPresenter(BaseActivity baseActivity, Bundle savedInstanceState) {
        return new TaskEditPresenter(this, AndroidSchedulers.mainThread());
    }

    @Override
    protected void onViewReady() {
        this.task = (Task) getIntent().getSerializableExtra("task");
        String taskName = task.getName() + " " + task.getId();
        taskNameInput.setText(taskName);
    }

    @Override
    public Observable removeTaskButtonClick() {
        return ViewObservable.clicks(removeTaskButton);
    }

    @Override
    public void removeTask() {
        new AlertDialog.Builder(TaskEditActivity.this)
                .setTitle("Usuwanie zadania")
                .setMessage("Czy na pewno chcesz usunąć to zadanie?")
                .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                    databaseHandler.removeTask(task);

                    close();
                })
                .setNegativeButton(android.R.string.no, (dialog, which) -> {
                    // do nothing
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void close() {
        this.finish();
    }
}
