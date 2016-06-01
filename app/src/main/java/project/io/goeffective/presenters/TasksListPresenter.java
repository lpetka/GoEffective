package project.io.goeffective.presenters;

import project.io.goeffective.services.INavigator;
import project.io.goeffective.views.ITasksListView;
import rx.Scheduler;

public class TasksListPresenter extends Presenter<ITasksListView> {
    private Scheduler uiThread;
    private final INavigator navigator;

    public TasksListPresenter(ITasksListView iTasksListView, INavigator navigator, Scheduler uiThread) {
        super(iTasksListView);
        this.navigator = navigator;
        this.uiThread = uiThread;
    }

    @Override
    public void start() {
        super.start();

        this.subscriptions.add(view.addTaskClick().observeOn(uiThread).subscribe(o ->
            {
                navigator.openTaskAddActivity();
            }
        ));
    }
}
