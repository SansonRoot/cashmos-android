package com.cashmos.cashmos.models;

import com.cashmos.cashmos.models.Account;
import com.google.gson.annotations.SerializedName;

/**
 * Created by brightantwiboasiako on 9/4/17.
 */

public class User extends Account{

    @SerializedName("first_name")
    private String firstName;
    @SerializedName("last_name")
    private String lastName;
    @SerializedName("phone")
    private String phoneNumber;
    @SerializedName("username")
    private String username;


    public String getUsername() {
        return username;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public String getType() {
        return com.cashmos.cashmos.contracts.Account.TYPE_USER;
    }
}
