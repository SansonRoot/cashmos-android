package com.cashmos.cashmos.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by brightantwiboasiako on 9/4/17.
 *
 * The access token object returned from the
 * Cashmos OAuth server
 */
public class AccessToken {

    @SerializedName("access_token")
    private String accessToken;

    @SerializedName("refresh_token")
    private String refreshToken;

    @SerializedName("token_type")
    private String tokenType;

    @SerializedName("expires_in")
    private int expiresIn;

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

}
