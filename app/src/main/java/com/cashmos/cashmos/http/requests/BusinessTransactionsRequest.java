package com.cashmos.cashmos.http.requests;

import com.cashmos.cashmos.contracts.RequestInterface;
import com.cashmos.cashmos.contracts.TransactionClient;
import com.cashmos.cashmos.http.ServiceGenerator;
import com.cashmos.cashmos.models.Paginator;

import retrofit2.Callback;

/**
 * Created by brightantwiboasiako on 9/5/17.
 */

public class BusinessTransactionsRequest implements RequestInterface<Paginator> {


    private String businessId;

    public BusinessTransactionsRequest(String businessId) {
        this.businessId = businessId;
    }

    @Override
    public void execute(Callback<Paginator> callback) {
        TransactionClient service = ServiceGenerator.createService(TransactionClient.class);
        service.getTransactions(businessId).enqueue(callback);

    }

}
