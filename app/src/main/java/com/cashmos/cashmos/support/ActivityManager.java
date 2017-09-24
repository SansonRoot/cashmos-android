package com.cashmos.cashmos.support;

import android.content.Context;
import android.content.Intent;

import com.cashmos.cashmos.DashboardActivity;

/**
 * Created by brightantwiboasiako on 9/7/17.
 */

public class ActivityManager {


    public static void toDashboard(Context context, boolean preserve){

        Intent intent = new Intent(context, DashboardActivity.class);

        if(!preserve){
            // We clear all previous activities

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        }

        context.startActivity(intent);
    }


    public static void toDashboard(Context context){
        toDashboard(context, false);
    }


}
