package com.checkapp.test.app;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import timber.log.Timber;

public class App extends MultiDexApplication {

    private static Context globalContext;

    public void onCreate() {
        super.onCreate();
        globalContext = this;
        iniTimber();

    }

    private void iniTimber() {
        Timber.plant(
                new Timber.DebugTree() {
                    // Add the line number to the tag.
                    @Override
                    protected String createStackElementTag(StackTraceElement element) {
                        return super.createStackElementTag(element) + ':' + element.getLineNumber();
                    }
                });
    }
    public static Context getGlobalContext() {
        return globalContext;
    }
}
