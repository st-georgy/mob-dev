package ru.mirea.zhidkov.internalfilestorage;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    private static final String FILE_NAME = "date.txt";
    private EditText dateEditText, descriptionEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dateEditText = findViewById(R.id.dateEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        Button saveButton = findViewById(R.id.saveFileButton);

        saveButton.setOnClickListener(v -> saveToFile());

        loadFromFile();
    }

    private void saveToFile() {
        String date = dateEditText.getText().toString();
        String description = descriptionEditText.getText().toString();

        try (FileOutputStream fos = openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
             OutputStreamWriter osw = new OutputStreamWriter(fos)) {
            osw.write(date + "\n");
            osw.write(description);
            Toast.makeText(this, "Данные сохранены в файл", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Ошибка при сохранении", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadFromFile() {
        try (FileInputStream fis = openFileInput(FILE_NAME);
             InputStreamReader isr = new InputStreamReader(fis);
             BufferedReader br = new BufferedReader(isr)) {

            String date = br.readLine();
            StringBuilder description = new StringBuilder();
            String line;

            while ((line = br.readLine()) != null) {
                description.append(line).append("\n");
            }

            dateEditText.setText(date);
            descriptionEditText.setText(description.toString().trim());
        } catch (IOException e) {
        }
    }
}