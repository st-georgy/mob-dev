package ru.mirea.zhidkov.mireaproject;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

import androidx.fragment.app.Fragment;

public class FilesFragment extends Fragment {

    private static final String FILE_NAME = "secret.txt";
    private static final String KEY = "mirea";

    public FilesFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_files, container, false);

        FloatingActionButton fab = view.findViewById(R.id.fab_file_action);
        fab.setOnClickListener(v -> showCryptoDialog());

        return view;
    }

    private void showCryptoDialog() {
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_convert, null);
        EditText inputEditText = dialogView.findViewById(R.id.editTextInput);

        new AlertDialog.Builder(getContext())
                .setTitle("Шифрование текста")
                .setView(dialogView)
                .setPositiveButton("Зашифровать", (dialog, which) -> {
                    String input = inputEditText.getText().toString();
                    String encrypted = encrypt(input, KEY);
                    writeToFile(encrypted);
                    Toast.makeText(getContext(), "Зашифровано и сохранено", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Расшифровать", (dialog, which) -> {
                    String encrypted = readFromFile();
                    if (encrypted.isEmpty()) {
                        Toast.makeText(getContext(), "Файл пуст или не найден", Toast.LENGTH_SHORT).show();
                    } else {
                        try {
                            String decrypted = decrypt(encrypted, KEY);
                            Toast.makeText(getContext(), "Расшифровка: " + decrypted, Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Toast.makeText(getContext(), "Ошибка при расшифровке", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNeutralButton("Отмена", null)
                .show();
    }

    private String encrypt(String input, String key) {
        byte[] inputBytes = input.getBytes();
        byte[] keyBytes = key.getBytes();
        byte[] output = new byte[inputBytes.length];

        for (int i = 0; i < inputBytes.length; i++) {
            output[i] = (byte) (inputBytes[i] ^ keyBytes[i % keyBytes.length]);
        }
        return Base64.encodeToString(output, Base64.DEFAULT);
    }

    private String decrypt(String base64Input, String key) {
        byte[] encryptedBytes = Base64.decode(base64Input, Base64.DEFAULT);
        byte[] keyBytes = key.getBytes();
        byte[] output = new byte[encryptedBytes.length];

        for (int i = 0; i < encryptedBytes.length; i++) {
            output[i] = (byte) (encryptedBytes[i] ^ keyBytes[i % keyBytes.length]);
        }
        return new String(output);
    }

    private void writeToFile(String data) {
        try (FileOutputStream fos = requireActivity().openFileOutput(FILE_NAME, Context.MODE_PRIVATE)) {
            fos.write(data.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String readFromFile() {
        try (FileInputStream fis = requireActivity().openFileInput(FILE_NAME)) {
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            return new String(buffer);
        } catch (IOException e) {
            return "";
        }
    }
}
