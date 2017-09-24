package com.cashmos.cashmos.contracts;

/**
 * Created by brightantwiboasiako on 9/4/17.
 */

import retrofit2.Callback;

public interface RequestInterface<T>{

    public void execute(Callback<T> callback);

}
