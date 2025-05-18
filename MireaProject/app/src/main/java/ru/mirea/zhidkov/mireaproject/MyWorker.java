package ru.mirea.zhidkov.mireaproject;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.util.concurrent.TimeUnit;

public class MyWorker extends Worker {
    private static final String TAG = "MyWorker";

    public MyWorker(
            @NonNull Context context,
            @NonNull WorkerParameters params) {
        super(context, params);
    }

    @Override
    public Result doWork() {
        Log.d(TAG, "Фоновая задача начата");

        try {
            TimeUnit.SECONDS.sleep(10);
            Log.d(TAG, "Фоновая задача успешно завершена");
            return Result.success();
        } catch (InterruptedException e) {
            Log.e(TAG, "Задача прервана", e);
            return Result.failure();
        }
    }
}
