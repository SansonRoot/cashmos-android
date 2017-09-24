package com.cashmos.cashmos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cashmos.cashmos.adapters.DashboardAccountAdapter;
import com.cashmos.cashmos.adapters.TransactionsAdapter;
import com.cashmos.cashmos.contracts.Account;
import com.cashmos.cashmos.http.requests.AccountsRequest;
import com.cashmos.cashmos.http.requests.Auth;
import com.cashmos.cashmos.http.requests.RecentTransactionsRequest;
import com.cashmos.cashmos.models.Transaction;
import com.cashmos.cashmos.models.UserAccountsBundle;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardActivity extends AppCompatActivity {


    private ProgressBar accountsProgressBar;
    private ProgressBar transactionsProgressBar;
    private ListView accountsListView;
    private ListView transactionsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // We welcome the user
        welcomeUser();

        // We get references to views
        accountsProgressBar = (ProgressBar) findViewById(R.id.progress_bar_accounts);
        accountsProgressBar.setVisibility(ProgressBar.VISIBLE);
        accountsListView = (ListView) findViewById(R.id.lstAccounts);

        transactionsProgressBar = (ProgressBar) findViewById(R.id.progress_bar_transactions);
        transactionsProgressBar.setVisibility(ProgressBar.VISIBLE);
        transactionsListView = (ListView) findViewById(R.id.lstTransactions);

        // We get user's accounts
        loadAccounts();

        // We get recent transactions
        loadRecentTransactions();

    }


    private void welcomeUser(){
        final TextView welcome = (TextView) findViewById(R.id.txtWelcome);
        welcome.setText(getString(R.string.welcome, Auth.getUser().getFirstName()));
    }


    private void loadAccounts(){

        AccountsRequest request = new AccountsRequest();
        request.execute(new Callback<UserAccountsBundle>() {
            @Override
            public void onResponse(Call<UserAccountsBundle> call, Response<UserAccountsBundle> response) {

                if(response.isSuccessful()){

                    accountsProgressBar.setVisibility(ProgressBar.INVISIBLE);

                    // We make the user's personal account the
                    // first item in the list

                    // We have user's accounts so we display them
                    DashboardActivity.this.populateAccounts(response.body().getAccounts());
                }else{
                    // TODO The request was not successful, we should let the user know
                    Toast.makeText(DashboardActivity.this, response.code()+"", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<UserAccountsBundle> call, Throwable t) {


            }
        });

    }


    private void populateAccounts(List<Account> accounts){
        DashboardAccountAdapter adapter = new DashboardAccountAdapter(accounts, this);
        accountsListView.setAdapter(adapter);
    }


    private void loadRecentTransactions(){

        RecentTransactionsRequest request = new RecentTransactionsRequest();

        request.execute(new Callback<List<Transaction>>() {
            @Override
            public void onResponse(Call<List<Transaction>> call, Response<List<Transaction>> response) {

                transactionsProgressBar.setVisibility(ProgressBar.INVISIBLE);
                
                if(response.isSuccessful()){
                    DashboardActivity.this.populateRecentTransactions(response.body());
                }

            }

            @Override
            public void onFailure(Call<List<Transaction>> call, Throwable t) {

            }
        });

    }


    private  void populateRecentTransactions(List<Transaction> transactions){

        TransactionsAdapter adapter = new TransactionsAdapter(transactions, this);
        transactionsListView.setAdapter(adapter);

    }


}
