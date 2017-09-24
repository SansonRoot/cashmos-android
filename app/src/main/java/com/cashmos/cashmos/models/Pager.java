package com.cashmos.cashmos.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by brightantwiboasiako on 9/5/17.
 */

public class Pager {

    @SerializedName("current_page")
    private int currentPage;
    @SerializedName("last_page")
    private int lastPage;
    private int total;


    public int getCurrentPage() {
        return currentPage;
    }

    public int getLastPage() {
        return lastPage;
    }

    public int getTotal() {
        return total;
    }
}
