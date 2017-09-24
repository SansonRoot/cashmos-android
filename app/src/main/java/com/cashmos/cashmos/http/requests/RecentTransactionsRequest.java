package com.cashmos.cashmos.http.requests;

import com.cashmos.cashmos.contracts.RequestInterface;
import com.cashmos.cashmos.contracts.TransactionClient;
import com.cashmos.cashmos.http.ServiceGenerator;
import com.cashmos.cashmos.models.Transaction;

import java.util.List;

import retrofit2.Callback;

/**
 * Created by brightantwiboasiako on 9/5/17.
 */

public class RecentTransactionsRequest implements RequestInterface<List<Transaction>> {

    @Override
    public void execute(Callback<List<Transaction>> callback) {

        TransactionClient service = ServiceGenerator.createService(TransactionClient.class);
        service.getRecentTransactions()
                .enqueue(callback);

    }
}
