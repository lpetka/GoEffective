package project.io.goeffective.di;

import android.content.Context;

import dagger.Module;

@Module
public class UtilsModule {
    private final Context context;

    public UtilsModule(Context context) {
        this.context = context;
    }
}
