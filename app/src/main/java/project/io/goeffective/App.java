package project.io.goeffective;

import android.app.Application;
import android.content.Context;

import project.io.goeffective.di.DIComponent;

public class App extends Application
{
    private static DIComponent component;

    private void buildComponentAndInject()
    {
        component = DIComponent.Initializer.init(this);
        component.inject(this);
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        buildComponentAndInject();
    }

    public static DIComponent getComponent() {
        return component;
    }
}
