package com.cashmos.cashmos.http;

import android.util.Log;

import com.cashmos.cashmos.http.requests.Auth;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by brightantwiboasiako on 9/4/17.
 *
 * This class generates services
 */

public class ServiceGenerator {

    private static final String BASE_URL = "http://cashmos.herokuapp.com/api/v1/";

    private static Gson gson = new GsonBuilder()
            .setLenient()
            .create();

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson));


    private static OkHttpClient.Builder httpClient =
            new OkHttpClient.Builder();

    public static <S> S createService(Class<S> serviceClass) {

        // We check if user is authenticated
        // Then we add access token header to
        // the request for OAuth authorization

        httpClient.addInterceptor(getJsonInterceptor());

        if(Auth.authorized()){
            httpClient.addInterceptor(getOAuthInterceptor());
        }else{
            Log.i("AUTH", "Not authorized");
        }

        builder.client(httpClient.build());

        return builder.build().create(serviceClass);
    }


    private static Interceptor getOAuthInterceptor(){
        return new Interceptor() {
            @Override
            public okhttp3.Response intercept(Interceptor.Chain chain) throws IOException {
                Request.Builder request = chain.request().newBuilder();
                request.header("Authorization","Bearer " + Auth.getToken()
                        .getAccessToken());
                return chain.proceed(request.build());
            }
        };
    }


    private static Interceptor getJsonInterceptor(){

        return new Interceptor() {
            @Override
            public okhttp3.Response intercept(Interceptor.Chain chain) throws IOException {
                Request.Builder request = chain.request().newBuilder();
                request.header("Accept", "application/json");
                request.header("Content-Type", "application/json");
                return chain.proceed(request.build());
            }
        };

    }



}
