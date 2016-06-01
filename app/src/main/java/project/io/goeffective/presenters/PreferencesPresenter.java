package project.io.goeffective.presenters;

import android.app.AlertDialog;
import android.content.Context;

import javax.inject.Inject;

import project.io.goeffective.App;
import project.io.goeffective.R;
import project.io.goeffective.activities.PreferencesActivity;
import project.io.goeffective.services.INavigator;
import project.io.goeffective.utils.DatabaseHandler;
import project.io.goeffective.views.IPreferencesView;
import rx.Scheduler;

public class PreferencesPresenter extends Presenter<IPreferencesView> {
    private INavigator navigator;
    private Scheduler uiThread;
    private Context context;

    @Inject
    DatabaseHandler databaseHandler;

    public PreferencesPresenter(IPreferencesView iPreferencesView, INavigator navigator, Scheduler uiThread, PreferencesActivity preferencesActivity) {
        super(iPreferencesView);
        this.navigator = navigator;
        this.uiThread = uiThread;
        this.context = preferencesActivity;
        App.getComponent().inject(this);
    }

    @Override
    public void start() {
        super.start();

        this.subscriptions.add(view.clearUserDataButtonClick().observeOn(uiThread).subscribe(o -> {
            clearUsersData();
        }));
    }

    private void clearUsersData() {
        new AlertDialog.Builder(context)
                .setTitle("Czyszczenie danych")
                .setMessage("Czy na pewno chcesz usunąć wszystkie zadania?")
                .setPositiveButton(R.string.yes, (dialog, which) -> {
                    databaseHandler.clearDatabase();
                })
                .setNegativeButton(R.string.no, (dialog, which) -> {
                    // do nothing
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
