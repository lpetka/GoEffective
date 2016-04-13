package project.io.goeffective.di;

import javax.inject.Singleton;
import dagger.Component;
import project.io.goeffective.App;

@Singleton
@Component(modules = {UtilsModule.class})
public interface DIComponent {
    void inject(App app);

    final class Initializer {
        private Initializer() {}
        public static DIComponent init(App app) {
            return DaggerDIComponent.builder()
                    .utilsModule(new UtilsModule(app))
                    .build();
        }
    }
}
