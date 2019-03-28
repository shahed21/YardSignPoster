package com.shahedrahim.yardsignposter.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface LocationDao {
    @Insert
    void insert(Location location);

    @Update
    void update(Location location);

    @Delete
    void delete(Location location);

    @Query("DELETE FROM location_table")
    void deleteAll();

    @Query("DELETE FROM location_table WHERE id like :id")
    void deleteId(int id);

    @Query("SELECT * FROM location_table")
    LiveData<List<Location>> getListOfLocations();
}
