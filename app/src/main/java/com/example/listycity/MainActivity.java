package com.example.listycity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private ListView cityList;
    private ArrayAdapter<String> cityAdapter;
    private ArrayList<String> dataList;
    private int selectedPosition = -1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityList = findViewById(R.id.city_list);
        Button addButton = findViewById(R.id.button_add);
        Button deleteButton = findViewById(R.id.button_delete);

        String[] cities = {"Edmonton", "Vancouver", "Moscow", "Sydney", "Berlin", "Vienna", "Tokyo", "Beijing", "Osaka", "New Delhi"};

        dataList = new ArrayList<>();
        dataList.addAll(Arrays.asList(cities));

        cityAdapter = new ArrayAdapter<>(this, R.layout.content, dataList);
        cityList.setAdapter(cityAdapter);
        cityList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        cityList.setOnItemClickListener((parent, view, position, id) -> {
            selectedPosition = position;
            cityList.setItemChecked(position, true);
        });

        addButton.setOnClickListener(v -> showAddCityDialog());

        deleteButton.setOnClickListener(v -> {
            if (selectedPosition >= 0 && selectedPosition < dataList.size()) {
                dataList.remove(selectedPosition);
                cityAdapter.notifyDataSetChanged();
                cityList.clearChoices();
                selectedPosition = -1;
            } else {
                Toast.makeText(this, "Select a city to delete", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showAddCityDialog() {
        BottomSheetDialog dialog = new BottomSheetDialog(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_add_city, null);
        dialog.setContentView(view);

        EditText input = view.findViewById(R.id.edit_city_name);
        Button confirm = view.findViewById(R.id.button_confirm);

        confirm.setOnClickListener(v -> {
            String city = input.getText().toString().trim();
            if (city.isEmpty()) {
                Toast.makeText(this, "City name cannot be empty", Toast.LENGTH_SHORT).show();
                return;
            }
            if (dataList.contains(city)) {
                Toast.makeText(this, "City already exists", Toast.LENGTH_SHORT).show();
                return;
            }
            dataList.add(city);
            cityAdapter.notifyDataSetChanged();
            dialog.dismiss();
        });

        dialog.show();
    }
}
