package com.cashmos.cashmos.contracts;

import java.util.HashMap;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by brightantwiboasiako on 9/6/17.
 */

public interface TransferClient {

    public static final String MOBILE_MONEY  = "mobile-money";
    public static final String BANK  = "bank";
    public static final String USER  = "user";
    public static final String BUSINESS  = "business";

    public static final String RECIPIENT_NAME  = "recipient-name";
    public static final String RECIPIENT_ID  = "recipient-id";
    public static final String RECIPIENT_TYPE  = "recipient-type";
    public static final String RECIPIENT_PROFILE_ID  = "recipient-profile-id";
    public static final String RECIPIENT_IMAGE  = "recipient-image";


    @POST("transactions/transfers")
    Call<Void> makeTransfer(
            @Body HashMap<String, Object> body
    );

}
