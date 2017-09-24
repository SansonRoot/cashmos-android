package com.cashmos.cashmos.http.requests;

import com.cashmos.cashmos.contracts.AccountClient;
import com.cashmos.cashmos.contracts.RequestInterface;
import com.cashmos.cashmos.http.ServiceGenerator;
import com.cashmos.cashmos.models.UserAccountsBundle;

import retrofit2.Callback;

/**
 * Created by brightantwiboasiako on 9/4/17.
 */

public class AccountsRequest implements RequestInterface<UserAccountsBundle> {

    @Override
    public void execute(Callback<UserAccountsBundle> callback) {
        AccountClient service = ServiceGenerator.createService(AccountClient.class);
        service.getAccounts().enqueue(callback);
    }
}
