package com.cashmos.cashmos.contracts;

import com.cashmos.cashmos.models.AccessToken;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by brightantwiboasiako on 9/4/17.
 *
 * The Cashmos OAuthClient
 */

public interface OAuthClient {

    @FormUrlEncoded
    @POST("oauth/token")
    Call<AccessToken> getAccessToken(
        @Field("grant_type") String grantType,
        @Field("username") String username,
        @Field("password") String password
    );


    @FormUrlEncoded
    @POST("oauth/refresh")
    Call<AccessToken> getFrestToken(
            @Field("refresh_token") String refereshToken
    );

}
