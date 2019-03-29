package com.shahedrahim.yardsignposter.data;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

@Database(entities = {Location.class, LocationDistances.class}, version=1, exportSchema = false)
public abstract class LocationDB extends RoomDatabase {
    private static final String TAG = "LocationDB";

    private static LocationDB locationDB;

    public abstract LocationDao locationDao();
    public abstract LocationDistancesDao locationDistancesDao();

    public static synchronized LocationDB getInstance(Context context) {
        if (locationDB==null) {
            Log.d(TAG, "getInstance: need to build db");
            locationDB = Room.databaseBuilder(
                    context.getApplicationContext(),
                    LocationDB.class,
                    "location_db")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
            Log.d(TAG, "getInstance: built db , null? " + (locationDB==null));
        }
        return locationDB;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            Log.d(TAG, "onCreate: came here");
            new PopulateDbAsyncTask(locationDB).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private static final String TAG = "PopulateDbAsyncTask";

        private LocationDao locationDao;
        private LocationDistancesDao locationDistancesDao;

        public PopulateDbAsyncTask(LocationDB locationDB) {
            this.locationDao = locationDB.locationDao();
            this.locationDistancesDao = locationDB.locationDistancesDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Log.d(TAG, "doInBackground: populating");
            Location location = new Location(
                    29.7045993,
                    -95.4213404,
                    "2600 Gramercy St, Houston, TX 77030",
                    "Houston",
                    "Texas",
                    "77030");
            location.setFeatureName("Home");
            locationDao.insert(location);
            return null;
        }
    }
}
