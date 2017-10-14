package com.cashmos.cashmos.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by brightantwiboasiako on 10/13/17.
 */

public class Address {

    private String street;
    private String city;
    @SerializedName("postal_code")
    private String postalCode;

    public String getCity() {
        return city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getStreet() {
        return street;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public void setStreet(String street) {
        this.street = street;
    }
}
