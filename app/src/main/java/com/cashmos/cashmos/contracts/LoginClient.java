package com.cashmos.cashmos.contracts;

import com.cashmos.cashmos.models.User;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * Created by brightantwiboasiako on 9/4/17.
 *
 * Login client
 */

public interface LoginClient {


    @FormUrlEncoded
    @POST("login")
    Call<User> login(
        @Field("username") String username,
        @Field("password") String password
    );


}
