package com.cashmos.cashmos.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cashmos.cashmos.AccountActivity;
import com.cashmos.cashmos.R;
import com.cashmos.cashmos.contracts.Account;

import java.util.List;

/**
 * Created by brightantwiboasiako on 9/4/17.
 */

public class DashboardAccountAdapter extends BaseAdapter {

    private List<Account> accounts;
    private Context context;

    public DashboardAccountAdapter(List<Account> accounts, Context context){
        this.accounts = accounts;
        this.context = context;
    }

    @Override
    public int getCount() {
        return accounts.size();
    }

    @Override
    public Account getItem(int position) {
        return accounts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        AccountHolder holder = null;

        if(row == null){
            // This is the User account
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.dashboard_account, parent, false);
            holder = new AccountHolder(row);
            row.setTag(holder);
        }else{
            holder = (AccountHolder) row.getTag();
        }

        holder.accountName.setText(getItem(position).getName());
        holder.accountBalance.setText(context.getString(R.string.money, getItem(position).getAvailableBalance()));

        // Register on-click listener for the account
        row.setOnClickListener(onAccountClickHandler(position));

        return row;

    }



    private View.OnClickListener onAccountClickHandler(int positon){

        final Account account = getItem(positon);

        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // We take the user to the Account Activity
                Intent intent = new Intent(DashboardAccountAdapter.this.context, AccountActivity.class);
                intent.putExtra(Account.NAME, account.getName());
                intent.putExtra(Account.BALANCE, account.getBalance());
                intent.putExtra(Account.AVAILABLE_BALANCE, account.getAvailableBalance());
                intent.putExtra(Account.PROFILE_ID, account.getProfileId());
                intent.putExtra(Account.ID, account.getId());
                intent.putExtra(Account.TYPE, account.getType());

                DashboardAccountAdapter.this.context.startActivity(intent);

            }
        };

    }



    private class AccountHolder{

        TextView accountName;
        TextView accountBalance;

        AccountHolder(View v){
            accountName = (TextView) v.findViewById(R.id.txtAccountName);
            accountBalance = (TextView) v.findViewById(R.id.txtAccountBalance);
        }

    }

}
