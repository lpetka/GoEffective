package project.io.goeffective.di;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class UtilsModule {
    private final Context context;

    public UtilsModule(Context context) {
        this.context = context;
    }

    @Provides @Singleton Context getContext() {
        return context;
    }
}
