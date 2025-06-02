package ru.mirea.zhidkov.lesson6;

import android.os.Bundle;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences sharedPref;
    private EditText groupEditText, numberEditText, movieEditText;

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

        groupEditText = findViewById(R.id.groupEditText);
        numberEditText = findViewById(R.id.numberEditText);
        movieEditText = findViewById(R.id.movieEditText);

        Button saveButton = findViewById(R.id.saveButton);

        sharedPref = getSharedPreferences("mirea_settings", Context.MODE_PRIVATE);

        loadData();

        saveButton.setOnClickListener(v -> saveData());
    }

    private void saveData() {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("GROUP", groupEditText.getText().toString());
        editor.putInt("NUMBER", Integer.parseInt(numberEditText.getText().toString()));
        editor.putString("MOVIE", movieEditText.getText().toString());
        editor.apply();
        Toast.makeText(this, "Данные сохранены", Toast.LENGTH_SHORT).show();
    }

    private void loadData() {
        groupEditText.setText(sharedPref.getString("GROUP", ""));
        numberEditText.setText(String.valueOf(sharedPref.getInt("NUMBER", 0)));
        movieEditText.setText(sharedPref.getString("MOVIE", ""));
    }
}