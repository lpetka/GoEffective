package project.io.goeffective.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import javax.inject.Inject;

import butterknife.InjectView;
import project.io.goeffective.App;
import project.io.goeffective.R;
import project.io.goeffective.common.BaseFragment;
import project.io.goeffective.presenters.IPresenter;
import project.io.goeffective.presenters.TasksListPresenter;
import project.io.goeffective.services.Navigator;
import project.io.goeffective.utils.DatabaseHandler;
import project.io.goeffective.utils.StringConstants;
import project.io.goeffective.utils.dbobjects.Task;
import project.io.goeffective.views.ITasksListView;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.android.view.ViewObservable;

public class TasksListFragment extends BaseFragment implements ITasksListView {
    @InjectView(R.id.addTaskButton)
    Button addTaskButton;

    @InjectView(R.id.addDummyTaskButton)
    Button addDummyTaskButton;

    @Inject
    DatabaseHandler databaseHandler;


    public TasksListFragment() {
        args.putInt(StringConstants.FRAGMENT_RESOURCE_ID, R.layout.fragment_task_list);
        super.setArguments(args);
        App.component(getContext()).inject(this);
    }

    @Override
    protected IPresenter createPresenter(BaseFragment baseFragment, Bundle savedInstanceState) {
        return new TasksListPresenter(this, new Navigator(getContext()), AndroidSchedulers.mainThread());
    }

    @Override
    public Observable addTaskClick() {
        return ViewObservable.clicks(addTaskButton);
    }

    @Override
    public void addDummyTask() {
        databaseHandler.addTask(new Task("Dummy task"));
    }

    @Override
    public Observable addDummyTaskClick() {
        return ViewObservable.clicks(addDummyTaskButton);
    }
}
