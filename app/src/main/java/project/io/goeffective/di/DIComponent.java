package project.io.goeffective.di;

import javax.inject.Singleton;
import dagger.Component;
import project.io.goeffective.App;
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
