package ru.mirea.zhidkov.looper;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

public class MyLooper extends Thread {

    public Handler mHandler;
    private Handler mainHandler;

    public MyLooper(Handler mainHandler) {
        this.mainHandler = mainHandler;
    }

    @Override
    public void run() {
        Looper.prepare();
        mHandler = new Handler(Looper.myLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                Bundle bundle = msg.getData();
                int age = Integer.parseInt(bundle.getString("AGE"));
                String job = bundle.getString("JOB");

                try {
                    Thread.sleep(age * 1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                String result = "Возраст: " + age + ", профессия: " + job;
                Log.d("MyLooper", "Результат: " + result);

                Message toMain = Message.obtain();
                Bundle data = new Bundle();
                data.putString("result", result);
                toMain.setData(data);
                mainHandler.sendMessage(toMain);
            }
        };

        Looper.loop();
    }
}
