package com.example.carshowcaseapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carshowcaseapp.data.Vehicle;
import com.example.carshowcaseapp.helpers.DatabaseHelper;
import com.example.carshowcaseapp.helpers.Utils;
import com.example.carshowcaseapp.services.SessionService;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private View view;

    private SearchView searchBar;
    private Button newBtn;
    private Spinner categorySpinner, brandsSpinner;
    private ImageView bookmarkIcon;

    private RecyclerView vehicleRecycler;
    private RecyclerView.LayoutManager layoutManager;

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

        try {
            DatabaseHelper.initialize(this);

            bindElements();
            setButtons();
            setRecycler();
            setSpinners();
            setSearch();
        } catch (Exception err) {
            err.printStackTrace();
            Utils.longToast(err.getMessage(), this);
        }
    }

    private void bindElements() {
        view = findViewById(R.id.main);
        searchBar = findViewById(R.id.searchBar);
        newBtn = findViewById(R.id.newBtn);
        categorySpinner = findViewById(R.id.categorySpinner);
        brandsSpinner = findViewById(R.id.brandsSpinner);
        bookmarkIcon = findViewById(R.id.bookmarksIcon);
        vehicleRecycler = findViewById(R.id.vehicleRecycler);
    }

    private void setButtons() {
        newBtn.setOnClickListener(v -> {
            // startActivity(new Intent(this, ));
        });
        bookmarkIcon.setOnClickListener(v -> {
            // startActivity(new Intent(this, ));
        });
    }

    private void setRecycler() {
        vehicleRecycler.setHasFixedSize(false);

        // set adapter

        layoutManager = new LinearLayoutManager(this);
        vehicleRecycler.setLayoutManager(layoutManager);
    }

    private void setSpinners() {
        ArrayAdapter<CharSequence> categoriesAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.categories_array,
                android.R.layout.simple_spinner_item
        );
        categoriesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoriesAdapter);

        ArrayAdapter<CharSequence> brandsAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.brands_array,
                android.R.layout.simple_spinner_item
        );
        brandsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        brandsSpinner.setAdapter(brandsAdapter);

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCategory = categoriesAdapter.getItem(position).toString();
                List<Vehicle> vehicles = DatabaseHelper.getVehicleBank().getAll();

                if (selectedCategory.equals("All")) {
                    // update adapter
                    return;
                }

                List<Vehicle> results = new ArrayList<>();
                for (Vehicle vehicle : vehicles) {
                    if (vehicle.getCategory().equals(selectedCategory)) {
                        results.add(vehicle);
                    }
                }

                // update adapter
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        brandsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedBrand = brandsAdapter.getItem(position).toString();
                List<Vehicle> vehicles = DatabaseHelper.getVehicleBank().getAll();

                if (selectedBrand.equals("All")) {
                    // update adapter
                    return;
                }

                List<Vehicle> results = new ArrayList<>();
                for (Vehicle vehicle : vehicles) {
                    if (vehicle.getBrand().equals(selectedBrand)) {
                        results.add(vehicle);
                    }
                }

                // update adapter
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setSearch() {
        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                update(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                update(newText);
                return false;
            }

            public void update(String query) {
                List<Vehicle> vehicles = DatabaseHelper.getVehicleBank().getAll();
                List<Vehicle> results = new ArrayList<>();

                query = query.toLowerCase();
                for (Vehicle vehicle : vehicles) {
                    String modelName = vehicle.getModel().toLowerCase();
                    if (modelName.contains(query)) {
                        results.add(vehicle);
                    }
                }

                // update adapter
            }
        });
    }
}