package com.example.carshowcaseapp;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.carshowcaseapp.data.Vehicle;
import com.example.carshowcaseapp.helpers.DatabaseHelper;
import com.example.carshowcaseapp.helpers.Utils;
import com.example.carshowcaseapp.services.VehicleService;
import com.google.android.material.textfield.TextInputEditText;

public class AddEditItem extends AppCompatActivity {
    public static final String VEHICLE_ID = "vehicleId";
    public static final String FORM_TYPE = "formType";
    public static final String EDIT = "edit";

    private View view;

    private TextInputEditText modelText, priceText, featuresText;
    private TextView headerText;
    private Button confirmBtn;
    private ImageView backBtn;

    private ArrayAdapter<CharSequence> imageAdapter, brandAdapter, categoryAdapter;
    private Spinner imageSpinner, brandSpinner, categorySpinner;

    private boolean isEditForm = false;
    private int currentVehicleId;
    private String selectedImageName, selectedBrand, selectedCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_edit_item);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        try {
            DatabaseHelper.initialize(this);
            String formType = getIntent().getStringExtra(FORM_TYPE);
            isEditForm = formType != null && formType.equals(EDIT);

            bindElements();
            setButtons();
            setSpinner();

            if (isEditForm) {
                headerText.setText("Edit Card");
                currentVehicleId = getIntent().getIntExtra(VEHICLE_ID, 1);
                autoFillFields();
            }
        } catch (Exception err) {
            err.printStackTrace();
            Utils.longToast(err.getMessage(), this);
        }
    }

    private void bindElements() {
        view = findViewById(R.id.main);
        backBtn = findViewById(R.id.backBtn);
        confirmBtn = findViewById(R.id.confirmBtn);
        modelText = findViewById(R.id.modelText);
        priceText = findViewById(R.id.priceText);
        featuresText = findViewById(R.id.featuresText);
        headerText = findViewById(R.id.headerText);
        imageSpinner = findViewById(R.id.imageSpinner);
        brandSpinner = findViewById(R.id.brandSpinner);
        categorySpinner = findViewById(R.id.categorySpinner);
    }

    private void setButtons() {
        backBtn.setOnClickListener(v -> {
            finish();
        });
        confirmBtn.setOnClickListener(v -> {
            String model = Utils.getText(modelText);
            String priceStr = Utils.getText(priceText);
            String features = Utils.getText(featuresText);

            if (model.isEmpty() || priceStr.isEmpty() || features.isEmpty() || selectedImageName.isEmpty()) {
                Utils.snackbar("All fields are required!", view);
                return;
            }

            double price = Double.parseDouble(priceStr);
            if (isEditForm) {
                Vehicle vehicle = DatabaseHelper.getVehicleBank().get(currentVehicleId);
                vehicle.setBrand(selectedBrand);
                vehicle.setModel(model);
                vehicle.setPrice(price);
                vehicle.setFeatures(features);
                vehicle.setImageName(selectedImageName);
                vehicle.setCategory(selectedCategory);
                VehicleService.edit(vehicle);
                Utils.longToast("Item has been edited!", this);
            } else {
                Vehicle vehicle = new Vehicle(model, selectedBrand, features, selectedCategory, selectedImageName, price);
                VehicleService.add(vehicle);
                Utils.longToast("Item has been successfully added!", this);
            }

            finish();
        });
    }

    private void setSpinner() {
        imageAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.images_array_form,
                android.R.layout.simple_spinner_item
        );
        imageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        imageSpinner.setAdapter(imageAdapter);

        brandAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.brands_array_form,
                android.R.layout.simple_spinner_item
        );
        brandAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        brandSpinner.setAdapter(brandAdapter);

        categoryAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.categories_array_form,
                android.R.layout.simple_spinner_item
        );
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryAdapter);

        imageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                selectedImageName = Utils.toImageName(selectedItem);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        brandSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedBrand = brandAdapter.getItem(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCategory = categoryAdapter.getItem(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void autoFillFields() {
        Vehicle vehicle = DatabaseHelper.getVehicleBank().get(currentVehicleId);

        modelText.setText(vehicle.getModel());
        priceText.setText(String.valueOf(vehicle.getPrice()));
        featuresText.setText(vehicle.getFeatures());
        // imageSpinner.setSelected(imageAdapter.getPosition(vehicle.getImageName());
        brandSpinner.setSelection(brandAdapter.getPosition(vehicle.getBrand()));
        categorySpinner.setSelection(categoryAdapter.getPosition(vehicle.getCategory()));
    }
}