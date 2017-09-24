package com.cashmos.cashmos;

import android.content.Intent;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cashmos.cashmos.http.requests.RegistrationRequest;
import com.cashmos.cashmos.models.User;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends AppCompatActivity {

    private EditText firstName;
    private EditText lastName;
    private EditText username;
    private EditText phone;
    private EditText password;
    private EditText passwordConfirmation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        firstName = (EditText)findViewById(R.id.edtFirstName);
        lastName = (EditText)findViewById(R.id.edtLastName);
        username = (EditText)findViewById(R.id.edtUsername);
        phone = (EditText)findViewById(R.id.edtPhone);
        password = (EditText)findViewById(R.id.edtPassword);
        passwordConfirmation = (EditText)findViewById(R.id.edtPasswordConfirmation);

        // We get the register button and
        // bind the listener to it

        final Button registerButton = (Button) findViewById(R.id.btnRegister);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Map<String, String> payload = RegistrationActivity.this.makeRegistrationPayload();

                RegistrationRequest registration = new RegistrationRequest(payload);

                registration.execute(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {

                        if(response.isSuccessful()){
                            // Request was successfull and the user has been registered
                            // Start Activation activity for account activation
                            Intent intent = new Intent(RegistrationActivity.this, ActivationActivity.class);
                            RegistrationActivity.this.startActivity(intent);
                        }else{
                            // There was an error with activation
                            AlertDialog.Builder builder = new AlertDialog.Builder(RegistrationActivity.this);

                            builder.setMessage("Registration Failed!")
                                    .setNegativeButton("Retry", null)
                                    .create()
                                    .show();

                        }

                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        // The user is probably not connected to the internet
                        Toast.makeText(RegistrationActivity.this, "No internet connection!", Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }
        });


    }


    private Map<String, String> makeRegistrationPayload(){

        Map<String, String> payload = new ArrayMap<>();

        payload.put("first_name", firstName.getText().toString());
        payload.put("last_name", lastName.getText().toString());
        payload.put("username", username.getText().toString());
        payload.put("phone", phone.getText().toString());
        payload.put("password", password.getText().toString());
        payload.put("password_confirmation", passwordConfirmation.getText().toString());

        return payload;
    }
}
