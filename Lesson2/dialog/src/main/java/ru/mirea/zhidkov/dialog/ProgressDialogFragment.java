package ru.mirea.zhidkov.dialog;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class ProgressDialogFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Загрузка");
        progressDialog.setMessage("Пожалуйста, подождите...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        new Handler().postDelayed(() -> {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(),
                        "Загрузка завершена",
                        Toast.LENGTH_SHORT).show();
            }
        }, 5000);

        return progressDialog;
    }
}
