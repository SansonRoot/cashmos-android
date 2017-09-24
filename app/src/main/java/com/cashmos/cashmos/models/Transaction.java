package com.cashmos.cashmos.models;

import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by brightantwiboasiako on 9/5/17.
 */

public class Transaction {

    private static final String STATUS_COMPLETED = "completed";
    public static final String TYPE_CREDIT = "credit";

    private double amount;
    private double fee;
    private double balance;
    @SerializedName("merchant_name")
    private String merchantName;
    private String description;
    @SerializedName("time_ago")
    private String timeAgo;
    private String status;
    private String type;
    @SerializedName("created_at")
    private String createdAt;

    public double getAmount() {
        return amount;
    }

    public double getFee() {
        return fee;
    }

    public double getBalance() {
        return balance;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public String getDescription() {
        return description;
    }

    public String getTimeAgo() {
        return timeAgo;
    }

    public String getStatus() {
        return status;
    }

    public String getType() {
        return type;
    }

    public boolean isComplete(){
        return STATUS_COMPLETED.equals(getStatus());
    }


    public String getDate(){

        SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
        try {
            Date date = format.parse(createdAt);
            return new SimpleDateFormat("MMM dd, yyyy").format(date);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return getTimeAgo();
    }

    public boolean isCredit(){
        return TYPE_CREDIT.equals(getType());
    }

}
