package com.cashmos.cashmos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.cashmos.cashmos.http.requests.Auth;
import com.cashmos.cashmos.http.requests.LoginRequest;
import com.cashmos.cashmos.models.AccessToken;
import com.cashmos.cashmos.models.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {


    private EditText username;
    private EditText password;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        username = (EditText) findViewById(R.id.edtUsername);
        password = (EditText) findViewById(R.id.edtPassword);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        final Button loginButton = (Button) findViewById(R.id.btnLogin);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressBar.setVisibility(ProgressBar.VISIBLE);

                // We get access token from Cashmos OAuth server
                // if the user is not authenticated
                if(!Auth.authorized()){

                    Auth.getAuthorization(username.getText().toString(),
                                        password.getText().toString(), LoginActivity.this.onAuthorized());
                }else{
                    // We already have Authorization
                    // We redirect to dashboard
                    // Otherwise, we attempt login
                    LoginActivity.this.attemptLogin();
                }

            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();

        // We check if user is authenticated,
        // Then we take user to Dashboard
        if(Auth.authorized()){
            toDashboard();
        }

    }


    private void toDashboard(){
        Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
        startActivity(intent);
    }


    private Callback<AccessToken> onAuthorized(){

        return new Callback<AccessToken>() {
            @Override
            public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {

                if(response.isSuccessful()){

                    // Authorization was successful
                    // We proceed with login request
                    LoginActivity.this.attemptLogin();
                }else{
                    Toast.makeText(LoginActivity.this, "Auth failed", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<AccessToken> call, Throwable t) {

            }
        };

    }



    private void attemptLogin(){

        LoginRequest request = new LoginRequest(username.getText().toString(),
                                                password.getText().toString());

        request.execute(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                if(response.isSuccessful()){
                    // We set the authenticated user
                    // and redirect to the dashboard
                    Auth.setUser(response.body());
                    toDashboard();
                }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }



}
