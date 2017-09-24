package com.cashmos.cashmos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cashmos.cashmos.contracts.Account;
import com.cashmos.cashmos.contracts.MobileMoney;

public class MobileMoneyTransferActivity extends AppCompatActivity {

    private EditText mTransferAmount;
    private EditText mMobileAccountName;
    private EditText mMobileNumber;
    private Spinner mTransferNetworks;
    private TextView mAvailableBalance;
    private TextView mAccountName;
    private Intent accountBundle;
    private Button mNext;
    private Button mCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_money_transfer);

        // We fetch the account's information
        // sent along with the intent
        accountBundle = getIntent();

        // We initialize views
        mTransferAmount = (EditText) findViewById(R.id.edtTransferAmount);
        mMobileAccountName = (EditText) findViewById(R.id.edtMobileName);
        mMobileNumber = (EditText) findViewById(R.id.edtMobileNumber);
        mTransferNetworks = (Spinner) findViewById(R.id.spnMobileNetworks);
        mAvailableBalance = (TextView) findViewById(R.id.txtAvailableBalance);
        mAccountName = (TextView) findViewById(R.id.txtAccountName);
        mNext = (Button) findViewById(R.id.btnNext);
        mCancel = (Button) findViewById(R.id.btnCancel);


        // Set values
        mAvailableBalance.setText(getString(R.string.transfer_available_balance,
                accountBundle.getDoubleExtra(Account.AVAILABLE_BALANCE, 0.0)));
        mTransferAmount.setHint(getString(R.string.transfer_amount_max,
                accountBundle.getDoubleExtra(Account.AVAILABLE_BALANCE, 0.0)));
        mAccountName.setText(accountBundle.getStringExtra(Account.NAME));

        // We populate the mobile money networks
        populateMobileNetworks();

        // We register action buttons click listeners

        // Going next
        onNext();

        // When user cancels
        onTransferCancelled();

    }


    private void onNext(){

        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MobileMoneyTransferActivity.this.isTransferDataValid()){

                    // The user has provided valid data
                    // We gather the account bundle
                    // and take them to the transfer confirmation page
                    Intent intent = new Intent(
                            MobileMoneyTransferActivity.this,
                            MobileMoneyTransferConfirmationActivity.class
                    );
                    intent.putExtras(accountBundle);

                    intent.putExtra("amount", Double.parseDouble(mTransferAmount.getText().toString()));
                    intent.putExtra("network", MobileMoneyTransferActivity.this.getMobileNetwork());
                    intent.putExtra("network_name", (String) mTransferNetworks.getSelectedItem());
                    intent.putExtra("number", mMobileNumber.getText().toString());
                    intent.putExtra("name", mMobileAccountName.getText().toString());

                    MobileMoneyTransferActivity.this.startActivity(intent);
                }
            }
        });

    }


    private String getMobileNetwork(){

        String network = null;

        switch (mTransferNetworks.getSelectedItemPosition()){
            case 1:
                network = MobileMoney.NETWORK_MTN;
                break;
            case 2:
                network = MobileMoney.NETWORK_VODAFONE;
                break;
            case 3:
                network = MobileMoney.NETWORK_TIGO;
                break;
            default:
                network = MobileMoney.NETWORK_AIRTEL;
        }

        return network;
    }


    private boolean isTransferDataValid(){

        return true; // TODO Build validation logic

    }

    private void onTransferCancelled(){

        // We send the user back to the account Activity


    }


    private void showError(String error){
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }


    private void populateMobileNetworks(){

        ArrayAdapter<String> adapter = new ArrayAdapter<>(MobileMoneyTransferActivity.this,
                android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.mobile_networks));

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mTransferNetworks.setAdapter(adapter);
    }


}
