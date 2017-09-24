package com.cashmos.cashmos.http.requests;

import com.cashmos.cashmos.models.Account;
import com.cashmos.cashmos.contracts.AccountClient;
import com.cashmos.cashmos.contracts.RequestInterface;
import com.cashmos.cashmos.http.ServiceGenerator;

import retrofit2.Callback;

/**
 * Created by brightantwiboasiako on 9/8/17.
 */

public class SingleAccountRequest implements RequestInterface<Account> {


    private String profileId;

    public SingleAccountRequest(String profileId){
        this.profileId = profileId;
    }


    @Override
    public void execute(Callback<Account> callback) {

        AccountClient service = ServiceGenerator.createService(AccountClient.class);
        service.getAccountByProfileId(profileId)
                .enqueue(callback);

    }
}
