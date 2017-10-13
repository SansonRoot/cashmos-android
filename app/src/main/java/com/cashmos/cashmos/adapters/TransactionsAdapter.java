package com.cashmos.cashmos.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cashmos.cashmos.R;
import com.cashmos.cashmos.models.Transaction;

import java.util.List;

/**
 * Created by brightantwiboasiako on 9/5/17.
 */

public class TransactionsAdapter extends BaseAdapter {


    private List<Transaction> transactions;
    private Context context;


    public TransactionsAdapter(List<Transaction> transactions, Context context){
        this.transactions = transactions;
        this.context = context;
    }


    @Override
    public int getCount() {
        return transactions.size();
    }

    @Override
    public Transaction getItem(int position) {
        return transactions.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        TransactionHolder holder = null;

        if(row == null){

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.transaction, parent, false);
            holder = new TransactionHolder(row);
            row.setTag(holder);
        }else{
            holder = (TransactionHolder) row.getTag();
        }

        setData(holder, position);

        row.setOnClickListener(
                new OnTransactionClicked(position)
        );

        return row;
    }



    private void showDetails(int position){

        // Create an alert dialog and show it
        AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(context);
        View mDialogView = ((LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                            .inflate(R.layout.single_transaction_dialog, null);

        TextView mMerchantName = (TextView) mDialogView.findViewById(R.id.txtMerchantName);
        TextView mTransactionAmount = (TextView) mDialogView.findViewById(R.id.txtTransactionAmount);
        TextView mTransactionDate = (TextView) mDialogView.findViewById(R.id.txtDate);
        TextView mDescription = (TextView) mDialogView.findViewById(R.id.txtDescription);
        TextView mFee = (TextView) mDialogView.findViewById(R.id.txtFee);
        TextView mStatus = (TextView) mDialogView.findViewById(R.id.txtStatus);

        mMerchantName.setText(getItem(position).getMerchantName());
        mTransactionAmount.setText(
                context.getString(R.string.amount, getItem(position).getAmount())
        );
        mTransactionDate.setText(getItem(position).getDate());
        mDescription.setText(getItem(position).getDescription());
        mFee.setText(
                context.getString(R.string.transaction_fee, getItem(position).getFee())
        );
        mStatus.setText(getItem(position).getStatus());

        if(getItem(position).isComplete()){
            setColor(mStatus, true);
        }else{
            setColor(mStatus, false);
        }

        mDialogBuilder.setView(mDialogView);

        AlertDialog dialog = mDialogBuilder.create();
        dialog.show();


    }



    private class OnTransactionClicked implements View.OnClickListener{

        private int position;

        OnTransactionClicked(int position){
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            TransactionsAdapter.this.showDetails(position);
        }
    }


    private void setData(TransactionHolder holder, int position){

        holder.merchantName.setText(getItem(position).getMerchantName());
        holder.amount.setText(context.getString(R.string.money, getItem(position).getAmount()));
        holder.balance.setText(context.getString(R.string.money, getItem(position).getBalance()));
        holder.description.setText(getItem(position).getDescription());
        holder.status.setText(getItem(position).getStatus());
        holder.date.setText(getItem(position).getDate());
        holder.mTransaction.setImageResource(getTransactionIcon(position));

        if(getItem(position).isComplete()){
            setColor(holder.status, true);
        }else{
            setColor(holder.status, false);
        }

        // We set the text color of the amount
        // based on whether it's a credit or debit transaction
        if(getItem(position).isCredit()){
            setColor(holder.amount, true);
        }else{
            setColor(holder.amount, false);
        }

    }



    private void setColor(TextView view, boolean isSuccess){

        int color = R.color.success;

        if(!isSuccess)
            color = R.color.danger;
        view.setTextColor(ContextCompat.getColor(context, color));
    }



    private int getTransactionIcon(int position){
        if(getItem(position).getDescription().contains("Online")){
            return R.drawable.online;
        }else if(getItem(position).getDescription().contains("Bank")){
            return R.drawable.bank;
        }else{
            return R.drawable.mobile;
        }
    }


    private class TransactionHolder{

        private TextView merchantName;
        private TextView amount;
        private TextView description;
        private TextView status;
        private TextView balance;
        private TextView date;
        private ImageView mTransaction;

        TransactionHolder(View v){
            merchantName = (TextView) v.findViewById(R.id.txtMerchantName);
            amount = (TextView) v.findViewById(R.id.txtAmount);
            balance = (TextView) v.findViewById(R.id.txtBalance);
            description = (TextView) v.findViewById(R.id.txtDescription);
            status = (TextView) v.findViewById(R.id.txtStatus);
            date = (TextView) v.findViewById(R.id.txtDate);
            mTransaction = (ImageView) v.findViewById(R.id.imgTransactionIcon);
        }

    }


}
