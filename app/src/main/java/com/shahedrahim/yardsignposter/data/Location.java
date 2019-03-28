package com.shahedrahim.yardsignposter.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "location_table")
public class Location {
    private static final String TAG = "Location";

    @PrimaryKey(autoGenerate = true)
    private int id;

    private double latitude;
    private double longitude;

    //multi-line
    private String address;

    //city
    private String locality;

    //county
    @ColumnInfo(name="sub_admin_area")
    private String subAdminArea;

    //state
    @ColumnInfo(name="admin_area")
    private String adminArea;

    //zip code
    @ColumnInfo(name="postal_code")
    private String postalCode;

    private String country;

    //phone number
    private String phone;

    //landmark
    @ColumnInfo(name = "feature_name")
    private String featureName;

    private String premises;

    private String url;

    //Constructor
    public Location(
            double latitude,
            double longitude,
            String address,
            String locality,
            String adminArea,
            String postalCode) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.locality = locality;
        this.adminArea = adminArea;
        this.postalCode = postalCode;
    }

//    // Constructor
//    public Location(
//            double latitude,
//            double longitude,
//            String address,
//            String locality,
//            String subAdminArea,
//            String adminArea,
//            String postalCode,
//            String country,
//            String phone,
//            String featureName,
//            String premises,
//            String url) {
//        this(latitude, longitude, address, locality, adminArea, postalCode);
//        this.subAdminArea = subAdminArea;
//        this.country = country;
//        this.phone = phone;
//        this.featureName = featureName;
//        this.premises = premises;
//        this.url = url;
//    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getSubAdminArea() {
        return subAdminArea;
    }

    public void setSubAdminArea(String subAdminArea) {
        this.subAdminArea = subAdminArea;
    }

    public String getAdminArea() {
        return adminArea;
    }

    public void setAdminArea(String adminArea) {
        this.adminArea = adminArea;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFeatureName() {
        return featureName;
    }

    public void setFeatureName(String featureName) {
        this.featureName = featureName;
    }

    public String getPremises() {
        return premises;
    }

    public void setPremises(String premises) {
        this.premises = premises;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        } else {
            Location objLocation = (Location) obj;
            if (id!=objLocation.id) return false;
            if (latitude != objLocation.latitude) return false;
            if (longitude != objLocation.longitude) return false;
            if (!((address==null && objLocation.address==null)
                    || (address.equals(objLocation.address))))
                return false;
            if (!((locality==null && objLocation.locality==null)
                    || (locality.equals(objLocation.locality))))
                return false;
            if (!((subAdminArea==null && objLocation.subAdminArea==null)
                    || (subAdminArea.equals(objLocation.subAdminArea))))
                return false;
            if (!((adminArea==null && objLocation.adminArea==null)
                    || (adminArea.equals(objLocation.adminArea))))
                return false;
            if (!((postalCode==null && objLocation.postalCode==null)
                    || (postalCode.equals(objLocation.postalCode))))
                return false;
            if (!((country==null && objLocation.country==null)
                    || (country.equals(objLocation.country))))
                return false;
            if (!((phone==null && objLocation.phone==null)
                    || (phone.equals(objLocation.phone))))
                return false;
            if (!((featureName==null && objLocation.featureName==null)
                    || (featureName.equals(objLocation.featureName))))
                return false;
            if (!((premises==null && objLocation.premises==null)
                    || (premises.equals(objLocation.premises))))
                return false;
            if (!((url==null && objLocation.url==null)
                    || (url.equals(objLocation.url))))
                return false;
            return true;
        }
    }
}
