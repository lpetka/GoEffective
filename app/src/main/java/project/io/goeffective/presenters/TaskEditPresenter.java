package project.io.goeffective.presenters;

import android.app.AlertDialog;
import android.content.Context;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.inject.Inject;

import project.io.goeffective.App;
import project.io.goeffective.R;
import project.io.goeffective.activities.TaskEditActivity;
import project.io.goeffective.utils.DatabaseHandler;
import project.io.goeffective.utils.dbobjects.Task;
import project.io.goeffective.utils.dbobjects.TaskStart;
import project.io.goeffective.views.ITaskEditView;
import project.io.goeffective.widgets.WeekDayView;
import project.io.goeffective.widgets.adapters.WeekDayListAdapter;
import rx.Scheduler;

public class TaskEditPresenter extends Presenter<ITaskEditView>{
    private Scheduler uiThread;
    private Context context;

    @Inject
    DatabaseHandler databaseHandler;

    public TaskEditPresenter(ITaskEditView iTaskEditView, Scheduler uiThread, TaskEditActivity taskEditActivity) {
        super(iTaskEditView);
        this.uiThread = uiThread;
        this.context = taskEditActivity;
        App.getComponent().inject(this);
    }

    @Override
    public void start() {
        super.start();

        this.subscriptions.add(view.removeTaskButtonClick().observeOn(uiThread).subscribe(o -> {
            removeTask(view.getTask());
        }));

        this.subscriptions.add(view.saveTaskButtonClick().observeOn(uiThread).subscribe(o -> {
            saveTask(view.getTask(), view.getTaskName(), view.getWeekDayListSelector(), view.getNoteInput());
        }));
    }

    private void saveTask(Task task, EditText taskName, WeekDayView weekDayListSelector, EditText noteInput) {
        switch(validateData(taskName, weekDayListSelector)) {
            case 0:     //valid
                updateTask(task, taskName, weekDayListSelector, noteInput);
                break;
            case 1:     //empty task name
                Toast.makeText(context, "Podaj nazwę zadania.", Toast.LENGTH_SHORT).show();
                break;
            case 2:     //no checked days
                Toast.makeText(context, "Wybierz dni tygodnia.", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void updateTask(Task task, EditText taskName, WeekDayView weekDayListSelector, EditText noteInput) {
        task.setName(taskName.getText().toString());
        task.getTaskStartList().clear();
        WeekDayListAdapter weekDayListAdapter = (WeekDayListAdapter) weekDayListSelector.getAdapter();
        boolean[] checked = weekDayListAdapter.getChecked();
        GregorianCalendar date;
        for(int i = 0; i < checked.length; i++) {
            if(checked[i]) {
                //in Gregorian Calendar day number 1 is Sunday
                int myDay = (i + 2) % 7;
                date = new GregorianCalendar(
                        Calendar.getInstance().get(Calendar.YEAR),
                        Calendar.getInstance().get(Calendar.MONTH),
                        Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
                );
                while (date.get(Calendar.DAY_OF_WEEK) != myDay)
                    date.add(Calendar.DATE, 1);
                task.addTaskStart(new TaskStart(-1, date.getTime(), 7));
            }
        }
        databaseHandler.updateTask(task);
    }

    private int validateData(EditText taskName, WeekDayView weekDayListSelector) {
        if(taskName.getText().toString().matches(""))
            return 1;

        WeekDayListAdapter weekDayListAdapter = (WeekDayListAdapter) weekDayListSelector.getAdapter();
        boolean[] checked = weekDayListAdapter.getChecked();
        int i = 0;
        while(i < checked.length) {
            if (checked[i])
                return 0;
            i++;
        }

        return 2;
    }

    private void removeTask(Task task) {
        new AlertDialog.Builder(context)
                .setTitle("Usuwanie zadania")
                .setMessage("Czy na pewno chcesz usunąć to zadanie?")
                .setPositiveButton(R.string.yes, (dialog, which) -> {
                    databaseHandler.removeTask(task);
                    view.close();
                })
                .setNegativeButton(R.string.no, (dialog, which) -> {
                    // do nothing
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
