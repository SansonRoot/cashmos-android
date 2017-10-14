package com.cashmos.cashmos;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cashmos.cashmos.http.requests.ActivationCodeRequest;
import com.cashmos.cashmos.http.requests.Auth;
import com.cashmos.cashmos.http.requests.ProfileRequest;
import com.cashmos.cashmos.support.Toaster;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {


    private TextView mPhoneNumber;
    private TextView mEmail;


    private TextView mStreet;
    private TextView mCity;
    private TextView mPostalCode;

    private TextView mChangeEmail;
    private TextView mChangePhone;
    private TextView mChangeAddress;
    private AlertDialog.Builder dialogBuilder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        if(dialogBuilder == null){
            dialogBuilder = new AlertDialog.Builder(this);
        }


        CircleImageView mProfileImage = (CircleImageView) findViewById(R.id.imgProfileImage);
        TextView mAccountName = (TextView) findViewById(R.id.txtAccountName);

        // Contact
        mPhoneNumber = (TextView) findViewById(R.id.txtPhoneNumber);
        mEmail = (TextView) findViewById(R.id.txtEmail);

        // Address
        mStreet = (TextView) findViewById(R.id.txtStreet);
        mCity = (TextView) findViewById(R.id.txtCity);
        mPostalCode = (TextView) findViewById(R.id.txtPostalCode);


        mStreet.setText(Auth.getUser().getAddress().getStreet());
        mCity.setText(Auth.getUser().getAddress().getCity());
        mPostalCode.setText(Auth.getUser().getAddress().getPostalCode());


        // Buttons
        mChangePhone = (TextView) findViewById(R.id.btnChangePhoneNumber);
        mChangeEmail = (TextView) findViewById(R.id.btnChangeEmail);
        mChangeAddress = (TextView) findViewById(R.id.btnChangeAddress);

        registerActions();

        mAccountName.setText(Auth.getUser().getName());
        mPhoneNumber.setText(Auth.getUser().getPhoneNumber());
        mEmail.setText(Auth.getUser().getEmail());

        // @TODO Set address information

        Picasso.with(this).load(
                Auth.getUser().getProfileImage()
        )
         //.resize(300, 300)
         //.centerCrop()
         .into(mProfileImage);

    }


    private void registerActions(){

        // Changing phone number
        onChangingPhoneNumber();

        // Changing email address
        onChangingEmail();

        // Changing address
        onChangingAddress();

    }


    private void onChangingPhoneNumber(){

        mChangePhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Changing phone number
                View mDialogView = getLayoutInflater().inflate(R.layout.profile_change_phone, null);
                final View mPhoneContainer = mDialogView.findViewById(R.id.phoneContainer);
                final View mActivationContainer = mDialogView.findViewById(R.id.activationContainer);

                mActivationContainer.setVisibility(View.GONE);

                // Phone number
                final EditText mPhone = (EditText) mDialogView.findViewById(R.id.edtPhone);
                Button mChangePhone = (Button) mDialogView.findViewById(R.id.btnChangePhone);
                Button mCancel = (Button) mDialogView.findViewById(R.id.btnCancel);

                mPhone.setText(Auth.getUser().getPhoneNumber());

                // Show dialog
                final AlertDialog dialog = dialogBuilder.setView(mDialogView).create();
                dialog.setCancelable(false);
                dialog.show();


                // Activation
                final EditText mActivationCode = (EditText) mDialogView.findViewById(R.id.edtActivationCode);
                Button mActivate = (Button)  mDialogView.findViewById(R.id.btnActivate);
                Button mCancelActivation = (Button)  mDialogView.findViewById(R.id.btnCancelActivation);


                // Progress bar
                final ProgressBar mProgress = (ProgressBar) mDialogView.findViewById(R.id.profileProgress);

                // Phone number
                mChangePhone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        // Send phone change request to server

                        mProgress.setVisibility(ProgressBar.VISIBLE);

                        ActivationCodeRequest request = new ActivationCodeRequest(
                                mPhone.getText().toString()
                        );

                        request.execute(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {

                                mProgress.setVisibility(ProgressBar.INVISIBLE);

                                // We show activation form
                                mPhoneContainer.setVisibility(View.GONE);
                                mActivationContainer.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {

                                mProgress.setVisibility(ProgressBar.INVISIBLE);

                                Toaster.toast(ProfileActivity.this,
                                        "Your phone number could not be changed. Please try again.");
                            }
                        });

                    }
                });


                mCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog.dismiss();

                    }
                });



                // Activation Code
                mActivate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        mProgress.setVisibility(ProgressBar.VISIBLE);

                        // Send phone change request with activation code to
                        // server
                        HashMap<String, Object> payload = new HashMap<>();
                        payload.put("field", "phone");
                        payload.put("phone", mPhone.getText().toString());
                        payload.put("code", mActivationCode.getText().toString());

                        ProfileRequest request = new ProfileRequest(payload);
                        request.execute(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {

                                mProgress.setVisibility(ProgressBar.GONE);

                                // Phone number changed
                                ProfileActivity.this.updatePhoneNumberFields(mPhone.getText().toString());
                                Toaster.toast(ProfileActivity.this,
                                        "Your phone number has been changed!");

                                dialog.dismiss();
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                mProgress.setVisibility(ProgressBar.INVISIBLE);
                                Toaster.toast(ProfileActivity.this,
                                        "We could not change your phone number. Please try again.");
                            }
                        });

                    }
                });


                mCancelActivation.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // User has cancelled activation
                        dialog.dismiss();
                    }
                });


            }
        });

    }


    private void onChangingEmail(){

        mChangeEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Create email view and add to dialog builder
                View mDialogView = getLayoutInflater().inflate(R.layout.profile_change_email, null);

                final EditText mEmail = (EditText) mDialogView.findViewById(R.id.edtEmail);
                Button mChange = (Button) mDialogView.findViewById(R.id.btnChangeEmail);
                Button mCancel = (Button) mDialogView.findViewById(R.id.btnCancel);
                final ProgressBar mProgress = (ProgressBar) mDialogView.findViewById(R.id.profileProgress);

                mEmail.setText(Auth.getUser().getEmail());

                // Create and show the alert
                ProfileActivity.this.dialogBuilder.setView(mDialogView);

                final AlertDialog dialog = ProfileActivity.this.dialogBuilder.create();


                dialog.show();
                // We prevent the dialog from being dismissed
                dialog.setCancelable(false);


                // Changing email
                mChange.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        mProgress.setVisibility(ProgressBar.VISIBLE);

                        HashMap<String, Object> payload = new HashMap<>();

                        payload.put("field", "email");
                        payload.put("email", mEmail.getText().toString());

                        // Request email change on server
                        ProfileRequest request = new ProfileRequest(payload);

                        request.execute(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {

                                mProgress.setVisibility(ProgressBar.GONE);
                                ProfileActivity.this.updateEmailFields(mEmail.getText().toString());

                                // Email has been changed successfully
                                Toast.makeText(ProfileActivity.this, "Email changed successfully!",
                                        Toast.LENGTH_LONG).show();
                                // We dismiss the dialog
                                dialog.dismiss();
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {

                                // There was an error while processing
                                Toast.makeText(ProfileActivity.this,
                                        "We could not change your email. Please try again!",
                                        Toast.LENGTH_LONG).show();

                                mProgress.setVisibility(ProgressBar.INVISIBLE);
                            }
                        });
                    }
                });


                mCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        // Cancelling email change
                        dialog.dismiss();

                    }
                });

            }
        });


    }


    private void onChangingAddress(){

        mChangeAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View mDialogView = getLayoutInflater().inflate(R.layout.profile_change_address, null);
                final EditText mStreet = (EditText) mDialogView.findViewById(R.id.edtStreet);
                final EditText mCity = (EditText) mDialogView.findViewById(R.id.edtCity);
                final EditText mPostalCode = (EditText) mDialogView.findViewById(R.id.edtPostalCode);

                mStreet.setText(Auth.getUser().getAddress().getStreet());
                mCity.setText(Auth.getUser().getAddress().getCity());
                mPostalCode.setText(Auth.getUser().getAddress().getPostalCode());

                final ProgressBar mProgress = (ProgressBar) mDialogView.findViewById(R.id.progressBar);
                mProgress.setVisibility(ProgressBar.INVISIBLE);

                Button mChangeAddress = (Button) mDialogView.findViewById(R.id.btnChangeAddress);
                Button mCancel = (Button) mDialogView.findViewById(R.id.btnCancel);

                final AlertDialog dialog = ProfileActivity.this.dialogBuilder.setView(mDialogView).create();
                dialog.setCancelable(false);
                dialog.show();


                mChangeAddress.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        mProgress.setVisibility(ProgressBar.VISIBLE);

                        HashMap<String, Object> payload = new HashMap<>();

                        payload.put("field", "address");
                        payload.put("street", mStreet.getText().toString());
                        payload.put("city", mCity.getText().toString());
                        payload.put("postal_code", mPostalCode.getText().toString());

                        ProfileRequest request = new ProfileRequest(payload);

                        request.execute(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {

                                ProfileActivity.this.updateAddressFields(
                                        mStreet.getText().toString(),
                                        mCity.getText().toString(),
                                        mPostalCode.getText().toString()
                                );

                                mProgress.setVisibility(ProgressBar.INVISIBLE);

                                dialog.dismiss();
                                Toaster.toast(ProfileActivity.this,
                                        "Your address has been changed.");
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {

                                mProgress.setVisibility(ProgressBar.INVISIBLE);
                                Toaster.toast(ProfileActivity.this,
                                        "Address could not be changed. Please try again.");

                            }
                        });

                    }
                });


                mCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

            }
        });

    }


    private void updateAddressFields(String street, String city, String postalCode){
        mStreet.setText(street);
        mCity.setText(city);
        mPostalCode.setText(postalCode);
    }


    private void updatePhoneNumberFields(String phoneNumber){
        Auth.getUser().setPhoneNumber(phoneNumber);
        mPhoneNumber.setText(phoneNumber);
    }



    private void updateEmailFields(String email){
        Auth.getUser().setEmail(email);
        mEmail.setText(email);
    }


}
