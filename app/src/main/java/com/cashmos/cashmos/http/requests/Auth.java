package com.cashmos.cashmos.http.requests;

import com.cashmos.cashmos.contracts.OAuthClient;
import com.cashmos.cashmos.http.ServiceGenerator;
import com.cashmos.cashmos.models.AccessToken;
import com.cashmos.cashmos.models.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by brightantwiboasiako on 9/4/17.
 */

public class Auth {


    private static AccessToken token = null;

    private static User user = null;


    public static void getAuthorization(String username, String password, final Callback<AccessToken> callback){

        OAuthClient service = ServiceGenerator.createService(OAuthClient.class);

        service.getAccessToken("password", username, password)
                .enqueue(new Callback<AccessToken>() {
                    @Override
                    public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                        Auth.setToken(response.body());
                        callback.onResponse(call, response);
                    }

                    @Override
                    public void onFailure(Call<AccessToken> call, Throwable t) {
                        callback.onFailure(call, t);
                    }
                });

    }


    public static boolean authorized(){
        return getToken() != null;
    }


    public static void setUser(User user){
        Auth.user = user;
    }


    public static User getUser() {
        return user;
    }

    public static AccessToken getToken(){
        return token;
    }

    public static void setToken(AccessToken token){
        Auth.token = token;
    }


}
