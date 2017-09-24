package com.cashmos.cashmos.contracts;

/**
 * Created by brightantwiboasiako on 9/4/17.
 */

public interface Account {

    public static final String NAME  = "account-name";
    public static final String BALANCE  = "balance";
    public static final String AVAILABLE_BALANCE  = "available-balance";
    public static final String PROFILE_ID  = "profile-id";
    public static final String ID  = "id";
    public static final String TYPE  = "type";
    public static final String TYPE_BUSINESS  = "business";
    public static final String TYPE_USER  = "user";

    public String getName();
    public double getBalance();
    public double getAvailableBalance();
    public String getProfileId();
    public String getId();
    public String getType();

}
