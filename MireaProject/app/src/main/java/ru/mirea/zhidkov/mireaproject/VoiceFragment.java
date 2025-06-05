package ru.mirea.zhidkov.mireaproject;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.io.IOException;

public class VoiceFragment extends Fragment {

    private static final int REQUEST_CODE_PERMISSIONS = 200;
    private MediaRecorder recorder;
    private MediaPlayer player;
    private File audioFile;

    private Button btnStart, btnStop, btnPlay;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_voice, container, false);

        btnStart = view.findViewById(R.id.btnStart);
        btnStop = view.findViewById(R.id.btnStop);
        btnPlay = view.findViewById(R.id.btnPlay);

        checkPermissions();

        btnStart.setOnClickListener(v -> startRecording());
        btnStop.setOnClickListener(v -> stopRecording());
        btnPlay.setOnClickListener(v -> playRecording());

        return view;
    }

    private void checkPermissions() {
        String[] permissions = {
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
        boolean allGranted = true;
        for (String perm : permissions) {
            if (ContextCompat.checkSelfPermission(requireContext(), perm) != PackageManager.PERMISSION_GRANTED) {
                allGranted = false;
                break;
            }
        }
        if (!allGranted) {
            ActivityCompat.requestPermissions(requireActivity(), permissions, REQUEST_CODE_PERMISSIONS);
        }
    }

    private void startRecording() {
        try {
            audioFile = new File(requireContext().getExternalFilesDir(Environment.DIRECTORY_MUSIC),
                    "voicenote_" + System.currentTimeMillis() + ".3gp");

            recorder = new MediaRecorder();
            recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            recorder.setOutputFile(audioFile.getAbsolutePath());
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            recorder.prepare();
            recorder.start();

            btnStart.setEnabled(false);
            btnStop.setEnabled(true);
            btnPlay.setEnabled(false);

            Toast.makeText(getContext(), "Запись началась", Toast.LENGTH_SHORT).show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void stopRecording() {
        recorder.stop();
        recorder.release();
        recorder = null;

        btnStart.setEnabled(true);
        btnStop.setEnabled(false);
        btnPlay.setEnabled(true);

        Toast.makeText(getContext(), "Запись сохранена", Toast.LENGTH_SHORT).show();
    }

    private void playRecording() {
        if (audioFile != null && audioFile.exists()) {
            player = new MediaPlayer();
            try {
                player.setDataSource(audioFile.getAbsolutePath());
                player.prepare();
                player.start();
                Toast.makeText(getContext(), "Воспроизведение", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (player != null) {
            player.release();
            player = null;
        }
        if (recorder != null) {
            recorder.release();
            recorder = null;
        }
    }
}
