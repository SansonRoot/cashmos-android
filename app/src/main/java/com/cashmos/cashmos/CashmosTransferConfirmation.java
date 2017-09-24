package com.cashmos.cashmos;

import android.content.Intent;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cashmos.cashmos.contracts.Account;
import com.cashmos.cashmos.contracts.TransferClient;
import com.cashmos.cashmos.http.requests.TransferRequest;
import com.cashmos.cashmos.support.TransferDialog;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class CashmosTransferConfirmation extends AppCompatActivity {


    private Intent transferBundle;
    private Button mBack;
    private Button mConfirm;
    private TransferDialog mAlert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cashmos_transfer_confirmation);

        TextView mAccountName = (TextView) findViewById(R.id.txtAccountName);
        TextView mAvailableBalance = (TextView) findViewById(R.id.txtAvailableBalance);
        TextView mTransferAmount = (TextView) findViewById(R.id.txtTransferAmount);
        TextView mRecipientName = (TextView) findViewById(R.id.txtRecipientName);
        TextView mRecipientProfileID = (TextView) findViewById(R.id.txtRecipientProfileId);
        ImageView mRecipientImage = (ImageView) findViewById(R.id.imgRecipient);

        mConfirm = (Button)  findViewById(R.id.btnConfirm);
        mBack = (Button)  findViewById(R.id.btnBack);

        transferBundle = getIntent();

        mAccountName.setText(transferBundle.getStringExtra(Account.NAME));
        mAvailableBalance.setText(getString(
                R.string.transfer_available_balance,
                transferBundle.getDoubleExtra(Account.AVAILABLE_BALANCE, 0)
        ));
        mTransferAmount.setText(
                getString(R.string.transfer_amount_max, transferBundle.getDoubleExtra("amount", 0))
        );

        mRecipientName.setText(
                transferBundle.getStringExtra(TransferClient.RECIPIENT_NAME)
        );

        mRecipientProfileID.setText(
                transferBundle.getStringExtra(TransferClient.RECIPIENT_PROFILE_ID)
        );

        if(transferBundle.getStringExtra(TransferClient.RECIPIENT_IMAGE) != null){

            Picasso.with(this).load(
                    transferBundle.getStringExtra(TransferClient.RECIPIENT_IMAGE)
            ).into(mRecipientImage);

        }else{

            // We set institution for business accounts
            if(com.cashmos.cashmos.models.Account.isBusinessId(
                    transferBundle.getStringExtra(TransferClient.RECIPIENT_PROFILE_ID)
            )){
                mRecipientImage.setImageResource(R.drawable.ic_account_balance_black_24dp);
            }

        }


        mAlert = new TransferDialog(this, getLayoutInflater().inflate(R.layout.process_dialog, null));


        onConfirm();

        onBack();

    }



    private void onConfirm(){


        mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mAlert.initProcessing();

                double amount = transferBundle.getDoubleExtra("amount", 0.0);
                Map<String, String> source = new ArrayMap<>();
                Map<String, Object> destination = new HashMap<>();

                // Create source and destination data

                source.put("method", transferBundle.getStringExtra(Account.TYPE));
                if(transferBundle.getStringExtra(Account.TYPE).equalsIgnoreCase(TransferClient.BUSINESS)){
                    source.put("business_id", transferBundle.getStringExtra(Account.ID));
                }else{
                    source.put("user_id", transferBundle.getStringExtra(Account.ID));
                }

                destination.put("method", transferBundle.getStringExtra(TransferClient.RECIPIENT_TYPE));
                if(transferBundle.getStringExtra(TransferClient.RECIPIENT_TYPE).equalsIgnoreCase(TransferClient.BUSINESS)){
                    destination.put("business_id", transferBundle.getStringExtra(TransferClient.RECIPIENT_ID));
                }else{
                    destination.put("user_id", transferBundle.getStringExtra(TransferClient.RECIPIENT_ID));
                }


                HashMap<String, Object> payload = new HashMap<>();

                payload.put("amount", amount);
                payload.put("source", source);
                payload.put("destination", destination);


                TransferRequest request = new TransferRequest(payload);
                request.execute(
                        mAlert.afterTransferRequest()
                );

            }
        });


    }



    private void onBack(){
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CashmosTransferConfirmation.this.onBackPressed();
            }
        });
    }


}