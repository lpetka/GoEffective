package project.io.goeffective.utils;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import project.io.goeffective.di.UtilsModule;


@Module(includes = UtilsModule.class)
public class DatabaseHandlerModule {
    @Provides @Singleton public DatabaseHandler provideDatabaseHandler(Context context) {
        return new DatabaseHandler(context);
    }
}
