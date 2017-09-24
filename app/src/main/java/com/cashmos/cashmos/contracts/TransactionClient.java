package com.cashmos.cashmos.contracts;

import com.cashmos.cashmos.models.Paginator;
import com.cashmos.cashmos.models.Transaction;
import com.cashmos.cashmos.models.UserAccountsBundle;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by brightantwiboasiako on 9/4/17.
 */

public interface TransactionClient {

    @GET("transactions/recent")
    Call<List<Transaction>> getRecentTransactions();

    @GET("transactions?limit=5")
    Call<Paginator> getTransactions();

    @GET("transactions/businesses/{businessId}?limit=5")
    Call<Paginator> getTransactions(@Path("businessId") String businessId);


}
