package project.io.goeffective.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import project.io.goeffective.App;
import project.io.goeffective.R;
import project.io.goeffective.common.BaseFragment;
import project.io.goeffective.models.TaskListModel;
import project.io.goeffective.presenters.IPresenter;
import project.io.goeffective.presenters.TasksListPresenter;
import project.io.goeffective.services.INavigator;
import project.io.goeffective.services.Navigator;
import project.io.goeffective.utils.DatabaseHandler;
import project.io.goeffective.utils.dbobjects.Task;
import project.io.goeffective.views.ITasksListView;
import project.io.goeffective.widgets.adapters.TaskListAdapter;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.android.view.ViewObservable;

public class TasksListFragment extends BaseFragment implements ITasksListView {
    private INavigator navigator;
    private TaskListAdapter taskListAdapter;

    @InjectView(R.id.add_task_button)
    Button addTaskButton;

    @InjectView(R.id.task_list)
    ListView taskList;

    @Inject
    DatabaseHandler databaseHandler;

    public TasksListFragment() {
        App.getComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_list, container, false);
        ButterKnife.inject(this, view);
        taskList.setOnItemClickListener((parent, view1, position, id) -> {
            Task task = (Task) parent.getItemAtPosition(position);
            navigator.openTaskEditActivity(task);
        });
        taskListAdapter = new TaskListAdapter(getContext(), new TaskListModel());
        taskList.setAdapter(taskListAdapter);
        return view;
    }

    @Override
    protected IPresenter createPresenter(BaseFragment baseFragment, Bundle savedInstanceState) {
        navigator = new Navigator(getContext());
        return new TasksListPresenter(this, navigator, AndroidSchedulers.mainThread());
    }

    @Override
    public void onResume() {
        super.onResume();
        taskListAdapter.updateView();
    }

    @Override
    public Observable addTaskClick() {
        return ViewObservable.clicks(addTaskButton);
    }
}
