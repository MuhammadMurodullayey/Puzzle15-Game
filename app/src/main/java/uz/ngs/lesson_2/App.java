package uz.ngs.lesson_2;

import android.app.Application;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        MySharepreference.initSharedPreferences(this);
    }
}
