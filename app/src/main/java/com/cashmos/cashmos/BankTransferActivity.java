package com.cashmos.cashmos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cashmos.cashmos.contracts.Account;

public class BankTransferActivity extends AppCompatActivity {

    private EditText mTransferAmount;
    private EditText mBankName;
    private EditText mBankBranch;
    private EditText mBankAccountName;
    private EditText mBankAccountNumber;
    private Button mNext;
    private Button mBack;
    private Intent accountBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_transfer);


        TextView mAvailableAmount = (TextView) findViewById(R.id.txtAvailableBalance);
        TextView mAccountName = (TextView) findViewById(R.id.txtAccountName);
        mTransferAmount = (EditText) findViewById(R.id.edtTransferAmount);
        mBankName = (EditText)findViewById(R.id.edtBankName);
        mBankBranch = (EditText) findViewById(R.id.edtBankBranch);
        mBankAccountName = (EditText) findViewById(R.id.edtAccountName);
        mBankAccountNumber = (EditText) findViewById(R.id.edtAccountNumber);
        mNext = (Button) findViewById(R.id.btnNext);
        mBack = (Button) findViewById(R.id.btnBack);


        accountBundle = getIntent();

        // Set account info in transfer header
        mAvailableAmount.setText(getString(R.string.transfer_available_balance, accountBundle.getDoubleExtra(
                Account.AVAILABLE_BALANCE,
                0
        )));
        mAccountName.setText(accountBundle.getStringExtra(Account.NAME));

        // Set the hint of the amount field using the available balance
        mTransferAmount.setHint(getString(R.string.transfer_amount_max,
                accountBundle.getDoubleExtra(Account.AVAILABLE_BALANCE, 0)));


        // Registers action for next button
        onNext();

        // Registers action for back button
        onBack();

    }


    private void onNext(){

        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(BankTransferActivity.this.isTransferDataValid()){

                    // Transfer data is valid,
                    // We send user to confirmation screen
                    Intent intent = new Intent(BankTransferActivity.this, BankTransferConfirmationActivity.class);
                    intent.putExtras(accountBundle);

                    intent.putExtra("amount", Double.parseDouble(mTransferAmount.getText().toString()));
                    intent.putExtra("bank-name", mBankName.getText().toString());
                    intent.putExtra("bank-branch", mBankBranch.getText().toString());
                    intent.putExtra("bank-account-name", mBankAccountName.getText().toString());
                    intent.putExtra("bank-account-number", mBankAccountNumber.getText().toString());

                    BankTransferActivity.this.startActivity(intent);
                }

            }
        });

    }



    private void onBack(){

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BankTransferActivity.this.onBackPressed();
            }
        });

    }


    private boolean isTransferDataValid(){
        return true;
    }


}
