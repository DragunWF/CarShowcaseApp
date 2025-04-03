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
import com.google.android.material.textfield.TextInputEditText;

public class AddEditItem extends AppCompatActivity {
    public static final String VEHICLE_ID = "vehicleId";
    public static final String FORM_TYPE = "formType";
    public static final String EDIT = "edit";

    private View view;

    private TextInputEditText brandText, modelText, priceText, featuresText;
    private TextView headerText;
    private Button confirmBtn;
    private ImageView backBtn;
    private Spinner imageSpinner;

    private boolean isEditForm = false;
    private int currentVehicleId;
    private String selectedImageName;

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
            isEditForm = getIntent().getStringExtra(FORM_TYPE).equals(EDIT);

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
        brandText = findViewById(R.id.brandText);
        modelText = findViewById(R.id.modelText);
        priceText = findViewById(R.id.priceText);
        featuresText = findViewById(R.id.featuresText);
        headerText = findViewById(R.id.headerText);
        imageSpinner = findViewById(R.id.imageSpinner);
    }

    private void setButtons() {
        backBtn.setOnClickListener(v -> {
            finish();
        });
        confirmBtn.setOnClickListener(v -> {
            String brand = Utils.getText(brandText);
            String model = Utils.getText(modelText);
            String priceStr = Utils.getText(priceText);
            String features = Utils.getText(featuresText);

            if (brand.isEmpty() || model.isEmpty() || priceStr.isEmpty() || features.isEmpty() || selectedImageName.isEmpty()) {
                Utils.snackbar("All fields are required!", view);
                return;
            }

            double price = Double.parseDouble(priceStr);
            if (isEditForm) {
                Vehicle vehicle = DatabaseHelper.getVehicleBank().get(currentVehicleId);
                vehicle.setBrand(brand);
                vehicle.setModel(model);
                vehicle.setPrice(price);
                vehicle.setFeatures(features);
                vehicle.setImageName(selectedImageName);
                // vehicle.setImageName();
                Utils.longToast("Item has been edited!", this);
            } else {
                // Vehicle vehicle = new Vehicle(model, brand, features)
                Utils.longToast("Item has been successfully added!", this);
            }

            finish();
        });
    }

    private void setSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.images_array,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        imageSpinner.setAdapter(adapter);

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
    }

    private void autoFillFields() {

    }
}