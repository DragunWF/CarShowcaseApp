package com.example.carshowcaseapp;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carshowcaseapp.adapters.VehicleAdapter;
import com.example.carshowcaseapp.data.Bookmark;
import com.example.carshowcaseapp.data.Vehicle;
import com.example.carshowcaseapp.helpers.DatabaseHelper;
import com.example.carshowcaseapp.helpers.Utils;
import com.example.carshowcaseapp.services.BookmarkService;

import java.util.ArrayList;
import java.util.List;

public class BookmarksListView extends AppCompatActivity {
    private ImageView backBtn;

    private RecyclerView bookmarkRecycler;
    private VehicleAdapter vehicleAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onResume() {
        super.onResume();
        vehicleAdapter.updateDataSet(BookmarkService.getBookmarkedVehicles());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_bookmarks_list_view);
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
        } catch (Exception err) {
            err.printStackTrace();
            Utils.longToast(err.getMessage(), this);
        }
    }

    private void bindElements() {
        backBtn = findViewById(R.id.backBtn);
        bookmarkRecycler = findViewById(R.id.bookmarkRecycler);
    }

    private void setButtons() {
        backBtn.setOnClickListener(v -> {
            finish();
        });
    }

    private void setRecycler() {
        bookmarkRecycler.setHasFixedSize(false);

        vehicleAdapter = new VehicleAdapter(BookmarkService.getBookmarkedVehicles(), this, true);
        bookmarkRecycler.setAdapter(vehicleAdapter);

        layoutManager = new LinearLayoutManager(this);
        bookmarkRecycler.setLayoutManager(layoutManager);
    }
}