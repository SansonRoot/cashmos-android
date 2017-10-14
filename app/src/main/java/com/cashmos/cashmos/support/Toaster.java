package com.cashmos.cashmos.support;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by brightantwiboasiako on 10/13/17.
 */

public class Toaster {

    public static void toast(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }


    public static void toast(Context context, String message, boolean shorter){

        if(!shorter){
            toast(context, message);
        }else{
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        }
    }

}
