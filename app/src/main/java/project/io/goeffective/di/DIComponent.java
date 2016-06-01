package project.io.goeffective.di;

import javax.inject.Singleton;
import dagger.Component;
import project.io.goeffective.App;
import project.io.goeffective.activities.TaskEditActivity;
import project.io.goeffective.fragments.TasksListFragment;
import project.io.goeffective.models.CalendarModel;
import project.io.goeffective.models.DayModel;
import project.io.goeffective.models.TaskListModel;
import project.io.goeffective.presenters.AddTaskPresenter;
import project.io.goeffective.presenters.PreferencesPresenter;
import project.io.goeffective.presenters.TaskEditPresenter;
import project.io.goeffective.utils.DatabaseHandler;
import project.io.goeffective.utils.DatabaseHandlerModule;

@Singleton
@Component(modules = {
        UtilsModule.class,
        DatabaseHandlerModule.class
    }
)
public interface DIComponent {
    void inject(App app);
    void inject(TaskListModel taskListModel);
    void inject(TasksListFragment    tasksListFragment);
    void inject(CalendarModel calendarModel);
    void inject(TaskEditActivity taskEditActivity);
    void inject(AddTaskPresenter addTaskPresenter);
    void inject (TaskEditPresenter taskEditPresenter);
    void inject(PreferencesPresenter preferencesPresenter);
    void inject(DayModel dayModel);

    DatabaseHandler provideDatabaseHandler();

    final class Initializer {
        private Initializer() {}
        public static DIComponent init(App app) {
            return DaggerDIComponent.builder()
                    .utilsModule(new UtilsModule(app))
                    .databaseHandlerModule(new DatabaseHandlerModule())
                    .build();
        }
    }


}
