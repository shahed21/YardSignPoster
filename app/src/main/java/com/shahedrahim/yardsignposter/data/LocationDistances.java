package com.shahedrahim.yardsignposter.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "location_distances_table")
public class LocationDistances {
    private static final String TAG = "LocationDistances";

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name="l1_id")
    private int l1Id;

    @ColumnInfo(name="l2_id")
    private int l2Id;

    private double distanceRadians;
    private double distanceMeters;
    private double distanceMiles;

    public LocationDistances(int l1Id, int l2Id, double distanceRadians, double distanceMeters, double distanceMiles) {
        this.l1Id = l1Id;
        this.l2Id = l2Id;
        this.distanceRadians = distanceRadians;
        this.distanceMeters = distanceMeters;
        this.distanceMiles = distanceMiles;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getL1Id() {
        return l1Id;
    }

    public void setL1Id(int l1Id) {
        this.l1Id = l1Id;
    }

    public int getL2Id() {
        return l2Id;
    }

    public void setL2Id(int l2Id) {
        this.l2Id = l2Id;
    }

    public double getDistanceRadians() {
        return distanceRadians;
    }

    public void setDistanceRadians(double distanceRadians) {
        this.distanceRadians = distanceRadians;
    }

    public double getDistanceMeters() {
        return distanceMeters;
    }

    public void setDistanceMeters(double distanceMeters) {
        this.distanceMeters = distanceMeters;
    }

    public double getDistanceMiles() {
        return distanceMiles;
    }

    public void setDistanceMiles(double distanceMiles) {
        this.distanceMiles = distanceMiles;
    }
}
