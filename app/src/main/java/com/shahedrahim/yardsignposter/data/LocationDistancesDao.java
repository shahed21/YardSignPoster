package com.shahedrahim.yardsignposter.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Update;

@Dao
interface LocationDistancesDao {
    @Insert
    void insert(LocationDistances locationDistances);

    @Update
    void update(LocationDistances locationDistances);

    @Delete
    void delete(LocationDistances locationDistances);
}
