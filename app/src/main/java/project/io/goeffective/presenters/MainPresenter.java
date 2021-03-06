package project.io.goeffective.presenters;

import project.io.goeffective.services.INavigator;
import project.io.goeffective.views.IMainView;
import rx.Scheduler;

public class MainPresenter extends Presenter<IMainView> {
    private Scheduler uiThread;
    private final INavigator navigator;

    public MainPresenter(IMainView iMainView, Scheduler uiThread, INavigator navigator) {
        super(iMainView);
        this.uiThread = uiThread;
        this.navigator = navigator;
    }

    @Override
    public void start() {
        super.start();
//        this.subscriptions.add(view.openPreferencesScreenClick().observeOn(uiThread).subscribe(o ->
//            {navigator.openPreferencesActivity();}));
    }
}
