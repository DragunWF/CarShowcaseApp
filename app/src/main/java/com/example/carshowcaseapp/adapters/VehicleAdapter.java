package com.example.carshowcaseapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.carshowcaseapp.R;
import com.example.carshowcaseapp.ViewItem;
import com.example.carshowcaseapp.data.Vehicle;
import com.example.carshowcaseapp.helpers.DatabaseHelper;
import com.example.carshowcaseapp.helpers.Utils;
import com.example.carshowcaseapp.services.BookmarkService;

import java.util.List;

public class VehicleAdapter extends RecyclerView.Adapter<VehicleAdapter.ViewHolder> {

    private List<Vehicle> localDataSet;
    private Context context;
    private boolean isBookmark = false;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView modelBrandText;
        private final ImageView bookmarkBtn, vehicleImage;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            modelBrandText = view.findViewById(R.id.modelBrandText);
            bookmarkBtn = view.findViewById(R.id.bookmarkBtn);
            vehicleImage = view.findViewById(R.id.vehicleImage);
        }

        public TextView getModelBrandText() {
            return modelBrandText;
        }

        public ImageView getBookmarkBtn() {
            return bookmarkBtn;
        }

        public ImageView getVehicleImage() {
            return vehicleImage;
        }
    }

    /**
     * Initialize the dataset of the Adapter
     *
     * @param dataSet String[] containing the data to populate views to be used
     * by RecyclerView
     */
    public VehicleAdapter(List<Vehicle> dataSet, Context context) {
        localDataSet = dataSet;
        this.context = context;
    }

    public VehicleAdapter(List<Vehicle> dataSet, Context context, boolean isBookmark) {
        localDataSet = dataSet;
        this.context = context;
        this.isBookmark = isBookmark;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recycle_card_item, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        Vehicle vehicle = localDataSet.get(position);
        int imageResource = Utils.getImageIdByName(vehicle.getImageName());

        viewHolder.getModelBrandText().setText(String.format("%s %s", vehicle.getModel(), vehicle.getBrand()));
        viewHolder.getVehicleImage().setImageResource(imageResource);

        viewHolder.getVehicleImage().setOnClickListener(v -> {
            Intent intent = new Intent(context, ViewItem.class);
            intent.putExtra(ViewItem.VEHICLE_ID, vehicle.getId());
            context.startActivity(intent);
        });
        viewHolder.getBookmarkBtn().setOnClickListener(v -> {
            if (BookmarkService.isBookmarked(vehicle.getId())) {
                BookmarkService.removeBookmark(vehicle.getId());
                Utils.toast(vehicle.getModel() + " has been removed from your bookmark list", context);
                if (isBookmark) {
                    updateDataSet(BookmarkService.getBookmarkedVehicles());
                }
            } else {
                BookmarkService.bookmark(vehicle);
                Utils.toast(vehicle.getModel() + " has been bookmarked!", context);
            }
        });
    }

    public void updateDataSet() {
        updateDataSet(DatabaseHelper.getVehicleBank().getAll());
    }

    public void updateDataSet(List<Vehicle> dataSet) {
        localDataSet.clear();
        localDataSet.addAll(dataSet);
        notifyDataSetChanged();
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}
