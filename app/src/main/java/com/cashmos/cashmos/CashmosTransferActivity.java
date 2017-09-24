package com.cashmos.cashmos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.cashmos.cashmos.contracts.Account;
import com.cashmos.cashmos.contracts.TransferClient;
import com.cashmos.cashmos.http.requests.SingleAccountRequest;

public class CashmosTransferActivity extends AppCompatActivity {


    private Button mNext;
    private Button mBack;
    private EditText mAmount;
    private EditText mProfileId;
    private ProgressBar mFetchRecipient;
    private Intent transferBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cashmos_transfer);


        TextView mAccountName = (TextView) findViewById(R.id.txtAccountName);
        TextView mAvailableBalance = (TextView) findViewById(R.id.txtAvailableBalance);
        mAmount = (EditText) findViewById(R.id.edtTransferAmount);
        mProfileId = (EditText) findViewById(R.id.edtProfileId);
        mNext = (Button) findViewById(R.id.btnNext);
        mBack = (Button) findViewById(R.id.btnBack);
        mFetchRecipient = (ProgressBar) findViewById(R.id.pgrFetchRecipient);


        transferBundle = getIntent();

        mAccountName.setText(transferBundle.getStringExtra(Account.NAME));
        mAvailableBalance.setText(
                getString(R.string.transfer_available_balance,
                        transferBundle.getDoubleExtra(Account.AVAILABLE_BALANCE, 0))
        );

        mAmount.setHint(
                getString(R.string.transfer_amount_max,
                        transferBundle.getDoubleExtra(Account.AVAILABLE_BALANCE, 0))
        );

        onNext();

        onBack();

    }


    private void onNext(){

        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(CashmosTransferActivity.this.isTransactionDataValid()){

                    // We make a request to server for an account
                    // that matches the provided profile id

                    CashmosTransferActivity.this.getRecipient();

                }

            }
        });

    }


    private void getRecipient(){

        // We make a request to the server to
        // get the receiving account corresponding
        // to the provided profile id
        mFetchRecipient.setVisibility(ProgressBar.VISIBLE);

        SingleAccountRequest request = new SingleAccountRequest(mProfileId.getText().toString());
        request.execute(whenDoneFetchingRecipient());

    }


    private Callback<com.cashmos.cashmos.models.Account> whenDoneFetchingRecipient(){

        mFetchRecipient.setVisibility(ProgressBar.INVISIBLE);

        return new Callback<com.cashmos.cashmos.models.Account>() {
            @Override
            public void onResponse(Call<com.cashmos.cashmos.models.Account> call, Response<com.cashmos.cashmos.models.Account> response) {

                if(response.isSuccessful()){

                    transferBundle.putExtra("amount", Double.parseDouble(mAmount.getText().toString()));
                    transferBundle.putExtra(TransferClient.RECIPIENT_NAME, response.body().getName());
                    transferBundle.putExtra(TransferClient.RECIPIENT_ID, response.body().getId());
                    transferBundle.putExtra(TransferClient.RECIPIENT_TYPE, response.body().getType());
                    transferBundle.putExtra(TransferClient.RECIPIENT_PROFILE_ID, response.body().getProfileId());
                    transferBundle.putExtra(TransferClient.RECIPIENT_IMAGE, response.body().getProfileImage());

                    // We have an account
                    // We then proceed to the confirmation screen
                    CashmosTransferActivity.this.toConfirmation();

                }else{

                    // Looks like we did not find the account
                    Toast.makeText(
                            CashmosTransferActivity.this,
                            "No account matches the provided ID",
                            Toast.LENGTH_LONG
                    ).show();

                }

            }

            @Override
            public void onFailure(Call<com.cashmos.cashmos.models.Account> call, Throwable t) {

            }
        };

    }


    private void toConfirmation(){

        Intent intent = new Intent(this, CashmosTransferConfirmation.class);
        intent.putExtras(transferBundle);
        startActivity(intent);
    }


    private void onBack(){

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CashmosTransferActivity.this.onBackPressed();
            }
        });

    }


    private boolean isTransactionDataValid(){

        return true;

    }



}
