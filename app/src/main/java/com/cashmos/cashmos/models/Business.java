package com.cashmos.cashmos.models;

import com.google.gson.annotations.SerializedName;
import com.cashmos.cashmos.models.Account;

/**
 * Created by brightantwiboasiako on 9/4/17.
 */

public class Business extends Account{


    @Override
    public String getType() {
        return com.cashmos.cashmos.contracts.Account.TYPE_BUSINESS;
    }
}
