package com.cashmos.cashmos.contracts;

import com.cashmos.cashmos.models.User;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by brightantwiboasiako on 9/4/17.
 */

public interface RegistrationClient {

    @FormUrlEncoded
    @POST("users")
    Call<User> register(
            @FieldMap Map<String, String> params
    );

}
