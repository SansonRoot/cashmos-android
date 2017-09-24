package com.cashmos.cashmos.contracts;



import com.cashmos.cashmos.models.Account;
import com.cashmos.cashmos.models.UserAccountsBundle;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by brightantwiboasiako on 9/8/17.
 */

public interface AccountClient {

    @GET("accounts")
    Call<UserAccountsBundle> getAccounts();

    @GET("accounts/{profileId}")
    Call<Account> getAccountByProfileId(@Path("profileId") String profileId);

}
