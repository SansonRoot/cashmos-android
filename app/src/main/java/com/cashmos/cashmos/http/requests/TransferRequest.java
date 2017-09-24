package com.cashmos.cashmos.http.requests;

import com.cashmos.cashmos.contracts.RequestInterface;
import com.cashmos.cashmos.contracts.TransferClient;
import com.cashmos.cashmos.http.ServiceGenerator;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Callback;

/**
 * Created by brightantwiboasiako on 9/6/17.
 */

public class TransferRequest implements RequestInterface<Void> {

    private HashMap<String, Object> body;

    public TransferRequest(HashMap<String, Object> body) {
        this.body = body;
    }

    @Override
    public void execute(Callback<Void> callback) {

        TransferClient service = ServiceGenerator.createService(TransferClient.class);
        service.makeTransfer(body)
        .enqueue(callback);

    }
}
