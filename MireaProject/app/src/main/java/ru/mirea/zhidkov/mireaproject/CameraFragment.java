package ru.mirea.zhidkov.mireaproject;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.*;
import android.widget.*;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.*;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CameraFragment extends Fragment {

    private static final int REQUEST_CAMERA_PERMISSION = 100;
    private ImageView imagePreview;
    private EditText editNote;
    private Uri imageUri;
    private boolean isWork = false;

    private ActivityResultLauncher<Intent> cameraLauncher;

    public CameraFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_camera, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imagePreview = view.findViewById(R.id.imagePreview);
        editNote = view.findViewById(R.id.editNote);
        Button btnTakePhoto = view.findViewById(R.id.btnTakePhoto);
        Button btnSave = view.findViewById(R.id.btnSave);

        int permissionStatus = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA);
        isWork = permissionStatus == PackageManager.PERMISSION_GRANTED;
        if (!isWork) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
        }

        cameraLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        if (imageUri != null) {
                            imagePreview.setImageURI(imageUri);
                        }
                    }
                });

        btnTakePhoto.setOnClickListener(v -> {
            if (!isWork) {
                Toast.makeText(getContext(), "Нет разрешения на камеру", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            try {
                File photoFile = createImageFile();
                String authorities = requireContext().getPackageName() + ".fileprovider";
                imageUri = FileProvider.getUriForFile(requireContext(), authorities, photoFile);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                cameraLauncher.launch(cameraIntent);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        btnSave.setOnClickListener(v -> {
            String note = editNote.getText().toString().trim();
            if (imageUri != null && !note.isEmpty()) {
                Toast.makeText(getContext(), "Заметка с фото сохранена!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Добавьте фото и текст", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH).format(new Date());
        String imageFileName = "IMAGE_" + timeStamp + "_";
        File storageDir = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(imageFileName, ".jpg", storageDir);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            isWork = grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED;
        }
    }
}
