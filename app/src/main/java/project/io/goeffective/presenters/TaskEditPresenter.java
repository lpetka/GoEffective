package project.io.goeffective.presenters;

import project.io.goeffective.views.ITaskEditView;
import rx.Scheduler;

public class TaskEditPresenter extends Presenter<ITaskEditView>{
    private Scheduler uiThread;

    public TaskEditPresenter(ITaskEditView iTaskEditView, Scheduler uiThread) {
        super(iTaskEditView);
        this.uiThread = uiThread;
    }

    @Override
    public void start() {
        super.start();

        this.subscriptions.add(view.removeTaskButtonClick().observeOn(uiThread).subscribe(o -> {
            view.removeTask();
        }));
    }
}
