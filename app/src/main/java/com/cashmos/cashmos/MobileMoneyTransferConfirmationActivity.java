package com.cashmos.cashmos;

import android.content.Intent;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cashmos.cashmos.contracts.Account;
import com.cashmos.cashmos.contracts.TransferClient;
import com.cashmos.cashmos.http.requests.TransferRequest;
import com.cashmos.cashmos.support.TransferDialog;

import java.util.HashMap;
import java.util.Map;

public class MobileMoneyTransferConfirmationActivity extends AppCompatActivity {


    private TextView mAccountName;
    private TextView mAccountBalance;
    private TextView mTransferAmount;
    private TextView mMobileAccountName;
    private TextView mNetworkName;
    private TextView mMobileAccountNumber;
    private Button mBack;
    private Button mConfirm;
    private TransferDialog mAlert;


    private Intent accountBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_money_transfer_confirmation);

        // Get instance of transfer dialog
        mAlert = new TransferDialog(this, getLayoutInflater().inflate(R.layout.process_dialog, null));

        // Intialize the views and set it's values
        mAccountName = (TextView) findViewById(R.id.txtAccountName);
        mAccountBalance = (TextView) findViewById(R.id.txtAvailableBalance);
        mTransferAmount = (TextView) findViewById(R.id.txtTransferAmount);
        mMobileAccountName = (TextView) findViewById(R.id.txtMobileAccountName);
        mNetworkName = (TextView) findViewById(R.id.txtNetworkName);
        mMobileAccountNumber = (TextView) findViewById(R.id.txtAccountNumber);
        mBack = (Button) findViewById(R.id.btnBack);
        mConfirm = (Button) findViewById(R.id.btnConfirm);

        accountBundle = getIntent();
        displayTransfer();

        // We set event listeners for buttons
        onConfirm();
        onBack();

    }


    private void displayTransfer(){

        // Account Info
        mAccountName.setText(accountBundle.getStringExtra(Account.NAME));
        mAccountBalance.setText(getString(R.string.transfer_available_balance,
                accountBundle.getDoubleExtra(Account.AVAILABLE_BALANCE, 0.0)));

        // Mobile Account
        mTransferAmount.setText(getString(R.string.money, accountBundle.getDoubleExtra("amount", 0.0)));
        mNetworkName.setText(accountBundle.getStringExtra("network_name"));
        mMobileAccountNumber.setText(accountBundle.getStringExtra("number"));
        mMobileAccountName.setText(accountBundle.getStringExtra("name"));
    }


    private void onConfirm(){

        // Send a request to the server
        mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            // We show processing alert box
            MobileMoneyTransferConfirmationActivity.this.mAlert.initProcessing();

            // We make a server request

            double amount = accountBundle.getDoubleExtra("amount", 0.0);
            Map<String, String> source = new ArrayMap<>();
            Map<String, String> destination = new HashMap<>();

            // Create source and destination data
            source.put("method", accountBundle.getStringExtra(Account.TYPE));
            if(accountBundle.getStringExtra(Account.TYPE).equalsIgnoreCase(TransferClient.BUSINESS)){
                source.put("business_id", accountBundle.getStringExtra(Account.ID));
            }else{
                source.put("user_id", accountBundle.getStringExtra(Account.ID));
            }

            destination.put("method", TransferClient.MOBILE_MONEY);
            destination.put("network", accountBundle.getStringExtra("network"));
            destination.put("number", accountBundle.getStringExtra("number"));
            destination.put("name", accountBundle.getStringExtra("name"));

            HashMap<String, Object> payload = new HashMap<>();

            payload.put("amount", amount);
            payload.put("source", source);
            payload.put("destination", destination);

            TransferRequest request = new TransferRequest(payload);
            request.execute(
                    MobileMoneyTransferConfirmationActivity.this.mAlert.afterTransferRequest()
            );

            }
        });

    }




    private void onBack(){

        // We take them back
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MobileMoneyTransferConfirmationActivity.this.onBackPressed();
            }
        });
    }


}
