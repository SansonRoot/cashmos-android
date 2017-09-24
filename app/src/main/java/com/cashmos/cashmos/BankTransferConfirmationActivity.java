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

public class BankTransferConfirmationActivity extends AppCompatActivity {


    private Intent transferBundle;
    private Button mConfirm;
    private Button mBack;
    private TransferDialog mAlert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_transfer_confirmation);

        TextView mAccountName = (TextView) findViewById(R.id.txtAccountName);
        TextView mAvailableBalance = (TextView) findViewById(R.id.txtAvailableBalance);
        TextView mBankName = (TextView) findViewById(R.id.txtBankName);
        TextView mBankBranch = (TextView) findViewById(R.id.txtBankBranch);
        TextView mBankAccountName = (TextView) findViewById(R.id.txtBankAccountName);
        TextView mBankAccountNumber = (TextView) findViewById(R.id.txtBankAccountNumber);
        TextView mTransferAmount = (TextView) findViewById(R.id.txtTransferAmount);
        mConfirm = (Button) findViewById(R.id.btnConfirm);
        mBack = (Button) findViewById(R.id.btnBack);

        transferBundle = getIntent();

        // We set the view values
        mAccountName.setText(transferBundle.getStringExtra(Account.NAME));
        mAvailableBalance.setText(getString(
                R.string.transfer_available_balance,
                transferBundle.getDoubleExtra(Account.AVAILABLE_BALANCE, 0)
        ));
        mBankName.setText(transferBundle.getStringExtra("bank-name"));
        mBankBranch.setText(transferBundle.getStringExtra("bank-branch"));
        mBankAccountName.setText(transferBundle.getStringExtra("bank-account-name"));
        mBankAccountNumber.setText(transferBundle.getStringExtra("bank-account-number"));
        mTransferAmount.setText(getString(
                R.string.money,
                transferBundle.getDoubleExtra("amount", 0)
        ));

        // Set transfer dialog
        mAlert = new TransferDialog(this, getLayoutInflater().inflate(R.layout.process_dialog, null));

        onComfirm();

        onBack();

    }



    private void onComfirm(){

        mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // We make bank transfer request
                // We show processing alert box
                BankTransferConfirmationActivity.this.mAlert.initProcessing();

                // We make a server request

                double amount = transferBundle.getDoubleExtra("amount", 0.0);
                Map<String, String> source = new ArrayMap<>();
                Map<String, Object> destination = new HashMap<>();
                Map<String, String> bank = new HashMap<>();
                Map<String, String> bankAccount = new HashMap<>();

                // Create source and destination data
                source.put("method", transferBundle.getStringExtra(Account.TYPE));
                if(transferBundle.getStringExtra(Account.TYPE).equalsIgnoreCase(TransferClient.BUSINESS)){
                    source.put("business_id", transferBundle.getStringExtra(Account.ID));
                }else{
                    source.put("user_id", transferBundle.getStringExtra(Account.ID));
                }

                destination.put("method", TransferClient.BANK);


                // Set the bank info and account
                bank.put("name", transferBundle.getStringExtra("bank-name"));
                bank.put("branch", transferBundle.getStringExtra("bank-branch"));
                bankAccount.put("name", transferBundle.getStringExtra("bank-account-name"));
                bankAccount.put("number", transferBundle.getStringExtra("bank-account-number"));

                destination.put("bank", bank);
                destination.put("account", bankAccount);

                HashMap<String, Object> payload = new HashMap<>();

                payload.put("amount", amount);
                payload.put("source", source);
                payload.put("destination", destination);


                TransferRequest request = new TransferRequest(payload);
                request.execute(
                        BankTransferConfirmationActivity.this.mAlert.afterTransferRequest()
                );

            }
        });

    }



    private void onBack(){

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BankTransferConfirmationActivity.this.onBackPressed();
            }
        });

    }


}
