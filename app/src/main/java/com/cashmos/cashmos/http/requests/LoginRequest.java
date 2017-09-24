package com.cashmos.cashmos.http.requests;

import com.cashmos.cashmos.contracts.LoginClient;
import com.cashmos.cashmos.contracts.RequestInterface;
import com.cashmos.cashmos.http.ServiceGenerator;
import com.cashmos.cashmos.models.User;

import retrofit2.Callback;

/**
 * Created by brightantwiboasiako on 9/4/17.
 *
 * Performs Login request to the server
 */

public class LoginRequest implements RequestInterface<User> {

    private String username;
    private String password;


    public LoginRequest(String username, String password){
        this.username = username;
        this.password = password;
    }


    @Override
    public void execute(Callback<User> callback) {
        LoginClient service = ServiceGenerator.createService(LoginClient.class);
        service.login(username, password).enqueue(callback);
    }
}
