package io.github.blackfishlabs.precificaapp;

import android.app.Application;

import timber.log.Timber;

public class BaseApplication extends Application {

    private static BaseApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        initLogging();
    }

    public static BaseApplication getInstance() {
        return mInstance;
    }

    protected void initLogging() {
        Timber.plant(new Timber.DebugTree() {
            @Override
            protected String createStackElementTag(final StackTraceElement element) {
                return super.createStackElementTag(element) + ":" + element.getLineNumber();
            }
        });
    }

}

