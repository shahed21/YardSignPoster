package com.shahedrahim.yardsignposter.data;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

public class LocationRepository {
    private static final String TAG = "LocationRepository";

    private LocationDao locationDao;
    private LiveData<List<Location>> listOfLocations;

    public LocationRepository(Application application) {
        LocationDB db = LocationDB.getInstance(application);
        Log.d(TAG, "LocationRepository: db null " + (db==null));
        locationDao = db.locationDao();
        Log.d(TAG, "LocationRepository: dao null " + (locationDao==null) );
        listOfLocations = locationDao.getListOfLocations();
        Log.d(TAG, "LocationRepository: list locations null " + (listOfLocations==null) );
    }

    public LiveData<List<Location>> getListOfLocations() {
        return listOfLocations;
    }

    public void insertNewLocation(Location location) {
        new InsertLocationAsyncTask(locationDao, location).execute();
    }

    private class InsertLocationAsyncTask extends AsyncTask<Void, Void, Void> {
        LocationDao locationDao;
        Location location;
        public InsertLocationAsyncTask(LocationDao locationDao, Location location) {
            this.locationDao = locationDao;
            this.location = location;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            locationDao.insert(location);
            return null;
        }
    }
}
