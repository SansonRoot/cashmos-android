package com.cashmos.cashmos.contracts;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by brightantwiboasiako on 9/6/17.
 */

public interface MobileMoney {

    public static final String NETWORK_MTN = "mtn";
    public static final String NETWORK_VODAFONE = "vodafone";
    public static final String NETWORK_TIGO = "tigo";
    public static final String NETWORK_AIRTEL = "airtel";

}
