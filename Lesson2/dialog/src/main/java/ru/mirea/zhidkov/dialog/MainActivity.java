package ru.mirea.zhidkov.dialog;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

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
    }

    public void onClickShowDialog(View view) {
        AlertDialogFragment dialogFragment = new AlertDialogFragment();
        dialogFragment.show(getSupportFragmentManager(), "mirea");
    }

    public void onOkClicked() {
        Toast.makeText(getApplicationContext(),
                "Вы выбрали кнопку \"Иду дальше\"!",
                Toast.LENGTH_LONG).show();
    }

    public void onCancelClicked() {
        Toast.makeText(getApplicationContext(),
                "Вы выбрали кнопку \"Нет\"!",
                Toast.LENGTH_LONG).show();
    }

    public void onNeutralClicked() {
        Toast.makeText(getApplicationContext(),
                "Вы выбрали кнопку \"На паузе\"!",
                Toast.LENGTH_LONG).show();
    }

    public void onClickShowSnackbar(View view) {
        Snackbar.make(view, "Это Snackbar", Snackbar.LENGTH_LONG)
                .setAction("Действие", v -> {
                    Toast.makeText(MainActivity.this,
                            "Действие выполнено",
                            Toast.LENGTH_SHORT).show();
                })
                .show();
    }

    public void onClickShowTimeDialog(View view) {
        TimeDialogFragment timeDialog = new TimeDialogFragment();
        timeDialog.show(getSupportFragmentManager(), "timePicker");
    }

    public void onClickShowDateDialog(View view) {
        DateDialogFragment dateDialog = new DateDialogFragment();
        dateDialog.show(getSupportFragmentManager(), "datePicker");
    }

    public void onClickShowProgressDialog(View view) {
        ProgressDialogFragment progressDialog = new ProgressDialogFragment();
        progressDialog.show(getSupportFragmentManager(), "progressDialog");
    }
}