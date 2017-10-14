package com.cashmos.cashmos.http.requests;

import com.cashmos.cashmos.contracts.ProfileClient;
import com.cashmos.cashmos.contracts.RequestInterface;
import com.cashmos.cashmos.http.ServiceGenerator;

import java.util.HashMap;

import retrofit2.Callback;

/**
 * Created by brightantwiboasiako on 10/13/17.
 */

public class ProfileRequest implements RequestInterface<Void> {


    private HashMap<String, Object> body;

    public ProfileRequest(HashMap<String, Object> body){

        this.body = body;

    }


    @Override
    public void execute(Callback<Void> callback) {
        ProfileClient service = ServiceGenerator.createService(ProfileClient.class);
        service.updateProfile(body).enqueue(callback);
    }
}
