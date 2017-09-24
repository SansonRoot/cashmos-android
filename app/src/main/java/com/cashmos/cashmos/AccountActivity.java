package com.cashmos.cashmos;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.cashmos.cashmos.adapters.TransactionsAdapter;
import com.cashmos.cashmos.contracts.Account;
import com.cashmos.cashmos.contracts.TransferClient;
import com.cashmos.cashmos.http.requests.BusinessTransactionsRequest;
import com.cashmos.cashmos.http.requests.UserTransactionsRequest;
import com.cashmos.cashmos.models.Paginator;
import com.cashmos.cashmos.models.Transaction;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountActivity extends AppCompatActivity {

    private String name;
    private String profileId;
    private double availableBalance;
    private String id;
    private String type;

    private ListView mTransactions;
    private ProgressBar mTransactionsProgressBar;
    private Paginator paginator;
    private Button mTransferButton;
    private Button mAddMoneyButton;
    private AlertDialog mTransferAlert;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        // Get the Account information from the intent
        populateAccountInfo(getIntent());

        // We set the views

        TextView mBalance = (TextView) findViewById(R.id.txtAvailableBalance);
        TextView mName = (TextView) findViewById(R.id.txtAccountName);
        mTransactions = (ListView) findViewById(R.id.lstTransactions);
        mTransferButton = (Button) findViewById(R.id.btnMakeTransfer);
        mAddMoneyButton = (Button) findViewById(R.id.btnAddMoney);
        mTransactionsProgressBar = (ProgressBar)findViewById(R.id.progress_bar_transactions);

        mBalance.setText(getString(R.string.amount, availableBalance));
        mName.setText(name);


        mTransferButton.setOnClickListener(makeTransferClickListener());


        // We fetch the transactions for the account
        fetchAccountTransactions();

    }


    private View.OnClickListener makeTransferClickListener(){

        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // We prepare the alert box and show it
                // But we only create the whole thing if we haven't already
                if(mTransferAlert == null){

                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(AccountActivity.this);
                    View mDialogView = getLayoutInflater().inflate(R.layout.transfer_dialog, null);

                    Button mMobileMoney = (Button) mDialogView.findViewById(R.id.btnMobileMoney);
                    Button mBankAccount = (Button) mDialogView.findViewById(R.id.btnBank);
                    Button mCashmosAccount = (Button) mDialogView.findViewById(R.id.btnCashmos);

                    // Bind click listeners to buttons
                    setTransferTypeClickListener(mMobileMoney, TransferClient.MOBILE_MONEY);
                    setTransferTypeClickListener(mBankAccount, TransferClient.BANK);

                    if(AccountActivity.this.isBusinessAccount()){
                        setTransferTypeClickListener(mCashmosAccount, TransferClient.BUSINESS);
                    }else{
                        setTransferTypeClickListener(mCashmosAccount, TransferClient.USER);
                    }

                    // Add buttons to builder
                    mBuilder.setView(mDialogView);

                    // We create the alert dialog
                    mTransferAlert = mBuilder.create();

                }

                // We show the alert
                mTransferAlert.show();

            }
        };

    }


    private void setTransferTypeClickListener(Button button, final String transferType){

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = null;

                switch (transferType){

                    case TransferClient.MOBILE_MONEY:
                        // Mobile money
                        intent = new Intent(AccountActivity.this, MobileMoneyTransferActivity.class);
                        break;
                    case TransferClient.BANK:
                        // Bank Transfer
                        intent = new Intent(AccountActivity.this, BankTransferActivity.class);
                        break;
                    default:
                        // Cashmos account (user/business)
                        intent = new Intent(AccountActivity.this, CashmosTransferActivity.class);

                }

                // We add necessary fields to the intent
                intent.putExtra(Account.ID, id);
                intent.putExtra(Account.PROFILE_ID, profileId);
                intent.putExtra(Account.NAME, name);
                intent.putExtra(Account.AVAILABLE_BALANCE, availableBalance);
                // We add an indicator to specify that the
                // account is either business or user account
                intent.putExtra(Account.TYPE, type);

                // Open up the applicable transfer activity
                startActivity(intent);
            }
        });

    }


    private void populateAccountInfo(Intent intent){
        name = intent.getStringExtra(Account.NAME);
        profileId = intent.getStringExtra(Account.PROFILE_ID);
        availableBalance = intent.getDoubleExtra(Account.AVAILABLE_BALANCE, 0.0);
        id = intent.getStringExtra(Account.ID);
        type = intent.getStringExtra(Account.TYPE);

    }


    private void fetchAccountTransactions(){

        if(isBusinessAccount()){
            // Fetch business tractions
            businessTransactions();
        }else{
            // Fetch personal account transactions
            personalAccountTransactions();
        }

    }


    private boolean isBusinessAccount(){
        return com.cashmos.cashmos.models.Account.isBusinessId(profileId);
    }


    private void personalAccountTransactions(){

        final UserTransactionsRequest request = new UserTransactionsRequest();
        request.execute(new Callback<Paginator>() {
            @Override
            public void onResponse(Call<Paginator> call, Response<Paginator> response) {

                if(response.isSuccessful()){
                    AccountActivity.this.paginator = response.body();
                    AccountActivity.this.populateTransactions();
                }

            }

            @Override
            public void onFailure(Call<Paginator> call, Throwable t) {

            }
        });

    }


    private void businessTransactions(){

        final BusinessTransactionsRequest request = new BusinessTransactionsRequest(id);
        request.execute(new Callback<Paginator>() {
            @Override
            public void onResponse(Call<Paginator> call, Response<Paginator> response) {

                if(response.isSuccessful()){
                    AccountActivity.this.paginator = response.body();
                    AccountActivity.this.populateTransactions();
                }

            }

            @Override
            public void onFailure(Call<Paginator> call, Throwable t) {

            }
        });

    }


    private void populateTransactions(){

        mTransactionsProgressBar.setVisibility(ProgressBar.INVISIBLE);

        List<Transaction> transactions = Arrays.asList(paginator.getTransactions());
        TransactionsAdapter adapter = new TransactionsAdapter(transactions, this);
        mTransactions.setAdapter(adapter);

    }

    @Override
    protected void onDestroy() {

        // Close alert box
        if(mTransferAlert != null){
            mTransferAlert.dismiss();
        }

        super.onDestroy();
    }
}
