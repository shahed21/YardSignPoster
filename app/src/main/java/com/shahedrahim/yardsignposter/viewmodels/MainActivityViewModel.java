package com.shahedrahim.yardsignposter.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;

import com.shahedrahim.yardsignposter.data.Location;
import com.shahedrahim.yardsignposter.data.LocationRepository;

import java.util.List;

public class MainActivityViewModel extends AndroidViewModel {

    private LocationManager locationManager;
    private LocationListener locationListener;
    protected LocationRepository repository;
    private LiveData<List<Location>> listOfLocations;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        repository = new LocationRepository(application);
        listOfLocations = repository.getListOfLocations();
    }

    public LocationManager getLocationManager() {
        return locationManager;
    }

    public void setLocationManager(LocationManager locationManager) {
        this.locationManager = locationManager;
    }

    public LocationListener getLocationListener() {
        return locationListener;
    }

    public void setLocationListener(LocationListener locationListener) {
        this.locationListener = locationListener;
    }

    public LiveData<List<Location>> getListOfLocations() {
        return listOfLocations;
    }

    public void insertNewLocation(Location location) {
        repository.insertNewLocation(location);
    }

    public void deleteLocation(Location location) {
        repository.deleteLocation(location);
    }
}
