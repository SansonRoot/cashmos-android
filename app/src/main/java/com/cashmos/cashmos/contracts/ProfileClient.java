package com.cashmos.cashmos.contracts;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.PATCH;
import retrofit2.http.POST;

/**
 * Created by brightantwiboasiako on 10/13/17.
 */

public interface ProfileClient {

    @PATCH("accounts/settings")
    Call<Void> updateProfile(@Body HashMap<String, Object> body);


    @POST("accounts/settings/phone/send-code")
    @FormUrlEncoded
    Call<Void> sendActivationCode(@Field("phone") String phone);

}
