package com.example.farhad_javadi.drawerlayout;

import android.app.Application;
import database.DBHelper;

// A class for make only one instance from database
public class App extends Application {

    public static DBHelper dbHelper;

    @Override
    public void onCreate() {
        super.onCreate();
        dbHelper=DBHelper.getsInstance(this);
    }
}
