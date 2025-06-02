package ru.mirea.zhidkov.employeedb;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ArrayAdapter<String> adapter;

    EditText nameEditText, powerEditText, strengthEditText;
    ListView heroesListView;
    Button addButton, viewAllButton;

    SuperHeroDao superHeroDao;

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

        nameEditText = findViewById(R.id.nameEditText);
        powerEditText = findViewById(R.id.powerEditText);
        strengthEditText = findViewById(R.id.strengthEditText);
        heroesListView = findViewById(R.id.heroesListView);
        addButton = findViewById(R.id.addButton);
        viewAllButton = findViewById(R.id.viewAllButton);

        superHeroDao = App.getInstance().getDatabase().superHeroDao();

        addButton.setOnClickListener(view -> {
            SuperHero hero = new SuperHero(
                    nameEditText.getText().toString(),
                    powerEditText.getText().toString(),
                    Integer.parseInt(strengthEditText.getText().toString())
            );
            superHeroDao.insert(hero);
            showHeroes();
        });

        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                new ArrayList<>());
        heroesListView.setAdapter(adapter);

        viewAllButton.setOnClickListener(view -> {
            showHeroes();
        });
    }

    private void showHeroes() {
        List<SuperHero> heroes = superHeroDao.getAll();
        adapter.clear();
        for (SuperHero hero : heroes) {
            adapter.add(String.format("%s: %s (сила %d)",
                    hero.name, hero.superpower, hero.strengthLevel));
        }
        adapter.notifyDataSetChanged();
    }
}