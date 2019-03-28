package com.shahedrahim.yardsignposter.activities;

import android.Manifest;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.shahedrahim.yardsignposter.R;
import com.shahedrahim.yardsignposter.adapters.LocationAdapter;
import com.shahedrahim.yardsignposter.viewmodels.MainActivityViewModel;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity
        implements LocationAdapter.OnItemClickListener {
    private static final String TAG = "MainActivity";

    private final int MAIN_ACTIVITY_REQUEST_ALL_PERMISSION_CODE = 1000;

    //ViewModel
    private MainActivityViewModel mainActivityViewModel;

    //Adapter
    private LocationAdapter locationAdapter;
    //UI Objects
    private RecyclerView recyclerView;

    private TextView emptyTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);

        locationAdapter = new LocationAdapter();
        locationAdapter.setOnItemClickListener(this);

        recyclerView = findViewById(R.id.content_main_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(locationAdapter);

        mainActivityViewModel.getListOfLocations().observe(this, new Observer<List<com.shahedrahim.yardsignposter.data.Location>>() {

            @Override
            public void onChanged(@Nullable List<com.shahedrahim.yardsignposter.data.Location> locations) {
                locationAdapter.submitList(locations);

                if (locationAdapter.getItemCount() == 0) {
                    emptyTextView.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                } else {
                    emptyTextView.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                }
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        emptyTextView = findViewById(R.id.edit_location);

        mainActivityViewModel.setLocationManager(
                (LocationManager)getSystemService(Context.LOCATION_SERVICE));
        mainActivityViewModel.setLocationListener(new MyLocationListener());

        FloatingActionButton fab2 = findViewById(R.id.fab2);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "This should make a route", Toast.LENGTH_SHORT).show();
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                if (checkPermissionFromDevice()) {
                    requestPermission();
                    return;
                } else {
                    try {
                        mainActivityViewModel.getLocationManager()
                                .requestLocationUpdates(
                                        LocationManager.GPS_PROVIDER,
                                        5000,
                                        10,
                                        mainActivityViewModel.getLocationListener());
                    } catch (SecurityException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }



    private boolean checkPermissionFromDevice() {
        if (Build.VERSION.SDK_INT>=23) {
            return (
                    ActivityCompat.checkSelfPermission(
                            this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(
                            this,
                            Manifest.permission.ACCESS_COARSE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED);
        } else {
            return false;
        }
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(
                this,
                new String[] {
                        Manifest.permission.ACCESS_FINE_LOCATION
                },
                MAIN_ACTIVITY_REQUEST_ALL_PERMISSION_CODE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemViewClicked(com.shahedrahim.yardsignposter.data.Location item) {
        Toast.makeText(this, "To view Location at " + item.getLatitude() + ", " + item.getLongitude(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemDeleteClicked(com.shahedrahim.yardsignposter.data.Location location) {
        Toast.makeText(this, "Deleting location", Toast.LENGTH_SHORT).show();
        mainActivityViewModel.deleteLocation(location);
    }

    /*---------- Listener class to get coordinates ------------- */
    private class MyLocationListener implements LocationListener {
        private static final String TAG = "MyLocationListener";

        @Override
        public void onLocationChanged(Location loc) {
            //pb.setVisibility(View.INVISIBLE);
            Toast.makeText(
                    getBaseContext(),
                    "Location changed: Lat: " + loc.getLatitude() + " Lng: "
                            + loc.getLongitude(), Toast.LENGTH_SHORT).show();
            String longitude = "Longitude: " + loc.getLongitude();
            Log.v(TAG, longitude);
            String latitude = "Latitude: " + loc.getLatitude();
            Log.v(TAG, latitude);

            /*------- To get city name from coordinates -------- */
            //String cityName = null;
            Geocoder gcd = new Geocoder(getBaseContext(), Locale.getDefault());
            com.shahedrahim.yardsignposter.data.Location location;
            List<Address> addresses;
            try {
                addresses = gcd.getFromLocation(loc.getLatitude(),
                        loc.getLongitude(), 1);
                if (addresses.size() > 0) {
                    Address address = addresses.get(0);
                    String addressLine = "";
                    Log.d(TAG, "onLocationChanged: getMaxAddressLineIndex = " + address.getMaxAddressLineIndex());
                    for (int i=0; i<=address.getMaxAddressLineIndex(); i++) {
                        addressLine += address.getAddressLine(i);
                        if (i<address.getMaxAddressLineIndex()) {
                            Log.d(TAG, "onLocationChanged: double lined address");
                            addressLine += "\n";
                        }
                    }
                    Log.d(TAG, "onLocationChanged: addressLine" + addressLine);
                    location = new com.shahedrahim.yardsignposter.data.Location(
                            loc.getLatitude(),
                            loc.getLongitude(),
                            addressLine,
                            address.getLocality(),
                            address.getAdminArea(),
                            address.getPostalCode());
                    Log.d(TAG, "onLocationChanged: featureName = " + address.getFeatureName() );
                    location.setFeatureName(address.getFeatureName());
                    Log.d(TAG, "onLocationChanged: country = " + address.getCountryName() );
                    location.setCountry(address.getCountryName());
                    Log.d(TAG, "onLocationChanged: subAdminArea = " + address.getSubAdminArea() );
                    location.setSubAdminArea(address.getSubAdminArea());
                    Log.d(TAG, "onLocationChanged: Phone = " + address.getPhone() );
                    location.setPhone(address.getPhone());
                    Log.d(TAG, "onLocationChanged: Premises = " + address.getPremises() );
                    location.setPremises(address.getPremises());
                    Log.d(TAG, "onLocationChanged: URL = " + address.getUrl() );
                    location.setUrl(address.getUrl());

                    mainActivityViewModel.insertNewLocation(location);
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onProviderDisabled(String provider) {}

        @Override
        public void onProviderEnabled(String provider) {}

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {}
    }
}
