package com.shahedrahim.yardsignposter.adapters;

import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.shahedrahim.yardsignposter.R;
import com.shahedrahim.yardsignposter.data.Location;

public class LocationAdapter extends ListAdapter<Location, LocationAdapter.LocationHolder> {
    private static final String TAG = "LocationAdapter";

    private OnItemClickListener onItemClickListener;

    private static final DiffUtil.ItemCallback<Location> DIFF_CALLBACK = new DiffUtil.ItemCallback<Location>() {
        @Override
        public boolean areItemsTheSame(@NonNull Location s, @NonNull Location t1) {
            return s.getId()==(t1.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Location s, @NonNull Location t1) {
            return s.equals(t1);
        }
    };

    public LocationAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public LocationAdapter.LocationHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.location_card_layout, viewGroup, false);
        return new LocationHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationAdapter.LocationHolder locationHolder, int i) {
        Location currentLocation = getItem(i);
        locationHolder.setLocation(currentLocation);

    }

    public class LocationHolder extends RecyclerView.ViewHolder {
        private Location location;
        private TextView addressView;
        private TextView cszView;
        private TextView latView;
        private TextView longView;
        private ImageButton locationButton;
        public LocationHolder(@NonNull View itemView) {
            super(itemView);
            addressView = itemView.findViewById(R.id.location_address_text_view);
            cszView = itemView.findViewById(R.id.location_csz_text_view);
            latView = itemView.findViewById(R.id.location_lat_text_view);
            longView = itemView.findViewById(R.id.location_long_text_view);

            locationButton = itemView.findViewById(R.id.location_view_button);

            locationButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (onItemClickListener!=null && position!=RecyclerView.NO_POSITION) {
                        onItemClickListener.onItemViewClicked(location);
                    }
                }
            });
        }

        public void setLocation(Location currentLocation) {
            location = currentLocation;
            addressView.setText(currentLocation.getAddress());
            cszView.setText(
                    currentLocation.getLocality() + ", "
                            + currentLocation.getAdminArea() + " "
                            + currentLocation.getPostalCode());
            latView.setText( ""+currentLocation.getLatitude());
            longView.setText(""+currentLocation.getLongitude());
        }
    }

    public interface OnItemClickListener {
        void onItemViewClicked(Location item);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
