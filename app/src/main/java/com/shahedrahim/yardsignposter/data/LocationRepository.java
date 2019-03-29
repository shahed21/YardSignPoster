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

    private LocationDistancesDao locationDistancesDao;

    public LocationRepository(Application application) {
        LocationDB db = LocationDB.getInstance(application);
        Log.d(TAG, "LocationRepository: db null " + (db==null));
        locationDao = db.locationDao();
        Log.d(TAG, "LocationRepository: dao null " + (locationDao==null) );
        listOfLocations = locationDao.getListOfLocations();
        Log.d(TAG, "LocationRepository: list locations null " + (listOfLocations==null) );
        locationDistancesDao = db.locationDistancesDao();
    }

    public LiveData<List<Location>> getListOfLocations() {
        return listOfLocations;
    }

    public void insertNewLocation(Location location) {
        new InsertLocationAsyncTask(locationDao, location).execute();
    }

    public void deleteLocation(Location location) {
        new DeleteLocationAsyncTask(locationDao).execute(location);
    }

    public void recordDistances(Location location) {
        new RecordDistancesAsyncTask(locationDao, locationDistancesDao).execute(location);
    }

    private class InsertLocationAsyncTask extends AsyncTask<Void, Void, Void> {
        private static final String TAG = "InsertLocationAsyncTask";

        LocationDao locationDao;
        Location location;

        public InsertLocationAsyncTask(LocationDao locationDao, Location location) {
            this.locationDao = locationDao;
            this.location = location;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if (!locationDao.exists(location.getLatitude(), location.getLongitude())) {
                locationDao.insert(location);
            }
            return null;
        }
    }

    private class DeleteLocationAsyncTask extends AsyncTask<Location, Void, Void>{
        private static final String TAG = "DeleteLocationAsyncTask";

        LocationDao locationDao;

        public DeleteLocationAsyncTask(LocationDao locationDao) {
            this.locationDao = locationDao;
        }

        @Override
        protected Void doInBackground(Location... locations) {
            for (Location location:locations) {
                locationDao.delete(location);
            }
            return null;
        }
    }

    private class RecordDistancesAsyncTask extends AsyncTask<Location, Void, Void> {
        private static final String TAG = "RecordDistancesAsyncTas";

        LocationDao locationDao;
        LocationDistancesDao locationDistancesDao;

        public RecordDistancesAsyncTask(LocationDao locationDao, LocationDistancesDao locationDistancesDao) {
            this.locationDao = locationDao;
            this.locationDistancesDao = locationDistancesDao;
        }

        @Override
        protected Void doInBackground(Location... locations) {
            List<Location> locationList = locationDao.getListOfLocationObjects();
            for (Location location:locations) {
                if (locationDao.exists(location.getLatitude(), location.getLongitude())) {
                    int lid = locationDao.getId(
                            location.getLatitude(),
                            location.getLongitude());
                    for (Location location1: locationList) {
                        int lid1 = locationDao.getId(
                                location1.getLatitude(),
                                location1.getLongitude());
                        double distanceRadians = calculateDistance(location, location1);
                        double distanceMeters = 6371000 * distanceRadians;
                        double distanceMiles = 3958.8 * distanceRadians;
                        LocationDistances distance = new LocationDistances(
                                lid,
                                lid1,
                                distanceRadians,
                                distanceMeters,
                                distanceMiles);
//                        LocationDistances distanceBack = new LocationDistances(
//                                lid1,
//                                lid,
//                                distanceRadians,
//                                distanceMeters,
//                                distanceMiles);
                        locationDistancesDao.insert(distance);
//                        locationDistancesDao.insert(distanceBack);
                    }
                }
            }
            return null;
        }

        private double calculateDistance(Location location, Location location1) {
            //Radius of earth in meters and miles
            double phi0 = location.getLatitude()*Math.PI/180;
            double phi1 = location1.getLatitude()*Math.PI/180;
            double deltaPhi = (location.getLatitude() - location1.getLatitude())*Math.PI/180;
            double deltaLambda = (location.getLongitude() - location1.getLongitude())*Math.PI/180;
            double a = Math.sin(deltaPhi/2) * Math.sin(deltaPhi/2) +
                    Math.cos(phi0) * Math.cos(phi1) * Math.sin(deltaLambda/2) * Math.sin(deltaLambda/2);
            double c = Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
            return c;
        }
    }
}
