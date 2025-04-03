package com.example.carshowcaseapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.carshowcaseapp.data.Vehicle;
import com.example.carshowcaseapp.helpers.DatabaseHelper;
import com.example.carshowcaseapp.helpers.Utils;
import com.example.carshowcaseapp.services.BookmarkService;

public class ViewItem extends AppCompatActivity {
    public static final String VEHICLE_ID = "vehicleId";

    private TextView brandText, modelText, priceText, featuresText;
    private ImageView backBtn, editBtn, vehicleImage;
    private Button bookmarkBtn;

    private int currentVehicleId;

    @Override
    protected void onResume() {
        super.onResume();
        setDetails();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_item);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        try {
            DatabaseHelper.initialize(this);
            currentVehicleId = getIntent().getIntExtra(VEHICLE_ID, 1);

            bindElements();
            setButtons();
            setDetails();
        } catch (Exception err) {
            err.printStackTrace();
            Utils.longToast(err.getMessage(), this);
        }
    }

    private void bindElements() {
        brandText = findViewById(R.id.brandText);
        modelText = findViewById(R.id.modelText);
        priceText = findViewById(R.id.priceText);
        featuresText = findViewById(R.id.featuresText);
        backBtn = findViewById(R.id.backBtn);
        bookmarkBtn = findViewById(R.id.bookmarkBtn);
        editBtn = findViewById(R.id.editBtn);
        vehicleImage = findViewById(R.id.vehicleImage);
    }

    private void setButtons() {
        backBtn.setOnClickListener(v -> {
            finish();
        });
        bookmarkBtn.setOnClickListener(v -> {
            if (BookmarkService.isBookmarked(currentVehicleId)) {
                BookmarkService.removeBookmark(currentVehicleId);
                Utils.longToast("Removed from bookmarks", this);
            } else {
                BookmarkService.bookmark(DatabaseHelper.getVehicleBank().get(currentVehicleId));
                Utils.longToast("Added to bookmarks", this);
            }
            setBookmarkBtnText();
        });
        editBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddEditItem.class);
            intent.putExtra(AddEditItem.FORM_TYPE, AddEditItem.EDIT);
            intent.putExtra(VEHICLE_ID, currentVehicleId);
            startActivity(intent);
        });
    }

    private void setDetails() {
        Vehicle vehicle = DatabaseHelper.getVehicleBank().get(currentVehicleId);

        brandText.setText(vehicle.getBrand());
        modelText.setText(vehicle.getModel());
        priceText.setText(vehicle.getPrice() + " PHP");
        featuresText.setText(vehicle.getFeatures());

        int imageResource = Utils.getImageIdByName(vehicle.getImageName());
        vehicleImage.setImageResource(imageResource);

        setBookmarkBtnText();
    }

    private void setBookmarkBtnText() {
        bookmarkBtn.setText(BookmarkService.isBookmarked(currentVehicleId) ? "Remove from bookmarks" : "Add to bookmarks");
    }
}