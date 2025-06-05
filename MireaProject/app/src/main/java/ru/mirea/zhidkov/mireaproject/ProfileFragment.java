package ru.mirea.zhidkov.mireaproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ProfileFragment extends Fragment {

    private SharedPreferences sharedPref;
    private EditText nameEditText, ageEditText, hobbyEditText;
    private static final String PREF_NAME = "profile_settings";

    public ProfileFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        nameEditText = view.findViewById(R.id.editName);
        ageEditText = view.findViewById(R.id.editAge);
        hobbyEditText = view.findViewById(R.id.editHobby);
        Button saveButton = view.findViewById(R.id.buttonSave);

        sharedPref = requireActivity().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        loadData();

        saveButton.setOnClickListener(v -> saveData());

        return view;
    }

    private void saveData() {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("NAME", nameEditText.getText().toString());
        editor.putInt("AGE", Integer.parseInt(ageEditText.getText().toString()));
        editor.putString("HOBBY", hobbyEditText.getText().toString());
        editor.apply();
        Toast.makeText(getContext(), "Данные сохранены", Toast.LENGTH_SHORT).show();
    }

    private void loadData() {
        nameEditText.setText(sharedPref.getString("NAME", ""));
        ageEditText.setText(String.valueOf(sharedPref.getInt("AGE", 0)));
        hobbyEditText.setText(sharedPref.getString("HOBBY", ""));
    }
}
