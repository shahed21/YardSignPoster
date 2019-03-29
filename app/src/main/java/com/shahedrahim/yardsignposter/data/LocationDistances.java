package com.shahedrahim.yardsignposter.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "location_distances_table", foreignKeys = @ForeignKey(entity = Location.class, parentColumns = "id", childColumns = {"l1_id", "l2_id"}))
public class LocationDistances {
    private static final String TAG = "LocationDistances";

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name="l1_id")
    private int l1Id;

    @ColumnInfo(name="l2_id")
    private int l2Id;

    private double distance;
}
