package ru.mirea.zhidkov.mireaproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class BackgroundFragment extends Fragment {

    private TextView textView;

    public BackgroundFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_background, container, false);

        textView = view.findViewById(R.id.statusTextView);
        WorkRequest uploadWorkRequest = new OneTimeWorkRequest.Builder(MyWorker.class)
                .build();

        WorkManager.getInstance(requireContext()).enqueue(uploadWorkRequest);
        WorkManager.getInstance(requireContext())
                .getWorkInfoByIdLiveData(uploadWorkRequest.getId())
                .observe(getViewLifecycleOwner(), workInfo -> {
                    if (workInfo != null) {
                        if (workInfo.getState() == WorkInfo.State.RUNNING) {
                            textView.setText("Задача выполняется");
                        } else if (workInfo.getState() == WorkInfo.State.SUCCEEDED) {
                            textView.setText("Задача успешно завершена");
                        } else if (workInfo.getState() == WorkInfo.State.FAILED) {
                            textView.setText("Ошибка");
                        }
                    }
                });

        return view;
    }
}