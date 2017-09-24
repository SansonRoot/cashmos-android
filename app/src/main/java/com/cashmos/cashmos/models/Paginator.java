package com.cashmos.cashmos.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by brightantwiboasiako on 9/5/17.
 */

public class Paginator {

    @SerializedName("data")
    private Transaction[] transactions;
    private Pager pager;


    public Transaction[] getTransactions() {
        return transactions;
    }

    public Pager getPager() {
        return pager;
    }
}
