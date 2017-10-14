package com.cashmos.cashmos.http.requests;

import com.cashmos.cashmos.contracts.ProfileClient;
import com.cashmos.cashmos.contracts.RequestInterface;
import com.cashmos.cashmos.http.ServiceGenerator;

import retrofit2.Callback;

/**
 * Created by brightantwiboasiako on 10/13/17.
 */

public class ActivationCodeRequest implements RequestInterface<Void> {


    String phoneNumber;

    public ActivationCodeRequest(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }


    @Override
    public void execute(Callback<Void> callback) {

        ProfileClient service = ServiceGenerator.createService(ProfileClient.class);
        service.sendActivationCode(phoneNumber)
                .enqueue(callback);

    }
}
