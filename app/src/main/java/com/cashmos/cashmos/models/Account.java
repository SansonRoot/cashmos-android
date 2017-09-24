package com.cashmos.cashmos.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by brightantwiboasiako on 9/4/17.
 */

public class Account implements com.cashmos.cashmos.contracts.Account {

    private double balance;
    @SerializedName("available_balance")
    private double availableBalance;
    @SerializedName("profile_id")
    private String profileId;
    protected String name;
    protected String id;
    @SerializedName("profile_image")
    private String profileImage;

    public double getBalance() {
        return balance;
    }

    public double getAvailableBalance() {
        return availableBalance;
    }

    public String getProfileId() {
        return profileId;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }


    public static boolean isBusinessId(String profileId){
        return profileId.contains("@");
    }

    @Override
    public String getType() {
        if(isBusinessId(getProfileId())) return com.cashmos.cashmos.contracts.Account.TYPE_BUSINESS;
        return com.cashmos.cashmos.contracts.Account.TYPE_USER;
    }

    public String getProfileImage(){
        if(!isBusinessId(getProfileId())){
            return profileImage;
        }else{
            return null;
        }
    }


}
