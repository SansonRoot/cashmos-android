package com.cashmos.cashmos.http.requests;

import com.cashmos.cashmos.contracts.RegistrationClient;
import com.cashmos.cashmos.contracts.RequestInterface;
import com.cashmos.cashmos.http.ServiceGenerator;
import com.cashmos.cashmos.models.User;

import java.util.Map;

import retrofit2.Callback;

/**
 * Created by brightantwiboasiako on 9/4/17.
 *
 * Sends a registration request to the server
 */

public class RegistrationRequest implements RequestInterface<User> {

    private Map<String, String> body;

    public RegistrationRequest(Map<String, String> requestBody){
        this.body = requestBody;
    }


    @Override
    public void execute(Callback<User> callback) {
        RegistrationClient service = ServiceGenerator.createService(RegistrationClient.class);
        service.register(body).enqueue(callback);
    }
}
