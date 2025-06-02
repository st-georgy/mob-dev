package ru.mirea.zhidkov.employeedb;

import android.app.Application;
import androidx.room.Room;

public class App extends Application {
    private static App instance;
    private AppDatabase database;

    public static App getInstance() {
        return instance;
    }

    public AppDatabase getDatabase() {
        return database;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        database = Room.databaseBuilder(getApplicationContext(),
                        AppDatabase.class, "superhero-db")
                .allowMainThreadQueries()
                .build();
    }
}